package tn.esprit.autoloc;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.*;

@SpringBootTest
class AutolocRepositoryTests {

    @Autowired private IAgenceRepository agenceRepository;
    @Autowired private IClientRepository clientRepository;
    @Autowired private IVehiculeRepository vehiculeRepository;
    @Autowired private IEmployeRepository employeRepository;
    @Autowired private IEquipementRepository equipementRepository;
    @Autowired private IContratRepository contratRepository;
    @Autowired private IPaiementRepository paiementRepository;

    // ================= PARTIE 2 : AGENCE =================
    @Test
    void testAgenceRepository() {
        System.out.println("===== TEST IAgenceRepository =====");
        long avant = agenceRepository.count();

        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis Centre");
        agence.setVille("Tunis");
        agence.setAdresse("12 Avenue Habib Bourguiba");
        agence.setTelephone("71123456");

        Agence saved = agenceRepository.save(agence);
        System.out.println("save() -> id=" + saved.getIdAgence() + ", nom=" + saved.getNom());
        assertNotNull(saved.getIdAgence());

        List<Agence> agences = agenceRepository.findAll();
        System.out.println("findAll() -> " + agences.size() + " agence(s)");
        assertFalse(agences.isEmpty());

        Optional<Agence> trouvee = agenceRepository.findById(saved.getIdAgence());
        assertTrue(trouvee.isPresent());
        System.out.println("findById() -> " + trouvee.get().getNom());

        assertTrue(agenceRepository.existsById(saved.getIdAgence()));
        System.out.println("existsById() -> true");

        assertEquals(avant + 1, agenceRepository.count());
        System.out.println("count() -> " + agenceRepository.count());

        agenceRepository.deleteById(saved.getIdAgence());
        assertFalse(agenceRepository.existsById(saved.getIdAgence()));
        System.out.println("deleteById() -> agence supprimée");
    }

    // ================= PARTIE 3 : CLIENT =================
    @Test
    void testClientRepository() {
        System.out.println("===== TEST IClientRepository =====");
        long avant = clientRepository.count();

        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed.benali@mail.com");
        c1.setTelephone("20111222");
        c1.setNumPermis("P-100234");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sara");
        c2.setEmail("sara.trabelsi@mail.com");
        c2.setTelephone("55333444");
        c2.setNumPermis("P-200567");
        c2.setDateInscription(LocalDate.now());

        Client s1 = clientRepository.save(c1);
        Client s2 = clientRepository.save(c2);
        System.out.println("save() -> client 1 id=" + s1.getIdClient() + " (" + s1.getNom() + ")");
        System.out.println("save() -> client 2 id=" + s2.getIdClient() + " (" + s2.getNom() + ")");
        assertNotNull(s1.getIdClient());
        assertNotNull(s2.getIdClient());
        assertNotEquals(s1.getIdClient(), s2.getIdClient());

        List<Client> clients = clientRepository.findAll();
        System.out.println("findAll() -> " + clients.size() + " client(s) :");
        clients.forEach(c -> System.out.println("   - " + c.getIdClient() + " | "
                + c.getNom() + " " + c.getPrenom() + " | " + c.getEmail()));

        Optional<Client> trouve = clientRepository.findById(s1.getIdClient());
        assertTrue(trouve.isPresent());
        System.out.println("findById() -> " + trouve.get().getPrenom() + " " + trouve.get().getNom());

        assertTrue(clientRepository.existsById(s1.getIdClient()));
        assertFalse(clientRepository.existsById(999_999L));
        System.out.println("existsById(" + s1.getIdClient() + ") -> true");
        System.out.println("existsById(999999) -> false");

        assertEquals(avant + 2, clientRepository.count());
        System.out.println("count() -> " + clientRepository.count());

        clientRepository.deleteById(s1.getIdClient());
        clientRepository.deleteById(s2.getIdClient());
    }

    // ================= PARTIE 4 : VEHICULE =================
    @Test
    void testVehiculeRepository() {
        System.out.println("===== TEST IVehiculeRepository =====");
        long avant = vehiculeRepository.count();

        Agence agence = new Agence();
        agence.setNom("AutoLoc Sfax");
        agence.setVille("Sfax");
        agence.setAdresse("5 Rue de la Liberté");
        agence.setTelephone("74987654");
        agence = agenceRepository.save(agence);

        Vehicule v = new Vehicule();
        v.setImmatriculation("200 TUN 4567");
        v.setMarque("Peugeot");
        v.setModele("208");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("80.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        v.setAgence(agence);

        Vehicule saved = vehiculeRepository.save(v);
        System.out.println("save() -> id=" + saved.getIdVehicule() + ", "
                + saved.getMarque() + " " + saved.getModele() + ", statut=" + saved.getStatut());
        assertNotNull(saved.getIdVehicule());

        List<Vehicule> vehicules = vehiculeRepository.findAll();
        System.out.println("findAll() -> " + vehicules.size() + " véhicule(s)");

        assertEquals(avant + 1, vehiculeRepository.count());
        System.out.println("count() -> " + vehiculeRepository.count());

        // Modification : id renseigné => merge (SELECT puis UPDATE)
        saved.setTarifJournalier(new BigDecimal("95.00"));
        saved.setStatut(StatutVehicule.LOUE);
        vehiculeRepository.save(saved);

        Vehicule modifie = vehiculeRepository.findById(saved.getIdVehicule()).orElseThrow();
        System.out.println("Après modification -> tarif=" + modifie.getTarifJournalier()
                + ", statut=" + modifie.getStatut());
        assertEquals(StatutVehicule.LOUE, modifie.getStatut());
        assertEquals(0, new BigDecimal("95.00").compareTo(modifie.getTarifJournalier()));

        vehiculeRepository.deleteById(saved.getIdVehicule());
        assertFalse(vehiculeRepository.existsById(saved.getIdVehicule()));
        System.out.println("deleteById() -> véhicule supprimé");

        agenceRepository.deleteById(agence.getIdAgence());
    }

    // ================= PARTIE 5 : EXERCICE AUTONOME =================
    // 1) Employe
    @Test
    void testEmployeRepository() {
        System.out.println("===== TEST IEmployeRepository =====");
        Agence agence = new Agence();
        agence.setNom("AutoLoc Sousse");
        agence.setVille("Sousse");
        agence = agenceRepository.save(agence);

        Employe e = new Employe();
        e.setNom("Gharbi");
        e.setPrenom("Karim");
        e.setRole(RoleEmploye.MANAGER);
        e.setAgence(agence);

        Employe saved = employeRepository.save(e);
        System.out.println("save() -> id=" + saved.getIdEmploye() + ", "
                + saved.getPrenom() + " " + saved.getNom() + ", role=" + saved.getRole());
        assertNotNull(saved.getIdEmploye());

        System.out.println("findAll() -> " + employeRepository.findAll().size() + " employé(s)");
        assertTrue(employeRepository.findById(saved.getIdEmploye()).isPresent());
        assertTrue(employeRepository.existsById(saved.getIdEmploye()));
        System.out.println("existsById() -> true");

        employeRepository.deleteById(saved.getIdEmploye());
        assertFalse(employeRepository.existsById(saved.getIdEmploye()));
        System.out.println("deleteById() -> employé supprimé");

        agenceRepository.deleteById(agence.getIdAgence());
    }

    // 2) Equipement
    @Test
    void testEquipementRepository() {
        System.out.println("===== TEST IEquipementRepository =====");
        Equipement eq = new Equipement();
        eq.setLibelle("GPS");

        Equipement saved = equipementRepository.save(eq);
        System.out.println("save() -> id=" + saved.getIdEquipement() + ", libelle=" + saved.getLibelle());
        assertNotNull(saved.getIdEquipement());

        System.out.println("findAll() -> " + equipementRepository.findAll().size() + " équipement(s)");
        assertTrue(equipementRepository.findById(saved.getIdEquipement()).isPresent());
        assertTrue(equipementRepository.existsById(saved.getIdEquipement()));
        System.out.println("existsById() -> true");

        equipementRepository.deleteById(saved.getIdEquipement());
        assertFalse(equipementRepository.existsById(saved.getIdEquipement()));
        System.out.println("deleteById() -> équipement supprimé");
    }

    // 3) Contrat (cascade sur Paiement)
    @Test
    void testContratRepository() {
        System.out.println("===== TEST IContratRepository (cascade Paiement) =====");
        long paiementsAvant = paiementRepository.count();

        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("450.00"));
        contrat.setValide(true);

        Paiement p = new Paiement();
        p.setMontant(new BigDecimal("150.00"));
        p.setDatePaiement(LocalDate.now());
        p.setModePaiement(ModePaiement.CARTE);
        p.setContrat(contrat);          // côté propriétaire (écrit contrat_id)
        contrat.getPaiements().add(p);  // côté inverse

        Contrat saved = contratRepository.save(contrat); // cascade : contrat puis paiement
        System.out.println("save() -> contrat id=" + saved.getIdContrat()
                + ", montant=" + saved.getMontantTotal());
        assertNotNull(saved.getIdContrat());
        assertEquals(paiementsAvant + 1, paiementRepository.count());
        System.out.println("Paiement créé par cascade : count paiements = " + paiementRepository.count());

        assertTrue(contratRepository.findById(saved.getIdContrat()).isPresent());
        assertTrue(contratRepository.existsById(saved.getIdContrat()));
        System.out.println("findAll() -> " + contratRepository.findAll().size() + " contrat(s)");

        contratRepository.deleteById(saved.getIdContrat()); // cascade REMOVE
        assertFalse(contratRepository.existsById(saved.getIdContrat()));
        assertEquals(paiementsAvant, paiementRepository.count());
        System.out.println("deleteById() -> contrat ET paiement supprimés (cascade)");
    }
}