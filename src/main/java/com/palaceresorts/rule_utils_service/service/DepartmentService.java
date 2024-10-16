package com.palaceresorts.rule_utils_service.service;


import com.palaceresorts.rule_utils_service.entity.Area;
import com.palaceresorts.rule_utils_service.entity.Company;
import com.palaceresorts.rule_utils_service.entity.Department;
import com.palaceresorts.rule_utils_service.repository.AreaRepository;
import com.palaceresorts.rule_utils_service.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AreaRepository areaRepository;

    public Optional<Department> findByProcessIdAndStatusActive(String processId) {
        return departmentRepository.findByProcessIdAndIsActiveTrue(processId);
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Optional<Department> findById(String processId) {
        return departmentRepository.findById(processId);
    }

    public Department create(Department department) {
        Department departmentBuilder = Department.builder()
                .processId(UUID.randomUUID().toString())
                .name(department.getName())
                .initialDate(department.getInitialDate())
                .endDate(department.getEndDate())
                .active(department.isActive())
                .objective(department.getObjective())
                .build();
        return departmentRepository.save(departmentBuilder);
    }

    public Department update(String processId, Department department) {
        if (!departmentRepository.existsById(processId)) {
            return null;
        }
        department.setProcessId(processId);
        return departmentRepository.save(department);
    }

    public void deleteById(String processId) {
        departmentRepository.deleteById(processId);
    }

    public boolean updateIsActive(String processId, boolean isActive) {
        Optional<Department> departmentOptional = departmentRepository.findById(processId);

        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            department.setActive(isActive);
            departmentRepository.save(department);
            return true;
        }
        return false; // O lanzar una excepción si lo prefieres
    }

    public Department addAreaToDepartment(String departmentIdId, String areaId) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentIdId);
        Optional<Area> areaOptional = areaRepository.findById(areaId);

        if (departmentOptional.isPresent() && areaOptional.isPresent()) {
            Department department = departmentOptional.get();
            Area area = areaOptional.get();
            department.getAreas().add(area);
            departmentRepository.save(department);
            return department;
        }
        return null;
    }
}
