package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @OneToOne
    @JoinColumn(name = "contrat_id")
    private Contrat contrat;
}