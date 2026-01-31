# OpenAPI Integration with Spring Boot

This folder contains a duplicate version of the dependency injection demo with OpenAPI integration added.

## Project Structure

```
src/main/java/za/co/test/demo/
├── openapi/                          # OpenAPI integration version
│   ├── skills/
│   │   ├── Skill.java               # Skill interface
│   │   ├── JavaSkill.java           # Java skill implementation
│   │   └── DatabaseSkill.java       # Database skill implementation
│   ├── Employee.java                 # Employee with getSkills() method for API
│   ├── SkillInfo.java                # DTO for API response
│   ├── EmployeeController.java       # REST API endpoints with OpenAPI docs
│   ├── OpenApiConfig.java            # OpenAPI configuration
│   └── OpenApiDemoRunner.java        # Demo runner for OpenAPI version
└── [original files...]               # Original dependency injection demo
```

## Key Differences from Original

### 1. Added Dependencies
```xml
<!-- Spring Boot Web Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- OpenAPI Documentation -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.0</version>
</dependency>
```

### 2. REST Controller with OpenAPI Annotations
```java
@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees and skills")
public class EmployeeController {
    
    @GetMapping("/skills")
    @Operation(summary = "Get employee skills", description = "Returns list of skills available to the employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of skills retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No skills found")
    })
    public ResponseEntity<List<SkillInfo>> getSkills() { ... }
}
```

### 3. OpenAPI Configuration
```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Employee Skills API")
                .version("1.0")
                .description("API demonstrating dependency injection with employee skills"));
    }
}
```

### 4. Enhanced Employee Class
```java
@Component
public class Employee {
    private final List<Skill> skills;
    
    // Added getSkills() method for API access
    public List<Skill> getSkills() {
        return skills;
    }
    
    // Original work() method remains unchanged
    public void work() { ... }
}
```

## Running the Application

1. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```

2. **Access API endpoints:**
   ```bash
   # Get employee work output
   curl http://localhost:8080/api/employees/work
   
   # Get employee skills
   curl http://localhost:8080/api/employees/skills
   ```

3. **View OpenAPI Documentation:**
   - Interactive UI: http://localhost:8080/swagger-ui.html
   - OpenAPI JSON: http://localhost:8080/v3/api-docs

## Expected Output

### Console Output:
```
=== OpenAPI Integration Demo ===
Employee created with 2 skills:
- Java Programming
- Database Management

API Endpoints available:
- GET http://localhost:8080/api/employees/work
- GET http://localhost:8080/api/employees/skills
- OpenAPI UI: http://localhost:8080/swagger-ui.html
=== Demo Complete ===
```

### API Responses:
```bash
GET /api/employees/skills
Response:
[
  {"name": "Java Programming"},
  {"name": "Database Management"}
]

GET /api/employees/work
Response:
"Employee completed work with injected skills"
```

## Benefits of OpenAPI Integration

1. **Automatic Documentation**: API docs generated from code annotations
2. **Interactive Testing**: Test endpoints in Swagger UI
3. **Client Generation**: Generate SDKs in multiple languages
4. **API Validation**: Ensure contract compliance
5. **Living Documentation**: Docs always match actual API

## Dependency Injection + OpenAPI

This demonstrates how Spring's dependency injection works seamlessly with OpenAPI:

1. **Spring creates beans**: `JavaSkill`, `DatabaseSkill`, `Employee`, `EmployeeController`
2. **Dependencies injected**: Skills → Employee → Controller
3. **OpenAPI scans annotations**: Generates documentation automatically
4. **REST endpoints available**: Accessible via HTTP with full documentation

The same dependency injection principles apply, but now exposed through a well-documented REST API!
