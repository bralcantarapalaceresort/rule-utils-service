package com.palaceresorts.rule_utils_service.controller;

import com.palaceresorts.rule_utils_service.entity.Company;
import com.palaceresorts.rule_utils_service.entity.Department;
import com.palaceresorts.rule_utils_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rule/utils/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.findAll();
        return ResponseEntity.ok(departments);
    }


    @GetMapping("/{processId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable String processId) {
        Optional<Department> department = departmentService.findById(processId);
        return department.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.create(department);
        return ResponseEntity.status(201).body(createdDepartment);
    }

    @PutMapping("/{processId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable String processId, @RequestBody Department department) {
        Department updatedDepartment = departmentService.update(processId, department);
        return updatedDepartment != null
                ? ResponseEntity.ok(updatedDepartment)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String processId) {
        departmentService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = departmentService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }

    @PutMapping("/{departmentId}/add-area/{areaId}")
    public ResponseEntity<Department> addAreaToDepartment(@PathVariable String departmentId,
                                                             @PathVariable String areaId) {
        Department department = departmentService.addAreaToDepartment(departmentId,areaId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
