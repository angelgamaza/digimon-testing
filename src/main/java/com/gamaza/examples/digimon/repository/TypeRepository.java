package com.gamaza.examples.digimon.repository;

import com.gamaza.examples.digimon.entity.Type;
import com.gamaza.examples.digimon.repository.base.CustomRepository;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.gamaza.examples.digimon.constant.EntityConstants.TYPE_ENTITY_GRAPH;

/**
 * Repository class for Type
 */
@Repository
@NonNullApi
public interface TypeRepository extends CustomRepository<Type, Long> {

    /**
     * Find a Type by its id
     *
     * @param id The ID of the Type
     * @return An Optional of the Type found
     */
    @Override
    @EntityGraph(value = TYPE_ENTITY_GRAPH)
    Optional<Type> findById(Long id);

    /**
     * Find a Type by its name
     *
     * @param name The name of the Type to find
     * @return An Optional of the Type found
     */
    @EntityGraph(value = TYPE_ENTITY_GRAPH)
    Optional<Type> findByName(String name);

}
