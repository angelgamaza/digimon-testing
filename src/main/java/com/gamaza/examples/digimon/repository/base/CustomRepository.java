package com.gamaza.examples.digimon.repository.base;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Custom repository interface
 *
 * @param <T> Entity type
 * @param <I> Entity ID type
 */
@NoRepositoryBean
@NonNullApi
public interface CustomRepository<T, I extends Serializable> extends JpaRepository<T, I> {

    @Override
    List<T> findAll();

    /**
     * Refresh the given entity in the database
     *
     * @param t The entity to refresh
     */
    void refresh(T t);

    /**
     * Save and refresh the entity in the database
     *
     * @param t The entity to process
     * @return The saved entity
     */
    T saveAndRefresh(T t);

}
