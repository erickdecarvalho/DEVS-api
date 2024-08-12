package devs.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "mutants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mutant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String password;
    private Integer power;
    private Integer age;
    private Integer enemiesDefeated;
    private  boolean isMemberOfESPADA;
    private  boolean isInSchool;
}
