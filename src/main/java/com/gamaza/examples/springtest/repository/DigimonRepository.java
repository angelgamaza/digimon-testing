package com.gamaza.examples.springtest.repository;

import com.gamaza.examples.springtest.entity.Digimon;
import com.gamaza.examples.springtest.repository.base.CustomRepository;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.gamaza.examples.springtest.constant.EntityConstants.DIGIMON_ENTITY_GRAPH;

/**
 * Repository class for Digimon
 */
@Repository
@NonNullApi
public interface DigimonRepository extends CustomRepository<Digimon, Long> {

    /**
     * Find a Level by its id
     *
     * @param id The ID of the Level
     * @return An Optional of the Level found
     */
    @Override
    @EntityGraph(value = DIGIMON_ENTITY_GRAPH)
    Optional<Digimon> findById(Long id);

    /**
     * Find a Level by its name
     *
     * @param name The name of the Level to find
     * @return An Optional of the Level found
     */
    @EntityGraph(value = DIGIMON_ENTITY_GRAPH)
    Optional<Digimon> findByName(String name);

    /**
     * Find all Digimon by Type ID
     *
     * @param id The ID of the Type
     * @return A List of Digimon found
     */
    List<Digimon> findAllByTypeId(Long id);

    /**
     * Find all Digimon by Type name
     *
     * @param name The name of the Type
     * @return A List of Digimon found
     */
    List<Digimon> findAllByTypeName(String name);

    /**
     * Find all Digimon by Level ID
     *
     * @param id The ID of the Level
     * @return A List of Digimon found
     */
    List<Digimon> findAllByLevelId(Long id);

    /**
     * Find all Digimon by Level name
     *
     * @param name The name of the Level
     * @return A List of Digimon found
     */
    List<Digimon> findAllByLevelName(String name);

}