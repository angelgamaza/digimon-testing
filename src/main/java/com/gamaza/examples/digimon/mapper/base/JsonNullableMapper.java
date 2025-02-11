package com.gamaza.examples.digimon.mapper.base;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Base {@link JsonNullable} Mapper for Partial Updates
 */
@Mapper(componentModel = SPRING)
public interface JsonNullableMapper {

    /**
     * Unwrap a {@link JsonNullable} to its actual value
     *
     * @param nullable The received {@link JsonNullable}
     * @param <T>      The {@link JsonNullable} type
     * @return The actual value
     */
    default <T> T unwrap(JsonNullable<T> nullable) {
        return nullable == null ? null : nullable.orElse(null);
    }

    /**
     * Checks whether a {@link JsonNullable} parameter was passed explicitly
     *
     * @param nullable The received {@link JsonNullable}
     * @param <T>      The {@link JsonNullable} type
     * @return True if value was set explicitly, false otherwise
     */
    @Condition
    default <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }

}
