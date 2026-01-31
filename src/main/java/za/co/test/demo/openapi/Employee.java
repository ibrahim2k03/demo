package za.co.test.demo.openapi;

import org.springframework.stereotype.Component;
import za.co.test.demo.openapi.skills.Skill;
import java.util.List;

// @Component tells Spring to create a bean of this class
// Spring will automatically discover this class during component scanning
// This class will receive dependencies via constructor injection
@Component("openapiEmployee")
public class Employee {
    // Final field to hold the injected dependencies
    // Spring will inject all beans that implement the Skill interface
    // This demonstrates collection injection - Spring finds all Skill beans and puts them in a List
    private final List<Skill> skills;

    // Constructor injection - Spring automatically calls this constructor
    // Spring finds all beans of type Skill (JavaSkill, DatabaseSkill) and injects them as a List
    // This is dependency injection in action - we don't create the dependencies ourselves
    public Employee(List<Skill> skills) {
        this.skills = skills;
    }

    // Make skills accessible for API endpoints
    public List<Skill> getSkills() {
        return skills;
    }

    // Method that uses the injected dependencies
    // The Employee class doesn't know or care which specific skills it has
    // It only knows they implement the Skill interface (loose coupling)
    public void work() {
        System.out.println("Employee is working with injected skills:");
        // Iterate through all injected skills
        // Each skill in this list was automatically injected by Spring
        for (Skill skill : skills) {
            System.out.println("- Using skill: " + skill.getName());
            // Call the perform method on each injected skill
            // This demonstrates polymorphism - each skill implementation has its own behavior
            skill.perform();
        }
    }
}
