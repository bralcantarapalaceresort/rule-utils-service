package com.palaceresorts.rule_utils_service.controller;

import com.palaceresorts.rule_utils_service.entity.Company;
import com.palaceresorts.rule_utils_service.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Company entities.
 */
@RestController
@RequestMapping("/api/rule/utils/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * Retrieves all companies.
     *
     * @return a ResponseEntity containing a list of all companies
     */
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        return ResponseEntity.ok(companies);
    }

    /**
     * Retrieves a company by its process ID.
     *
     * @param processId the process ID of the company to retrieve
     * @return a ResponseEntity containing the found company, or 404 if not found
     */
    @GetMapping("/{processId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String processId) {
        Optional<Company> company = companyService.findById(processId);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new company.
     *
     * @param company the company to create
     * @return a ResponseEntity containing the created company
     */
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyService.create(company);
        return ResponseEntity.status(201).body(createdCompany);
    }

    /**
     * Updates an existing company.
     *
     * @param processId the process ID of the company to update
     * @param company the updated company data
     * @return a ResponseEntity containing the updated company, or 404 if not found
     */
    @PutMapping("/{processId}")
    public ResponseEntity<Company> updateCompany(@PathVariable String processId, @RequestBody Company company) {
        Company updatedCompany = companyService.update(processId, company);
        return updatedCompany != null
                ? ResponseEntity.ok(updatedCompany)
                : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a company by its process ID.
     *
     * @param processId the process ID of the company to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String processId) {
        companyService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the active status of a company.
     *
     * @param processId the process ID of the company to update
     * @param isActive the new active status
     * @return a ResponseEntity indicating the result of the operation
     */
    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = companyService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }

    /**
     * Adds a department to a company.
     *
     * @param companyId the process ID of the company to update
     * @param departmentId the process ID of the department to add
     * @return a ResponseEntity containing the updated company, or 404 if not found
     */
    @PutMapping("/{companyId}/add-department/{departmentId}")
    public ResponseEntity<Company> addDepartmentToCompany(@PathVariable String companyId,
                                                             @PathVariable String departmentId) {
        Company company = companyService.addDepartmentToCompany(companyId,departmentId);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }
}
