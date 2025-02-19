# 📝 Proyecto de Gestión de Eventos

Este proyecto es una aplicación de gestión de eventos desarrollada con **Spring Boot** y **MySQL**. La aplicación permite crear, actualizar, listar y eliminar eventos y participantes. Además, los eventos y participantes están relacionados, lo que permite la asociación de múltiples participantes a un evento.

## 🌟 Características

- 📅 Crear, actualizar, listar y eliminar eventos
- 👤 Crear, actualizar, listar y eliminar participantes
- 🔍 Filtrar eventos por fecha de inicio y fin
- 🕵️‍♂️ Filtrar participantes por fecha de registro

## 🛠 Requisitos Previos

- ☕ **Java** 11 o superior
- 🐘 **Maven** 3.6.3 o superior
- 🐳 **Docker** y **Docker Compose**
- 🐬 **MySQL** 8.0 o superior

## ⚙️ Configuración del Entorno

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/luciahernandezdev/apirest-eventos.git
  
   ```
2. **Configurar variables de entorno**:
- Asigna las siguiente variables de Entorno.
```bash

spring.datasource.url=${DB_URL}

spring.datasource.username=${DB_USER_NAME}

spring.datasource.password=${DB_PASSWORD}

```
3. **Crea los valores de las variables de entorno creadas.**
   Parte superior en Run -> Edit Config -> Variables de entornos -> (+) para agregar una variables
   y luego su valor en un conjunto <K,V>, asignales datos a las 3 varibales.

5. **Asegurate de que arranca la app con las nuevas variables insertadas.**
5. **Configurar Docker Compose.**
    Asegúrate de que tienes un archivo docker-compose.yml en el directorio raíz del proyecto con el siguiente contenido:
 ```bash

version: '3'

services:
  app:
    build: app
    mem_limit: 512m
    ports:
      - "8080:8080"
    environment:
      DB_URL: jdbc:mysql://mysql:3306/cursos?createDatabaseIfNotExist=true&serverTimezone=UTC&allowPublicKeyRetrieval=true
      DB_USER_NAME: root
      DB_PASSWORD: ROOT
    restart: always
    depends_on:
      mysql:
          condition: service_healthy
  mysql:
    image: mysql:8.0.21 
    ports:
      - "3307:3306"
    environment:
      MYSQL_ROOT_PASSWORD: ROOT
      MYSQL_PASSWORD: ROOT
      MYSQL_DATABASE: cursos
    restart: always
    healthcheck:
      test: ["CMD", "mysqladmin" ,"ping", "-h", "localhost"]
      timeout: 10s
      retries: 10

```

6. **Construir y ejecutar los contenedores Docker.**

## 🚀 Uso y Endpoints de la API
Esta sección detalla cómo utilizar los endpoints de la API para interactuar con el sistema de gestión de eventos. A continuación, se describen los principales endpoints disponibles para manejar eventos y participantes.

## 📡 Endpoints Disponibles

### Eventos:
- Crear evento: POST /eventos
- Listar todos los eventos: GET /eventos
- Obtener evento por ID: GET /eventos/{id}
- Actualizar evento: PUT /eventos/{id}
- Eliminar evento: DELETE /eventos/{id}

### Participantes:

- Crear participante: POST /participantes
- Listar todos los participantes: GET /participantes
- Obtener participante por ID: GET /participantes/{id}
- Actualizar participante: PUT /participantes/{id}
- Eliminar participante: DELETE /participantes/{id}

## 🧪 Pruebas
Las pruebas unitarias están incluidas en el proyecto para las clases de servicio EventoService y ParticipanteService






   
