package com.gamaza.examples.springtest.mapper;

import com.gamaza.examples.springtest.dto.request.type.TypePatchDto;
import com.gamaza.examples.springtest.dto.request.type.TypePostDto;
import com.gamaza.examples.springtest.dto.response.type.TypeDto;
import com.gamaza.examples.springtest.dto.response.type.TypeRelationsDto;
import com.gamaza.examples.springtest.entity.Type;
import com.gamaza.examples.springtest.mapper.base.JsonNullableMapper;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Mapper for Type
 */
@Mapper(
        componentModel = SPRING,
        uses = JsonNullableMapper.class,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.IGNORE
)
public interface TypeMapper {

    /**
     * Entity mappings
     */
    Type asEntity(TypePostDto source);

    @InheritConfiguration
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Type asEntity(@MappingTarget Type target, TypePatchDto source);

    /**
     * DTO Mappings
     */
    TypeDto asDto(Type source);

    TypeRelationsDto asRelationsDto(Type type);

}
