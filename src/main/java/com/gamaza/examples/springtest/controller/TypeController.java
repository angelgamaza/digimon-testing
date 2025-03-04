package com.gamaza.examples.springtest.controller;

import com.gamaza.examples.springtest.dto.request.type.TypePatchDto;
import com.gamaza.examples.springtest.dto.request.type.TypePostDto;
import com.gamaza.examples.springtest.dto.response.type.TypeDto;
import com.gamaza.examples.springtest.dto.response.type.TypeRelationsDto;
import com.gamaza.examples.springtest.service.TypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Type Controller class
 */
@RestController
@RequestMapping(value = "/type")
public class TypeController {

    // Injection variables
    private final TypeService service;

    /**
     * Constuctor injection
     */
    public TypeController(TypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TypeDto> save(@RequestBody @Valid TypePostDto postData) {
        TypeDto result = service.save(postData);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .port(8081)
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(result);
    }

    @GetMapping
    public ResponseEntity<List<TypeDto>> findAll() {
        List<TypeDto> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TypeRelationsDto> findById(@PathVariable Long id) {
        TypeRelationsDto result = service.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/name")
    public ResponseEntity<TypeRelationsDto> findByName(@RequestParam String name) {
        TypeRelationsDto result = service.findByName(name);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<TypeRelationsDto> update(@PathVariable Long id, @RequestBody @Valid TypePatchDto patchData) {
        TypeRelationsDto result = service.update(id, patchData);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
