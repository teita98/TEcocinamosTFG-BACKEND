-- =========================================
-- SCHEMA: db-extra.sql
-- =========================================
USE tecocinamos;

-- =========================================
-- 1) ÍNDICES
-- =========================================
-- acelera búsquedas como SELECT * FROM usuario WHERE nombre LIKE 'María%' o joins sobre usuario.nombre
CREATE INDEX idx_usuario_nombre ON usuario(nombre);

-- acelera filtrados de pedidos por su estado, p. ej. SELECT … FROM pedido WHERE estado_id = 3.
CREATE INDEX idx_pedido_estado_id ON pedido(estado_id);

-- acelera cuándo buscas todos los platos de una categoría concreta, p. ej. SELECT … FROM plato WHERE categoria_id = 2.
CREATE INDEX idx_plato_categoria_id ON plato(categoria_id);

-- acelera los joins entre plato y plato_ingrediente, p. ej. al obtener rápidamente todos los ingredientes de un plato o viceversa.
CREATE INDEX idx_plato_ingrediente ON plato_ingrediente(plato_id, ingrediente_id);


-- =========================================
-- 2) FUNCIONES
-- =========================================
DELIMITER //

CREATE FUNCTION calcular_total_pedido(pedidoId INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
  DECLARE total DECIMAL(10,2);
  SELECT SUM((p.precio - d.descuento) * d.cantidad_plato)
    INTO total
    FROM detalles_pedido d
    JOIN plato p ON p.plato_id = d.plato_id
    WHERE d.pedido_id = pedidoId;
  RETURN IFNULL(total, 0.00);
END;
//

CREATE FUNCTION contar_platos_pedido(pedidoId INT)
RETURNS INT
DETERMINISTIC
RETURN (
  SELECT SUM(cantidad_plato)
  FROM detalles_pedido
  WHERE pedido_id = pedidoId
);
//

CREATE FUNCTION es_admin(usuarioId INT)
RETURNS BOOLEAN
DETERMINISTIC
RETURN (
  SELECT (r.nombre_rol = 'ADMIN')
  FROM usuario u
  JOIN rol r ON u.rol_id = r.rol_id
  WHERE u.usuario_id = usuarioId
);
//

DELIMITER ;


-- =========================================
-- 3) PROCEDIMIENTOS ALMACENADOS
-- =========================================
DELIMITER $$

CREATE PROCEDURE cancelar_pedido(
  IN p_pedido_id INT,
  IN p_admin_id   INT
)
BEGIN
  DECLARE v_estado_actual VARCHAR(50);
  DECLARE v_count INT;

  -- 1) Verificar que existe el pedido
  SELECT COUNT(*) INTO v_count
  FROM pedido
  WHERE pedido_id = p_pedido_id;
  IF v_count = 0 THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Pedido no encontrado';
  END IF;

  -- 2) Obtener estado actual del pedido
  SELECT e.nombre_estado INTO v_estado_actual
  FROM pedido pe
  JOIN estado e ON pe.estado_id = e.estado_id
  WHERE pe.pedido_id = p_pedido_id;

  -- 3) Cambiar estado a 'Cancelado'
  UPDATE pedido pe
  JOIN estado e2 ON e2.nombre_estado = 'Cancelado'
  SET pe.estado_id = e2.estado_id,
      pe.fecha_actualizacion = NOW(),
      pe.total = 0
  WHERE pe.pedido_id = p_pedido_id;

  -- 4) Restaurar stock de cada plato del pedido
  UPDATE plato pl
  JOIN detalles_pedido dp ON pl.plato_id = dp.plato_id
  SET pl.stock = pl.stock + dp.cantidad_plato
  WHERE dp.pedido_id = p_pedido_id;

  -- 5) Insertar registro en log_auditoria
  INSERT INTO log_auditoria (
    entidad, campo_modificado, valor_anterior,
    valor_nuevo, accion, usuario_admin_id
  )
  VALUES (
    'Pedido',
    'estado',
    v_estado_actual,
    'Cancelado',
    CONCAT('Cancelado pedido ', p_pedido_id),
    p_admin_id
  );
END$$

DELIMITER ;


-- =========================================
-- 4) TRIGGERS
-- =========================================

-- 4.1) Forzar email en minúsculas
DELIMITER //
CREATE TRIGGER trg_email_minusculas
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
  SET NEW.email = LOWER(NEW.email);
END;
//
DELIMITER ;


-- 4.2) Validar que fecha_entrega > fecha_creado
DELIMITER //
CREATE TRIGGER validar_fecha_entrega
BEFORE INSERT ON pedido
FOR EACH ROW
BEGIN
  IF NEW.fecha_entrega <= IFNULL(NEW.fecha_creado, CURDATE()) THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'La fecha de entrega debe ser posterior a la fecha de creación del pedido';
  END IF;
END;
//
DELIMITER ;


-- 4.3) Rellenar fecha_creado automáticamente
DELIMITER //
CREATE TRIGGER set_fecha_creacion
BEFORE INSERT ON pedido
FOR EACH ROW
BEGIN
  SET NEW.fecha_creado = IFNULL(NEW.fecha_creado, CURDATE());
END;
//
DELIMITER ;


-- 4.4) Verificar stock antes de insertar un detalle de pedido
DELIMITER //
CREATE TRIGGER check_stock
BEFORE INSERT ON detalles_pedido
FOR EACH ROW
BEGIN
  DECLARE disponible INT;
  SELECT stock INTO disponible
  FROM plato
  WHERE plato_id = NEW.plato_id;

  IF disponible < NEW.cantidad_plato THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Stock insuficiente para el plato';
  END IF;
END;
//
DELIMITER ;


-- 4.5) Descontar stock tras insertar un detalle de pedido
DELIMITER //
CREATE TRIGGER descontar_stock
AFTER INSERT ON detalles_pedido
FOR EACH ROW
BEGIN
  UPDATE plato
  SET stock = stock - NEW.cantidad_plato
  WHERE plato_id = NEW.plato_id;
END;
//
DELIMITER ;


-- 4.6) Registrar cambios de estado de pedido
DELIMITER //
CREATE TRIGGER trg_log_estado_pedido
AFTER UPDATE ON pedido
FOR EACH ROW
BEGIN
  IF OLD.estado_id <> NEW.estado_id THEN
    INSERT INTO log_auditoria (
      entidad, entidad_id, campo_modificado,
      valor_anterior, valor_nuevo, accion, usuario_admin_id
    )
    VALUES (
      'Pedido',
      OLD.pedido_id,
      'estado_id',
      OLD.estado_id,
      NEW.estado_id,
      'Cambio de estado',
      NULL
    );
  END IF;
END;
//
DELIMITER ;


-- 4.7) Registrar cambio de precio en plato
DELIMITER //
CREATE TRIGGER log_cambio_precio
AFTER UPDATE ON plato
FOR EACH ROW
BEGIN
  IF OLD.precio <> NEW.precio THEN
    INSERT INTO log_auditoria (
      entidad, campo_modificado, valor_anterior,
      valor_nuevo, usuario_admin_id
    )
    VALUES (
      'Plato',
      'precio',
      OLD.precio,
      NEW.precio,
      1  -- aquí podrías usar NULL o un ID de admin fijo, o bien cambiar la lógica para pasar el ID por la aplicación
    );
  END IF;
END;
//
DELIMITER ;


-- 4.8) Actualizar pedido.total después de INSERT en detalles_pedido
DELIMITER $$
CREATE TRIGGER trg_detalle_insert
AFTER INSERT ON detalles_pedido
FOR EACH ROW
BEGIN
  DECLARE nuevo_total DECIMAL(10,2);

  SELECT IFNULL(SUM(p.precio * dp.cantidad_plato - dp.descuento), 0)
    INTO nuevo_total
  FROM detalles_pedido dp
  JOIN plato p ON dp.plato_id = p.plato_id
  WHERE dp.pedido_id = NEW.pedido_id;

  UPDATE pedido
  SET total = nuevo_total
  WHERE pedido_id = NEW.pedido_id;
END$$

-- 4.9) Actualizar pedido.total después de DELETE en detalles_pedido
CREATE TRIGGER trg_detalle_delete
AFTER DELETE ON detalles_pedido
FOR EACH ROW
BEGIN
  DECLARE nuevo_total DECIMAL(10,2);

  SELECT IFNULL(SUM(p.precio * dp.cantidad_plato - dp.descuento), 0)
    INTO nuevo_total
  FROM detalles_pedido dp
  JOIN plato p ON dp.plato_id = p.plato_id
  WHERE dp.pedido_id = OLD.pedido_id;

  UPDATE pedido
  SET total = nuevo_total
  WHERE pedido_id = OLD.pedido_id;
END$$

DELIMITER ;

DELIMITER //

CREATE TRIGGER trg_anonimizar_usuario
BEFORE UPDATE ON usuario
FOR EACH ROW
BEGIN
  -- Si marcan eliminado=TRUE, anonimizar campos sensibles
  IF NEW.eliminado = TRUE AND OLD.eliminado = FALSE THEN
    SET NEW.nombre = CONCAT('ANON_', OLD.usuario_id);
    SET NEW.email  = CONCAT('anon_', OLD.usuario_id, '@tecocinamos.local');
    SET NEW.telefono = NULL;
    SET NEW.direccion = NULL;
    SET NEW.fecha_eliminado = CURDATE();
  END IF;
END;
//

DELIMITER ;
