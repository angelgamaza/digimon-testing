package com.gamaza.examples.digimon.service;

import com.gamaza.examples.digimon.dto.request.type.TypePatchDto;
import com.gamaza.examples.digimon.dto.request.type.TypePostDto;
import com.gamaza.examples.digimon.dto.response.type.TypeDto;
import com.gamaza.examples.digimon.dto.response.type.TypeRelationsDto;

import java.util.List;

/**
 * Service for Type
 */
public interface TypeService {

    /**
     * Method to save a Type
     *
     * @param postData The Type data to save
     * @return The created Type
     */
    TypeDto save(TypePostDto postData);

    /**
     * Find all Types in database
     *
     * @return The found Types
     */
    List<TypeDto> findAll();

    /**
     * Find a Type in database by id
     *
     * @param id The id of the Type to search
     * @return The found Type
     */
    TypeRelationsDto findById(Long id);

    /**
     * Find a Type in database by name
     *
     * @param name The name of the Type to search
     * @return The found Type
     */
    TypeRelationsDto findByName(String name);

    /**
     * Update a Type in database by id
     *
     * @param id        The id of the Type
     * @param patchData The Type data to update
     * @return The found Type
     */
    TypeRelationsDto update(Long id, TypePatchDto patchData);

    /**
     * Delete a Type in database by id
     *
     * @param id The id of the Type
     */
    void delete(Long id);

}
