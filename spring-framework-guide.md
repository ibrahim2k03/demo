# Spring Framework Guide

## Spring Framework

### What is Spring Framework?
Spring is a lightweight, open-source application framework for Java. It provides comprehensive infrastructure support for developing Java applications.

### What Problem Does It Solve?
**Before Spring:**
- Manual object creation and management
  ```java
  // Manual creation - tight coupling
  JavaSkill javaSkill = new JavaSkill();
  DatabaseSkill dbSkill = new DatabaseSkill();
  List<Skill> skills = Arrays.asList(javaSkill, dbSkill);
  Employee employee = new Employee(skills);
  ```
- Tight coupling between components
  ```java
  // Employee knows exactly which skills to create
  public Employee() {
      this.skills = Arrays.asList(new JavaSkill(), new DatabaseSkill());
  }
  ```
- Complex dependency management
  ```java
  // Manual wiring becomes complex with many dependencies
  ServiceA serviceA = new ServiceA(new RepositoryA(), new ServiceB(new RepositoryB()));
  ```
- Boilerplate code for common tasks
  ```java
  // Lots of setup code needed
  public void setupApplication() {
      // Create all objects manually
      // Wire dependencies manually
      // Handle lifecycle manually
  }
  ```

**After Spring:**
- Automatic object creation and lifecycle management
  ```java
  @Component
  public class JavaSkill implements Skill { ... }
  // Spring creates automatically - no 'new' needed
  ```
- Loose coupling through dependency injection
  ```java
  @Component
  public class Employee {
      public Employee(List<Skill> skills) {  // Any skills, not specific ones
          this.skills = skills;
      }
  }
  ```
- Simplified dependency management
  ```java
  @Component
  public class ServiceA {
      public ServiceA(RepositoryA repoA, ServiceB serviceB) {
          // Spring injects dependencies automatically
      }
  }
  ```
- Reduced boilerplate code
  ```java
  @SpringBootApplication
  public class DemoApplication {
      public static void main(String[] args) {
          SpringApplication.run(DemoApplication.class, args);
          // That's it! Spring handles everything else
      }
  }
  ```

### How Does It Work?
Spring uses an **Inversion of Control (IoC) container** that:
1. Scans for components (`@Component`, `@Service`, `@Repository`, `@Controller`)
2. Creates and manages bean instances
3. Injects dependencies automatically
4. Handles bean lifecycle

## Spring Core

### Core Concepts
- **IoC Container**: Manages object creation and lifecycle
- **Beans**: Objects managed by Spring container
- **ApplicationContext**: Central interface for IoC container

### Key Annotations
```java
@Component  // Marks a class as a Spring bean
@Autowired  // Injects dependencies (optional with constructors)
@Service   // Specialized @Component for service layer
@Repository // Specialized @Component for data layer
```

## Spring Boot

### What is Spring Boot?
Spring Boot is an opinionated framework that simplifies Spring application development.

### Key Features
- **Auto-configuration**: Automatically configures Spring based on dependencies
- **Embedded servers**: No need to deploy WAR files
- **Starter dependencies**: Simplified dependency management
- **Production-ready**: Health checks, metrics, externalized configuration

### Example from Our Code
```java
@SpringBootApplication  // Enables auto-configuration and component scanning
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

## Dependency Injection

### What is Dependency Injection?
Dependency Injection (DI) is a design pattern where dependencies are "injected" into a class rather than created by the class itself.

### Types of Dependency Injection
1. **Constructor Injection** (Recommended)
2. **Setter Injection**
3. **Field Injection**

### Our Example - Constructor Injection
```java
// Interface - the dependency contract
public interface Skill {
    String getName();
    void perform();
}

// Concrete implementations
@Component
public class JavaSkill implements Skill { ... }

@Component  
public class DatabaseSkill implements Skill { ... }

// Dependent class receiving injection
@Component
public class Employee {
    private final List<Skill> skills;  // Dependency injected here

    public Employee(List<Skill> skills) {  // Constructor injection
        this.skills = skills;
    }
}
```

### Benefits Demonstrated
- **Loose Coupling**: Employee doesn't know which skills it gets
- **Easy Testing**: Can mock Skill interface for tests
- **Flexibility**: Add new skills without changing Employee
- **Automatic Wiring**: Spring finds all Skill implementations automatically

## Bean Lifecycle

### Lifecycle Phases

#### 1. Instantiation
Spring creates bean instances:
```java
@Component
public class JavaSkill implements Skill {
    public JavaSkill() {
        // Constructor called during instantiation
    }
}
```

#### 2. Dependency Injection
Spring injects dependencies:
```java
@Component
public class Employee {
    private final List<Skill> skills;

    public Employee(List<Skill> skills) {
        // Spring injects all Skill implementations here
        this.skills = skills;
    }
}
```

#### 3. Initialization
Optional initialization phase:
```java
@PostConstruct  // Called after dependency injection
public void initialize() {
    // Initialization logic
}
```

#### 4. Usage
Bean is ready for use:
```java
@Component
public class DemoRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        employee.work();  // Bean in use
    }
}
```

#### 5. Destruction
Cleanup when application shuts down:
```java
@PreDestroy  // Called before bean destruction
public void cleanup() {
    // Cleanup logic
}
```

### Complete Lifecycle Flow in Our Example

```
1. Application Startup
   ↓
2. Component Scanning
   - Finds @JavaSkill, @DatabaseSkill, @Employee, @DemoRunner
   ↓
3. Bean Creation
   - new JavaSkill()
   - new DatabaseSkill()  
   - new Employee(List<Skill>)
   - new DemoRunner(Employee)
   ↓
4. Dependency Injection
   - Skills → Employee
   - Employee → DemoRunner
   ↓
5. Initialization
   - @PostConstruct methods (if any)
   ↓
6. Bean Usage
   - DemoRunner.run() executes
   - employee.work() uses injected skills
   ↓
7. Application Shutdown
   - @PreDestroy methods (if any)
```

### Key Takeaways
- **Spring manages everything**: No manual object creation
- **Automatic dependency resolution**: Spring finds and injects dependencies
- **Lifecycle management**: Spring handles creation, initialization, and destruction
- **Loose coupling**: Components depend on interfaces, not implementations

## OpenAPI with Spring Boot

### What is OpenAPI?
OpenAPI (formerly Swagger) is a specification for documenting REST APIs. It provides a standard way to describe API endpoints, request/response formats, and authentication.

### How It Integrates with Spring Boot

#### 1. Add Dependencies
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.0</version>
</dependency>
```

#### 2. Create REST Controller with OpenAPI Documentation
```java
@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees and skills")
public class EmployeeController {
    
    private final Employee employee;
    
    public EmployeeController(Employee employee) {
        this.employee = employee;
    }
    
    @GetMapping("/work")
    @Operation(summary = "Get employee work output", description = "Returns what the employee is working on with their skills")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<String> getWorkOutput() {
        // Capture the work output and return as JSON
        return ResponseEntity.ok("Employee is working with injected skills");
    }
    
    @GetMapping("/skills")
    @Operation(summary = "Get employee skills", description = "Returns list of skills available to the employee")
    @ApiResponse(responseCode = "200", description = "List of skills retrieved successfully")
    public ResponseEntity<List<SkillInfo>> getSkills() {
        List<SkillInfo> skills = employee.getSkills().stream()
            .map(skill -> new SkillInfo(skill.getName()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(skills);
    }
}
```

#### 3. Configure OpenAPI
```java
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Employee Skills API")
                .version("1.0")
                .description("API demonstrating dependency injection with employee skills"))
            .externalDocs(new ExternalDocumentation()
                .description("Spring Boot Documentation")
                .url("https://spring.io/projects/spring-boot"));
    }
}
```

#### 4. Enhanced Employee Class for API
```java
@Component
public class Employee {
    private final List<Skill> skills;
    
    public Employee(List<Skill> skills) {
        this.skills = skills;
    }
    
    // Make skills accessible for API
    public List<Skill> getSkills() {
        return skills;
    }
    
    public void work() {
        System.out.println("Employee is working with injected skills:");
        for (Skill skill : skills) {
            System.out.println("- Using skill: " + skill.getName());
            skill.perform();
        }
    }
}

// DTO for API response
public record SkillInfo(String name) {}
```

### Benefits of OpenAPI Integration

#### 1. **Automatic Documentation**
```java
// OpenAPI automatically generates docs from annotations
@Operation(summary = "Get employee skills")
@GetMapping("/skills")
public List<SkillInfo> getSkills() { ... }
```

#### 2. **Interactive UI**
- Access at `http://localhost:8080/swagger-ui.html`
- Test API endpoints directly in browser
- View request/response schemas

#### 3. **Client Generation**
- Generate client SDKs in multiple languages
- Automatic type safety for API consumers
- Reduced integration errors

#### 4. **API Validation**
- Validate request/response formats
- Ensure API contract compliance
- Automated testing capabilities

### Complete Flow with OpenAPI

```
1. Spring Boot starts with dependency injection
   ↓
2. Employee bean created with injected skills
   ↓
3. REST controller created with Employee dependency
   ↓
4. OpenAPI scans annotations and generates documentation
   ↓
5. API endpoints available at /api/employees/*
   ↓
6. Documentation available at /swagger-ui.html
```

### Example API Endpoints

```bash
# Get employee work output
GET http://localhost:8080/api/employees/work

# Get employee skills
GET http://localhost:8080/api/employees/skills

# OpenAPI Documentation
GET http://localhost:8080/swagger-ui.html
```

### Key Advantages

1. **Living Documentation**: Docs always match actual API
2. **Developer Friendly**: Interactive testing interface
3. **Standardized**: Industry-standard API specification
4. **Tool Integration**: Works with many API tools and clients

This demonstrates how OpenAPI complements Spring Boot's dependency injection by providing excellent API documentation and testing capabilities for your services.

## Real-World Benefits

1. **Maintainability**: Easy to modify and extend
2. **Testability**: Simple to unit test with mocks
3. **Scalability**: Components can be easily swapped
4. **Productivity**: Less boilerplate, more business logic

This demonstrates how Spring Framework, particularly Spring Boot, simplifies Java development through dependency injection and bean lifecycle management.
