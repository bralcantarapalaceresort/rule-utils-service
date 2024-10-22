package com.palaceresorts.rule_utils_service.controller;

import com.palaceresorts.rule_utils_service.entity.Department;
import com.palaceresorts.rule_utils_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Department entities.
 */
@RestController
@RequestMapping("/api/rule/utils/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * Retrieves all departments.
     *
     * @return a ResponseEntity containing a list of all departments
     */
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.findAll();
        return ResponseEntity.ok(departments);
    }

    /**
     * Retrieves a department by its process ID.
     *
     * @param processId the process ID of the department to retrieve
     * @return a ResponseEntity containing the found department, or 404 if not found
     */
    @GetMapping("/{processId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable String processId) {
        Optional<Department> department = departmentService.findById(processId);
        return department.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new department.
     *
     * @param department the department to create
     * @return a ResponseEntity containing the created department
     */
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.create(department);
        return ResponseEntity.status(201).body(createdDepartment);
    }

    /**
     * Updates a department.
     *
     * @param processId the process ID of the department to update
     * @param department the department to update
     * @return a ResponseEntity containing the updated department, or 404 if not found
     */
    @PutMapping("/{processId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable String processId, @RequestBody Department department) {
        Department updatedDepartment = departmentService.update(processId, department);
        return updatedDepartment != null
                ? ResponseEntity.ok(updatedDepartment)
                : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a department.
     *
     * @param processId the process ID of the department to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String processId) {
        departmentService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the isActive flag of a department.
     *
     * @param processId the process ID of the department to update
     * @param isActive the new value of the isActive flag
     * @return a ResponseEntity with no content
     */
    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = departmentService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }

    /**
     * Adds an area to a department.
     *
     * @param departmentId the process ID of the department to update
     * @param areaId the process ID of the area to add
     * @return a ResponseEntity containing the updated department, or 404 if not found
     */
    @PutMapping("/{departmentId}/add-area/{areaId}")
    public ResponseEntity<Department> addAreaToDepartment(@PathVariable String departmentId,
                                                             @PathVariable String areaId) {
        Department department = departmentService.addAreaToDepartment(departmentId,areaId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
