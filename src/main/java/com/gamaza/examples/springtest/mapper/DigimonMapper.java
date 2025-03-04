package com.gamaza.examples.springtest.mapper;

import com.gamaza.examples.springtest.dto.request.digimon.DigimonPatchDto;
import com.gamaza.examples.springtest.dto.request.digimon.DigimonPostDto;
import com.gamaza.examples.springtest.dto.response.digimon.DigimonDto;
import com.gamaza.examples.springtest.dto.response.digimon.DigimonRelationsDto;
import com.gamaza.examples.springtest.entity.Digimon;
import com.gamaza.examples.springtest.entity.Level;
import com.gamaza.examples.springtest.entity.Type;
import com.gamaza.examples.springtest.mapper.base.JsonNullableMapper;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Mapper for Digimon
 */
@Mapper(
        componentModel = SPRING,
        uses = JsonNullableMapper.class,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.IGNORE
)
public interface DigimonMapper {

    /**
     * Entity mappings
     */
    @Mapping(source = "typeId", target = "type", qualifiedByName = "typeMapping")
    @Mapping(source = "levelId", target = "level", qualifiedByName = "levelMapping")
    Digimon asEntity(DigimonPostDto source);

    @InheritConfiguration
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "typeId", target = "type", qualifiedByName = "typeMapping")
    @Mapping(source = "levelId", target = "level", qualifiedByName = "levelMapping")
    Digimon asEntity(@MappingTarget Digimon target, DigimonPatchDto source);

    /**
     * DTO Mappings
     */
    DigimonDto asDto(Digimon source);

    DigimonRelationsDto asRelationsDto(Digimon type);

    /*
     * Custom Mappings
     */

    @Named("typeMapping")
    default Type typeMapping(Long typeId) {
        return new Type(typeId);
    }

    @Named("levelMapping")
    default Level levelMapping(Long levelId) {
        return new Level(levelId);
    }

}
