package com.gamaza.examples.springtest.controller;

import com.gamaza.examples.springtest.dto.request.level.LevelPatchDto;
import com.gamaza.examples.springtest.dto.request.level.LevelPostDto;
import com.gamaza.examples.springtest.dto.response.level.LevelDto;
import com.gamaza.examples.springtest.dto.response.level.LevelRelationsDto;
import com.gamaza.examples.springtest.service.LevelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Level Controller class
 */
@RestController
@RequestMapping(value = "/level")
public class LevelController {

    // Injection variables
    private final LevelService service;

    /**
     * Constuctor injection
     */
    public LevelController(LevelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LevelDto> save(@RequestBody @Valid LevelPostDto postData) {
        LevelDto result = service.save(postData);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .port(8081)
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(result);
    }

    @GetMapping
    public ResponseEntity<List<LevelDto>> findAll() {
        List<LevelDto> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LevelRelationsDto> findById(@PathVariable Long id) {
        LevelRelationsDto result = service.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/name")
    public ResponseEntity<LevelRelationsDto> findByName(@RequestParam String name) {
        LevelRelationsDto result = service.findByName(name);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<LevelRelationsDto> update(@PathVariable Long id, @RequestBody @Valid LevelPatchDto patchData) {
        LevelRelationsDto result = service.update(id, patchData);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}