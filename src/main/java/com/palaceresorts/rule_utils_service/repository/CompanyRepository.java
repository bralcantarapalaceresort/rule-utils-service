package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Company;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Company entities.
 * Extends Neo4jRepository to provide CRUD operations for Company entities.
 * Includes a custom query method to find active Company entities by processId.
 */
@Repository
public interface CompanyRepository extends Neo4jRepository<Company,String> {

    /**
     * Finds an active Company entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Company entity, or empty if no active Company entity with the given processId exists
     */
    @Query("MATCH (c:Company) WHERE c.processId = $processId AND c.active = true RETURN c")
    Optional<Company> findByProcessIdAndIsActiveTrue(String processId);
}
