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

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    public Optional<Company> findByProcessIdAndStatusActive(String processId) {
        return companyRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(String processId) {
        return companyRepository.findById(processId);
    }

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

    public Company update(String processId, Company company) {
        if (!companyRepository.existsById(processId)) {
            return null;
        }
        company.setProcessId(processId);
        return companyRepository.save(company);
    }

    public void deleteById(String processId) {
        companyRepository.deleteById(processId);
    }

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
