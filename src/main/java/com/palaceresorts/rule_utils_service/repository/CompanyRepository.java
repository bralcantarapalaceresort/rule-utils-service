package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Company;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends Neo4jRepository<Company,String> {
    @Query("MATCH (c:Company) WHERE c.processId = $processId AND c.active = true RETURN c")
    Optional<Company> findByProcessIdAndIsActiveTrue(String processId);
}
