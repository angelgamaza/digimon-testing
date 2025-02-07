package com.gamaza.examples.digimon.entity;

import com.gamaza.examples.digimon.entity.audit.Auditable;
import jakarta.persistence.*;

import static com.gamaza.examples.digimon.constant.EntityConstants.*;
import static jakarta.persistence.ConstraintMode.CONSTRAINT;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Digimon Entity class
 */
@Entity(name = DIGIMON_ENTITY_NAME)
@Table(
        schema = SCHEMA_PUBLIC_STRING,
        name = DIGIMON_ENTITY_NAME,
        indexes = {
                @Index(name = UINDEX_DIGIMON_NAME_STRING, columnList = FIELD_NAME_STRING, unique = true)
        },
        uniqueConstraints = {
                @UniqueConstraint(name = UK_DIGIMON_NAME_STRING, columnNames = FIELD_NAME_STRING)
        }
)
@NamedEntityGraph(
        name = DIGIMON_ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = TYPE_RELATIONSHIP_STRING, subgraph = DIGIMON_TYPE_ENTITY_SUBGRAPH),
                @NamedAttributeNode(value = LEVEL_RELATIONSHIP_STRING, subgraph = DIGIMON_LEVEL_ENTITY_SUBGRAPH)
        },
        subgraphs = {
                @NamedSubgraph(
                        name = DIGIMON_TYPE_ENTITY_SUBGRAPH,
                        attributeNodes = {
                                @NamedAttributeNode(value = DIGIMONS_RELATIONSHIP_STRING)
                        }
                ),
                @NamedSubgraph(
                        name = DIGIMON_LEVEL_ENTITY_SUBGRAPH,
                        attributeNodes = {
                                @NamedAttributeNode(value = DIGIMONS_RELATIONSHIP_STRING)
                        }
                )
        }
)
public class Digimon extends Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = FIELD_ID_STRING, nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = FIELD_NAME_STRING, nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(
            name = TYPE_RELATIONSHIP_STRING,
            referencedColumnName = FIELD_ID_STRING,
            foreignKey = @ForeignKey(name = FK_DIGIMON_TYPE, value = CONSTRAINT)
    )
    private Type type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(
            name = LEVEL_RELATIONSHIP_STRING,
            referencedColumnName = FIELD_ID_STRING,
            foreignKey = @ForeignKey(name = FK_DIGIMON_LEVEL)
    )
    private Level level;

    /**
     * Void constructor
     */
    public Digimon() {
    }

    /**
     * Constructor
     */
    public Digimon(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

}