package com.palaceresorts.rule_utils_service.service;

import com.palaceresorts.rule_utils_service.entity.Company;
import com.palaceresorts.rule_utils_service.entity.Department;
import com.palaceresorts.rule_utils_service.repository.CompanyRepository;
import com.palaceresorts.rule_utils_service.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing Company entities.
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Finds an active Company entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Company entity, or empty if no active Company entity with the given processId exists
     */
    public Optional<Company> findByProcessIdAndStatusActive(String processId) {
        return companyRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    /**
     * Finds all active Company entities.
     *
     * @return a List containing all active Company entities
     */
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * Finds an active Company entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Company entity, or empty if no active Company entity with the given processId exists
     */
    public Optional<Company> findById(String processId) {
        return companyRepository.findById(processId);
    }

    /**
     * Finds an active Company entity by its name.
     *
     * @param name the name to search for
     * @return an Optional containing the found Company entity, or empty if no active Company entity with the given name exists
     */
    public Company create(Company company) {
        Company companyBuilder = Company.builder()
                .processId(UUID.randomUUID().toString())
                .name(company.getName())
                .initialDate(company.getInitialDate())
                .endDate(company.getEndDate())
                .active(company.isActive())
                .mission(company.getMission())
                .vision(company.getVision())
                .build();
        return companyRepository.save(companyBuilder);
    }

    /**
     * Updates an existing Company entity.
     *
     * @param processId the process ID of the Company entity to update
     * @param company the updated Company entity data
     * @return the updated Company entity, or null if no Company entity with the given processId exists
     */
    public Company update(String processId, Company company) {
        if (!companyRepository.existsById(processId)) {
            return null;
        }
        company.setProcessId(processId);
        return companyRepository.save(company);
    }

    /**
     * Deletes an existing Company entity.
     *
     * @param processId the process ID of the Company entity to delete
     */
    public void deleteById(String processId) {
        companyRepository.deleteById(processId);
    }

    /**
     * Updates the isActive status of an existing Company entity.
     *
     * @param processId the process ID of the Company entity to update
     * @param isActive the new isActive status
     * @return true if the Company entity was updated, false if no Company entity with the given processId exists
     */
    public boolean updateIsActive(String processId, boolean isActive) {
        Optional<Company> companyOptional = companyRepository.findById(processId);

        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setActive(isActive);
            companyRepository.save(company);
            return true;
        }
        return false; // O lanzar una excepción si lo prefieres
    }

    /**
     * Adds a Department entity to a Company entity.
     *
     * @param companyId the ID of the Company entity
     * @param departmentId the ID of the Department entity to add
     * @return the updated Company entity, or null if either the Company or Department entity does not exist
     */
    public Company addDepartmentToCompany(String companyId, String departmentId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);

        if (departmentOptional.isPresent() && companyOptional.isPresent()) {
            Company company = companyOptional.get();
            Department department = departmentOptional.get();
            company.getDepartments().add(department);
            companyRepository.save(company);
            return company;
        }
        return null;
    }
}
