package com.gamaza.examples.digimon.dto.request.digimon;

import org.openapitools.jackson.nullable.JsonNullable;

import java.io.Serializable;

/**
 * DTO for Digimon PATCHs
 */
public class DigimonPatchDto implements Serializable {

    // Private variables
    private JsonNullable<String> name;
    private JsonNullable<Long> typeId;
    private JsonNullable<Long> levelId;

    /**
     * Constructors
     */
    public DigimonPatchDto() {
    }

    public DigimonPatchDto(JsonNullable<String> name) {
        this.name = name;
    }

    public DigimonPatchDto(JsonNullable<String> name, JsonNullable<Long> typeId, JsonNullable<Long> levelId) {
        this.name = name;
        this.typeId = typeId;
        this.levelId = levelId;
    }

    /**
     * Static Of methods
     */
    public static DigimonPatchDto of(JsonNullable<String> name) {
        return new DigimonPatchDto(name);
    }

    public static DigimonPatchDto of(JsonNullable<String> name, JsonNullable<Long> typeId, JsonNullable<Long> levelId) {
        return new DigimonPatchDto(name, typeId, levelId);
    }

    /**
     * Misc
     */

    public JsonNullable<String> getName() {
        return name;
    }

    public void setName(JsonNullable<String> name) {
        this.name = name;
    }

    public JsonNullable<Long> getTypeId() {
        return typeId;
    }

    public void setTypeId(JsonNullable<Long> typeId) {
        this.typeId = typeId;
    }

    public JsonNullable<Long> getLevelId() {
        return levelId;
    }

    public void setLevelId(JsonNullable<Long> levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return "DigimonPatchDto{" +
                "name='" + name + '\'' +
                ", typeId=" + typeId +
                ", levelId=" + levelId +
                '}';
    }

}
