package com.gamaza.examples.springtest.mapper;

import com.gamaza.examples.springtest.dto.request.level.LevelPatchDto;
import com.gamaza.examples.springtest.dto.request.level.LevelPostDto;
import com.gamaza.examples.springtest.dto.response.level.LevelDto;
import com.gamaza.examples.springtest.dto.response.level.LevelRelationsDto;
import com.gamaza.examples.springtest.entity.Level;
import com.gamaza.examples.springtest.mapper.base.JsonNullableMapper;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Mapper for Level
 */
@Mapper(
        componentModel = SPRING,
        uses = JsonNullableMapper.class,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.IGNORE
)
public interface LevelMapper {

    /**
     * Entity mappings
     */
    Level asEntity(LevelPostDto source);

    @InheritConfiguration
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Level asEntity(@MappingTarget Level target, LevelPatchDto source);

    /**
     * DTO Mappings
     */
    LevelDto asDto(Level source);

    LevelRelationsDto asRelationsDto(Level type);

}
