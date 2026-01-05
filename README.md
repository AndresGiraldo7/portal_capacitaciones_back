# Portal de Capacitaciones Interactivo 🎓

Portal web para consulta y seguimiento de capacitaciones del CoE de Desarrollo. Permite a los colaboradores explorar cursos organizados por módulos, registrar su progreso y obtener insignias al completar capacitaciones.

## 🔗 Enlaces Rápidos

| Recurso | URL |
|---------|-----|
| **Aplicación (Frontend)** | [https://portal-capacitaciones-front-fv8q.onrender.com](https://portal-capacitaciones-front-fv8q.onrender.com) |
| **Documentación API (Swagger)** | [https://portal-capacitaciones-back-ifot.onrender.com/swagger-ui/index.html](https://portal-capacitaciones-back-ifot.onrender.com/swagger-ui/index.html) |
| **Repositorio Backend** | [https://github.com/AndresGiraldo7/portal_capacitaciones_back](https://github.com/AndresGiraldo7/portal_capacitaciones_back) |
| **Repositorio Frontend** | [https://github.com/AndresGiraldo7/portal_capacitaciones_front](https://github.com/AndresGiraldo7/portal_capacitaciones_front) |

## 🔑 Credenciales de Prueba

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| `admin_demo` | `admin_123` | ADMIN |
| `user_demo` | `user_123` | USER |

## 🛠️ Stack Tecnológico

### Frontend
- **Framework:** Angular 20
- **Estilos:** TailwindCSS
- **Routing:** Angular Router con lazy loading
- **HTTP Client:** HttpClient de Angular
- **Build:** Angular CLI con optimización de producción

### Backend
- **Framework:** Spring Boot 4.0.1
- **Lenguaje:** Java 21 (LTS)
- **Empaquetado:** WAR (Web Application Archive)
- **ORM:** Hibernate + Spring Data JPA
- **Servidor:** Tomcat embebido
- **Pool de conexiones:** HikariCP

### Base de Datos
- **Motor:** PostgreSQL 15
- **Nombre:** `core_banca_db`

### Email Service - MailHog
- **Nombre:** mailhog-service
- **Tipo:** Image Service
- **Región:** Oregon (us-west)
- **Imagen:** mailhog/mailhog:latest
- **Puertos:**
  - `1025`: SMTP (interno)
  - `8025`: Web UI (público)

### Herramientas de Desarrollo
- **Control de versiones:** Git
- **Gestión de dependencias:**
  - Maven (Backend)
  - npm (Frontend)
- **IDE recomendado:** VS Code / Spring Tools

## 📐 Arquitectura del Sistema

```
┌─────────────────┐
│   Frontend      │
│   (Angular)     │
└────────┬────────┘
         │ HTTP/REST
         ↓
┌─────────────────┐
│   Backend       │
│ (Spring Boot)   │
└────────┬────────┘
         │ JPA/Hibernate
         ↓
┌─────────────────┐
│   PostgreSQL    │
│  core_banca_db  │
└─────────────────┘
```

## ✨ Funcionalidades

### Requerimientos Principales
- ✅ **Autenticación:** Login con usuario y contraseña
- ✅ **Módulos de Capacitación:**
  - 🖥️ Fullstack
  - 🔗 APIs e Integraciones
  - ☁️ Cloud
  - 📊 Data Engineer
- ✅ **Seguimiento de Cursos:** Marcar como "iniciado" o "completado"
- ✅ **Sistema de Insignias:** Otorgadas al completar cursos
- ✅ **Panel de Administración:** CRUD completo de cursos

### Funcionalidades Adicionales
- 📊 Dashboard con estadísticas personales
- 📜 Historial completo de cursos
- 👤 Perfil de usuario con progreso por módulo
- 🕒 Actividad reciente
- 🏆 Badges visuales de estado

## 🗄️ Esquema de Base de Datos

### Tabla: `usuario`
```sql
CREATE TABLE usuario (
    id_usuario      SERIAL PRIMARY KEY,
    username        VARCHAR(50) UNIQUE NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,
    nombre          VARCHAR(100) NOT NULL,
    email           VARCHAR(100),
    rol             VARCHAR(20) NOT NULL CHECK (rol IN ('USER', 'ADMIN')),
    fecha_creacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tabla: `modulo`
```sql
CREATE TABLE modulo (
    id_modulo   SERIAL PRIMARY KEY,
    nombre      VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT
);
```
**Módulos disponibles:** Fullstack, APIs e Integraciones, Cloud, Data Engineer

### Tabla: `curso`
```sql
CREATE TABLE curso (
    id_curso       SERIAL PRIMARY KEY,
    id_modulo      INTEGER NOT NULL REFERENCES modulo(id_modulo),
    titulo         VARCHAR(200) NOT NULL,
    descripcion    TEXT,
    url_contenido  VARCHAR(500),
    activo         BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
- **Índice:** `idx_curso_modulo` en `id_modulo`

### Tabla: `progreso_curso`
```sql
CREATE TABLE progreso_curso (
    id_progreso      SERIAL PRIMARY KEY,
    id_usuario       INTEGER NOT NULL REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    id_curso         INTEGER NOT NULL REFERENCES curso(id_curso) ON DELETE CASCADE,
    estado           VARCHAR(20) NOT NULL CHECK (estado IN ('NO_INICIADO', 'EN_PROGRESO', 'COMPLETADO')),
    fecha_inicio     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_completado TIMESTAMP,
    UNIQUE(id_usuario, id_curso)
);
```
- **Índices:** 
  - `idx_progreso_usuario` en `id_usuario`
  - `idx_progreso_estado` en `estado`
- **Constraint:** Un usuario no puede tener progreso duplicado en el mismo curso

### Tabla: `insignia`
```sql
CREATE TABLE insignia (
    id_insignia SERIAL PRIMARY KEY,
    nombre      VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT,
    imagen_url  VARCHAR(255)
);
```

### Tabla: `usuario_insignia`
```sql
CREATE TABLE usuario_insignia (
    id_usuario     INTEGER NOT NULL REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    id_insignia    INTEGER NOT NULL REFERENCES insignia(id_insignia) ON DELETE CASCADE,
    fecha_otorgada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_insignia)
);
```

### Relaciones
```
usuario (1) ──────< (N) progreso_curso
modulo  (1) ──────< (N) curso
curso   (1) ──────< (N) progreso_curso
usuario (N) >─────< (N) insignia (via usuario_insignia)
```

## 🚀 Configuración Local

### Prerequisitos
- Node.js 18+ y npm
- Java 21 (JDK)
- Maven 3.8+
- PostgreSQL 15+
- Docker (para MailHog)

### Backend

1. **Clonar el repositorio:**
```bash
git clone https://github.com/AndresGiraldo7/portal_capacitaciones_back
cd backend
```

2. **Configurar base de datos:**
Editar `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/core_banca_db
spring.datasource.username=svc_banca_app
spring.datasource.password=B4nc0_2026!Pg#Secure
```

3. **Levantar MailHog:**
```bash
docker run -d -p 1025:1025 -p 8025:8025 mailhog/mailhog
```
UI disponible en: http://localhost:8025

4. **Ejecutar aplicación:**
```bash
./mvnw spring-boot:run
```
Backend disponible en: **http://localhost:8080**

### Frontend

1. **Instalar dependencias:**
```bash
cd frontend
npm install
```

2. **Verificar configuración:**
Editar `src/environments/environment.ts`
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

3. **Ejecutar en desarrollo:**
```bash
ng serve
```
Frontend disponible en: **http://localhost:4200**

## 📝 Licencia

Este proyecto es parte del CoE de Desarrollo.

---

**Desarrollado con ❤️ por el equipo de CoE de Desarrollo**
