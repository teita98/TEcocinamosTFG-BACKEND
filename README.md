# TECOCINAMOS – Backend (API REST)

Este repositorio contiene la implementación del **backend** de TECOCINAMOS, una plataforma de gestión para servicios de catering.  
Está construido con **Spring Boot 3.4.4**, **Spring Security** (JWT), **MySQL 8**, **iText7** (para generación de facturas PDF) y **Spring Mail** (para envío de correos).

---

## 🔎 Descripción general

La API REST de TECOCINAMOS ofrece todos los servicios necesarios para:

- Autenticación y autorización mediante JWT (roles CLIENTE y ADMIN).
- Gestión de usuarios, roles, categorías, alérgenos, proveedores e ingredientes.
- CRUD de platos (asociados a categorías e ingredientes), pedidos y estados de pedido.
- Generación de facturas en PDF (iText7) y envío de correos automáticos (Spring Mail).
- Auditoría de cambios (log de auditoría).
- Consultas estadísticas (top platos vendidos, ingresos por período, pedidos por estado).

---

## 📋 Prerrequisitos

Antes de arrancar el backend, asegúrate de tener:

1. **Java 17** (sólo Java 17 o superior, ya que Spring Boot 3 requiere Java 17+).
2. **Maven 3.6+** o superior.
3. **MySQL 8** (o MariaDB compatible).
4. Una cuenta de correo SMTP para pruebas de envío (p. ej. Gmail o similar).

---

## ⚙️ Configuración inicial

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/teita98/TEcocinamosTFG-BACKEND.git
   cd TEcocinamosTFG-BACKEND
   ```

2. **Crear la base de datos en MySQL**
   En tu servidor MySQL (local o remoto), crea una base de datos llamada `tecocinamos_db`.

```sql
CREATE DATABASE tecocinamos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

3. **Configurar las credenciales en `application.properties`**
   Edita `src/main/resources/application.properties` y ajusta los siguientes valores:

   ```yaml
   # Nombre de la aplicación
    spring.application.name=tecocinamos-backend

    # DATOS DE CONEXIÓN A MYSQL
    spring.datasource.url=jdbc:mysql://localhost:3306/tecocinamos
    spring.datasource.username=tu_usuario_mysql
    spring.datasource.password=tu_contraseña_mysql

    # Modo de validación JPA: validate, update, create, create-drop
    spring.jpa.hibernate.ddl-auto=validate

    # Mostrar SQL en consola
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true

    # CONFIGURACIÓN DE JWT
    # Debe coincidir con la clave de 256 bits (hex) que usas en JwtUtils
    jwt.secret=ARBITRARY_SECRET_KEY_SUPERSECRETA
    # Tiempo de expiración en milisegundos
    jwt.expiration=3600000 # 1 hora

    # CONFIGURACIÓN DE CORREO (SMTP)
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=tu_correo@gmail.com
    spring.mail.password=tu_password_de_app_o_smtp
    spring.mail.protocol=smtp
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    spring.mail.properties.mail.smtp.starttls.required=true
    spring.mail.properties.mail.debug=false

   ```

   - **Nota**: si usas Gmail, revisa que hayas generado una contraseña de aplicación (App Password).
   - Cambia `secret` por una cadena larga y segura.

4. **Levantar MySQL**

   - En las carpertas del backend en `/resources`encontrareis:

     1. El schema de la bbdd: `tecocinamos_schema.sql`
     2. Los datos de prueba a insertar: `tecocinamos_seed.sql`
     3. Los triggers, indices, procedimientos y funciones usados: `tecocinamos_extra.sql`

---

## 🚀 Ejecutar la aplicación

Una vez configurada la base de datos y el archivo `application-properties`, puedes compilar y arrancar el backend:

```bash
# 1. Construir con Maven
mvn clean install -DskipTests

# 2. Levantar la aplicación
mvn spring-boot:run
```

Por defecto, la API quedará escuchando en `http://localhost:8080`.

---

## 🔐 Autenticación y roles

- Se emplea **JWT** (JSON Web Tokens) para proteger la mayoría de rutas.
- **Rutas públicas** (no requieren token):

  - `POST /api/v1/auth/register`
  - `POST /api/v1/auth/login`
  - `GET  /api/v1/platos/**`
  - `GET  /api/v1/categorias/**`
  - `GET  /api/v1/alergenos/**`
  - `GET  /api/v1/ingredientes/**`
  - `POST /api/v1/contact/**` (formulario de contacto/evento)

- **Rutas protegidas** (requieren header `Authorization: Bearer <token>`). Dentro de ellas, algunas requieren **rol ADMIN** y otras permiten tanto ADMIN como CLIENTE:

  - **ADMIN only**:

    - Gestión de usuarios (`/api/v1/users/**`)
    - Gestión de roles (`/api/v1/roles/**`)
    - CRUD de categorías (`/api/v1/categorias/**`)
    - CRUD de alérgenos (`/api/v1/alergenos/**`)
    - CRUD de proveedores (`/api/v1/proveedores/**`)
    - CRUD de ingredientes (`/api/v1/ingredientes/**`)
    - CRUD de platos (`/api/v1/platos` POST, PUT, DELETE)
    - Gestión de pedidos (`PUT /api/v1/pedidos/{id}/estado/{estadoId}`, `GET /api/v1/pedidos`, etc.)
    - Generación de facturas (`GET /api/v1/facturas/{pedidoId}`)
    - Estadísticas (`/api/v1/estadisticas/**`)
    - Auditoría (`/api/v1/logs`)

  - **ADMIN o CLIENTE**:

    - `GET /api/v1/estados`
    - `POST /api/v1/pedidos` (crear pedido)
    - `GET  /api/v1/pedidos/usuario` (historial de pedidos del usuario autenticado)
    - `GET  /api/v1/pedidos/{id}` (detalle de un pedido)
    - `DELETE /api/v1/pedidos/{id}` (cancelar o eliminar pedido)
    - `PUT /api/v1/users/password` (cambiar contraseña)
    - `PUT /api/v1/users/perfil` (editar perfil)
    - `GET  /api/v1/users/me` (obtener perfil actual)

- **Roles disponibles**:

  - `CLIENTE` (rol por defecto al registrarse).
  - `ADMIN` (privilegios totales).

---

## 📑 Principales endpoints

> A continuación un resumen, pero consulta la documentación completa (o mira el controlador correspondiente) para ver todos los parámetros, cuerpos JSON y ejemplos de respuesta.

### 1. Autenticación (AuthController)

- **POST /api/v1/auth/register**:
  Registra un usuario con rol CLIENTE.

  - Body JSON:

    ```json
    {
      "nombre": "teresa",
      "email": "teresa@correo.com",
      "contrasena": "MiPassword123!",
      "telefono": "+34-600-111-222"
    }
    ```

  - Respuesta (201 Created):

    ```json
    {
      "id": 5,
      "nombre": "teresa",
      "email": "teresa@correo.com",
      "rol": "CLIENTE",
      "fechaCreado": "2025-06-02"
    }
    ```

- **POST /api/v1/auth/login**:
  Valida credenciales y devuelve un token JWT.

  - Body JSON:

    ```json
    {
      "email": "teresa@correo.com",
      "contrasena": "MiPassword123!"
    }
    ```

  - Respuesta (200 OK):

    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```

### 2. Usuarios (UsuarioController)

- **GET /api/v1/users/me**: Perfil del usuario autenticado. (CLIENTE o ADMIN)
- **PUT /api/v1/users/password**: Cambiar contraseña. (CLIENTE o ADMIN)
- **PUT /api/v1/users/perfil**: Actualizar nombre y teléfono. (CLIENTE o ADMIN)
- **GET /api/v1/users/profile/{nombre}**: Perfil público. (público)
- **GET /api/v1/users/search?name={nombre}**: Buscar usuarios. (ADMIN)
- **GET /api/v1/users**: Listar todos los usuarios. (ADMIN)
- **DELETE /api/v1/users/{id}**: Eliminar (soft delete) usuario. (ADMIN)
- **PUT /api/v1/users/{id}/rol/{rolId}**: Asignar rol a usuario. (ADMIN)

### 3. Roles (RolController)

- **GET /api/v1/roles**: Listar roles. (ADMIN)
- **PUT /api/v1/roles/{id}**: Actualizar rol. (ADMIN)

### 4. Categorías (CategoriaController)

- **GET /api/v1/categorias**: Listar categorías. (público)

### 5. Alérgenos (AlergenoController)

- **GET /api/v1/alergenos**: Listar alérgenos. (público)
- **POST /api/v1/alergenos?nombre=xxxx**: Crear alérgeno. (ADMIN)
- **DELETE /api/v1/alergenos/{id}**: Eliminar alérgeno. (ADMIN)

### 6. Proveedores (ProveedorController)

- **POST /api/v1/proveedores**: Crear proveedor. (ADMIN)
- **GET /api/v1/proveedores**: Listar proveedores. (ADMIN)
- **GET /api/v1/proveedores/{id}**: Obtener proveedor por ID. (ADMIN)
- **PUT /api/v1/proveedores/{id}**: Actualizar proveedor. (ADMIN)
- **DELETE /api/v1/proveedores/{id}**: Eliminar proveedor. (ADMIN)

### 7. Ingredientes (IngredienteController)

- **GET /api/v1/ingredientes?page=\&size=**: Paginado de ingredientes. (público)
- **GET /api/v1/ingredientes/{id}**: Obtener ingrediente por ID. (público)
- **GET /api/v1/ingredientes/por-proveedor/{id}?page=\&size=**: Listar por proveedor. (público)
- **GET /api/v1/ingredientes/{id}/alergenos**: Alérgenos de un ingrediente. (público)
- **POST /api/v1/ingredientes/{ingredienteId}/alergenos/{alergenoId}**: Añadir alérgeno. (ADMIN)
- **PUT /api/v1/ingredientes/{id}**: Actualizar ingrediente. (ADMIN)
- **DELETE /api/v1/ingredientes/{id}**: Eliminar ingrediente. (ADMIN)

### 8. Platos (PlatoController)

- **GET /api/v1/platos?page=\&size=**: Listar platos (paginado). (público)
- **GET /api/v1/platos/{id}**: Obtener plato por ID. (público)
- **GET /api/v1/platos/categoria?nombre=\&page=\&size=**: Buscar por categoría. (público)
- **GET /api/v1/platos/buscar?nombre=\&page=\&size=**: Buscar por nombre. (público)
- **GET /api/v1/platos/{id}/ingredientes**: Ingredientes de un plato. (público)
- **GET /api/v1/platos/{id}/alergenos**: Alérgenos de un plato. (público)
- **POST /api/v1/platos**: Crear plato. (ADMIN)
- **PUT /api/v1/platos/{id}**: Actualizar plato. (ADMIN)
- **DELETE /api/v1/platos/{id}**: Eliminar plato. (ADMIN)

### 9. Estados de pedido (EstadoController)

- **GET /api/v1/estados**: Listar estados. (CLIENTE o ADMIN)
- **POST /api/v1/estados**: Crear estado. (ADMIN)
- **DELETE /api/v1/estados/{id}**: Eliminar estado. (ADMIN)

### 10. Pedidos (PedidoController)

- **POST /api/v1/pedidos**: Crear pedido. (CLIENTE o ADMIN)

  - Se genera PDF y se envía correo al cliente.

- **GET /api/v1/pedidos/usuario**: Listar pedidos del usuario autenticado. (CLIENTE o ADMIN)
- **GET /api/v1/pedidos?page=\&size=\&estadoId=**: Listar todos los pedidos. (ADMIN)
- **GET /api/v1/pedidos/{id}**: Obtener pedido por ID. (ADMIN o CLIENTE que posee el pedido)
- **DELETE /api/v1/pedidos/{id}**: Cancelar/Eliminar pedido. (CLIENTE o ADMIN)
- **PUT /api/v1/pedidos/{id}/estado/{estadoId}**: Cambiar estado de pedido. (ADMIN)

### 11. Facturas (FacturaController)

- **GET /api/v1/facturas/{pedidoId}**: Descargar factura PDF. (ADMIN o CLIENTE dueño del pedido)

### 12. Estadísticas (EstadisticaController) – Sólo ADMIN

- **GET /api/v1/estadisticas/top-platos?topN={n}**: Top N platos vendidos.
- **GET /api/v1/estadisticas/ingresos?desde=\&hasta=**: Ingresos por período.
- **GET /api/v1/estadisticas/pedidos-por-estado**: Pedidos agrupados por estado.

### 13. Auditoría (LogAuditoriaController) – Sólo ADMIN

- **GET /api/v1/logs**: Listar logs de auditoría.

---

## 🙋‍♂️ Soporte y contribución

- Si encuentras errores o quieres proponer mejoras, abre un _issue_ o haz un _pull request_.
- Sigue las guías del proyecto para formateo de código, convenciones de Git y estándares de Java.

---

¡Gracias por usar y contribuir al backend de TECOCINAMOS!
