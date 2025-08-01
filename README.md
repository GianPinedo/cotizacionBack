# 🚘 Backend - Cotizador de Seguros Vehiculares

Este sistema permite simular el costo de asegurar un vehículo, calculando la prima total según criterios predefinidos como marca, año, edad del conductor y tipo de uso. Se construyó utilizando Java 17, Spring Boot WebFlux y Redis para cacheo de cotizaciones.

---

## 📌 Descripción del sistema

El backend expone un conjunto de endpoints REST que permiten:

- Realizar la **cotización** de un seguro vehicular
- Obtener la lista de **marcas** disponibles
- Obtener los **tipos de uso** del vehículo
- Cachear solicitudes repetidas en Redis durante 5 minutos

---

## ✅ Requisitos previos

- Java 17+
- Maven 3.8+
- Redis (puedes usarlo local o con Docker)
- Docker (opcional, pero recomendado)
- Postman (para pruebas opcionales)

---

## ⚙️ Instalación local

1. **Clona el repositorio**

```bash
git clone https://github.com/tu-usuario/cotizador-backend.git
cd cotizador-backend
```

2. **Configura Redis**

   **Opción A: Redis con Docker (recomendado)**
   ```bash
   docker run -d --name redis-cotizador -p 6379:6379 redis:7-alpine
   ```

   **Opción B: Redis local**
   ```bash
   # En macOS con Homebrew
   brew install redis
   brew services start redis
   
   # Verificar que Redis está corriendo
   redis-cli ping
   ```

3. **Configura las variables de entorno**

   Crea un archivo `.env` en la raíz del proyecto:
   ```env
   SPRING_PROFILES_ACTIVE=local
   REDIS_HOST=localhost
   REDIS_PORT=6379
   SERVER_PORT=8080
   ```

4. **Instala las dependencias**

   ```bash
   mvn clean install
   ```

5. **Ejecuta la aplicación**

   ```bash
   mvn spring-boot:run
   ```

   O alternativamente:
   ```bash
   java -jar target/cotizador-backend-1.0.0.jar
   ```

6. **Verifica que la aplicación esté corriendo**

   ```bash
   curl http://localhost:8080/actuator/health
   ```

---

## 🐳 Despliegue con Docker

1. **Construye la imagen Docker**

   ```bash
   docker build -t cotizador-backend .
   ```

2. **Ejecuta con Docker Compose**

   ```bash
   docker-compose up -d
   ```

   Esto levantará tanto la aplicación como Redis automáticamente.

3. **Verifica los contenedores**

   ```bash
   docker-compose ps
   ```

---

## 🧪 Pruebas

1. **Ejecuta las pruebas unitarias**

   ```bash
   mvn test
   ```



2. **Ejemplo de solicitud de cotización**

   ```bash
   curl -X POST http://localhost:8080/api/cotizacion \
     -H "Content-Type: application/json" \
     -d '{
       "marca": "TOYOTA",
       "año": 2020,
       "edadConductor": 30,
       "tipoUso": "PARTICULAR"
     }'
   ```

---

## 📊 Endpoints disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/cotizacion` | Calcula la prima del seguro |
| GET | `/api/marcas` | Lista de marcas disponibles |
| GET | `/api/uso` | Tipos de uso del vehículo |
| GET | `/api/modelos` | Estado de la aplicación |

---



---

## 🐛 Troubleshooting

### Redis no conecta:
```bash
# Verificar si Redis está corriendo
docker ps | grep redis
# O si es instalación local
brew services list | grep redis
```

### Puerto 8080 ocupado:
```bash
# Cambiar puerto en application.properties o variable de entorno
export SERVER_PORT=8081
mvn spring-boot:run
```

### Problemas de Java:
```bash
# Verificar versión de Java
java -version
# Debe ser Java 17+
```

---

5. **Crear la tabla modelo**

   Conéctate a la base de datos y ejecuta el siguiente script SQL:
   ```sql
   CREATE DATABASE cotizacion;
   
   \c cotizacion;
   
   CREATE TABLE modelo (
       id SERIAL PRIMARY KEY,
       nombre VARCHAR(100) NOT NULL
      
   );