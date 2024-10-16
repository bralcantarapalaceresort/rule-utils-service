
# Proyecto Spring Boot con Neo4j for Rule Utils

Este proyecto es una implementación de un sistema de gestión de procesos de negocio utilizando Spring Boot y Neo4j como base de datos. El sistema permite la creación y gestión de entidades como **Company** y **Department**, las cuales están relacionadas entre sí.

## Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

- **Company**: Representa una organización o compañía. Cada compañía tiene atributos como `processId`, `name`, `initialDate`, `endDate`, `isActive`, `mision`, y `vision`.
- **Department**: Representa un departamento dentro de una compañía. Cada departamento tiene atributos como `processId`, `name`, `initialDate`, `endDate`, `active` y `objective`.
- **Area**:
- **Process**:
- **SubProcess**:

## Tecnologías Utilizadas

- **Spring Boot**: Framework principal para el desarrollo de la aplicación.
- **Neo4j**: Base de datos orientada a grafos utilizada para almacenar y gestionar las relaciones entre las entidades.
- **Java**: Lenguaje de programación utilizado para desarrollar el backend.
- **Docker**: Utilizado para levantar Neo4j en un contenedor.
- **Apache Maven**: Herramienta de construcción y gestión del ciclo de vida del proyecto.

## Requisitos Previos

1. **JDK 17** o superior.
2. **Maven** para gestionar las dependencias del proyecto.
3. **Neo4j** configurado en Docker o instalado localmente.

### Comando para levantar Neo4j en Docker:
```bash
docker run --name neo4j -p7474:7474 -p7687:7687 -d neo4j:latest
```

## Configuración de la Base de Datos

La conexión a Neo4j debe estar configurada en el archivo `application.properties` o `application.yml`. A continuación, se muestra un ejemplo de configuración en `application.properties`:

```properties
spring.neo4j.uri=bolt://localhost:7687
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=neo4j_password
```

## Ejecución del Proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/usuario/proyecto-spring-neo4j.git
   ```

2. Navegar al directorio del proyecto:
   ```bash
   cd proyecto-spring-neo4j
   ```

3. Construir el proyecto usando Maven:
   ```bash
   mvn clean install
   ```

4. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints

### 1. Crear una **Company**
```http
POST /api/company
```
**Body**:
```json
{
  "processId": "uuid",
  "name": "Palace Resorts",
  "initialDate": "2024-01-01T00:00:00Z",
  "endDate": "2027-01-01T00:00:00Z",
  "isActive": false,
  "mision": "To provide unforgettable vacation experiences...",
  "vision": "To be the leading global provider of all-inclusive luxury vacations..."
}
```

### 2. Crear un **Department**
```http
POST /api/department
```
**Body**:
```json
{
  "processId": "uuid",
  "name": "Marketing",
  "initialDate": "2024-01-01T00:00:00Z",
  "endDate": "2027-01-01T00:00:00Z",
  "active": true,
  "objective": "To enhance the brand's visibility..."
}
```

## Clases Principales

### Company
```java
public class Company {
    private UUID processId;
    private String name;
    private LocalDateTime initialDate;
    private LocalDateTime endDate;
    private boolean isActive;
    private String mission;
    private String vision;

    // Getters y setters
}
```

### Department
```java
public class Department {
    private UUID processId;
    private String name;
    private LocalDateTime initialDate;
    private LocalDateTime endDate;
    private boolean active;
    private String objective;

    // Getters y setters
}
```

## Documentación de Código

El código está documentado en inglés para facilitar su comprensión. Cada clase y método principal tiene comentarios que explican su propósito y funcionamiento.

## Contribuciones

Si deseas contribuir a este proyecto, puedes realizar un fork y enviar tus pull requests.

## Licencia

Este proyecto está bajo la licencia MIT. Para más información, consulta el archivo [LICENSE](LICENSE).
