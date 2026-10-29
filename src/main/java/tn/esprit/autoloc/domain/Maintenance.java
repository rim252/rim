package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Maintenance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaintenance;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;
}