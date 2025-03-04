package com.gamaza.examples.springtest.entity;

import com.gamaza.examples.springtest.entity.audit.Auditable;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static com.gamaza.examples.springtest.constant.EntityConstants.*;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Type Entity class
 */
@Entity(name = TYPE_ENTITY_NAME)
@Table(
        schema = SCHEMA_PUBLIC_STRING,
        name = TYPE_ENTITY_NAME,
        indexes = {
                @Index(name = UINDEX_TYPE_NAME_STRING, columnList = FIELD_NAME_STRING, unique = true)
        },
        uniqueConstraints = {
                @UniqueConstraint(name = UK_TYPE_NAME_STRING, columnNames = FIELD_NAME_STRING)
        }
)
@NamedEntityGraph(
        name = TYPE_ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = DIGIMONS_RELATIONSHIP_STRING)
        }
)
public class Type extends Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = FIELD_ID_STRING, nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = FIELD_NAME_STRING, nullable = false, unique = true)
    private String name;

    @OrderBy(value = "id DESC")
    @OneToMany(mappedBy = TYPE_RELATIONSHIP_STRING, cascade = ALL, orphanRemoval = true, targetEntity = Digimon.class)
    private Set<Digimon> digimons = new HashSet<>();

    /**
     * Void constructor
     */
    public Type() {
    }

    /**
     * Constructor
     */
    public Type(Long id) {
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

    public Set<Digimon> getDigimons() {
        return digimons;
    }

    public void setDigimons(Set<Digimon> digimons) {
        this.digimons = digimons;
    }

}