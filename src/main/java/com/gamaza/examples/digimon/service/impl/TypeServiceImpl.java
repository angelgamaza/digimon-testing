package com.gamaza.examples.digimon.service.impl;

import com.gamaza.examples.digimon.dto.request.type.TypePatchDto;
import com.gamaza.examples.digimon.dto.request.type.TypePostDto;
import com.gamaza.examples.digimon.dto.response.type.TypeDto;
import com.gamaza.examples.digimon.dto.response.type.TypeRelationsDto;
import com.gamaza.examples.digimon.entity.Type;
import com.gamaza.examples.digimon.exception.AlreadyExistsException;
import com.gamaza.examples.digimon.exception.GenericRuntimeException;
import com.gamaza.examples.digimon.exception.NotFoundException;
import com.gamaza.examples.digimon.mapper.TypeMapper;
import com.gamaza.examples.digimon.repository.TypeRepository;
import com.gamaza.examples.digimon.service.TypeService;
import com.gamaza.examples.digimon.util.ExceptionUtils;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.gamaza.examples.digimon.constant.EntityConstants.TYPE_OBJECT_NAME;

/**
 * TypeService Implementation class
 */
@Service
public class TypeServiceImpl implements TypeService {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeServiceImpl.class);

    // Injection variables
    private final TypeRepository repository;
    private final TypeMapper mapper;

    /**
     * Constructor injection
     */
    public TypeServiceImpl(TypeRepository repository, TypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TypeDto save(TypePostDto postData) {
        // Result variable
        TypeDto result;
        try {
            // 1. Map the post object to an entity
            Type mappedEntity = mapper.asEntity(postData);
            // 2. Save in database
            Type savedEntity = repository.save(mappedEntity);
            // 3. Map to a DTO
            result = mapper.asDto(savedEntity);

        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            // Error messages
            String errorMessage = ExceptionUtils.getCauseOrDefaultMessage(e);
            // Logs
            LOGGER.error(errorMessage);
            // Throw a custom exception
            throw new AlreadyExistsException(TYPE_OBJECT_NAME, String.format("name=%s", postData.getName()));
        } catch (Exception e) {
            // Error messages
            String errorMessage = ExceptionUtils.getCauseOrDefaultMessage(e);
            // Logs
            LOGGER.error(errorMessage);
            // Throw a custom exception
            throw new GenericRuntimeException(errorMessage);
        }
        return result;
    }

    @Override
    public List<TypeDto> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::asDto)
                .toList();
    }

    @Override
    public TypeRelationsDto findById(Long id) {
        return mapper.asRelationsDto(
                this.internalFindById(id)
        );
    }

    @Override
    public TypeRelationsDto findByName(String name) {
        // 1. Retrieve from database
        Optional<Type> retrievedEntity = repository.findByName(name);
        // 2. Check null value
        if (retrievedEntity.isEmpty()) {
            // Throw the exception
            throw new NotFoundException(
                    TYPE_OBJECT_NAME,
                    String.format("name=%s", name)
            );
        }
        // 3. Return the found entity
        return mapper.asRelationsDto(
                retrievedEntity.get()
        );
    }

    @Override
    @Transactional
    public TypeRelationsDto update(Long id, TypePatchDto patchData) {
        // 1. Retrieve the entity
        Type retrievedEntity = this.internalFindById(id);
        // 2. Update the entity (Mapping via)
        Type updatedEntity = mapper.asEntity(retrievedEntity, patchData);
        // 3. Update in datbase and return
        return mapper.asRelationsDto(
                repository.save(updatedEntity)
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Type retrievedEntity = this.internalFindById(id);
        repository.deleteById(retrievedEntity.getId());
    }

    /**
     * Internal method to find an entity by id
     *
     * @param id The id to search
     * @return The found entity
     */
    private Type internalFindById(Long id) {
        // 1. Retrieve from database
        Optional<Type> retrievedEntity = repository.findById(id);
        // 2. Check null value
        if (retrievedEntity.isEmpty()) {
            // Throw the exception
            throw new NotFoundException(
                    TYPE_OBJECT_NAME,
                    String.format("id=%s", id)
            );
        }
        return retrievedEntity.get();
    }

}
