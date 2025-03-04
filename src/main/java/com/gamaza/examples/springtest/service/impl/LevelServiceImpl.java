package com.gamaza.examples.springtest.service.impl;

import com.gamaza.examples.springtest.dto.request.level.LevelPostDto;
import com.gamaza.examples.springtest.dto.request.level.LevelPatchDto;
import com.gamaza.examples.springtest.dto.response.level.LevelDto;
import com.gamaza.examples.springtest.dto.response.level.LevelRelationsDto;
import com.gamaza.examples.springtest.entity.Level;
import com.gamaza.examples.springtest.exception.AlreadyExistsException;
import com.gamaza.examples.springtest.exception.GenericRuntimeException;
import com.gamaza.examples.springtest.exception.NotFoundException;
import com.gamaza.examples.springtest.mapper.LevelMapper;
import com.gamaza.examples.springtest.repository.LevelRepository;
import com.gamaza.examples.springtest.service.LevelService;
import com.gamaza.examples.springtest.util.ExceptionUtils;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.gamaza.examples.springtest.constant.EntityConstants.LEVEL_OBJECT_NAME;
import static com.gamaza.examples.springtest.constant.EntityConstants.TYPE_OBJECT_NAME;

/**
 * LevelService Implementation class
 */
@Service
public class LevelServiceImpl implements LevelService {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(LevelServiceImpl.class);

    // Injection variables
    private final LevelRepository repository;
    private final LevelMapper mapper;

    /**
     * Constructor injection
     */
    public LevelServiceImpl(LevelRepository repository, LevelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public LevelDto save(LevelPostDto postData) {
        // Result variable
        LevelDto result;
        try {
            // 1. Map the post object to an entity
            Level mappedEntity = mapper.asEntity(postData);
            // 2. Save in database
            Level savedEntity = repository.save(mappedEntity);
            // 3. Map to a DTO
            result = mapper.asDto(savedEntity);

        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            // Error messages
            String errorMessage = ExceptionUtils.getCauseOrDefaultMessage(e);
            // Logs
            LOGGER.error(errorMessage);
            // Throw a custom exception
            throw new AlreadyExistsException(LEVEL_OBJECT_NAME, String.format("name=%s", postData.getName()));
        } catch (Exception e) {
            // Error messages
            String errorMessage = ExceptionUtils.getCauseOrDefaultMessage(e);
            // Logs
            LOGGER.error(errorMessage);
            // Throw a custom exception
            throw new GenericRuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public List<LevelDto> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::asDto)
                .toList();
    }

    @Override
    public LevelRelationsDto findById(Long id) {
        return mapper.asRelationsDto(
                this.internalFindById(id)
        );
    }

    @Override
    public LevelRelationsDto findByName(String name) {
        // 1. Retrieve from database
        Optional<Level> retrievedEntity = repository.findByName(name);
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
    public LevelRelationsDto update(Long id, LevelPatchDto patchData) {
        // 1. Retrieve entity from database
        Level retrievedEntity = this.internalFindById(id);
        // 2. Update the entity
        Level updatedEntity = mapper.asEntity(retrievedEntity, patchData);
        // 3. Update and return
        return mapper.asRelationsDto(
                repository.save(updatedEntity)
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Level retrievedEntity = this.internalFindById(id);
        repository.deleteById(retrievedEntity.getId());
    }

    /**
     * Internal method to find an entity by id
     *
     * @param id The id to search
     * @return The found entity
     */
    private Level internalFindById(Long id) {
        // 1. Retrieve from database
        Optional<Level> retrievedEntity = repository.findById(id);
        // 2. Check null value
        if (retrievedEntity.isEmpty()) {
            // Throw the exception
            throw new NotFoundException(
                    LEVEL_OBJECT_NAME,
                    String.format("id=%s", id)
            );
        }
        return retrievedEntity.get();
    }

}
