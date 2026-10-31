package tn.esprit.autoloc.service.strategy;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class TarifWeekendStrategy implements ITarificationStrategy {
    private static final BigDecimal REMISE_WEEKEND = new BigDecimal("0.90");
    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        long jours= ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        jours= Math.max(jours,1);
        BigDecimal tarifBrut= vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(jours));
        return tarifBrut.multiply(REMISE_WEEKEND);    }
    @Override
    public String getCode() {
        return "WEEKEND";
    }
}