package tn.esprit.autoloc.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Contrat;

import tn.esprit.autoloc.exception.ResourceNotFoundException;

import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IContratRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;

    @Override
    public Reservation create(Reservation entite) {

        if (entite.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant");
        }

        valider(entite);

        if (entite.getClient() == null ||
                entite.getClient().getIdClient() == null) {
            throw new IllegalArgumentException("Le client est obligatoire");
        }

        if (entite.getVehicule() == null ||
                entite.getVehicule().getIdVehicule() == null) {
            throw new IllegalArgumentException("Le véhicule est obligatoire");
        }

        Client client = clientRepository.findById(
                entite.getClient().getIdClient()
        ).orElseThrow(() ->
                new IllegalArgumentException("Client introuvable"));

        Vehicule vehicule = vehiculeRepository.findById(
                entite.getVehicule().getIdVehicule()
        ).orElseThrow(() ->
                new IllegalArgumentException("Véhicule introuvable"));

        entite.setClient(client);
        entite.setVehicule(vehicule);

        if (entite.getContrat() != null &&
                entite.getContrat().getIdContrat() != null) {

            Contrat contrat = contratRepository.findById(
                    entite.getContrat().getIdContrat()
            ).orElseThrow(() ->
                    new IllegalArgumentException("Contrat introuvable"));

            entite.setContrat(contrat);
        }

        return reservationRepository.save(entite);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation entite) {

        Reservation existant = findById(id);

        valider(entite);

        if (entite.getClient() == null ||
                entite.getClient().getIdClient() == null) {
            throw new IllegalArgumentException("Le client est obligatoire");
        }

        if (entite.getVehicule() == null ||
                entite.getVehicule().getIdVehicule() == null) {
            throw new IllegalArgumentException("Le véhicule est obligatoire");
        }

        Client client = clientRepository.findById(
                entite.getClient().getIdClient()
        ).orElseThrow(() ->
                new IllegalArgumentException("Client introuvable"));

        Vehicule vehicule = vehiculeRepository.findById(
                entite.getVehicule().getIdVehicule()
        ).orElseThrow(() ->
                new IllegalArgumentException("Véhicule introuvable"));

        existant.setDateDebut(entite.getDateDebut());
        existant.setDateFin(entite.getDateFin());
        existant.setStatut(entite.getStatut());
        existant.setClient(client);
        existant.setVehicule(vehicule);

        if (entite.getContrat() != null &&
                entite.getContrat().getIdContrat() != null) {

            Contrat contrat = contratRepository.findById(
                    entite.getContrat().getIdContrat()
            ).orElseThrow(() ->
                    new IllegalArgumentException("Contrat introuvable"));

            existant.setContrat(contrat);
        }

        return reservationRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
    }

    private void valider(Reservation entite) {

        if (entite.getDateDebut() == null ||
                entite.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires");
        }

        if (entite.getDateFin().isBefore(entite.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}