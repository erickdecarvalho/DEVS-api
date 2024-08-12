package devs.api.service;

import devs.api.model.Mutant;
import devs.api.repository.MutantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MutantService {
    private static final Integer MINIMUM_QUANTITY_OF_DEFEATED_ALIENS_TO_JOIN_ESPADA = 20;

    private final MutantRepository mutantRepository;
    public MutantService(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    public Boolean hasDefeatedMoreThanTwentyAliens(Integer aliensDefeated ) {
        return aliensDefeated > MINIMUM_QUANTITY_OF_DEFEATED_ALIENS_TO_JOIN_ESPADA ? true : false;
    }

    public Boolean mustJoinEspada(UUID id) {
        Mutant mutant = mutantRepository.getReferenceById(id);

        double aliensPercent = 26.8;
        int deadAliens = (int) Math.round(mutant.getEnemiesDefeated() * aliensPercent / 100);

        return this.hasDefeatedMoreThanTwentyAliens(deadAliens) ? true : false;
    }

    public void recruitMutantForEspada(UUID id) {
        Mutant mutant = mutantRepository.getReferenceById(id);
        mutant.setMemberOfESPADA(true);
        mutantRepository.save(mutant);
    }

    public String recruitmentMessage(UUID id) {
        String message = this.mustJoinEspada(id)
                ? "O mutante foi convocado para E.S.P.A.D.A"
                : "O mutante não foi convocado para E.S.P.A.D.A";

        return message;
    }

    public long countMutantsInSchool() {
        return mutantRepository.countByIsInSchoolTrue();
    }

    public List<Mutant> getMutantsInSchool() {
        return mutantRepository.findByIsInSchoolTrue();
    }
}
