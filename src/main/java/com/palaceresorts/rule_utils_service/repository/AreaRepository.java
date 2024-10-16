package com.palaceresorts.rule_utils_service.repository;

import com.palaceresorts.rule_utils_service.entity.Area;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends Neo4jRepository<Area,String> {

    @Query("MATCH (a:Area) WHERE a.processId = $processId AND a.active = true RETURN a")
    Optional<Area> findByProcessIdAndIsActiveTrue(String processId);
}
