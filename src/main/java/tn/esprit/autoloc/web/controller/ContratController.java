package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;
@Tag(name= "Gestion Contrat")
@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService service;
    @Operation(description = "ajouter un objet contrat dans la BD")


    @PostMapping("/api/add")
    public ResponseEntity<Contrat> create(@RequestBody Contrat entite) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entite));
    }

    @GetMapping("getByID/{id}")
    public Contrat findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/getAll")
    public List<Contrat> findAll() {
        return service.findAll();
    }

    @PutMapping("/update/{id}")
    public Contrat update(@PathVariable Long id, @RequestBody Contrat entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
