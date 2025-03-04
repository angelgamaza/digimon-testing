package com.gamaza.examples.springtest.dto.request.level;

import org.openapitools.jackson.nullable.JsonNullable;

import java.io.Serializable;

/**
 * DTO for Level PATCHs
 */
public class LevelPatchDto implements Serializable {

    // Private variables
    private JsonNullable<String> name;

    /**
     * Constructors
     */
    public LevelPatchDto() {
    }

    public LevelPatchDto(JsonNullable<String> name) {
        this.name = name;
    }

    /**
     * Static Of methods
     */
    public static LevelPatchDto of(JsonNullable<String> name) {
        return new LevelPatchDto(name);
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

    @Override
    public String toString() {
        return "LevelPatchDto{" +
                "name='" + name + '\'' +
                '}';
    }

}
