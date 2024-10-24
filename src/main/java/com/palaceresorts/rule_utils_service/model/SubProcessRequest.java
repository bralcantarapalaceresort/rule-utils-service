package com.palaceresorts.rule_utils_service.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Represents a request for a subprocess, including its details and metadata.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubProcessRequest {
    /**
     * The unique identifier for the company process. This serves as the primary key in Neo4j.
     */
    @Id
    private String processId;

    /**
     * The name of the process.
     */
    private String name;

    /**
     * The date when the process started.
     */
    private LocalDateTime initialDate;

    /**
     * The date when the  process ended, if applicable.
     */
    private LocalDateTime endDate;

    /**
     * A flag indicating whether the process is currently active.
     * If {@code true}, the process is active; if {@code false}, the process is inactive (logically deleted).
     */
    private boolean active;

    /**
     * The process's objective statement.
     **/
    private String objective;

    /**
     * The process's entity type as a generic object or a map to store dynamic data.
     **/
    private Map<String, Object> rule;
}
