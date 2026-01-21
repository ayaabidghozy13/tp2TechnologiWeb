package pharmacie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Dispensaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull @NotBlank
    private String nom;

    @Embedded // Les champs de AdressePostale deviendront des colonnes dans la table DISPENSAIRE
    private AdressePostale adresse;

    @NonNull @NotBlank
    private String region;

    @OneToMany(mappedBy = "dispensaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commande> commandes = new ArrayList<>();
    
}