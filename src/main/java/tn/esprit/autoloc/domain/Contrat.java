package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Contrat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;

    @OneToOne(mappedBy = "contrat")
    @JsonIgnore
    @ToString.Exclude
    private Reservation reservation;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("contrat-paiements")
    @ToString.Exclude
    private List<Paiement> paiements = new ArrayList<>();
}