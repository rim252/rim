package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

@Tag(name = "Gestion Equipement")
@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService service;

    @Operation(description = "Ajouter un objet equipement dans la BD")
    @PostMapping("/api/add")
    public ResponseEntity<Equipement> create(@RequestBody Equipement entite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(entite));
    }

    @Operation(description = "Récupérer un equipement par son ID")
    @GetMapping("/getByID/{id}")
    public Equipement findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(description = "Récupérer la liste de tous les equipements")
    @GetMapping("/getAll")
    public List<Equipement> findAll() {
        return service.findAll();
    }

    @Operation(description = "Modifier un equipement existant")
    @PutMapping("/update/{id}")
    public Equipement update(@PathVariable Long id, @RequestBody Equipement entite) {
        return service.update(id, entite);
    }

    @Operation(description = "Supprimer un equipement par son ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}