package com.gamaza.examples.digimon.dto.response.level;

import com.gamaza.examples.digimon.dto.response.digimon.DigimonDto;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO for Level with Relations
 */
public class LevelRelationsDto extends LevelDto {

    // Private variables
    private Set<DigimonDto> digimons = new HashSet<>();

    /**
     * Constructors
     */
    public LevelRelationsDto() {
    }

    public LevelRelationsDto(Long id) {
        super(id);
    }

    public LevelRelationsDto(Long id, String name) {
        super(id, name);
    }

    public LevelRelationsDto(Long id, String name, Set<DigimonDto> digimons) {
        super(id, name);
        this.digimons = digimons;
    }

    /**
     * Static Of method
     */
    public static LevelRelationsDto of(Long id) {
        return new LevelRelationsDto(id);
    }

    public static LevelRelationsDto of(Long id, String name) {
        return new LevelRelationsDto(id, name);
    }

    public static LevelRelationsDto of(Long id, String name, Set<DigimonDto> digimons) {
        return new LevelRelationsDto(id, name, digimons);
    }

    /**
     * Misc
     */

    public Set<DigimonDto> getDigimons() {
        return digimons;
    }

    public void setDigimons(Set<DigimonDto> digimons) {
        this.digimons = digimons;
    }

    @Override
    public String toString() {
        return "LevelRelationsDto{" +
                "digimons=" + digimons +
                "} " + super.toString();
    }
}
