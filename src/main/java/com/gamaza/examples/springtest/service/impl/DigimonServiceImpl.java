package com.gamaza.examples.springtest.service.impl;

import com.gamaza.examples.springtest.dto.request.digimon.DigimonPatchDto;
import com.gamaza.examples.springtest.dto.request.digimon.DigimonPostDto;
import com.gamaza.examples.springtest.dto.response.digimon.DigimonDto;
import com.gamaza.examples.springtest.dto.response.digimon.DigimonRelationsDto;
import com.gamaza.examples.springtest.entity.Digimon;
import com.gamaza.examples.springtest.exception.AlreadyExistsException;
import com.gamaza.examples.springtest.exception.GenericRuntimeException;
import com.gamaza.examples.springtest.exception.NotFoundException;
import com.gamaza.examples.springtest.mapper.DigimonMapper;
import com.gamaza.examples.springtest.repository.DigimonRepository;
import com.gamaza.examples.springtest.service.DigimonService;
import com.gamaza.examples.springtest.util.ExceptionUtils;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.gamaza.examples.springtest.constant.EntityConstants.DIGIMON_OBJECT_NAME;

/**
 * DigimonService Implementation class
 */
@Service
public class DigimonServiceImpl implements DigimonService {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DigimonServiceImpl.class);

    // Injection variables
    private final DigimonRepository repository;
    private final DigimonMapper mapper;

    /**
     * Constructor injection
     */
    public DigimonServiceImpl(DigimonRepository repository, DigimonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DigimonDto save(DigimonPostDto postData) {
        // Result variable
        DigimonDto result;
        try {
            // 1. Map the post object to an entity
            Digimon mappedEntity = mapper.asEntity(postData);
            // 2. Save in database
            Digimon savedEntity = repository.save(mappedEntity);
            // 3. Map to a DTO
            result = mapper.asDto(savedEntity);

        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            // Error messages
            String errorMessage = ExceptionUtils.getCauseOrDefaultMessage(e);
            // Logs
            LOGGER.error(errorMessage);
            // Throw a custom exception
            throw new AlreadyExistsException(DIGIMON_OBJECT_NAME, String.format("name=%s", postData.getName()));
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
    public List<DigimonDto> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::asDto)
                .toList();
    }

    @Override
    public DigimonRelationsDto findById(Long id) {
        return mapper.asRelationsDto(
                this.internalFindById(id)
        );
    }

    @Override
    public DigimonRelationsDto findByName(String name) {
        // 1. Retrieve from database
        Optional<Digimon> retrievedEntity = repository.findByName(name);
        // 2. Check null value
        if (retrievedEntity.isEmpty()) {
            // Throw the exception
            throw new NotFoundException(
                    DIGIMON_OBJECT_NAME,
                    String.format("name=%s", name)
            );
        }
        // 3. Return the found entity
        return mapper.asRelationsDto(
                retrievedEntity.get()
        );
    }

    @Override
    public List<DigimonDto> findAllByTypeId(Long id) {
        return repository
                .findAllByTypeId(id)
                .stream()
                .map(mapper::asDto)
                .toList();
    }

    @Override
    public List<DigimonDto> findAllByTypeName(String name) {
        return repository
                .findAllByTypeName(name)
                .stream()
                .map(mapper::asDto)
                .toList();
    }

    @Override
    public List<DigimonDto> findAllByLevelId(Long id) {
        return repository
                .findAllByLevelId(id)
                .stream()
                .map(mapper::asDto)
                .toList();
    }

    @Override
    public List<DigimonDto> findAllByLevelName(String name) {
        return repository
                .findAllByLevelName(name)
                .stream()
                .map(mapper::asDto)
                .toList();
    }

    @Override
    @Transactional
    public DigimonRelationsDto update(Long id, DigimonPatchDto patchData) {
        // 1. Retrieve entity from database
        Digimon retrievedEntity = this.internalFindById(id);
        // 2. Update the entity
        Digimon updatedEntity = mapper.asEntity(retrievedEntity, patchData);
        // 3. Update and return
        return mapper.asRelationsDto(
                repository.save(updatedEntity)
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Digimon retrievedEntity = this.internalFindById(id);
        repository.deleteById(retrievedEntity.getId());
    }

    /**
     * Internal method to find an entity by id
     *
     * @param id The id to search
     * @return The found entity
     */
    private Digimon internalFindById(Long id) {
        // 1. Retrieve from database
        Optional<Digimon> retrievedEntity = repository.findById(id);
        // 2. Check null value
        if (retrievedEntity.isEmpty()) {
            // Throw the exception
            throw new NotFoundException(
                    DIGIMON_OBJECT_NAME,
                    String.format("id=%s", id)
            );
        }
        return retrievedEntity.get();
    }

}
