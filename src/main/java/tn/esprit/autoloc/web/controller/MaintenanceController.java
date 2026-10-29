package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

@Tag(name = "Gestion Maintenance")
@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService service;

    @Operation(description = "Ajouter un objet maintenance dans la BD")
    @PostMapping("/api/add")
    public ResponseEntity<Maintenance> create(@RequestBody Maintenance entite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(entite));
    }

    @Operation(description = "Récupérer une maintenance par son ID")
    @GetMapping("/getByID/{id}")
    public Maintenance findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(description = "Récupérer la liste de toutes les maintenances")
    @GetMapping("/getAll")
    public List<Maintenance> findAll() {
        return service.findAll();
    }

    @Operation(description = "Modifier une maintenance existante")
    @PutMapping("/update/{id}")
    public Maintenance update(@PathVariable Long id, @RequestBody Maintenance entite) {
        return service.update(id, entite);
    }

    @Operation(description = "Supprimer une maintenance par son ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}