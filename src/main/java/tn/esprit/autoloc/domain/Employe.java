package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import java.util.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Employe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;
    private String nom;
    private String prenom;

    @Enumerated(EnumType.STRING)
    private RoleEmploye role;

    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;
}