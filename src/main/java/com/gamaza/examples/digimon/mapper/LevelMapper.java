package com.gamaza.examples.digimon.mapper;

import com.gamaza.examples.digimon.dto.request.level.LevelPatchDto;
import com.gamaza.examples.digimon.dto.request.level.LevelPostDto;
import com.gamaza.examples.digimon.dto.response.level.LevelDto;
import com.gamaza.examples.digimon.dto.response.level.LevelRelationsDto;
import com.gamaza.examples.digimon.entity.Level;
import com.gamaza.examples.digimon.mapper.base.JsonNullableMapper;
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
