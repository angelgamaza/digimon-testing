package com.gamaza.examples.digimon.dto.request.digimon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for Digimon POSTs
 */
public class DigimonPostDto implements Serializable {

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotNull(message = "Type id can not be null")
    private Long typeId;

    @NotNull(message = "Level id can not be null")
    private Long levelId;

    /**
     * Constructors
     */
    public DigimonPostDto() {
    }

    public DigimonPostDto(String name) {
        this.name = name;
    }

    public DigimonPostDto(String name, Long typeId, Long levelId) {
        this.name = name;
        this.typeId = typeId;
        this.levelId = levelId;
    }

    /**
     * Static Of methods
     */
    public static DigimonPostDto of(String name) {
        return new DigimonPostDto(name);
    }

    public static DigimonPostDto of(String name, Long typeId, Long levelId) {
        return new DigimonPostDto(name, typeId, levelId);
    }

    /**
     * Misc
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return "DigimonPostDto{" +
                "name='" + name + '\'' +
                ", typeId=" + typeId +
                ", levelId=" + levelId +
                '}';
    }

}
