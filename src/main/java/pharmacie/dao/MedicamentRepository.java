package pharmacie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pharmacie.entity.Medicament;
import java.util.List;
import java.util.Optional;

public interface MedicamentRepository extends JpaRepository<Medicament, Integer> {
    // Correction de l'erreur de compilation :
    Optional<Medicament> findByNom(String nom);

    // Utilisé dans RepositoryCustomMethodsTest :
    List<Medicament> findByIndisponibleFalse();

    // Requête TP2 : Médicaments disponibles par catégorie
    @Query("SELECT m FROM Medicament m WHERE m.categorie.code = :idCat " +
           "AND m.indisponible = false " +
           "AND m.unitesEnStock >= m.unitesCommandees")
    List<Medicament> findDisponiblesParCategorie(@Param("idCat") Integer idCat);
}
