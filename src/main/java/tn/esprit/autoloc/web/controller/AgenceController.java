package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
@Tag(name= "Gestion Agence")
@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService service;
    @Operation(description = "ajouter un objet agence dans la BD")
    @PostMapping("/api/add")
    public ResponseEntity<Agence> create(@RequestBody Agence entite) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entite));
    }

    @GetMapping("getByID/{id}")
    public Agence findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/getAll")
    public List<Agence> findAll() {
        return service.findAll();
    }

    @PutMapping("/update/{id}")
    public Agence update(@PathVariable Long id, @RequestBody Agence entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
