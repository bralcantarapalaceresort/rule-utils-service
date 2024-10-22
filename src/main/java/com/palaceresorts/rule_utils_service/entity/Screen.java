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
public class Screen {
    @Id
    private String processId;


    private String name;


    private Date initialDate;;


    private Date endDate;


    private boolean active;

    /**
     * The System's objective statement.
     **/
    private String objective;

    @Relationship(type = "has_rule_component", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private Set<RuleComponent> ruleComponents = new HashSet<>();
}
