package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

@Tag(name = "Gestion Employe")
@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService service;

    @Operation(description = "Ajouter un objet employe dans la BD")
    @PostMapping("/api/add")
    public ResponseEntity<Employe> create(@RequestBody Employe entite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(entite));
    }

    @Operation(description = "Récupérer un employe par son ID")
    @GetMapping("/getByID/{id}")
    public Employe findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(description = "Récupérer la liste de tous les employes")
    @GetMapping("/getAll")
    public List<Employe> findAll() {
        return service.findAll();
    }

    @Operation(description = "Modifier un employe existant")
    @PutMapping("/update/{id}")
    public Employe update(@PathVariable Long id, @RequestBody Employe entite) {
        return service.update(id, entite);
    }

    @Operation(description = "Supprimer un employe par son ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}