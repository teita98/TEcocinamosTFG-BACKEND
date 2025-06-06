-- ================================================
--   Script: tecocinamos_schema.sql
--   Descripción: Crea la base de datos y todas las tablas
--                necesarias para el proyecto "Tecocinamos".
-- ================================================

DROP DATABASE IF EXISTS tecocinamos;
CREATE DATABASE tecocinamos CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE tecocinamos;


-- ================================================
-- 1) Tabla rol
-- ================================================
CREATE TABLE rol (
  rol_id INT AUTO_INCREMENT PRIMARY KEY,
  nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

-- ================================================
-- 2) Tabla usuario
-- ================================================
CREATE TABLE usuario (
  usuario_id INT AUTO_INCREMENT PRIMARY KEY,
  rol_id INT NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL UNIQUE,
  contrasena VARCHAR(100) NOT NULL,
  telefono VARCHAR(20),
  direccion VARCHAR(200),
  eliminado BOOLEAN DEFAULT FALSE,
  fecha_eliminado DATE,
  FOREIGN KEY (rol_id) REFERENCES rol(rol_id)
);

-- ================================================
-- 3) Tabla estado
-- ================================================
CREATE TABLE estado (
  estado_id INT AUTO_INCREMENT PRIMARY KEY,
  nombre_estado VARCHAR(50) NOT NULL UNIQUE
);

-- ================================================
-- 4) Tabla pedido
-- ================================================
CREATE TABLE pedido (
  pedido_id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  estado_id INT NOT NULL,
  fecha_creado DATE NOT NULL,
  fecha_entrega DATE NOT NULL,
  direccion_entrega VARCHAR(200) NOT NULL,
  fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id),
  FOREIGN KEY (estado_id) REFERENCES estado(estado_id)
);

-- ================================================
-- 5) Tabla categoria
-- ================================================
CREATE TABLE categoria (
  categoria_id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE
);

-- ================================================
-- 6) Tabla plato
-- ================================================
CREATE TABLE plato (
  plato_id INT AUTO_INCREMENT PRIMARY KEY,
  nombre_plato VARCHAR(50) NOT NULL,
  cantidad VARCHAR(50),
  precio DECIMAL(10,2) NOT NULL,
  stock INT DEFAULT 0,
  preparacion_casa VARCHAR(500),
  recomendaciones VARCHAR(500),
  image_base_name VARCHAR(100),
  fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  categoria_id INT NOT NULL,
  FOREIGN KEY (categoria_id) REFERENCES categoria(categoria_id)
);

-- ================================================
-- 7) Tabla proveedor
-- ================================================
CREATE TABLE proveedor (
  proveedor_id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL UNIQUE,
  contacto VARCHAR(100),
  telefono VARCHAR(20),
  email VARCHAR(100)
);

-- ================================================
-- 8) Tabla ingrediente
-- ================================================
CREATE TABLE ingrediente (
  ingrediente_id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  categoria VARCHAR(50),
  proveedor_id INT,
  cantidad_envase DECIMAL(6,2) NOT NULL,
  unidad_envase VARCHAR(20) NOT NULL,
  precio_envase DECIMAL(10,2) NOT NULL,
  precio_unitario DECIMAL(10,4),
  unidad VARCHAR(20),
  FOREIGN KEY (proveedor_id) REFERENCES proveedor(proveedor_id)
);

-- ================================================
-- 9) Tabla alergenos
-- ================================================
CREATE TABLE alergenos (
  alergeno_id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE
);

-- ================================================
-- 10) Tabla ingrediente_alergeno (N:M entre ingrediente y alergeno)
-- ================================================
CREATE TABLE ingrediente_alergeno (
  id INT AUTO_INCREMENT PRIMARY KEY,
  ingrediente_id INT NOT NULL,
  alergeno_id INT NOT NULL,
  FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(ingrediente_id),
  FOREIGN KEY (alergeno_id) REFERENCES alergenos(alergeno_id),
  UNIQUE KEY (ingrediente_id, alergeno_id)
);

-- ================================================
-- 11) Tabla plato_ingrediente (N:M entre plato e ingrediente con cantidad usada)
-- ================================================
CREATE TABLE plato_ingrediente (
  id INT AUTO_INCREMENT PRIMARY KEY,
  plato_id INT NOT NULL,
  ingrediente_id INT NOT NULL,
  cantidad_usada DECIMAL(6,2),
  unidad VARCHAR(20),
  FOREIGN KEY (plato_id) REFERENCES plato(plato_id),
  FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(ingrediente_id),
  UNIQUE KEY (plato_id, ingrediente_id)
);

-- ================================================
-- 12) Tabla detalles_pedido (cada línea de pedido: plato + cantidad)
-- ================================================
CREATE TABLE detalles_pedido (
  detalles_pedido_id INT AUTO_INCREMENT PRIMARY KEY,
  pedido_id INT NOT NULL,
  plato_id INT NOT NULL,
  cantidad_plato INT NOT NULL,
  descuento DECIMAL(5,2) DEFAULT 0.00,
  FOREIGN KEY (pedido_id) REFERENCES pedido(pedido_id) ON DELETE CASCADE,
  FOREIGN KEY (plato_id) REFERENCES plato(plato_id)
);

-- ================================================
-- 13) Tabla log_auditoria
-- ================================================
CREATE TABLE log_auditoria (
  log_id INT AUTO_INCREMENT PRIMARY KEY,
  entidad VARCHAR(50),
  entidad_id INT,
  campo_modificado VARCHAR(50),
  valor_anterior VARCHAR(100),
  valor_nuevo VARCHAR(100),
  fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  accion VARCHAR(100),
  usuario_admin_id INT,
  FOREIGN KEY (usuario_admin_id) REFERENCES usuario(usuario_id)
);