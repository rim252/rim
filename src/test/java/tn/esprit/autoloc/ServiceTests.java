package tn.esprit.autoloc;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.*;
import tn.esprit.autoloc.service.*;

@SpringBootTest
class ServiceTests {

    @Autowired private IAgenceService agenceService;
    @Autowired private IClientService clientService;
    @Autowired private IVehiculeService vehiculeService;
    @Autowired private IEmployeService employeService;
    @Autowired private IEquipementService equipementService;
    @Autowired private IMaintenanceService maintenanceService;

    // Les repositories servent uniquement à count() et existsById() (absents des interfaces Service)
    @Autowired private IAgenceRepository agenceRepository;
    @Autowired private IClientRepository clientRepository;
    @Autowired private IVehiculeRepository vehiculeRepository;
    @Autowired private IEmployeRepository employeRepository;
    @Autowired private IEquipementRepository equipementRepository;
    @Autowired private IMaintenanceRepository maintenanceRepository;

    // ---------- Partie 2 : AgenceService ----------
    @Test
    void testAgenceService() {
        long avant = agenceRepository.count();

        Agence agence = new Agence();
        agence.setNom("Agence Tunis Centre");
        agence.setVille("Tunis");
        agence.setAdresse("10 Avenue Habib Bourguiba");
        agence.setTelephone("71000000");

        Agence saved = agenceService.create(agence);
        assertNotNull(saved);
        assertNotNull(saved.getIdAgence());
        Long idAgence = saved.getIdAgence();
        System.out.println("Agence enregistrée : id=" + idAgence + ", nom=" + saved.getNom());

        System.out.println("--- findAll ---");
        agenceService.findAll().forEach(a ->
                System.out.println(a.getIdAgence() + " | " + a.getNom() + " | " + a.getVille()));

        Agence trouvee = agenceService.findById(idAgence);
        assertEquals("Agence Tunis Centre", trouvee.getNom());
        System.out.println("findById : " + trouvee.getNom());

        // update
        Agence modif = new Agence();
        modif.setNom("Agence Tunis Lac");
        modif.setVille("Tunis");
        modif.setAdresse("Les Berges du Lac");
        modif.setTelephone("71111111");
        Agence maj = agenceService.update(idAgence, modif);
        assertEquals("Agence Tunis Lac", maj.getNom());
        System.out.println("update : " + maj.getNom());

        assertEquals(avant + 1, agenceRepository.count());
        System.out.println("Nombre d'agences : " + agenceRepository.count());

        agenceService.deleteById(idAgence);
        assertFalse(agenceRepository.existsById(idAgence));
        assertThrows(ResourceNotFoundException.class,
                () -> agenceService.findById(idAgence));
        assertEquals(avant, agenceRepository.count());
        System.out.println("Agence supprimée, elle n'existe plus.");
    }

    // ---------- Partie 3 : ClientService ----------
    @Test
    void testClientService() {
        long avant = clientRepository.count();

        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed.benali@test.tn");
        c1.setTelephone("20111111");
        c1.setNumPermis("P-1001");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sarra");
        c2.setEmail("sarra.trabelsi@test.tn");
        c2.setTelephone("20222222");
        c2.setNumPermis("P-1002");
        c2.setDateInscription(LocalDate.now());

        Client s1 = clientService.create(c1);
        Client s2 = clientService.create(c2);
        assertNotNull(s1.getIdClient());
        assertNotNull(s2.getIdClient());
        assertNotEquals(s1.getIdClient(), s2.getIdClient());
        Long idClient1 = s1.getIdClient();
        Long idClient2 = s2.getIdClient();
        System.out.println("Clients enregistrés : " + idClient1 + " et " + idClient2);

        System.out.println("--- findAll ---");
        clientService.findAll().forEach(c ->
                System.out.println(c.getIdClient() + " | " + c.getNom() + " " + c.getPrenom()
                        + " | " + c.getEmail()));

        Client trouve = clientService.findById(idClient1);
        assertEquals("Ben Ali", trouve.getNom());
        System.out.println("findById : " + trouve.getNom() + " " + trouve.getPrenom());

        assertEquals(avant + 2, clientRepository.count());
        System.out.println("Nombre de clients : " + clientRepository.count());

        clientService.deleteById(idClient1);
        assertFalse(clientRepository.existsById(idClient1));
        assertThrows(ResourceNotFoundException.class,
                () -> clientService.findById(idClient1));
        System.out.println("Client " + idClient1 + " supprimé, il n'existe plus.");

        // nettoyage du second client
        clientService.deleteById(idClient2);
        assertEquals(avant, clientRepository.count());
    }

    // ---------- Partie 4 : VehiculeService ----------
    @Test
    void testVehiculeService() {
        long avant = vehiculeRepository.count();

        Vehicule v = new Vehicule();
        v.setImmatriculation("123 TUN 4567");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.values()[0]);
        v.setTarifJournalier(new BigDecimal("80.00"));
        v.setStatut(StatutVehicule.values()[0]);

        Vehicule saved = vehiculeService.create(v);
        assertNotNull(saved.getIdVehicule());
        System.out.println("Véhicule enregistré : id=" + saved.getIdVehicule()
                + ", " + saved.getMarque() + " " + saved.getModele());

        System.out.println("--- findAll ---");
        vehiculeService.findAll().forEach(x ->
                System.out.println(x.getIdVehicule() + " | " + x.getImmatriculation()
                        + " | " + x.getMarque() + " " + x.getModele()
                        + " | " + x.getTarifJournalier()));

        Vehicule trouve = vehiculeService.findById(saved.getIdVehicule());
        assertEquals("Renault", trouve.getMarque());
        System.out.println("findById : " + trouve.getMarque() + " " + trouve.getModele());

        assertEquals(avant + 1, vehiculeRepository.count());
        System.out.println("Nombre de véhicules : " + vehiculeRepository.count());

        // règle de gestion : tarif négatif refusé
        Vehicule invalide = new Vehicule();
        invalide.setTarifJournalier(new BigDecimal("-5"));
        assertThrows(IllegalArgumentException.class, () -> vehiculeService.create(invalide));

        vehiculeService.deleteById(saved.getIdVehicule());
        assertFalse(vehiculeRepository.existsById(saved.getIdVehicule()));
        assertEquals(avant, vehiculeRepository.count());
        System.out.println("Véhicule supprimé.");
    }

    // ---------- Partie 5 : exercice autonome (Employe, Equipement, Maintenance) ----------
    @Test
    void testEmployeService() {
        Employe e = new Employe();
        e.setNom("Gharbi");
        e.setPrenom("Karim");
        e.setRole(RoleEmploye.values()[0]);

        Employe saved = employeService.create(e);
        assertNotNull(saved.getIdEmploye());
        System.out.println("Employé créé : " + saved.getIdEmploye() + " " + saved.getNom());

        assertEquals("Gharbi", employeService.findById(saved.getIdEmploye()).getNom());
        System.out.println("findAll : " + employeService.findAll().size() + " employé(s)");

        assertTrue(employeRepository.existsById(saved.getIdEmploye()));
        employeService.deleteById(saved.getIdEmploye());
        assertFalse(employeRepository.existsById(saved.getIdEmploye()));
        System.out.println("Employé supprimé, existsById = " + employeRepository.existsById(saved.getIdEmploye()));
    }

    @Test
    void testEquipementService() {
        Equipement eq = new Equipement();
        eq.setLibelle("GPS");

        Equipement saved = equipementService.create(eq);
        assertNotNull(saved.getIdEquipement());
        System.out.println("Équipement créé : " + saved.getIdEquipement() + " " + saved.getLibelle());

        assertEquals("GPS", equipementService.findById(saved.getIdEquipement()).getLibelle());
        System.out.println("findAll : " + equipementService.findAll().size() + " équipement(s)");

        assertTrue(equipementRepository.existsById(saved.getIdEquipement()));
        equipementService.deleteById(saved.getIdEquipement());
        assertFalse(equipementRepository.existsById(saved.getIdEquipement()));
        System.out.println("Équipement supprimé, existsById = " + equipementRepository.existsById(saved.getIdEquipement()));
    }

    @Test
    void testMaintenanceService() {
        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.now());
        m.setDateFin(LocalDate.now().plusDays(2));
        m.setDescription("Vidange et contrôle des freins");

        Maintenance saved = maintenanceService.create(m);
        assertNotNull(saved.getIdMaintenance());
        System.out.println("Maintenance créée : " + saved.getIdMaintenance() + " " + saved.getDescription());

        assertEquals("Vidange et contrôle des freins",
                maintenanceService.findById(saved.getIdMaintenance()).getDescription());
        System.out.println("findAll : " + maintenanceService.findAll().size() + " maintenance(s)");

        // règle de gestion : dateFin avant dateDebut refusée
        Maintenance invalide = new Maintenance();
        invalide.setDateDebut(LocalDate.now());
        invalide.setDateFin(LocalDate.now().minusDays(1));
        assertThrows(IllegalArgumentException.class, () -> maintenanceService.create(invalide));

        assertTrue(maintenanceRepository.existsById(saved.getIdMaintenance()));
        maintenanceService.deleteById(saved.getIdMaintenance());
        assertFalse(maintenanceRepository.existsById(saved.getIdMaintenance()));
        System.out.println("Maintenance supprimée, existsById = " + maintenanceRepository.existsById(saved.getIdMaintenance()));
    }
}