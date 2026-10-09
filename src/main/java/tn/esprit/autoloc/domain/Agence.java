package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Agence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;

    // 1 agence -> plusieurs employés
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Employe> employes = new ArrayList<>();

    // 1 agence -> plusieurs véhicules
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Vehicule> vehicules = new ArrayList<>();
}