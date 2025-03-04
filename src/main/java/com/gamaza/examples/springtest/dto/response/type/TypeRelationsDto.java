package com.gamaza.examples.springtest.dto.response.type;

import com.gamaza.examples.springtest.dto.response.digimon.DigimonDto;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO for Type with Relations
 */
public class TypeRelationsDto extends TypeDto {

    // Private variables
    private Set<DigimonDto> digimons = new HashSet<>();

    /**
     * Constructors
     */
    public TypeRelationsDto() {
    }

    public TypeRelationsDto(Long id) {
        super(id);
    }

    public TypeRelationsDto(Long id, String name) {
        super(id, name);
    }

    public TypeRelationsDto(Long id, String name, Set<DigimonDto> digimons) {
        super(id, name);
        this.digimons = digimons;
    }

    /**
     * Static Of method
     */
    public static TypeRelationsDto of(Long id) {
        return new TypeRelationsDto(id);
    }

    public static TypeRelationsDto of(Long id, String name) {
        return new TypeRelationsDto(id, name);
    }

    public static TypeRelationsDto of(Long id, String name, Set<DigimonDto> digimons) {
        return new TypeRelationsDto(id, name, digimons);
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
        return "TypeRelationsDto{" +
                "digimons=" + digimons +
                "} " + super.toString();
    }

}
