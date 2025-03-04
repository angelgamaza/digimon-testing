package com.gamaza.examples.springtest.dto.request.type;

import org.openapitools.jackson.nullable.JsonNullable;

import java.io.Serializable;

/**
 * DTO for Type PATCHs
 */
public class TypePatchDto implements Serializable {

    // Private variables
    private JsonNullable<String> name;

    /**
     * Constructors
     */
    public TypePatchDto() {
    }

    public TypePatchDto(JsonNullable<String> name) {
        this.name = name;
    }

    /**
     * Static Of methods
     */
    public static TypePatchDto of(JsonNullable<String> name) {
        return new TypePatchDto(name);
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
        return "TypePatchDto{" +
                "name='" + name + '\'' +
                '}';
    }

}
