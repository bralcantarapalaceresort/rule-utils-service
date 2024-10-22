package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.SubProcess;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for SubProcess entities.
 * Extends Neo4jRepository to provide CRUD operations for SubProcess entities.
 * Includes a custom query method to find active SubProcess entities by processId.
 */
@Repository
public interface SubProcessRepository extends Neo4jRepository<SubProcess,String> {

    /**
     * Finds an active SubProcess entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found SubProcess entity, or empty if no active SubProcess entity with the given processId exists
     */
    @Query("MATCH (sp:SubProcess) WHERE sp.processId = $processId AND sp.active = true RETURN sp")
    Optional<SubProcess> findByProcessIdAndIsActiveTrue(String processId);
}
