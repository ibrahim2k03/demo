package za.co.test.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// @Component tells Spring to create a bean of this class
// CommandLineRunner interface ensures this runs after application context is loaded
// This allows us to demonstrate dependency injection after all beans are created
@Component
public class DemoRunner implements CommandLineRunner {
    // Final field to hold the injected Employee dependency
    // Spring will find the Employee bean and inject it here
    // This demonstrates dependency injection - we don't create the Employee ourselves
    private final Employee employee;

    // Constructor injection - Spring automatically calls this constructor
    // Spring finds the Employee bean (which already has its Skill dependencies injected)
    // and injects it into this DemoRunner
    public DemoRunner(Employee employee) {
        this.employee = employee;
    }

    // This method is automatically called by Spring after application startup
    // At this point, all dependency injection has been completed
    // The Employee bean has been created with all Skill beans injected
    // This DemoRunner has been created with the Employee bean injected
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Dependency Injection Demo: Skills Injected to Employee ===");
        
        // Call the work method on the injected Employee
        // This will use the skills that Spring injected into the Employee
        // The complete dependency chain: Spring -> DemoRunner -> Employee -> List<Skill>
        employee.work();
        
        System.out.println("=== Demo Complete ===");
        System.out.println("Key Points:");
        System.out.println("1. Spring automatically created all @Component beans");
        System.out.println("2. Spring injected all Skill implementations into Employee");
        System.out.println("3. Spring injected Employee into DemoRunner");
        System.out.println("4. No manual object creation needed - everything is managed by Spring");
    }
}
