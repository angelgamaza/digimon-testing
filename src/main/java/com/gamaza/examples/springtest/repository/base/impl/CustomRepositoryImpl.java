package com.gamaza.examples.springtest.repository.base.impl;

import com.gamaza.examples.springtest.repository.base.CustomRepository;
import io.micrometer.common.lang.NonNullApi;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * {@link CustomRepository} implementation
 */
@NonNullApi
public class CustomRepositoryImpl<T, I extends Serializable> extends SimpleJpaRepository<T, I> implements CustomRepository<T, I> {

    // Injected entity manager
    private final EntityManager entityManager;

    /**
     * Constructor
     */
    public CustomRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void refresh(T t) {
        entityManager.refresh(t);
    }

    @Override
    @Transactional
    public T saveAndRefresh(T entity) {
        T savedEntity = super.save(entity);
        entityManager.refresh(savedEntity);
        return savedEntity;
    }

}
