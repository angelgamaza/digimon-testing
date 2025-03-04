package com.gamaza.examples.springtest.controller;

import com.gamaza.examples.springtest.dto.request.digimon.DigimonPatchDto;
import com.gamaza.examples.springtest.dto.request.digimon.DigimonPostDto;
import com.gamaza.examples.springtest.dto.response.digimon.DigimonDto;
import com.gamaza.examples.springtest.dto.response.digimon.DigimonRelationsDto;
import com.gamaza.examples.springtest.service.DigimonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Digimon Controller class
 */
@RestController
@RequestMapping(value = "/digimon")
public class DigimonController {


    // Injection variables
    private final DigimonService service;

    /**
     * Constuctor injection
     */
    public DigimonController(DigimonService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DigimonDto> save(@RequestBody @Valid DigimonPostDto postData) {
        DigimonDto result = service.save(postData);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .port(8081)
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(result);
    }

    @GetMapping
    public ResponseEntity<List<DigimonDto>> findAll() {
        List<DigimonDto> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DigimonRelationsDto> findById(@PathVariable Long id) {
        DigimonRelationsDto result = service.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/name")
    public ResponseEntity<DigimonRelationsDto> findByName(@RequestParam String name) {
        DigimonRelationsDto result = service.findByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/type/{id}")
    public ResponseEntity<List<DigimonDto>> findAllByTypeId(@PathVariable Long id) {
        List<DigimonDto> result = service.findAllByTypeId(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/type/name")
    public ResponseEntity<List<DigimonDto>> findAllByTypeName(@RequestParam String name) {
        List<DigimonDto> result = service.findAllByTypeName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/level/{id}")
    public ResponseEntity<List<DigimonDto>> findAllByLevelId(@PathVariable Long id) {
        List<DigimonDto> result = service.findAllByLevelId(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/level/name")
    public ResponseEntity<List<DigimonDto>> findAllByLevelName(@RequestParam String name) {
        List<DigimonDto> result = service.findAllByLevelName(name);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<DigimonRelationsDto> update(@PathVariable Long id, @RequestBody @Valid DigimonPatchDto patchData) {
        DigimonRelationsDto result = service.update(id, patchData);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
