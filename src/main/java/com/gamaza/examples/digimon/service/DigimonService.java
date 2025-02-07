package com.gamaza.examples.digimon.service;

import com.gamaza.examples.digimon.dto.request.digimon.DigimonPatchDto;
import com.gamaza.examples.digimon.dto.request.digimon.DigimonPostDto;
import com.gamaza.examples.digimon.dto.response.digimon.DigimonDto;
import com.gamaza.examples.digimon.dto.response.digimon.DigimonRelationsDto;

import java.util.List;

/**
 * Service for Digimon
 */
public interface DigimonService {

    /**
     * Method to save a Digimon
     *
     * @param postData The Digimon data to save
     * @return The created Digimon
     */
    DigimonDto save(DigimonPostDto postData);

    /**
     * Find all Digimons in database
     *
     * @return The found Digimons
     */
    List<DigimonDto> findAll();

    /**
     * Find a Digimon in database by id
     *
     * @param id The id of the Digimon to search
     * @return The found Digimon
     */
    DigimonRelationsDto findById(Long id);

    /**
     * Find a Digimon in database by name
     *
     * @param name The name of the Digimon to search
     * @return The found Digimon
     */
    DigimonRelationsDto findByName(String name);

    /**
     * Find all Digimons in database by Type id
     *
     * @param id The Type id
     * @return The found Digimons
     */
    List<DigimonDto> findAllByTypeId(Long id);

    /**
     * Find all Digimons in database by Type id
     *
     * @param name The Type name
     * @return The found Digimons
     */
    List<DigimonDto> findAllByTypeName(String name);

    /**
     * Find all Digimons in database by Level id
     *
     * @param id The Level id
     * @return The found Digimons
     */
    List<DigimonDto> findAllByLevelId(Long id);

    /**
     * Find all Digimons in database by Level id
     *
     * @param name The Level name
     * @return The found Digimons
     */
    List<DigimonDto> findAllByLevelName(String name);

    /**
     * Update a Digimon in database by id
     *
     * @param id        The id of the Digimon
     * @param patchData The Digimon data to update
     * @return The found Digimon
     */
    DigimonRelationsDto update(Long id, DigimonPatchDto patchData);

    /**
     * Delete a Digimon in database by id
     *
     * @param id The id of the Digimon
     */
    void delete(Long id);

}
