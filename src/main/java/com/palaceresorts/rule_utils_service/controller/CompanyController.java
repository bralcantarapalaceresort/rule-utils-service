package com.palaceresorts.rule_utils_service.controller;

import com.palaceresorts.rule_utils_service.entity.Company;
import com.palaceresorts.rule_utils_service.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rule/utils/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        return ResponseEntity.ok(companies);
    }


    @GetMapping("/{processId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String processId) {
        Optional<Company> company = companyService.findById(processId);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyService.create(company);
        return ResponseEntity.status(201).body(createdCompany);
    }

    @PutMapping("/{processId}")
    public ResponseEntity<Company> updateCompany(@PathVariable String processId, @RequestBody Company company) {
        Company updatedCompany = companyService.update(processId, company);
        return updatedCompany != null
                ? ResponseEntity.ok(updatedCompany)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String processId) {
        companyService.deleteById(processId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{processId}/isActive")
    public ResponseEntity<Void> updateIsActive(@PathVariable String processId, @RequestParam boolean isActive) {
        boolean updated = companyService.updateIsActive(processId, isActive);

        if (updated) {
            return ResponseEntity.ok().build(); // Status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Status 404 Not Found
        }
    }

    @PutMapping("/{companyId}/add-department/{departmentId}")
    public ResponseEntity<Company> addDepartmentToCompany(@PathVariable String companyId,
                                                             @PathVariable String departmentId) {
        Company company = companyService.addDepartmentToCompany(companyId,departmentId);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }
}
