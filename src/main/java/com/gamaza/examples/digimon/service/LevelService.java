package com.gamaza.examples.digimon.service;

import com.gamaza.examples.digimon.dto.request.level.LevelPatchDto;
import com.gamaza.examples.digimon.dto.request.level.LevelPostDto;
import com.gamaza.examples.digimon.dto.response.level.LevelDto;
import com.gamaza.examples.digimon.dto.response.level.LevelRelationsDto;

import java.util.List;

/**
 * Service for Level
 */
public interface LevelService {

    /**
     * Method to save a Level
     *
     * @param postData The Level data to save
     * @return The created Level
     */
    LevelDto save(LevelPostDto postData);

    /**
     * Find all Levels in database
     *
     * @return The found Levels
     */
    List<LevelDto> findAll();

    /**
     * Find a Level in database by id
     *
     * @param id The id of the Level to search
     * @return The found Level
     */
    LevelRelationsDto findById(Long id);

    /**
     * Find a Level in database by name
     *
     * @param name The name of the Level to search
     * @return The found Level
     */
    LevelRelationsDto findByName(String name);

    /**
     * Update a Level in database by id
     *
     * @param id        The id of the Level
     * @param patchData The Level data to update
     * @return The found Level
     */
    LevelRelationsDto update(Long id, LevelPatchDto patchData);

    /**
     * Delete a Level in database by id
     *
     * @param id The id of the Level
     */
    void delete(Long id);

}