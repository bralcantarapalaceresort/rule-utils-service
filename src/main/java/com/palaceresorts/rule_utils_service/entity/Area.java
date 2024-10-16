package com.palaceresorts.rule_utils_service.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Node
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    /**
     * The unique identifier for the area process. This serves as the primary key in Neo4j.
     */
    @Id
    private String processId;

    /**
     * The name of the area.
     */
    private String name;

    /**
     * The date when the area process started.
     */
    private Date initialDate;;

    /**
     * The date when the area process ended, if applicable.
     */
    private Date endDate;

    /**
     * A flag indicating whether the department is currently active.
     * If {@code true}, the area is active; if {@code false}, the area is inactive (logically deleted).
     */
    private boolean active;

    /** The area's objective statement.
     **/
    private String objective;

    @Relationship(type = "has_process", direction = Relationship.Direction.OUTGOING)
    private Set<Process> processes = new HashSet<>();
}
