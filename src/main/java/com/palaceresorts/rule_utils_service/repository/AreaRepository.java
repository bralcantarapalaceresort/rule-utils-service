package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Area;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Area entities.
 * Extends Neo4jRepository to provide CRUD operations for Area entities.
 * Includes a custom query method to find active Area entities by processId.
 */
@Repository
public interface AreaRepository extends Neo4jRepository<Area,String> {

    /**
     * Finds an active Area entity by its processId.
     *
     * @param processId the process ID to search for
     * @return an Optional containing the found Area entity, or empty if no active Area entity with the given processId exists
     */
    @Query("MATCH (a:Area) WHERE a.processId = $processId AND a.active = true RETURN a")
    Optional<Area> findByProcessIdAndIsActiveTrue(String processId);
}
