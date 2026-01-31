package za.co.test.demo.openapi.skills;

// Skill interface - defines the contract that all skills must implement
// This is the abstraction that enables dependency injection
public interface Skill {
    // Method to get the name of the skill
    String getName();
    
    // Method to perform the skill action
    void perform();
}
