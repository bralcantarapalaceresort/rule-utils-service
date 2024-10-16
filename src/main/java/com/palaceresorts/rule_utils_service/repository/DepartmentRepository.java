package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Company;
import com.palaceresorts.rule_utils_service.entity.Department;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends Neo4jRepository<Department,String> {
    @Query("MATCH (d:Department) WHERE d.processId = $processId AND d.active = true RETURN d")
    Optional<Department> findByProcessIdAndIsActiveTrue(String processId);
}
