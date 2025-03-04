package com.gamaza.examples.springtest.dto.response.digimon;

import com.gamaza.examples.springtest.dto.response.level.LevelDto;
import com.gamaza.examples.springtest.dto.response.type.TypeDto;

/**
 * DTO for Digimon with Relations
 */
public class DigimonRelationsDto extends DigimonDto {

    // Private variables
    private TypeDto type;
    private LevelDto level;

    /**
     * Constructors
     */
    public DigimonRelationsDto() {
    }

    public DigimonRelationsDto(Long id) {
        super(id);
    }

    public DigimonRelationsDto(Long id, String name) {
        super(id, name);
    }

    public DigimonRelationsDto(Long id, String name, TypeDto type, LevelDto level) {
        super(id, name);
        this.type = type;
        this.level = level;
    }

    /**
     * Static Of method
     */
    public static DigimonRelationsDto of(Long id) {
        return new DigimonRelationsDto(id);
    }

    public static DigimonRelationsDto of(Long id, String name) {
        return new DigimonRelationsDto(id, name);
    }

    public static DigimonRelationsDto of(Long id, String name, TypeDto type, LevelDto level) {
        return new DigimonRelationsDto(id, name, type, level);
    }

    /**
     * Misc
     */

    public TypeDto getType() {
        return type;
    }

    public void setType(TypeDto type) {
        this.type = type;
    }

    public LevelDto getLevel() {
        return level;
    }

    public void setLevel(LevelDto level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "DigimonRelationsDto{" +
                "type=" + type +
                ", level=" + level +
                "} " + super.toString();
    }

}
