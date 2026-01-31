package za.co.test.demo.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees and skills")
public class EmployeeController {
    
    private final Employee employee;
    
    // Spring injects Employee dependency here
    public EmployeeController(Employee employee) {
        this.employee = employee;
    }
    
    @GetMapping("/work")
    @Operation(summary = "Get employee work output", description = "Returns what the employee is working on with their skills")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> getWorkOutput() {
        // Trigger the work method and return result
        employee.work();
        return ResponseEntity.ok("Employee completed work with injected skills");
    }
    
    @GetMapping("/skills")
    @Operation(summary = "Get employee skills", description = "Returns list of skills available to the employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of skills retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No skills found")
    })
    public ResponseEntity<List<SkillInfo>> getSkills() {
        List<SkillInfo> skills = employee.getSkills().stream()
            .map(skill -> new SkillInfo(skill.getName()))
            .collect(Collectors.toList());
        
        if (skills.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(skills);
    }
}
