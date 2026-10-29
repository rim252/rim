package tn.esprit.autoloc.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;
@Tag(name= "Gestion Client")
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService service;
    @Operation(description = "ajouter un objet client dans la BD")
    @PostMapping("/api/add")
    public ResponseEntity<Client> create(@RequestBody Client entite) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(entite));
    }

    @GetMapping("getByID/{id}")
    public Client findById(@PathVariable Long id) {
        return service.findById(id);
    }
    @GetMapping("/getAll")

    public List<Client> findAll() {
        return service.findAll();
    }
    @PutMapping("/update/{id}")

    public Client update(@PathVariable Long id, @RequestBody Client entite) {
        return service.update(id, entite);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
