package pharmacie.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pharmacie.entity.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RepositoryCustomMethodsTest {

    @Autowired private CategorieRepository categorieRepository;
    @Autowired private MedicamentRepository medicamentRepository;
    @Autowired private CommandeRepository commandeRepository;
    @Autowired private DispensaireRepository dispensaireRepository;

    @Test
    public void testMedicamentCustomMethods() {    
        List<Medicament> disponibles = medicamentRepository.findByIndisponibleFalse();
        assertNotNull(disponibles);
    }

    @Test
    public void testCategorieCustomMethods() {
        Categorie c1 = new Categorie();
        c1.setLibelle("AnalgesiquesTest");
        categorieRepository.save(c1);
        assertNotNull(categorieRepository.findByLibelle("AnalgesiquesTest"));
    }

    @Test
    public void testRequetesMetierTP2() {
        // Test des requêtes JPQL du TP
        Long total = commandeRepository.countArticlesExpedies(1);
        List<Commande> enCours = commandeRepository.findByDispensaireIdAndDateExpeditionIsNull(1);
        List<Medicament> dispos = medicamentRepository.findDisponiblesParCategorie(1);
        
        assertNotNull(enCours);
        assertNotNull(dispos);
    }

    @Test
public void testSuppressionCascadeDispensaire() {
    // 1. Créer le dispensaire
    Dispensaire d = new Dispensaire();
    d.setNom("Hôpital de Test");
    d.setRegion("Occitanie");
    // Initialiser la liste si elle est nulle
    if (d.getCommandes() == null) {
        d.setCommandes(new java.util.ArrayList<>());
    }

    // 2. Créer la commande ET l'ajouter au dispensaire
    Commande c = new Commande();
    c.setDispensaire(d);
    c.setDateSaisie(LocalDate.now());
    d.getCommandes().add(c); // Liaison bidirectionnelle

    // 3. Sauvegarder le dispensaire (S'il y a CascadeType.ALL ou PERSIST, la commande est sauvée aussi)
    // Sinon, on sauve les deux séparément dans l'ordre
    dispensaireRepository.saveAndFlush(d);
    commandeRepository.saveAndFlush(c);

    long countAvant = commandeRepository.count();
    assertTrue(countAvant > 0, "Il doit y avoir au moins une commande");

    // 4. Action : Supprimer le dispensaire
    dispensaireRepository.delete(d);
    dispensaireRepository.flush(); // Force H2 à appliquer la suppression

    // 5. Vérification
    assertEquals(countAvant - 1, commandeRepository.count(), 
        "La commande liée aurait dû être supprimée par la cascade");
}
}
