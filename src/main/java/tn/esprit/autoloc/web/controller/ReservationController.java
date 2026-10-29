package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

@Tag(name = "Gestion Reservation")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService service;

    @Operation(description = "Ajouter une reservation dans la BD")
    @PostMapping("/api/add")
    public ResponseEntity<Reservation> create(@RequestBody Reservation entite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(entite));
    }

    @Operation(description = "Récupérer une reservation par son ID")
    @GetMapping("/getByID/{id}")
    public Reservation findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(description = "Récupérer la liste de toutes les reservations")
    @GetMapping("/getAll")
    public List<Reservation> findAll() {
        return service.findAll();
    }

    @Operation(description = "Modifier une reservation existante")
    @PutMapping("/update/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation entite) {
        return service.update(id, entite);
    }

    @Operation(description = "Supprimer une reservation par son ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}