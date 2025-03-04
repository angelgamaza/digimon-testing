package com.gamaza.examples.springtest.dto.request.level;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for Level POSTs
 */
public class LevelPostDto implements Serializable {

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;

    /**
     * Constructors
     */
    public LevelPostDto() {
    }

    public LevelPostDto(String name) {
        this.name = name;
    }

    /**
     * Static Of methods
     */
    public static LevelPostDto of(String name) {
        return new LevelPostDto(name);
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

    @Override
    public String toString() {
        return "LevelPostDto{" +
                "name='" + name + '\'' +
                '}';
    }

}
