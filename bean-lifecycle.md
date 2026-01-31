# Spring Bean Lifecycle Demo

## Lifecycle Phases

### 1. Bean Creation
Spring creates all `@Component` beans:

```java
@Component
public class JavaSkill implements Skill { ... }

@Component  
public class DatabaseSkill implements Skill { ... }

@Component
public class Employee { ... }

@Component
public class DemoRunner implements CommandLineRunner { ... }
```

### 2. Dependency Injection
Spring injects dependencies via constructors:

```java
// Skills injected into Employee
public Employee(List<Skill> skills) {
    this.skills = skills;  // JavaSkill + DatabaseSkill
}

// Employee injected into DemoRunner  
public DemoRunner(Employee employee) {
    this.employee = employee;
}
```

### 3. Bean Usage
`CommandLineRunner.run()` executes after all beans are ready:

```java
@Override
public void run(String... args) {
    employee.work();  // Uses injected skills
}
```

## Execution Flow

```
1. Spring starts
2. Creates beans: JavaSkill → DatabaseSkill → Employee → DemoRunner
3. Injects dependencies: Skills → Employee → DemoRunner
4. Calls DemoRunner.run()
5. Employee.work() uses injected skills
6. Application shuts down
```

## Key Benefits

- **No `new` keywords** - Spring creates everything
- **Loose coupling** - Employee only knows about Skill interface
- **Automatic injection** - Spring finds and wires dependencies
- **Easy testing** - Dependencies can be mocked

## Output

```
=== Dependency Injection Demo: Skills Injected to Employee ===
Employee is working with injected skills:
- Using skill: Java Programming
Writing Java code with Spring Boot!
- Using skill: Database Management
Managing databases and writing SQL queries!
=== Demo Complete ===
```
