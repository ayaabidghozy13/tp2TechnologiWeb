package pharmacie.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pharmacie.entity.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
    Categorie findByLibelle(String libelle);
    List<Categorie> findByLibelleContaining(String substring);
}
