package pharmacie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pharmacie.entity.Commande;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {

    // Calculer le nombre d'articles déjà expédiés (dateExpedition non nulle)
    @Query("SELECT SUM(l.quantite) FROM Ligne l " +
           "WHERE l.commande.dispensaire.id = :idDispensaire " +
           "AND l.commande.dateExpedition IS NOT NULL")
    Long countArticlesExpedies(@Param("idDispensaire") Integer idDispensaire);

    // Trouver les commandes en cours (dateExpedition est nulle)
    List<Commande> findByDispensaireIdAndDateExpeditionIsNull(Integer idDispensaire);

}