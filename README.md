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
