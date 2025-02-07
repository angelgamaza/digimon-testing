package com.gamaza.examples.digimon.dto.request.type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for Type POSTs
 */
public class TypePostDto implements Serializable {

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;

    /**
     * Constructors
     */
    public TypePostDto() {
    }

    public TypePostDto(String name) {
        this.name = name;
    }

    /**
     * Static Of methods
     */
    public static TypePostDto of(String name) {
        return new TypePostDto(name);
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
        return "TypePostDto{" +
                "name='" + name + '\'' +
                '}';
    }

}
