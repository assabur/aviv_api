
#  Architecture du projet Spring Boot — *Property API*

Ce document décrit l’architecture **logique** et **technique** d’un service Spring Boot qui expose la table `property` (PostgreSQL), avec couches **Controller → Service → Repository**, **DTOs**, **mappers MapStruct**, **JPA/Hibernate**, **validation**, **docs OpenAPI**, **tests** et **observabilité**.

---

## 🔭 Vue d’ensemble (couches)

```
┌──────────────┐   HTTP/JSON    ┌──────────────────────────┐
│  Controller  │  <──────────>  │  DTO (Request/Response)  │
└──────┬───────┘                └───────────┬──────────────┘
       │ (MapStruct)                         │
       ▼                                     ▼
┌──────────────┐    Règles métier, TX   ┌──────────────┐
│   Service    │  @Transactional        │   Mapper     │  (Entity ↔ DTO)
└──────┬───────┘                        └──────────────┘
       │
       ▼
┌──────────────┐    JPA/Hibernate
│ Repository   │  (Spring Data)
└──────┬───────┘
       │
       ▼
┌──────────────┐    PostgreSQL
│   Entity     │  (schéma public)
└──────────────┘
```



---

##  Domain & Persistence (JPA)

- **Entities** minimalistes (anémique) : mapping des colonnes, relations `@ManyToOne` si nécessaire.
- **Repositories** : interfaces `JpaRepository<Entity, Id>` + méthodes de requêtes custom
- **Transactions** : méthodes **service** annotées `@Transactional`

**Exemple :** `PropertyRepository`
```java
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {}
```

---

## DTOs & MapStruct

- **DTOs** : séparent le contrat HTTP du modèle de persistance (`PropertyDto` sans `id` côté POST).
- **MapStruct** : `@Mapper(componentModel="spring")` → bean injectable.
- **Compilation** : activer **annotation processing** (Maven `maven-compiler-plugin` avec `mapstruct-processor` + Lombok).

**Mapper simple (entity → dto uniquement) :**
```java
@Mapper(componentModel = "spring")
public interface PropertyMapper {
    PropertyDto toDto(Property entity);
}
```

---

## API REST

- **Controllers** : `@RestController` + `@RequestMapping("/properties")`.
- **Validation** : `@Valid` + annotations Jakarta (`@NotNull`, `@Positive`, …) sur les DTOs.
- **Erreurs** : `@ControllerAdvice` pour un JSON d’erreur homogène (code, message, timestamp).

**Search controller (extrait) :**
```java
@GetMapping("/search")
public ResponseEntity<ApiResponse<List<PropertyDto>>> search(
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice,
        @RequestParam(required = false) BigDecimal minArea,
        @RequestParam(required = false) BigDecimal maxArea,
        @RequestParam(required = false) Integer roomCount,
        @RequestParam(required = false) String city
) {
    List<PropertyDto> data = service.search(minPrice, maxPrice, minArea, maxArea, roomCount, city)
                                    .stream().map(mapper::toDto).toList();
    if (data.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.of(404, "Aucune propriété trouvée", data));
    }
    return ResponseEntity.ok(ApiResponse.of(200, "Propriétés trouvées: " + data.size(), data));
}
```

---

##  Configuration & Profils

**`application.yml` (exemple)**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_db
    username: your_user
    password: your_password
  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
server:
  port: 8080
```


## Observabilité : TODO

- **Actuator** : `/actuator/health`, `/metrics`, `/info` (ajoute `spring-boot-starter-actuator`).
- **Logs** : `logback-spring.xml` (JSON en prod, niveaux par package), MDC (correlation id).

---

## Build & Run

```bash
mvn spring-boot:run
# ou
mvn clean package
java -jar target/aviv-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

---

## Dockerisé l'api

**Dockerfile**
```dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/*.jar app.jar
ENV JAVA_OPTS="-Xms256m -Xmx512m"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

**Compose (PostgreSQL + API)**
```yaml
services:
  db:
    image: postgres:15
    environment:
      POSTGRES_DB: your_db
      POSTGRES_USER: your_user
      POSTGRES_PASSWORD: your_password
    ports: ["5432:5432"]
  api:
    build: .
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/your_db
      SPRING_DATASOURCE_USERNAME: your_user
      SPRING_DATASOURCE_PASSWORD: your_password
    ports: ["8080:8080"]
    depends_on: [db]
```

