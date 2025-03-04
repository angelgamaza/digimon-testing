package com.gamaza.examples.springtest.dto.response.digimon;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for Digimon
 */
public class DigimonDto implements Serializable {

    // Private variables
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    /**
     * Constructors
     */
    public DigimonDto() {
    }

    public DigimonDto(Long id) {
        this.id = id;
    }

    public DigimonDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Static Of method
     */
    public static DigimonDto of(Long id) {
        return new DigimonDto(id);
    }

    public static DigimonDto of(Long id, String name) {
        return new DigimonDto(id, name);
    }

    /**
     * Misc
     */

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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "DigimonDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }

}
