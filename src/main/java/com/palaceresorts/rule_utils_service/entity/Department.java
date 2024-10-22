package com.palaceresorts.rule_utils_service.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Department} class represents an entity in the Neo4j database.
 * This class is mapped as a Node entity in the graph database, where each instance
 * represents a Department, with attributes such as its process ID, name, initial and end
 * dates, and whether it is active.
 * @author [AQUILES YONATAN ARMENTA HERNANDEZ]
 * @version 1.0
 * @since 2024-10-04
 * */

@Node
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    /**
     * The unique identifier for the department process. This serves as the primary key in Neo4j.
     */
    @Id
    private String processId;

    /**
     * The name of the department.
     */
    private String name;

    /**
     * The date when the department process started.
     */
    private Date initialDate;;

    /**
     * The date when the department process ended, if applicable.
     */
    private Date endDate;

    /**
     * A flag indicating whether the department is currently active.
     * If {@code true}, the deparment is active; if {@code false}, the department is inactive (logically deleted).
     */
    private boolean active;

    /** The department's objective statement.
     **/
    private String objective;

    @Relationship(type = "has_area", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private Set<Area> areas = new HashSet<>();
}
