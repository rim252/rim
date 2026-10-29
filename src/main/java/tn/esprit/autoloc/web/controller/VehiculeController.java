package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@Tag(name = "Gestion Vehicule")
@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService service;

    @Operation(description = "Ajouter un vehicule dans la BD")
    @PostMapping("/api/add")
    public ResponseEntity<Vehicule> create(@RequestBody Vehicule entite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(entite));
    }

    @Operation(description = "Récupérer un vehicule par son ID")
    @GetMapping("/getByID/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(description = "Récupérer la liste de tous les vehicules")
    @GetMapping("/getAll")
    public List<Vehicule> findAll() {
        return service.findAll();
    }

    @Operation(description = "Modifier un vehicule existant")
    @PutMapping("/update/{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule entite) {
        return service.update(id, entite);
    }

    @Operation(description = "Supprimer un vehicule par son ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}