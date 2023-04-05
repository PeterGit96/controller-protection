package co.develhope.controllerprotection.salary.controllers;

import co.develhope.controllerprotection.salary.dto.SalaryCreateDTO;
import co.develhope.controllerprotection.salary.dto.SalaryDTO;
import co.develhope.controllerprotection.salary.services.SalaryService;
import co.develhope.controllerprotection.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('" + Roles.ADMIN + "')")
    public ResponseEntity<SalaryDTO> createSalary(@RequestBody SalaryCreateDTO salaryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salaryService.createSalary(salaryDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('" + Roles.ADMIN + "')")
    public List<SalaryDTO> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('"+ Roles.ADMIN +"')")
    public SalaryDTO getSalaryByUserid(@PathVariable Long id){
        return salaryService.getSalaryByUserId(id);
    }

}
