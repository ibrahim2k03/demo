package za.co.test.demo.openapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("openapiDemoRunner")
public class OpenApiDemoRunner implements CommandLineRunner {
    private final Employee employee;

    // Spring injects Employee here
    public OpenApiDemoRunner(Employee employee) {
        this.employee = employee;
    }

    // Runs after Spring starts up
    @Override
    public void run(String... args) {
        System.out.println("=== OpenAPI Integration Demo ===");
        System.out.println("Employee created with " + employee.getSkills().size() + " skills:");
        employee.getSkills().forEach(skill -> 
            System.out.println("- " + skill.getName())
        );
        System.out.println("\nAPI Endpoints available:");
        System.out.println("- GET http://localhost:8080/api/employees/work");
        System.out.println("- GET http://localhost:8080/api/employees/skills");
        System.out.println("- OpenAPI UI: http://localhost:8080/swagger-ui.html");
        System.out.println("=== Demo Complete ===");
    }
}
