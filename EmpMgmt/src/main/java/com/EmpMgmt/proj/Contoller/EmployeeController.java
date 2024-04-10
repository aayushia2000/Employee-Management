package com.EmpMgmt.proj.Contoller;

import com.EmpMgmt.proj.Exception.ResourceNotFoundException;
import com.EmpMgmt.proj.Modal.Employee;
import com.EmpMgmt.proj.Repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //get all


    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    //create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
    //get employee by rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with Id:" + id));
        return ResponseEntity.ok(employee);
    }
    //update employee details
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee1){
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with Id:" + id));
        employee.setFirstName(employee1.getFirstName());
        employee.setLastName(employee1.getLastName());
        employee.setEmailId(employee1.getEmailId());
        Employee updatedEmp = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmp);
    }
    //Delete an employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>>deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with Id:" + id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
