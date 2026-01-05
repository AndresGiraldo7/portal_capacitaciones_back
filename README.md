Portal de Capacitaciones Interactivo
Portal web para consulta y seguimiento de capacitaciones del CoE de Desarrollo. Permite a los colaboradores explorar cursos organizados por módulos, registrar su progreso y obtener insignias al completar capacitaciones.
📋 Tabla de Contenidos

Stack Tecnológico
Funcionalidades
Base de Datos
Instalación
Credenciales de Prueba

🛠 Stack Tecnológico

Frontend: Angular 20 + TailwindCSS
Backend: Spring Boot 4.0.1 + Java 21
Base de Datos: PostgreSQL
Despliegue: Render (frontend y backend)

✨ Funcionalidades
Requerimientos Implementados

Autenticación: Login con usuario y contraseña
Módulos de Capacitación:

🖥 Fullstack
🔗 APIs e Integraciones
☁️ Cloud
📊 Data Engineer


Seguimiento de Cursos: Marcar como "iniciado" o "completado"
Sistema de Insignias: Otorgadas al completar cursos
Panel de Administración: CRUD completo de cursos

Funcionalidades Adicionales

Dashboard con estadísticas personales
Historial completo de cursos
Perfil de usuario con progreso por módulo
Actividad reciente
Badges visuales de estado

🗄 Base de Datos
Información General

Motor: PostgreSQL
Nombre: core_banca_db

Esquema de Tablas
1. usuario
Almacena información de colaboradores del sistema.
sqlCREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('USER', 'ADMIN')),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
2. modulo
Define los módulos de capacitación disponibles.
sqlCREATE TABLE modulo (
    id_modulo SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT
);
Módulos Predefinidos:

Fullstack
APIs e Integraciones
Cloud
Data Engineer

3. curso
Contiene los cursos de cada módulo.
sqlCREATE TABLE curso (
    id_curso SERIAL PRIMARY KEY,
    id_modulo INTEGER NOT NULL REFERENCES modulo(id_modulo),
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    url_contenido VARCHAR(500),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_curso_modulo ON curso(id_modulo);
4. progreso_curso
Registra el progreso de cada usuario en los cursos.
sqlCREATE TABLE progreso_curso (
    id_progreso SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    id_curso INTEGER NOT NULL REFERENCES curso(id_curso) ON DELETE CASCADE,
    estado VARCHAR(20) NOT NULL CHECK (estado IN ('NO_INICIADO', 'EN_PROGRESO', 'COMPLETADO')),
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_completado TIMESTAMP,
    UNIQUE(id_usuario, id_curso)
);

CREATE INDEX idx_progreso_usuario ON progreso_curso(id_usuario);
CREATE INDEX idx_progreso_estado ON progreso_curso(estado);
Constraint: Un usuario no puede tener progreso duplicado en el mismo curso.
5. insignia
Define las insignias otorgables al completar cursos.
sqlCREATE TABLE insignia (
    id_insignia SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT,
    imagen_url VARCHAR(255)
);
Insignias de Ejemplo:

Primer Curso Completado
Explorador de Módulos
Maestro del Aprendizaje

6. usuario_insignia
Relación muchos a muchos entre usuarios e insignias.
sqlCREATE TABLE usuario_insignia (
    id_usuario INTEGER NOT NULL REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    id_insignia INTEGER NOT NULL REFERENCES insignia(id_insignia) ON DELETE CASCADE,
    fecha_otorgada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_insignia)
);
```

### Diagrama de Relaciones
```
usuario (1) ──────< (N) progreso_curso
modulo (1) ──────< (N) curso
curso (1) ──────< (N) progreso_curso
usuario (N) >─────< (N) insignia (via usuario_insignia)
🚀 Instalación
bash# Clonar el repositorio
git clone <url-del-repositorio>

# Backend
cd backend
./mvnw spring-boot:run

# Frontend
cd frontend
npm install
ng serve
🔑 Credenciales de Prueba
Administrador

Usuario: admin_demo
Contraseña: admin_123

Usuario Regular

Usuario: user_demo
Contraseña: user_123
