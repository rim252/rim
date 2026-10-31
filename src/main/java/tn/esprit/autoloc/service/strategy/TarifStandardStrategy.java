package tn.esprit.autoloc.service.strategy;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class TarifStandardStrategy implements ITarificationStrategy {
    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        jours = Math.max(jours, 1);
        return vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(jours));
    }
    @Override
    public String getCode() {
        return "STANDARD";
    }
}

