package za.co.test.demo.skills;

import org.springframework.stereotype.Component;

// @Component tells Spring to create a bean of this class
// Spring will automatically discover this class during component scanning
// This bean can now be injected into other classes
@Component
public class JavaSkill implements Skill {
    
    // Implementation of the getName method from Skill interface
    // This provides a concrete implementation of the abstract method
    @Override
    public String getName() {
        return "Java Programming";
    }

    // Implementation of the perform method from Skill interface
    // This is the concrete behavior that will be executed when this skill is used
    @Override
    public void perform() {
        System.out.println("Writing Java code with Spring Boot!");
    }
}
