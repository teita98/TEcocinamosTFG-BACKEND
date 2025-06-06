-- ================================================
--   Script: tecocinamos_seed.sql
--   Descripción: Inserta datos de referencia en cada tabla.
-- ================================================

USE tecocinamos;

-- ------------------------------------------------
-- 1) ROLES
-- ------------------------------------------------
INSERT INTO rol (nombre_rol) VALUES 
('ADMIN'),
('CLIENTE');

-- ------------------------------------------------
-- 2) USUARIOS
--    - Creamos un administrador y dos clientes de prueba.
--    - Asumimos que las contraseñas ya están hasheadas con BCrypt.
--      Aquí se muestran como texto plano para luego re-hashear al cargar.
-- ------------------------------------------------
INSERT INTO usuario (rol_id, nombre, email, contrasena, telefono, direccion, eliminado)
VALUES
(1, 'Teresa', 'tcharlomillan@gmail.com', '$2b$12$1KYKRXdG.reW3EbiXjET2.VAo4TYqJ1Huxz2ubpaNoECdIAYTYYfi', '600000001', 'Calle sevilla 123', FALSE), -- contraseña real: Admin123!
(2, 'Tere', 'teitacharlo98@gmail.com', '$2b$12$UP20tiSrr.wZorFo00crNe1XkY7l6RDyTh7gAsBEeoTkAvHo.AAeS', '600000002', 'Av. finlandia 742', FALSE); -- contraseña real: User123!

-- ------------------------------------------------
-- 3) ESTADOS DE PEDIDO
-- ------------------------------------------------
INSERT INTO estado (nombre_estado) VALUES
('Pendiente'),
('Confirmado'),
('Entregado'),
('Cancelado');

-- ------------------------------------------------
-- 4) CATEGORÍAS DE PLATOS
-- ------------------------------------------------
INSERT INTO categoria (nombre) VALUES
('Aperitivos'),
('Principales'),
('Postres'),
('Mesas');

-- ------------------------------------------------
-- 5) ALÉRGENOS
-- ------------------------------------------------
INSERT INTO alergenos (nombre) VALUES
('GLUTEN'),
('CRUSTACEOS'),
('HUEVO'),
('PESCADO'),
('CACAHUETES'),
('SOJA'),
('LACTEOS'),
('FRUTOS_DE_CASCARA'),
('APIO'),
('MOSTAZA'),
('SESAMO'),
('DIOXIDO_DE_AZUFRE_Y_SULFITOS'),
('ALTRAMUCES'),
('MOLUSCOS');

-- ------------------------------------------------
-- 6) PROVEEDORES
-- ------------------------------------------------
INSERT INTO proveedor (proveedor_id, nombre, contacto, telefono, email) VALUES 
(1, 'Carniceria', 'Contacto Carniceria', '600123000', 'contacto@carniceria.com'),
(2, 'Carrefour', 'Contacto Carrefour', '600123001', 'contacto@carrefour.com'),
(3, 'CASA TRIA', 'Contacto CASA TRIA', '600123002', 'contacto@casatria.com'), 
(4, 'Costco', 'Contacto Costco', '600123003', 'contacto@costco.com'),
(5, 'El jamon', 'Contacto El jamon', '600123004', 'contacto@eljamon.com'),
(6, 'Hiper Oriente 2', 'Contacto Hiper Oriente 2', '600123005', 'contacto@hiperoriente.com'),
(7, 'Lidl', 'Contacto Lidl', '600123006', 'contacto@lidl.com'), 
(8, 'Makro', 'Contacto Makro', '600123007', 'contacto@makro.com'),
(9, 'Mercadona', 'Contacto Mercadona', '600123008', 'contacto@mercadona.com');

-- ------------------------------------------------
-- 7) INGREDIENTES (con stock)
--    - Asumimos que stock es la cantidad disponible en unidad de uso.
--    - precio_envase y cantidad_envase determinados arbitrariamente.
-- ------------------------------------------------
INSERT INTO ingrediente (nombre, categoria, proveedor_id, cantidad_envase, unidad_envase, precio_envase, precio_unitario, unidad) VALUES
('Aceite de girasol', 'Aceites', 2, 5000, 'mililitros', 8.35, 0.0017, 'mililitros'),
('Zumo lima', 'Aceites', 8, 1000, 'mililitros', 2.85, 0.0029, 'mililitros'),
('Aceite tapón verde', 'Aceites', 9, 3000, 'mililitros', 18.30, 0.0061, 'mililitros'),
('Bicarbonato', 'Aceites', 9, 1000, 'gramos', 1.50, 0.0015, 'gramos'),
('Jugo limón', 'Aceites', 9, 280, 'mililitros', 1.00, 0.0036, 'mililitros'),
('Maldón', 'Aceites', 9, 125, 'gramos', 2.00, 0.0160, 'gramos'),
('Sal fina', 'Aceites', 9, 1000, 'gramos', 0.40, 0.0004, 'gramos'),
('Vinagre balsámico', 'Aceites', 9, 250, 'gramos', 1.75, 0.0070, 'gramos'),
('Vinagre de manzana', 'Aceites', 9, 1000, 'mililitros', 0.80, 0.0008, 'mililitros'),
('Tomillo seco', 'Especias', 3, 20, 'gramos', 0.00, 0.0000, 'gramos'),
('Clavo', 'Especias', 7, 25, 'gramos', 1.09, 0.0436, 'gramos'),
('Comino', 'Especias', 7, 45, 'gramos', 0.89, 0.0198, 'gramos'),
('Curcuma', 'Especias', 7, 50, 'gramos', 0.99, 0.0198, 'gramos'),
('Curry', 'Especias', 7, 45, 'gramos', 0.85, 0.0189, 'gramos'),
('Laurel', 'Especias', 7, 7.5, 'gramos', 0.49, 0.0653, 'gramos'),
('Nuez moscada', 'Especias', 7, 45, 'gramos', 1.19, 0.0264, 'gramos'),
('Pimienta', 'Especias', 7, 50, 'gramos', 0.69, 0.0138, 'gramos'),
('Ajo en polvo', 'Especias', 9, 115, 'gramos', 1.14, 0.0099, 'gramos'),
('Anís en grano', 'Especias', 9, 50, 'gramos', 2.00, 0.0400, 'gramos'),
('Cebolla en polvo', 'Especias', 9, 60, 'gramos', 0.94, 0.0157, 'gramos'),
('Cebolla frita crujiente', 'Especias', 9, 150, 'gramos', 1.15, 0.0077, 'gramos'),
('cilantro', 'Especias', 9, 12, 'gramos', 1.85, 0.1542, 'gramos'),
('Eneldo', 'Especias', 9, 18, 'gramos', 1.85, 0.1028, 'gramos'),
('Hierbas provenzales', 'Especias', 9, 25, 'gramos', 1.20, 0.0480, 'gramos'),
('Pimentón de la vera', 'Especias', 9, 75, 'gramos', 1.85, 0.0247, 'gramos'),
('Pimentón dulce', 'Especias', 9, 75, 'gramos', 1.30, 0.0173, 'gramos'),
('Pimentón picante', 'Especias', 9, 60, 'gramos', 1.35, 0.0225, 'gramos'),
('Tabasco', 'Salsas', 3, 1, 'mililitros', 0.00, 0.0000, 'mililitros'),
('Salsa gaucha', 'Salsas', 2, 300, 'mililitros', 2.45, 0.0082, 'mililitros'),
('Salsa Perrins', 'Salsas', 2, 150, 'gramos', 3.59, 0.0239, 'gramos'),
('Siracha', 'Salsas', 2, 215, 'mililitros', 3.49, 0.0162, 'mililitros'),
('Aceite de sésamo', 'Salsas', 6, 500, 'mililitros', 6.49, 0.0130, 'mililitros'),
 ('Salsa de soja', 'Salsas', 6, 1000, 'mililitros', 4.19, 0.0042, 'mililitros'),
('Salsa de ostras (opcional)', 'Salsas', 6, 907, 'gramos', 5.25, 0.0058, 'gramos'),
('Vinagre de arroz', 'Salsas', 6, 200, 'mililitros', 2.65, 0.0133, 'mililitros'),
('Mostaza Dijón', 'Salsas', 7, 185, 'mililitros', 1.15, 0.0062, 'mililitros'),
('Extracto de vainilla', 'Salsas', 9, 150, 'mililitros', 2.00, 0.0133, 'mililitros'),
('Mayonesa Hacenado', 'Salsas', 9, 500, 'mililitros', 1.10, 0.0022, 'mililitros'),
('Miel y mostaza', 'Salsas', 9, 340, 'gramos', 1.45, 0.0043, 'gramos'),
('Mostaza antigua', 'Salsas', 9, 200, 'gramos', 1.70, 0.0085, 'gramos'),
('Mostaza hacendado', 'Salsas', 9, 388, 'gramos', 1.35, 0.0035, 'gramos'),
('Salsa de trufa', 'Salsas', 9, 80, 'gramos', 3.10, 0.0388, 'gramos'),
('Salsa barbacoa', 'Salsas', 9, 350, 'gramos', 1.15, 0.0033, 'gramos'),
('Alcaparras', 'Aceitunas/encurtidos', 7, 69, 'gramos', 0.95, 0.0138, 'gramos'),
('Guindillas', 'Aceitunas/encurtidos', 8, 390, 'gramos', 4.24, 0.0109, 'gramos'),
('Aceitunas jalapeño', 'Aceitunas/encurtidos', 9, 150, 'gramos', 1.65, 0.0110, 'gramos'),
('Pepinillos agridulces', 'Aceitunas/encurtidos', 9, 360, 'gramos', 1.50, 0.0042, 'gramos'),
('Nueces', 'Frutos secos', 9, 150, 'gramos', 1.48, 0.0099, 'gramos'),
('Almendras blancas', 'Frutos secos', 9, 200, 'gramos', 2.65, 0.0133, 'gramos'),
('Almendras fritas', 'Frutos secos', 9, 200, 'gramos', 2.55, 0.0128, 'gramos'),
('Arandanos deshidratados', 'Frutos secos', 9, 200, 'gramos', 2.30, 0.0115, 'gramos'),
('Combinado frutos secos', 'Frutos secos', 9, 200, 'gramos', 2.20, 0.0110, 'gramos'),
('Maiz gigante', 'Frutos secos', 9, 150, 'gramos', 1.40, 0.0093, 'gramos'),
('Pasas sultanas', 'Frutos secos', 9, 250, 'gramos', 1.41, 0.0056, 'gramos'),
('Pipas de calabaza', 'Frutos secos', 9, 150, 'gramos', 1.69, 0.0113, 'gramos'),
('Pistachos', 'Frutos secos', 9, 100, 'gramos', 2.74, 0.0274, 'gramos'),
('Sésamo blanco', 'Frutos secos', 9, 150, 'gramos', 1.50, 0.0100, 'gramos'),
('Nachos Blancos', 'Snacks', 9, 150, 'gramos', 0.90, 0.0060, 'gramos'),
('Huevo', 'Pasta', 7, 24, 'unidades', 3.86, 0.1608, 'unidades'),
('Claras Huevo', 'Pasta', 9, 30, 'unidades', 2.50, 0.0833, 'unidades'),
('Placas canelón', 'Pasta', 9, 20, 'unidades', 1.23, 0.0615, 'unidades'),
('Azúcar', 'Azucares', 7, 1000, 'gramos', 1.09, 0.0011, 'gramos'),
('Azúcar glass', 'Azucares', 9, 500, 'gramos', 2.60, 0.0052, 'gramos'),
('Azúcar moreno', 'Azucares', 9, 1000, 'gramos', 1.85, 0.0019, 'gramos'),
('Miel', 'Azucares', 9, 500, 'gramos', 2.95, 0.0059, 'gramos'),
('Chocolate granulado mixto', 'Chocolates', 8, 500, 'gramos', 14.84, 0.0297, 'gramos'),
('Chocolate 85 %', 'Chocolates', 9, 100, 'gramos', 1.85, 0.0185, 'gramos'),
('Chocolate blanco', 'Chocolates', 9, 200, 'gramos', 2.25, 0.0113, 'gramos'),
('Chocolate con leche', 'Chocolates', 9, 200, 'gramos', 2.40, 0.0120, 'gramos'),
('Chocolate negro', 'Chocolates', 9, 200, 'gramos', 3.40, 0.0170, 'gramos'),
('Mermelada naranja amarga', 'Mermeladas', 8, 800, 'gramos', 5.16, 0.0065, 'gramos'),
('Mermelada mango y cebolla', 'Mermeladas', 8, 770, 'gramos', 6.59, 0.0086, 'gramos'),
('Mermelada higos', 'Mermeladas', 9, 340, 'gramos', 1.95, 0.0057, 'gramos'),
('Mermelada tomate', 'Mermeladas', 9, 440, 'gramos', 1.95, 0.0044, 'gramos'),
('Trufa', 'Hongos', 1, 1, 'unidades', 5.45, 5.4500, 'unidades'),
('Cointreau', 'Bebidas', 8, 1, 'botella', 17.53, 17.5300, 'botella'),
('Tequila', 'Bebidas', 8, 1, 'botella', 6.52, 6.5200, 'botella'),
('Brandy -  Jerez solera reserva', 'Bebidas', 9, 700, 'mililitros', 5.90, 0.0084, 'mililitros'),
('Fino corredera', 'Bebidas', 9, 750, 'mililitros', 2.85, 0.0038, 'mililitros'),
('Manzanilla la bailaora', 'Bebidas', 9, 750, 'mililitros', 3.90, 0.0052, 'mililitros'),
('Oloroso', 'Bebidas', 9, 750, 'mililitros', 4.30, 0.0057, 'mililitros'),
('Oporto tawny', 'Bebidas', 7, 750, 'mililitros', 7.00, 0.0093, 'mililitros'),
('Licor de anís dulce', 'Bebidas', 9, 700, 'mililitros', 5.50, 0.0079, 'mililitros'),
('Vino tinto', 'Bebidas', 9, 1000, 'mililitros', 1.05, 0.0011, 'mililitros'),
('Cacao en polvo', 'Café/cacao', 9, 265, 'gramos', 4.15, 0.0157, 'gramos'),
('Café', 'Café/cacao', 9, 250, 'gramos', 3.50, 0.0140, 'gramos'),
('Cantero', 'Carne/Charcutería', 1, 1000, 'gramos', 21.83, 0.0218, 'gramos'),
('Huesos jamón', 'Carne/Charcutería', 1, 1000, 'gramos', 5.00, 0.0050, 'gramos'),
('Huesos Ternera', 'Carne/Charcutería', 1, 1000, 'gramos', 6.00, 0.0060, 'gramos'),
('Jamón ibérico lonchas', 'Carne/Charcutería', 1, 1000, 'gramos', 26.83, 0.0268, 'gramos'),
('Lomo bajo', 'Carne/Charcutería', 1, 1000, 'gramos', 23.30, 0.0233, 'gramos'),
('Pavo', 'Carne/Charcutería', 1, 1000, 'gramos', 9.98, 0.0100, 'gramos'),
('Solomillo ternera', 'Carne/Charcutería', 1, 1, 'gramos', 0.00, 0.0000, 'gramos'),
('Tocino ibérico', 'Carne/Charcutería', 1, 1000, 'gramos', 7.21, 0.0072, 'gramos'),
('Pate de pato martiko', 'Carne/Charcutería', 2, 150, 'gramos', 4.35, 0.0290, 'gramos'),
('Guanciale', 'Carne/Charcutería', 7, 1000, 'gramos', 17.00, 0.0170, 'gramos'),
('Higado de pato', 'Carne/Charcutería', 7, 500, 'gramos', 24.95, 0.0499, 'gramos'),
('Rabo de toro', 'Carne/Charcutería', 7, 3000, 'gramos', 39.45, 0.0132, 'gramos'),
('Solomillo Ternera Makro', 'Carne/Charcutería', 7, 3000, 'gramos', 117.00, 0.0390, 'gramos'),
('Bacón', 'Carne/Charcutería', 9, 250, 'gramos', 2.20, 0.0088, 'gramos'),
('Cecina', 'Carne/Charcutería', 9, 100, 'gramos', 3.25, 0.0325, 'gramos'),
('Carne picada mixta', 'Carne/Charcutería', 9, 1000, 'gramos', 7.00, 0.0070, 'gramos'),
('Carne picada vacuno', 'Carne/Charcutería', 9, 500, 'gramos', 4.20, 0.0084, 'gramos'),
('Cerdo a tacos', 'Carne/Charcutería', 9, 500, 'gramos', 3.22, 0.0064, 'gramos'),
('Fuet', 'Carne/Charcutería', 9, 1, 'unidades', 2.57, 2.5700, 'unidades'),
('Paleta de cebo ibérica 50%', 'Carne/Charcutería', 9, 100, 'gramos', 4.90, 0.0490, 'gramos'),
('Jamón picado', 'Carne/Charcutería', 9, 180, 'gramos', 2.55, 0.0142, 'gramos'),
('Morcilla de pueblo', 'Carne/Charcutería', 9, 380, 'gramos', 2.65, 0.0070, 'gramos'),
('Pechuga pollo', 'Carne/Charcutería', 9, 1200, 'gramos', 6.95, 0.0058, 'gramos'),
('Pollo asado HACENDADO', 'Carne/Charcutería', 9, 1200, 'gramos', 6.24, 0.0052, 'gramos'),
('Presa', 'Carne/Charcutería', 9, 240, 'gramos', 6.14, 0.0256, 'gramos'),
('Solomillo de cerdo', 'Carne/Charcutería', 9, 800, 'gramos', 6.24, 0.0078, 'gramos'),
('Solomillo de cerdo Sin Marinar', 'Carne/Charcutería', 9, 500, 'gramos', 4.10, 0.0082, 'gramos'),
('Tocino envasado', 'Carne/Charcutería', 9, 290, 'gramos', 2.03, 0.0070, 'gramos'),
('Anchoa Makro', 'Pescado', 8, 600, 'gramos', 30.24, 0.0504, 'gramos'),
('Bonito ahumado', 'Pescado', 8, 300, 'gramos', 5.77, 0.0192, 'gramos'),
('Boquerones Makro', 'Pescado', 8, 1000, 'gramos', 6.33, 0.0063, 'gramos'),
('Anillas pota makro', 'Pescado', 8, 1000, 'gramos', 12.15, 0.0122, 'gramos'),
('Cazón Makro', 'Pescado', 8, 1000, 'gramos', 9.85, 0.0099, 'gramos'),
('Huevas de maruca', 'Pescado', 8, 168, 'gramos', 9.55, 0.0568, 'gramos'),
('Mojama', 'Pescado',8, 400, 'gramos', 20.34, 0.0509, 'gramos'),
('Potón Makro', 'Pescado', 8, 1600, 'gramos', 14.58, 0.0091, 'gramos'),
('Puntillitas', 'Pescado', 8, 900, 'gramos', 4.79, 0.0053, 'gramos'),
('Atún makro', 'Pescado', 8, 1000, 'gramos', 28.95, 0.0290, 'gramos'),
('Anillas de pota', 'Pescado', 9, 260, 'gramos', 3.95, 0.0152, 'gramos'),
('Atún aleta amarilla', 'Pescado', 9, 250, 'gramos', 4.38, 0.0175, 'gramos'),
('Anchoa Mercadona', 'Pescado', 9, 87, 'gramos', 2.85, 0.0328, 'gramos'),
('Bacalao salado', 'Pescado', 9, 240, 'gramos', 5.50, 0.0229, 'gramos'),
('Boquerones en vinagre', 'Pescado', 9, 60, 'gramos', 1.65, 0.0275, 'gramos'),
('Boquerones mercadona', 'Pescado', 9, 200, 'gramos', 1.79, 0.0090, 'gramos'),
('Corvina', 'Pescado', 9, 1000, 'gramos', 11.83, 0.0118, 'gramos'),
('Gambas medianas', 'Pescado', 9, 360, 'gramos', 4.95, 0.0138, 'gramos'),
('Langostinos', 'Pescado', 9, 220, 'gramos', 3.95, 0.0180, 'gramos'),
('Lubina', 'Pescado', 9, 390, 'gramos', 3.22, 0.0083, 'gramos'),
('Patas de pulpo cocidas', 'Pescado', 9, 250, 'gramos', 9.95, 0.0398, 'gramos'),
('Potón mercadona', 'Pescado', 9, 400, 'gramos', 4.45, 0.0111, 'gramos'),
('Salmón', 'Pescado', 9, 850, 'gramos', 16.11, 0.0189, 'gramos'),
('Sardinas marinadas', 'Pescado', 9, 50, 'gramos', 1.45, 0.0290, 'gramos'),
('Nata para cocinar', 'Lacteos', 7, 600, 'mililitros', 1.69, 0.0028, 'mililitros'),
('Creme fraiche', 'Lacteos', 9, 200, 'mililitros', 1.20, 0.0060, 'mililitros'),
('Leche condensada', 'Lacteos', 9, 370, 'gramos', 2.10, 0.0057, 'gramos'),
('Leche de coco', 'Lacteos', 9, 400, 'mililitros', 1.35, 0.0034, 'mililitros'),
('Leche entera', 'Lacteos', 9, 1000, 'mililitros', 0.94, 0.0009, 'mililitros'),
('Mantequilla con sal', 'Lacteos', 9, 250, 'gramos', 2.30, 0.0092, 'gramos'),
('Mantequilla sin sal', 'Lacteos', 9, 250, 'gramos', 2.30, 0.0092, 'gramos'),
('Nata para montar', 'Lacteos', 9, 600, 'mililitros', 2.20, 0.0037, 'mililitros'),
('Yogur natural', 'Lacteos', 9, 750, 'gramos', 0.99, 0.0013, 'gramos'),
('Wensleydale', 'Quesos', 4, 400, 'gramos', 6.69, 0.0167, 'gramos'),
('Apolonio', 'Quesos', 8, 725, 'gramos', 11.65, 0.0161, 'gramos'),
('Manchego', 'Quesos', 8, 1500, 'gramos', 31.35, 0.0209, 'gramos'),
('Stilton', 'Quesos', 8, 1000, 'gramos', 35.65, 0.0357, 'gramos'),
('Queso cheddar american style', 'Quesos', 8, 84, 'lonchas', 12.12, 0.1443, 'lonchas'),
('Queso pesto verde', 'Quesos', 8, 1000, 'gramos', 20.66, 0.0207, 'gramos'),
('Brie', 'Quesos', 9, 200, 'gramos', 1.75, 0.0088, 'gramos'),
('Feta', 'Quesos', 9, 150, 'gramos', 2.40, 0.0160, 'gramos'),
('Mascarpone', 'Quesos', 9, 250, 'gramos', 2.55, 0.0102, 'gramos'),
('Queso azul', 'Quesos', 9, 180, 'gramos', 2.45, 0.0136, 'gramos'),
('Queso crema', 'Quesos', 9, 300, 'gramos', 1.35, 0.0045, 'gramos'),
('Queso de cabra', 'Quesos', 9, 200, 'gramos', 2.35, 0.0118, 'gramos'),
('Queso parmesano', 'Quesos', 9, 230, 'gramos', 3.63, 0.0158, 'gramos'),
('Queso lonchas cheddar', 'Quesos', 9, 200, 'gramos', 2.05, 0.0103, 'gramos'),
('Queso tostado', 'Quesos', 9, 395, 'gramos', 5.59, 0.0142, 'gramos'),
('Caldo de pollo', 'Caldos', 9, 1000, 'mililitros', 0.70, 0.0007, 'mililitros'),
('Caldo de verduras', 'Caldos', 9, 1000, 'mililitros', 1.00, 0.0010, 'mililitros'),
('Pastillas Caldo de carne', 'Caldos', 9, 120, 'gramos', 0.85, 0.0071, 'gramos'),
('Salmorejo', 'Caldos', 9, 1000, 'mililitros', 3.15, 0.0032, 'mililitros'),
('Tomate triturado', 'Caldos', 9, 400, 'gramos', 0.65, 0.0016, 'gramos'),
('Lima', 'Fruta', 8, 2000, 'gramos', 7.19, 0.0036, 'gramos'),
('Agucate', 'Fruta', 9, 500, 'gramos', 2.70, 0.0054, 'gramos'),
('Frutos rojos congelados', 'Fruta', 9, 300, 'gramos', 1.66, 0.0055, 'gramos'),
('Granada', 'Fruta', 9, 1, 'unidades', 0.82, 0.8200, 'unidades'),
('Limón', 'Fruta', 9, 1000, 'gramos', 1.99, 0.0020, 'gramos'),
('Mango', 'Fruta', 9, 1, 'unidades', 1.20, 1.2000, 'unidades'),
('Manzana roja rulce', 'Fruta', 9, 1, 'unidades', 0.42, 0.4200, 'unidades'),
('Naranja', 'Fruta', 9, 260, 'gramos', 0.59, 0.0023, 'gramos'),
('Pera', 'Fruta', 9, 220, 'gramos', 0.59, 0.0027, 'gramos'),
('Piña', 'Fruta', 9, 500, 'gramos', 3.20, 0.0064, 'gramos'),
('Plátano', 'Fruta', 9, 110, 'gramos', 0.34, 0.0031, 'gramos'),
('Uvas rojas', 'Fruta', 9, 500, 'gramos', 2.65, 0.0053, 'gramos'),
('Lombarda', 'Verduras', 2, 1000, 'gramos', 1.99, 0.0020, 'gramos'),
('Nabo', 'Verduras', 2, 1000, 'gramos', 2.54, 0.0025, 'gramos'),
('Cebollitas francesas', 'Verduras', 8, 500, 'gramos', 1.35, 0.0027, 'gramos'),
('Chalota', 'Verduras', 8, 500, 'gramos', 2.05, 0.0041, 'gramos'),
('Patata guarnición', 'Verduras', 8, 5000, 'gramos', 6.19, 0.0012, 'gramos'),
('Pimientos del padrón', 'Verduras', 8, 1000, 'gramos', 5.40, 0.0054, 'gramos'),
('Ajo', 'Verduras', 9, 250, 'gramos', 1.63, 0.0065, 'gramos'),
('Ajo negro', 'Verduras', 9, 85, 'gramos', 4.45, 0.0524, 'gramos'),
('Alga Wakame', 'Verduras', 9, 125, 'gramos', 1.70, 0.0136, 'gramos'),
('Apio', 'Verduras', 9, 600, 'gramos', 1.31, 0.0022, 'gramos'),
('Calabaza', 'Verduras', 9, 500, 'gramos', 1.05, 0.0021, 'gramos'),
('Cebolla', 'Verduras', 9, 1000, 'gramos', 1.63, 0.0016, 'gramos'),
('Cebolla morada', 'Verduras', 9, 500, 'gramos', 1.43, 0.0029, 'gramos'),
('Cebollino', 'Verduras', 9, 20, 'gramos', 1.80, 0.0900, 'gramos'),
('Champiñones', 'Verduras', 9, 300, 'gramos', 1.54, 0.0051, 'gramos'),
('Col', 'Verduras', 9, 1170, 'gramos', 1.95, 0.0017, 'gramos'),
('Hierbabuena', 'Verduras', 9, 40, 'gramos', 1.35, 0.0338, 'gramos'),
('Jengibre', 'Verduras', 9, 180, 'gramos', 1.69, 0.0094, 'gramos'),
('Patata', 'Verduras', 9, 5000, 'gramos', 6.37, 0.0013, 'gramos'),
('Perejil Fresco', 'Verduras', 9, 40, 'gramos', 0.79, 0.0198, 'gramos'),
('Pimiento rojo', 'Verduras', 9, 300, 'gramos', 0.96, 0.0032, 'gramos'),
('Pimiento del piquillo', 'Verduras', 9, 275, 'gramos', 1.45, 0.0053, 'gramos'),
('Pimiento verde', 'Verduras', 9, 250, 'gramos', 0.95, 0.0038, 'gramos'),
('Puerro', 'Verduras', 9, 3, 'unidades', 2.11, 0.7033, 'unidades'),
('Remolacha', 'Verduras', 9, 450, 'gramos', 0.99, 0.0022, 'gramos'),
('Rúcula', 'Verduras', 9, 50, 'gramos', 0.83, 0.0166, 'gramos'),
('Tomate', 'Verduras', 9, 2000, 'gramos', 3.54, 0.0018, 'gramos'),
('Zanahoria', 'Verduras', 9, 1000, 'gramos', 1.02, 0.0010, 'gramos'),
('Mini bao blanco', 'Panadería', 8, 43, 'unidades', 7.43, 0.1728, 'unidades'),
('Mini brioche hamburguesas', 'Panadería', 8, 10, 'unidades', 2.03, 0.203, 'unidades'),
('Mini croissants', 'Panadería', 8, 50, 'unidades', 13.30, 0.266, 'unidades'),
('Mini pulguita brioche', 'Panadería', 8, 20, 'unidades', 2.48, 0.124, 'unidades'),
('Mini tartaletas', 'Panadería', 8, 105, 'unidades', 29.76, 0.2834, 'unidades'),
('Harina de garbanzos Makro', 'Panadería', 8, 3000, 'gramos', 7.79, 0.0026, 'gramos'),
('Panko', 'Panadería', 8, 1000, 'gramos', 6.49, 0.0065, 'gramos'),
('Pan bruchetta', 'Panadería', 8, 400, 'gramos', 2.34, 0.0059, 'gramos'),
('Plancha pan de molde', 'Panadería', 8, 500, 'gramos', 3.14, 0.0063, 'gramos'),
('Tartaletas', 'Panadería', 8, 36, 'unidades', 17.99, 0.4997, 'unidades'),
('Obleas arroz', 'Panadería', 2, 15, 'unidades', 2.25, 0.15, 'unidades'),
('Pan brioche', 'Panadería', 4, 600, 'gramos', 5.99, 0.01, 'gramos'),
('Obleas empanadillas', 'Panadería', 8, 16, 'unidades', 1.08, 0.0675, 'unidades'),
('Bizcochos soletilla', 'Panadería', 9, 400, 'gramos', 1.65, 0.0041, 'gramos'),
('Coco rallado', 'Panadería', 9, 125, 'gramos', 1.14, 0.0091, 'gramos'),
('Galletas maria', 'Panadería', 9, 800, 'gramos', 1.45, 0.0018, 'gramos'),
('Harina de fuerza', 'Panadería', 9, 1000, 'gramos', 0.95, 0.0009, 'gramos'),
('Harina de garbanzos mercadona', 'Panadería', 9, 500, 'gramos', 2.00, 0.004, 'gramos'),
('Harina de freir', 'Panadería', 9, 1000, 'gramos', 1.46, 0.0015, 'gramos'),
('Harina de trigo', 'Panadería', 9, 5000, 'gramos', 3.02, 0.0006, 'gramos'),
('Hojaldre rectangular', 'Panadería', 9, 2, 'unidades', 2.40, 1.2, 'unidades'),
('Maicena', 'Panadería', 9 ,400, 'gramos', 1.20, 0.003, 'gramos'),
('Masa empanada', 'Panadería', 9, 2, 'unidades', 2.40, 1.2, 'unidades'),
('Masa filo', 'Panadería', 9, 10, 'unidades', 2.00, 0.2, 'unidades'),
('Pan bimbo', 'Panadería', 9, 450, 'gramos', 1.20, 0.0027, 'gramos'),
('Pan hamburguesa', 'Panadería', 9, 4, 'unidades', 0.85, 0.2125, 'unidades'),
('Pan chapata', 'Panadería', 9, 284, 'gramos', 1.40, 0.0049, 'gramos'),
('Pan rallado', 'Panadería', 9, 750, 'gramos', 0.90, 0.0012, 'gramos'),
('Picatostes', 'Panadería', 9, 100, 'gramos', 0.70, 0.007, 'gramos'),
('Tortitas tacos', 'Panadería', 9, 10, 'unidades', 1.20, 0.12, 'unidades'),
('Picos morenos', 'Picos/tostas', 8, 500, 'gramos', 3.58, 0.0072, 'gramos'),
('Tostas foie pasas', 'Picos/tostas', 8, 90, 'gramos', 1.53, 0.017, 'gramos'),
('Tostas fuet', 'Picos/tostas', 8, 500, 'gramos', 3.69, 0.0074, 'gramos'),
('Tostas sin glúten', 'Picos/tostas', 2, 150, 'gramos', 2.75, 0.0183, 'gramos'),
('Picos de pipas', 'Picos/tostas', 9, 166, 'gramos', 1.35, 0.0081, 'gramos'),
('Maiz', 'Conservas', 9, 3, 'latas', 1.60, 0.5333, 'latas'),
('Tomate seco', 'Conservas', 9, 95, 'gramos', 2.30, 0.0242, 'gramos'),
('Atún en AOVE', 'Conservas', 9, 6, 'latas', 5.15, 0.8583, 'latas'),
('Cuajada polvo', 'Preparados', 9, 4, 'sobres', 1.45, 0.3625, 'sobres'),
('Hojas de gelatina', 'Preparados', 9, 12, 'unidades', 1.00, 0.0833, 'unidades'),
('Levadura fermento', 'Preparados', 9, 70, 'gramos', 1.50, 0.0214, 'gramos'),
('Levadura fresca', 'Preparados', 9, 50, 'gramos', 0.45, 0.009, 'gramos'),
('Impulsor', 'Preparados', 9, 90, 'gramos', 0.70, 0.0078, 'gramos'),
('Malla atar carnes', 'Otros', 8, 2000, 'centimetros', 3.21, 0.0016, 'centimetros'),
('Carga sifón metrochef', 'Otros', 8, 50, 'unidades', 26.56, 0.5312, 'unidades'),
('Alga sushi', 'Otros', 8, 50, 'unidades', 12.93, 0.2586, 'unidades'),
('Perlas de wasabi', 'Otros', 8, 150, 'gramos', 6.48, 0.0432, 'gramos');



-- ------------------------------------------------
-- 8) RELACIÓN INGREDIENTE–ALÉRGENO
--    Ejemplo:
--      Harina (ingrediente_id=1) contiene GLUTEN (alergeno_id=1)
--      Leche (ingrediente_id=2) contiene LACTEOS (alergeno_id=2)
--      Huevos (ingrediente_id=9) contiene HUEVO   (alergeno_id=4)
--      Bacalao (ingrediente_id=4) contiene PESCADO (alergeno_id=5)
-- ------------------------------------------------

INSERT INTO ingrediente_alergeno (ingrediente_id, alergeno_id) VALUES
  ( 28,   6),  -- Tabasco → SOJA (contiene salsa de soja en su formulación original)
  ( 29,  10),  -- Salsa gaucha → MOSTAZA
  ( 30,   6),  -- Salsa Perrins → SOJA
  ( 31,   6),  -- Siracha → SOJA  
  ( 33,   6),  -- Salsa de soja → SOJA
  ( 34,  14),  -- Salsa de ostras (opcional) → MOLUSCOS  
  ( 36,  10),  -- Mostaza Dijón → MOSTAZA
  ( 38,   3),  -- Mayonesa Hacenado → HUEVO  
  ( 39,  10),  -- Miel y mostaza → MOSTAZA
  ( 40,  10),  -- Mostaza antigua → MOSTAZA
  ( 41,  10),  -- Mostaza hacendado → MOSTAZA
  ( 42,   7),  -- Salsa de trufa → LACTEOS (suele llevar nata o crema)
  ( 43,   7),  -- Salsa barbacoa → LACTEOS (algunas recetas llevan lacteos, asimilamos a LACTEOS)
  ( 48,   8),  -- Nueces → FRUTOS_DE_CASCARA
  ( 49,   8),  -- Almendras blancas → FRUTOS_DE_CASCARA
  ( 50,   8),  -- Almendras fritas → FRUTOS_DE_CASCARA
  ( 51,   8),  -- Arandanos deshidratados → (ningún alérgeno obligatorio; frutas no entran)
  ( 52,   8),  -- Combinado frutos secos → FRUTOS_DE_CASCARA
  ( 53,   8),  -- Maiz gigante → (ningún alérgeno obligatorio)
  ( 54,   8),  -- Pasas sultanas → (ningún alérgeno obligatorio)
  ( 55,   8),  -- Pipas de calabaza → (ningún alérgeno obligatorio)
  ( 56,   8),  -- Pistachos → FRUTOS_DE_CASCARA
  ( 57,  11),  -- Sésamo blanco → SESAMO
  ( 59,   3),  -- Huevo → HUEVO
  ( 60,   3),  -- Claras Huevo → HUEVO
  ( 61,   1),  -- Placas canelón → GLUTEN
  ( 66,   8),  -- Chocolate granulado mixto → FRUTOS_DE_CASCARA (puede llevar avellanas, etc.)
  ( 67,   8),  -- Chocolate 85 % → FRUTOS_DE_CASCARA (puede contener trazas de frutos secos)
  ( 68,   8),  -- Chocolate blanco → FRUTOS_DE_CASCARA (puede contener trazas de frutos secos)
  ( 69,   8),  -- Chocolate con leche → FRUTOS_DE_CASCARA (puede contener trazas de frutos secos)
  ( 70,   8),  -- Chocolate negro → FRUTOS_DE_CASCARA (puede contener trazas de frutos secos)
  ( 78,   7),  -- Brandy – Jerez solera reserva → LACTEOS (a veces añaden clarificantes lácteos)
  ( 79,   7),  -- Fino corredera → LACTEOS (puede contener sulfatos y trazas lácteas)
  ( 80,   7),  -- Manzanilla la bailaora → LACTEOS (puede contener trazas lácteas)
  ( 81,   7),  -- Oloroso → LACTEOS (puede contener trazas lácteas)
  ( 85,   7),  -- Cacao en polvo → LACTEOS (puede contener trazas de leche)
  ( 95,   1),  -- Paté de pato martiko → GLUTEN (depende de la preparación, suele llevar pan)
  (100,   7),  -- Bacón → LACTEOS (puede contener caseína añadida como conservante)
  (115,   4),  -- Anchoa Makro → PESCADO
  (116,   4),  -- Bonito ahumado → PESCADO
  (117,   4),  -- Boquerones Makro → PESCADO
  (118,   4),  -- Anillas pota makro → PESCADO (son potas, calamares, igual que MOLUSCOS; se enlaza a PESCADO de forma general)
  (119,   4),  -- Cazón Makro → PESCADO
  (120,   4),  -- Huevas de maruca → PESCADO  
  (121,   4),  -- Mojama → PESCADO
  (122,   4),  -- Potón Makro → PESCADO
  (123,   4),  -- Puntillitas → MOLUSCOS (son calamares pequeños)
  (124,   4),  -- Atún makro → PESCADO
  (125,   4),  -- Anillas de pota → PESCADO
  (126,   4),  -- Atún aleta amarilla → PESCADO
  (127,   4),  -- Anchoa Mercadona → PESCADO
  (128,   4),  -- Bacalao salado → PESCADO
  (129,   4),  -- Boquerones en vinagre → PESCADO
  (130,   4),  -- Boquerones mercadona → PESCADO
  (131,   4),  -- Corvina → PESCADO
  (132,   2),  -- Gambas medianas → CRUSTACEOS
  (133,   2),  -- Langostinos → CRUSTACEOS
  (134,  14),  -- Lubina → MOLUSCOS (en algunos casos se clasifica como MOLUSCO, dependiendo si son filetes empanados)
  (135,   4),  -- Patas de pulpo cocidas → PESCADO  (octópodo se declara como PESCADO en etiquetado genérico)
  (136,  14),  -- Potón mercadona → MOLUSCOS
  (137,   4),  -- Salmón → PESCADO
  (138,   4),  -- Sardinas marinadas → PESCADO
  (139,   7),  -- Nata para cocinar → LACTEOS
  (140,   7),  -- Creme fraiche → LACTEOS
  (141,   7),  -- Leche condensada → LACTEOS
  (143,   7),  -- Leche entera → LACTEOS
  (144,   7),  -- Mantequilla con sal → LACTEOS
  (145,   7),  -- Mantequilla sin sal → LACTEOS
  (146,   7),  -- Nata para montar → LACTEOS
  (147,   7),  -- Yogur natural → LACTEOS
  (148,   7),  -- Wensleydale → LACTEOS
  (149,   7),  -- Apolonio → LACTEOS
  (150,   7),  -- Manchego → LACTEOS
  (151,   7),  -- Stilton → LACTEOS
  (152,   7),  -- Queso cheddar american style → LACTEOS
  (153,   7),  -- Queso pesto verde → LACTEOS
  (154,   7),  -- Brie → LACTEOS
  (155,   7),  -- Feta → LACTEOS
  (156,   7),  -- Mascarpone → LACTEOS
  (157,   7),  -- Queso azul → LACTEOS
  (158,   7),  -- Queso crema → LACTEOS
  (159,   7),  -- Queso de cabra → LACTEOS
  (160,   7),  -- Queso parmesano → LACTEOS
  (161,   7),  -- Queso lonchas cheddar → LACTEOS
  (162,   7),  -- Queso tostado → LACTEOS
  (189,  9),   -- Apio → APIO (id=9)
  (208,  1),   -- Mini bao blanco → GLUTEN (id=1)
  (209,  1),   -- Mini brioche hamburguesas → GLUTEN (id=1)
  (209,  3),   -- Mini brioche hamburguesas → HUEVO (id=3)
  (209,  7),   -- Mini brioche hamburguesas → LACTEOS (id=7)
  (210,  1),   -- Mini croissants → GLUTEN (id=1)
  (210,  3),   -- Mini croissants → HUEVO (id=3)
  (210,  7),   -- Mini croissants → LACTEOS (id=7)
  (211,  1),   -- Mini pulguita brioche → GLUTEN (id=1)
  (211,  3),   -- Mini pulguita brioche → HUEVO (id=3)
  (211,  7),   -- Mini pulguita brioche → LACTEOS (id=7)
  (212,  1),   -- Mini tartaletas → GLUTEN (id=1)
  (212,  7),   -- Mini tartaletas → LACTEOS (id=7)
  (214,  1),   -- Panko → GLUTEN (id=1)
  (215,  1),   -- Pan bruchetta → GLUTEN (id=1)
  (216,  1),   -- Plancha pan de molde → GLUTEN (id=1)
  (217,  1),   -- Tartaletas → GLUTEN (id=1)
  (217,  7),   -- Tartaletas → LACTEOS (id=7)
  (219,  1),   -- Pan brioche → GLUTEN (id=1)
  (219,  3),   -- Pan brioche → HUEVO (id=3)
  (219,  7),   -- Pan brioche → LACTEOS (id=7)
  (220,  1),   -- Obleas empanadillas → GLUTEN (id=1)
  (221,  1),   -- Bizcochos soletilla → GLUTEN (id=1)
  (221,  3),   -- Bizcochos soletilla → HUEVO (id=3)
  (223,  1),   -- Galletas maria → GLUTEN (id=1)
  (223,  7),   -- Galletas maria → LACTEOS (id=7)
  (224,  1),   -- Harina de fuerza → GLUTEN (id=1)
  (226,  1),   -- Harina de freír → GLUTEN (id=1)
  (227,  1),   -- Harina de trigo → GLUTEN (id=1)
  (228,  1),   -- Hojaldre rectangular → GLUTEN (id=1)
  (228,  7),   -- Hojaldre rectangular → LACTEOS (id=7)
  (230,  1),   -- Masa empanada → GLUTEN (id=1)
  (231,  1),   -- Masa filo → GLUTEN (id=1)
  (232,  1),   -- Pan bimbo → GLUTEN (id=1)
  (233,  1),   -- Pan hamburguesa → GLUTEN (id=1)
  (234,  1),   -- Pan chapata → GLUTEN (id=1)
  (235,  1),   -- Pan rallado → GLUTEN (id=1)
  (236,  1),   -- Picatostes → GLUTEN (id=1)
  (238,  1),   -- Picos morenos → GLUTEN (id=1)
  (239,  1),   -- Tostas foie pasas → GLUTEN (id=1)
  (239, 12),   -- Tostas foie pasas → DIOXIDO_DE_AZUFRE_Y_SULFITOS (id=12)
  (240,  1),   -- Tostas fuet → GLUTEN (id=1)
  (245,  4),   -- Atún en AOVE → PESCADO (id=4)
  (246,  7);   -- Cuajada polvo → LACTEOS (id=7)
 
-- ------------------------------------------------
-- 9) PLATOS (incluye image_base_name)
--    - image_base_name debe coincidir con los ficheros en Angular:
--      e.g. croqBac1.webp, croqBac2.webp, croqBac3.webp
-- ------------------------------------------------
INSERT INTO plato (nombre_plato, cantidad, precio, stock, preparacion_casa, recomendaciones, fecha_actualizacion, categoria_id, image_base_name) VALUES
-- Aperitivos
('Albondiguitas con salsa mozarabe', 32, 12.95, 20, 'Calentar en horno a 180 °C durante 10 minutos',
   'Servir caliente acompañadas de la salsa', '2025-06-04', 1, 'albon'),
('Breguas de queso de cabra y cebolla caramelizada', 24, 11.95, 20,'Calentar ligeramente en horno a 160 °C durante 5 minutos',
   'Servir templadas o a temperatura ambiente', '2025-06-04', 1, 'breguas'),
('Brochetas de solomillo en costra de parmesano', 17, 15.95, 20,  'Calentar a la plancha o sartén 2 minutos por cada lado a fuego medio',
   'Servir calientes al momento', '2025-06-04', 1, 'soloCParm'),
('Canoli de brandada de bacalao', 20, 24.95, 20,  'Freír en abundante aceite a 180 °C durante 2–3 minutos',
   'Servir recién hechos para mantener el crujiente', '2025-06-04', 1, 'canoliBac'),
('Corona Brie', 1, 18.95, 20,'Hornear a 170 °C durante 5 minutos para fundir ligeramente',
   'Servir caliente con mermelada y nueces', '2025-06-04', 1, 'corona'),
('Croquetas carbonara', 24, 14.95, 20, 'Freír en aceite a 170 °C hasta que estén doradas (3–4 minutos)',
   'Servir calientes inmediatamente','2025-06-04', 1, 'croqCarb'),
('Crujiente de langostino con mayonesa de curry', 20, 16.95, 20, 'Freír en aceite a 170 °C durante 2–3 minutos',
   'Servir calientes con la mayonesa de curry al lado', '2025-06-04', 1, 'crujLan'),
('Ensaladilla de pollo, mostaza y kikos', 500, 11.95, 20,'Servir fría directamente sin necesidad de calentado',
   'Acompañar con kikos crujientes por encima', '2025-06-04', 1, 'ensPoll'),
('Foie Mi Cuit con tostas', 170, 25.95, 20,'Servir frío acompañado de tostas y frutos secos',
   'Añadir mermelada de oporto para potenciar el sabor', '2025-06-04', 1, 'foie'),
('Mini croquetas de bacalao con alioli de ajo negro', 28, 14.95, 20, 'Freír en aceite a 170 °C durante 3–4 minutos',
   'Servir calientes con el alioli de ajo negro al lado', '2025-06-04', 1, 'croqBac'),
('Mini croquetas de gambas al ajillo', 28, 10.95, 20, 'Freír en aceite a 170 °C durante 3–4 minutos',
   'Servir calientes con un toque de limón exprimido', '2025-06-04', 1, 'croqGam'),
('Mini croquetas de jamón', 28, 10.95, 20,  'Freír en aceite a 170 °C durante 3–4 minutos',
   'Servir calientes con pan rallado crujiente por encima', '2025-06-04', 1, 'croqJam'),
('Mini hamburguesitas wellington', 12, 18.95, 20,'Calentar al horno a 180 °C durante 8 minutos',
   'Servir calientes con salsa de acompañamiento','2025-06-04', 1, 'miniBWell'),
('Salmón marinado con salsa tártara', 1, 25.95, 20,  'Servir frío, no requiere calentado',
   'Acompañar con tostadas y alcaparras', '2025-06-04', 1, 'salmon'),
('Sandwich frío de atún, nueces y oporto', 12, 12.95, 20,    'Servir frío, sin necesidad de preparación adicional',
   'Refrigerar hasta el momento de servir', '2025-06-04', 1, 'sandAtun'),
('Sandwich frío de pollo a la mostaza', 12, 12.95, 20,  'Servir frío, sin necesidad de calentado',
   'Añadir hoja de lechuga fresca al momento de servir', '2025-06-04', 1, 'sandPollo'),
('Saquitos de morcilla y manzana caramelizada', 12, 12.95, 20, 'Hornear a 170 °C durante 6–7 minutos',
   'Servir calientes, sugiere acompañar con reducción de vino tinto', '2025-06-04', 1, 'saqMorc'),
('Tabla de quesos', 1, 28.95, 20,  'Servir a temperatura ambiente (15 min antes de consumir)',
   'Acompañar con mermeladas y frutos secos', '2025-06-04', 1, 'tablaQuesos'),
('Tacos de corvina con alioli de manzanilla', 24, 23.95, 20, 'Freir a 180ºC unos 5 minutos',
   'Servir templados, decorar con cebollino fresco','2025-06-04', 1, 'tacCorv'),
('Tartar de fuet con tostas', 1, 15.95, 20, 'Servir frío, montar sobre tostas justo antes de servir',
   'Acompañar con pepinillos agridulces','2025-06-04', 1, 'fuet'),
('Tartar de remolacha', 1, 14.95, 20, 'Servir frío, montar en aro de presentación',
   'Acompañar con queso de cabra desmenuzado', '2025-06-04', 1, 'tartRemo'),
('Tataki de solomillo de cerdo con mayonesa de soja', 1, 17.95, 20, 'Sacar el tataki del envasado al vacío y cortar en lonchas finas.','Nuestro plato estrella, ¡solo disfruta!', '2025-06-04', 1, 'tatakiCerdo');

-- Principales
INSERT INTO plato (nombre_plato, cantidad, precio, stock, preparacion_casa, recomendaciones, fecha_actualizacion, categoria_id, image_base_name) VALUES
  ('Canelones de rabo de toro trufados', 12, 29.95, 20,
     '1. Descongelar los canelones.  
      2. Precalentar el horno a 180 °C y hornear 15 minutos hasta que estén dorados.  
      3. Retirar del horno y servir en la fuente deseada.',
     'Añadir más queso al gratinar para un acabado extra crujiente y sabroso.', 
     '2025-06-04', 2, 'canelones'),
  ('Roast Beef con puré de patatas', 1, 57.95, 20,
     '1. Sacar el Roast beef del envase y colocar sobre una tabla.  
      2. Calentar el puré de patatas en olla o microondas hasta que esté bien tibio.  
      3. Cortar el Roast beef en lonchas finas y disponer en una fuente junto al puré.',
     'Perfecto para preparar sándwiches fríos con salsa de mostaza; combina muy bien con un toque inglés.',
     '2025-06-04', 2, 'roastBeef');
     
-- Postres
INSERT INTO plato (nombre_plato, cantidad, precio, stock, preparacion_casa, recomendaciones, fecha_actualizacion, categoria_id, image_base_name) VALUES
 ('Alfajores', 16, 12.95, 20,'Espolvorear con coco rallado.', 
     'Conservar en un recipiente hermético a temperatura ambiente; quedan más tiernos con el relleno reposado de un día para otro.', 
     '2025-06-04', 3, 'alfajores'),
  ('Merenguitos', 32, 4.95, 20,'Mantener en lugar fresco y seco; se conservan hasta 2 semanas en recipiente hermético.', 'Comer sin remordimiento', 
     '2025-06-04', 3, 'merenguitos'),
  ('Mini tartas de queso', 12, 12.95, 20,
     '1. Triturar galletas maria y mezclar con mantequilla derretida; forrar la base de moldes individuales de tarta.  
      2. Batir queso crema con azúcar y las yemas hasta homogeneizar.  
      3. Añadir la maicena disuelta en un poco de nata, mezclar suavemente.  
      4. Verter sobre la base de galleta y hornear a 160 °C durante 20–25 minutos hasta que cuaje ligeramente.  
      5. Dejar enfriar y refrigerar al menos 2 horas antes de servir.', 
     'Servir frío decorado con frutos rojos o coulis al gusto.', 
     '2025-06-04', 3, 'tartQueso'),
  ('Mini tiramisú', 4, 12.95, 20,
     '1. Batir mascarpone con yema y azúcar hasta obtener una crema suave.  
      2. Montar la clara a punto de nieve e incorporar suavemente.  
      3. Empapar bizcochos soletilla en café breve e ir colocando capas alternas con la crema de mascarpone.  
      4. Terminar con cacao en polvo espolvoreado.  
      5. Refrigerar al menos 4 horas antes de servir.', 
     'Dejar reposar en frío para que los sabores se integren; espolvorear cacao justo antes de servir.', 
     '2025-06-04', 3, 'tiramisu'),
  ('Panna cotta con coulis de frutos rojos', 4, 12.95, 20,
     '1. Hidratar hojas de gelatina en agua fría.  
      2. Calentar nata para montar con azúcar y extracto de vainilla hasta que hierva suavemente.  
      3. Retirar del fuego, añadir gelatina escurrida y remover hasta disolver.  
      4. Verter en moldes individuales y refrigerar mínimo 4 horas.  
      5. Para el coulis, calentar frutos rojos con azúcar, triturar y pasar por colador. Refrigerar.', 
     'Desmoldar la panna cotta sobre plato y napar con coulis frío antes de servir.', 
     '2025-06-04', 3, 'pannaCotta'),
  ('Tarta 3 chocolates', 4, 12.95, 20,
     ' Desmoldar con cuidado antes de servir.', 
     'Mantener en frío hasta el último momento; decorar con virutas de chocolate o frutos rojos.', 
     '2025-06-04', 3, 'tarta3Choco'),
  ('Tarta banoffe', 4, 12.95, 20,
     '1. Base: mezclar galletas maria trituradas con mantequilla fundida, presionar en molde.  
      2. Caramelo: calentar plátano, azúcar glass y mantequilla hasta formar salsa, verter sobre la base.  
      3. Disponer rodajas de plátano fresco sobre el caramelo.  
      4. Montar nata con azúcar glass y colocar capa de nata encima.  
      5. Refrigerar mínimo 2 horas antes de servir.', 
     'Espolvorear con cacao en polvo o ralladura de chocolate para decorar.', 
     '2025-06-04', 3, 'bannofee'),
  ('Tartaletas de limón', 2, 6.95, 20,
     '1. Precalentar el horno a 180 °C.  
      2. Rellenar tartaletas con crema de limón (jugo de limón, azúcar, maicena, clara de huevo y ralladura de limón cocidos a fuego suave hasta espesar).  
      3. Hornear 10 minutos para dorar ligeramente la superficie.  
      4. Dejar enfriar y refrigerar antes de servir.', 
     'Servir frías con ralladura de limón adicional y hojas de hierbabuena.', 
     '2025-06-04', 3, 'lemonPie'),
  ('Trufas de chocolate', 16, 12.95, 20,
     '1. Fundir chocolate con nata al baño maría o microondas en intervalos cortos.  
      2. Incorporar mantequilla y azúcar, mezclar hasta homogeneizar.  
      3. Dejar enfriar la mezcla en frío durante 1 hora.  
      4. Formar bolitas y rebozar en cacao en polvo.  
      5. Refrigerar hasta el momento de servir.', 
     'Conservar en frío en caja o recipiente hermético; servir a temperatura ambiente para mejor textura.', 
     '2025-06-04', 3, 'trufas'),
  ('Yemas', 20, 12.95, 20,
     '1. Batir yemas con azúcar hasta obtener una crema espesa y pálida.  
      2. Colocar en cápsulas de papel y hornear a 150 °C durante 15–20 minutos hasta que cuajen ligeramente.  
      3. Dejar reposar antes de servir.', 
     'Espolvorear con azúcar glas justo antes de servir para un acabado aterciopelado.', 
     '2025-06-04', 3, 'yemas');

-- ------------------------------------------------
-- 10) RELACIÓN PLATO–INGREDIENTE (plato_ingrediente)
--    - Cantidad de cada ingrediente por ración/sabor
-- ------------------------------------------------
-- 1. Albondiguitas con salsa mozarabe (plato_id = 1)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Carne picada mixta'), 111.11, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla'), 27.78, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Ajo'), 0.89, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Perejil Fresco'), 0.33, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.11, 'unidades'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pimienta'), 0.11, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 11.11, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Comino'), 1.11, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cilantro'), 0.56, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Oloroso'), 53.33, 'mililitros'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pasas sultanas'), 11.11, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Almendras blancas'), 11.11, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Harina de trigo'), 0.56, 'gramos'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite de girasol'), 44.44, 'mililitros'),
  (1, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nata para cocinar'), 11.11, 'mililitros');

-- 2. Breguas de queso de cabra y cebolla caramelizada (plato_id = 2)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (2, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Masa filo'), 2.67, 'laminas'),
  (2, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite tapón verde'), 8.48, 'mililitros'),
  (2, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso de cabra'), 48.48, 'gramos'),
  (2, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla'), 181.82, 'gramos'),
  (2, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mermelada tomate'), 38.79, 'gramos');

-- 3. Brochetas de solomillo en costra de parmesano (plato_id = 3)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (3, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Solomillo de cerdo Sin Marinar'), 230.00, 'gramos'),
  (3, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso parmesano'), 50.00, 'gramos'),
  (3, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pan rallado'), 50.00, 'gramos'),
  (3, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 1.00, 'unidades'),
  (3, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mostaza antigua'), 30.00, 'gramos'),
  (3, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite tapón verde'), 10.00, 'mililitros');

-- 4. Canoli de brandada de bacalao (plato_id = 4)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (4, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite tapón verde'), 15.00, 'mililitros'),
  (4, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Harina de trigo'), 25.00, 'gramos'),
  (4, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 96.50, 'mililitros'),
  (4, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Bacalao salado'), 150.00, 'gramos'),
  (4, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite de girasol'), 200.00, 'mililitros'),
  (4, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Obleas empanadillas'), 10.00, 'unidades');

-- 5. Corona Brie (plato_id = 5)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (5, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Brie'), 80.00, 'gramos'),
  (5, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nueces'), 24.00, 'gramos'),
  (5, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Granada'), 0.50, 'unidades'),
  (5, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mermelada tomate'), 70.00, 'gramos'),
  (5, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Hojaldre rectangular'), 1.00, 'unidades'),
  (5, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla'), 375.00, 'gramos'),
  (5, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 1.00, 'unidades'),
  (5, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite tapón verde'), 30.00, 'mililitros');

-- 6. Croquetas carbonara (plato_id = 6)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (6, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Bacón'), 96.00, 'gramos'),
  (6, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Harina de trigo'), 38.40, 'gramos'),
  (6, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 360.00, 'mililitros'),
  (6, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 2.40, 'unidades'),
  (6, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso parmesano'), 28.80, 'gramos'),
  (6, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pan rallado'), 72.00, 'gramos'),
  (6, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Panko'), 48.00, 'gramos');

-- 7. Crujiente de langostino con mayonesa de curry (plato_id = 7)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Gambas medianas'), 149.23, 'gramos'),
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Masa filo'), 3.85, 'unidades'),
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pimienta'), 0.77, 'gramos'),
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Ajo en polvo'), 7.69, 'gramos'),
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cilantro'), 7.69, 'gramos'),
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla en polvo'), 7.69, 'gramos'),
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Curry'), 7.69, 'gramos'),
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite de girasol'), 230.77, 'mililitros'),
  (7, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.77, 'unidades');

-- 8. Ensaladilla de pollo, mostaza y kikos (plato_id = 8)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (8, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pechuga pollo'), 187.97, 'gramos'),
  (8, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Patata'), 187.97, 'gramos'),
  (8, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mayonesa Hacenado'), 112.78, 'mililitros'),
  (8, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla morada'), 46.99, 'gramos'),
  (8, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Maiz gigante'), 5.64, 'gramos'),
  (8, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mostaza Dijón'), 11.28, 'gramos'),
  (8, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Miel y mostaza'), 15.04, 'gramos');

-- 9. Foie Mi Cuit con tostas (plato_id = 9)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (9, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Higado de pato'), 170.00, 'gramos'),
  (9, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pimienta'), 1.70, 'gramos'),
  (9, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 1.02, 'gramos'),
  (9, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Brandy -  Jerez solera reserva'), 10.20, 'mililitros'),
  (9, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Oporto tawny'), 10.20, 'mililitros'),
  (9, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mermelada naranja amarga'), 45.90, 'gramos'),
  (9, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Tostas fuet'), 132.60, 'gramos');

-- 10. Mini croquetas de bacalao con alioli de ajo negro (plato_id = 10)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 28.00, 'gramos'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Harina de trigo'), 28.00, 'gramos'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Bacalao salado'), 93.33, 'gramos'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 186.67, 'mililitros'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pimienta'), 0.47, 'gramos'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nuez moscada'), 0.93, 'gramos'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pan rallado'), 46.67, 'gramos'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 1, 'unidades'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Ajo negro'), 14.00, 'gramos'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Ajo'), 2.33, 'gramos'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite de girasol'), 140.00, 'mililitros'),
  (10, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Jugo limón'), 7.00, 'mililitros');

-- 11. Mini croquetas de gambas al ajillo (plato_id = 11)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Gambas medianas'), 75.23, 'gramos'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Ajo'), 2.51, 'gramos'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Perejil Fresco'), 0.63, 'gramos'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pimienta'), 0.21, 'gramos'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 27.16, 'gramos'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Harina de trigo'), 41.79, 'gramos'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 208.96, 'mililitros'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nuez moscada'), 0.42, 'gramos'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.42, 'unidades'),
  (11, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pan rallado'), 52.24, 'gramos');

-- 12. Mini croquetas de jamón (plato_id = 12)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Jamón picado'), 37.06, 'gramos'),
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla'), 25.74, 'gramos'),
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Harina de trigo'), 41.18, 'gramos'),
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nuez moscada'), 0.41, 'gramos'),
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pimienta'), 0.21, 'gramos'),
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 26.76, 'gramos'),
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 205.88, 'mililitros'),
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.41, 'unidades'),
  (12, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pan rallado'), 51.47, 'gramos');

-- 13. Mini hamburguesitas wellington (plato_id = 13)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (13, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Carne picada vacuno'), 176.47, 'gramos'),
  (13, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Brie'), 49.41, 'gramos'),
  (13, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Obleas empanadillas'), 16.24, 'unidades'),
  (13, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla'), 49.41, 'gramos'),
  (13, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.71, 'unidades'),
  (13, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Sésamo blanco'), 7.06, 'gramos'),
  (13, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Salsa gaucha'), 7.06, 'gramos');

-- 14. Salmón marinado con salsa tártara (plato_id = 14)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Salmón'), 400.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 400.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Sal fina'), 400.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Eneldo'), 9.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 1.00, 'unidades'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Yogur natural'), 100.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Alcaparras'), 25.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pepinillos agridulces'), 25.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Chalota'), 25.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mostaza Dijón'), 10.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Jugo limón'), 15.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Perejil Fresco'), 4.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebollino'), 3.00, 'gramos'),
  (14, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite de girasol'), 200.00, 'mililitros');

-- 15. Sandwich frío de atún, nueces y oporto (plato_id = 15)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (15, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Atún en AOVE'), 2.00, 'latas'),
  (15, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso crema'), 80.00, 'gramos'),
  (15, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nueces'), 30.00, 'gramos'),
  (15, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pasas sultanas'), 32.00, 'gramos'),
  (15, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Oporto tawny'), 50.00, 'mililitros'),
  (15, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Plancha pan de molde'), 3.00, 'unidades');

-- 16. Sandwich frío de pollo a la mostaza (plato_id = 16)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (16, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pollo asado HACENDADO'), 150.00, 'gramos'),
  (16, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Tomate seco'), 30.00, 'gramos'),
  (16, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla frita crujiente'), 20.00, 'gramos'),
  (16, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso crema'), 80.00, 'gramos'),
  (16, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mostaza antigua'), 0.00, 'gramos'),
  (16, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mostaza hacendado'), 30.00, 'gramos'),
  (16, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Plancha pan de molde'), 3.00, 'unidades');

-- 17. Saquitos de morcilla y manzana caramelizada (plato_id = 17)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (17, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Morcilla de pueblo'), 100.00, 'gramos'),
  (17, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Manzana roja rulce'), 1.20, 'unidades'),
  (17, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar moreno'), 18.00, 'gramos'),
  (17, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 10.00, 'gramos'),
  (17, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla'), 80.00, 'gramos'),
  (17, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Obleas empanadillas'), 6.40, 'unidades'),
  (17, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.40, 'unidades'),
  (17, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite tapón verde'), 6.00, 'mililitros');

-- 18. Tabla de quesos (plato_id = 18)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Apolonio'), 90.00, 'gramos'),
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Wensleydale'), 90.00, 'gramos'),
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Brie'), 90.00, 'gramos'),
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Manchego'), 90.00, 'gramos'),
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso pesto verde'), 90.00, 'gramos'),
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Salsa de trufa'), 15.00, 'gramos'),
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nueces'), 25.00, 'gramos'),
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Arandanos deshidratados'), 25.00, 'gramos'),
  (18, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mermelada mango y cebolla'), 40.00, 'gramos');

-- 19. Tacos de corvina con alioli de manzanilla (plato_id = 19)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (19, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Corvina'), 476.00, 'gramos'),
  (19, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Panko'), 100.00, 'gramos'),
  (19, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 3.00, 'unidades'),
  (19, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite de girasol'), 300.00, 'mililitros'),
  (19, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Manzanilla la bailaora'), 20.00, 'mililitros');

-- 20. Tartar de fuet con tostas (plato_id = 20)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Fuet'), 1.00, 'unidades'),
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 1.00, 'unidades'),
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pepinillos agridulces'), 30.00, 'gramos'),
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Alcaparras'), 5.00, 'gramos'),
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Siracha'), 15.00, 'mililitros'),
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mostaza hacendado'), 15.00, 'gramos'),
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Salsa Perrins'), 5.00, 'gramos'),
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite tapón verde'), 10.00, 'mililitros'),
  (20, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Tostas fuet'), 130.00, 'gramos');

-- 21. Tartar de remolacha (plato_id = 21)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (21, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Remolacha'), 150.00, 'gramos'),
  (21, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso de cabra'), 33.33, 'gramos'),
  (21, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Alcaparras'), 40.00, 'gramos'),
  (21, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cebolla morada'), 45.33, 'gramos'),
  (21, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite tapón verde'), 6.29, 'mililitros'),
  (21, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Tostas fuet'), 130.00, 'gramos');

-- 22. Tataki de solomillo de cerdo con mayonesa de soja (plato_id = 22)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (22, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Solomillo de cerdo'), 400.00, 'gramos'),
  (22, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Salsa de soja'), 42.00, 'mililitros'),
  (22, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.50, 'unidades'),
  (22, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite de girasol'), 150.00, 'mililitros');

-- 23. Canelones de rabo de toro trufados (plato_id = 23)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Rabo de toro'), 250.00, 'gramos'),
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Trufa'), 10.00, 'gramos'),
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Solomillo Ternera Makro'), 0.00, 'gramos'),  -- no se usa directo, sólo trufa y rabo
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 200.00, 'mililitros'),
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 20.00, 'gramos'),
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Harina de trigo'), 20.00, 'gramos'),
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Aceite de girasol'), 10.00, 'mililitros'),
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso parmesano'), 20.00, 'gramos'),
  (23, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Sal fina'), 1.00, 'gramos'); 

-- 24. Roast Beef con puré de patatas (plato_id = 24)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (24, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Solomillo Ternera Makro'), 500.00, 'gramos'),
  (24, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Patata'), 500.00, 'gramos'),
  (24, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 100.00, 'mililitros'),
  (24, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 30.00, 'gramos'),
  (24, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Sal fina'), 2.00, 'gramos'),
  (24, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Pimienta'), 1.00, 'gramos');
  
-- 25. Alfajores (plato_id = 25)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 3.33, 'gramos'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar glass'), 2.78, 'gramos'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.07, 'unidades'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Extracto de vainilla'), 0.22, 'mililitros'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Harina de trigo'), 3.67, 'gramos'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Maicena'), 4.44, 'gramos'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Bicarbonato'), 0.07, 'gramos'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Impulsor'), 0.11, 'gramos'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche condensada'), 10.00, 'gramos'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Sal fina'), 0.09, 'gramos'),
  (25, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Coco rallado'), 0.33, 'gramos');

-- 26. Merenguitos (plato_id = 26)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (26, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Claras Huevo'), 0.03, 'unidades'),
  (26, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 2.08, 'gramos');

-- 27. Mini tartas de queso (plato_id = 27)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (27, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Queso crema'), 11.43, 'gramos'),
  (27, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.06, 'unidades'),
  (27, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Maicena'), 0.21, 'gramos'),
  (27, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 2.86, 'gramos'),
  (27, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nata para montar'), 5.71, 'mililitros'),
  (27, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Galletas maria'), 4.29, 'gramos'),
  (27, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 0.86, 'gramos');

-- 28. Mini tiramisú (plato_id = 28)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (28, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mascarpone'), 25.00, 'gramos'),
  (28, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.10, 'unidades'),
  (28, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Café'), 1.50, 'gramos'),
  (28, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 4.00, 'gramos'),
  (28, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cacao en polvo'), 1.50, 'gramos'),
  (28, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Bizcochos soletilla'), 2.00, 'gramos');

-- 29. Panna cotta con coulis de frutos rojos (plato_id = 29)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (29, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nata para montar'), 50.00, 'mililitros'),
  (29, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 10.00, 'mililitros'),
  (29, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 15.00, 'gramos'),
  (29, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Hojas de gelatina'), 0.40, 'unidades'),
  (29, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Extracto de vainilla'), 1.50, 'mililitros'),
  (29, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Frutos rojos congelados'), 20.00, 'gramos'),
  (29, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Hierbabuena'), 1.70, 'gramos');

-- 30. Tarta 3 chocolates (plato_id = 30)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (30, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Galletas maria'), 14.29, 'gramos'),
  (30, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 5.71, 'gramos'),
  (30, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Chocolate negro'), 10.71, 'gramos'),
  (30, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nata para montar'), 14.29, 'mililitros'),
  (30, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cuajada polvo'), 0.14, 'unidades'),
  (30, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche entera'), 1.43, 'mililitros'),
  (30, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Chocolate con leche'), 10.71, 'gramos'),
  (30, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Chocolate blanco'), 10.71, 'gramos');

-- 31. Tarta banoffe (plato_id = 31)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (31, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Galletas maria'), 16.67, 'gramos'),
  (31, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 8.33, 'gramos'),
  (31, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Leche condensada'), 50.00, 'gramos'),
  (31, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Plátano'), 10.00, 'gramos'),
  (31, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Nata para montar'), 18.58, 'mililitros'),
  (31, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar glass'), 1.42, 'gramos');

-- 32. Tartaletas de limón (plato_id = 32)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (32, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Tartaletas'), 1.00, 'unidades'),
  (32, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.33, 'unidades'),
  (32, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Limón'), 1.67, 'gramos'),
  (32, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Jugo limón'), 13.33, 'mililitros'),
  (32, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 11.11, 'gramos'),
  (32, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Maicena'), 3.33, 'gramos'),
  (32, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Claras Huevo'), 0.33, 'unidades');

-- 33. Trufas de chocolate (plato_id = 33)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (33, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Cacao en polvo'), 2.42, 'gramos'),
  (33, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Mantequilla con sal'), 1.93, 'gramos'),
  (33, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 1.33, 'gramos'),
  (33, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.04, 'unidades'),
  (33, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Chocolate granulado mixto'), 1.11, 'gramos');

-- 34. Yemas (plato_id = 34)
INSERT INTO plato_ingrediente (plato_id, ingrediente_id, cantidad_usada, unidad) VALUES
  (34, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Huevo'), 0.20, 'unidades'),
  (34, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar'), 3.33, 'gramos'),
  (34, (SELECT ingrediente_id FROM ingrediente WHERE nombre = 'Azúcar glass'), 2.50, 'gramos');
  
