package com.gamaza.examples.springtest.repository;

import com.gamaza.examples.springtest.entity.Level;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.gamaza.examples.springtest.constant.EntityConstants.LEVEL_ENTITY_GRAPH;

/**
 * Repository class for Level
 */
@Repository
@NonNullApi
public interface LevelRepository extends JpaRepository<Level, Long> {

    /**
     * Find a Level by its id
     *
     * @param id The ID of the Level
     * @return An Optional of the Level found
     */
    @Override
    @EntityGraph(value = LEVEL_ENTITY_GRAPH)
    Optional<Level> findById(Long id);

    /**
     * Find a Level by its name
     *
     * @param name The name of the Level to find
     * @return An Optional of the Level found
     */
    @EntityGraph(value = LEVEL_ENTITY_GRAPH)
    Optional<Level> findByName(String name);

}