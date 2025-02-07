package com.gamaza.examples.digimon.dto.response.level;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for Level
 */
public class LevelDto implements Serializable {

    // Private variables
    private Long id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    /**
     * Constructors
     */
    public LevelDto() {
    }

    public LevelDto(Long id) {
        this.id = id;
    }

    public LevelDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Static Of method
     */
    public static LevelDto of(Long id) {
        return new LevelDto(id);
    }

    public static LevelDto of(Long id, String name) {
        return new LevelDto(id, name);
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
        return "LevelDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }

}
