package devs.api.controller;

import devs.api.dto.MutantLoginDTO;
import devs.api.dto.MutanteCreateDTO;
import devs.api.model.Mutant;
import devs.api.repository.MutantRepository;
import devs.api.service.MutantService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mutantes")
public class MutantController {

    private final MutantRepository mutantRepository;
    private final MutantService mutantService;

    public MutantController(MutantRepository mutantRepository, MutantService mutantService) {
        this.mutantRepository = mutantRepository;
        this.mutantService = mutantService;
    }

    @PostMapping("escola/login")
    public ResponseEntity<Object> login(@RequestBody @Valid MutantLoginDTO mutantLoginDTO) {
        if (!"apocalipse".equals(mutantLoginDTO.password())) {
            throw new IllegalArgumentException("Invalid password");
        }

        int totalOfEnemies = mutantLoginDTO.enemiesDefeated();

        var savedMutant = mutantRepository.getReferenceById(mutantLoginDTO.id());
        savedMutant.setEnemiesDefeated(savedMutant.getEnemiesDefeated() + totalOfEnemies);
        savedMutant.setInSchool(true);
        mutantRepository.save(savedMutant);

        double aliensPercent = 26.8;
        double demonsPercent = 43.2;

        int deadAliens = (int) Math.round(totalOfEnemies * aliensPercent / 100);
        int deadDemons = (int) Math.round(totalOfEnemies * demonsPercent / 100);

        String message = mutantService.recruitmentMessage(mutantLoginDTO.id());

        return ResponseEntity.status(HttpStatus.OK).body(new Object() {
            public final String alienigenas = "O mutante derrotou: " + deadAliens + " alienígenas";
            public final String demonios = "O mutante derrotou: " + deadDemons + " demônios";
            public final String recruitmentMessage = message;
        });
    }

    @GetMapping
    public ResponseEntity<List<Mutant>> getAllMutants() {
        return ResponseEntity.status(HttpStatus.OK).body(mutantRepository.findAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createMutant(@RequestBody @Valid MutanteCreateDTO mutanteCreateDTO) {
        var mutant =  new Mutant();
        BeanUtils.copyProperties(mutanteCreateDTO, mutant);
        mutant.setInSchool(true);

        Mutant savedMutant = mutantRepository.save(mutant);

        String message = mutantService.recruitmentMessage(mutant.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new Object() {
            public final Mutant mutant = savedMutant;
            public final String recruitmentMessage = message;
        });
    }

    @GetMapping("/{id}")
    public String seeIfMutantMustJoinEspada(@PathVariable UUID id) {
        String message = mutantService.recruitmentMessage(id);
        return message;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> recruitMutantForEspada(@PathVariable UUID id) {
        mutantService.recruitMutantForEspada(id);
        return ResponseEntity.status(HttpStatus.OK).body("O mutante agora é membro da E.S.P.A.D.A");
    }

    @PutMapping("/escola/sair/{id}")
    public void exitSchool(@PathVariable UUID id) {
        Mutant mutant = mutantRepository.getReferenceById(id);
        mutant.setInSchool(false);
        mutantRepository.save(mutant);
    }

    @GetMapping("/quantidade-na-escola")
    public long countMutantsInSchool() {
        return mutantService.countMutantsInSchool();
    }

    @GetMapping("/estao-na-escola")
    public List<Mutant> getMutantsInSchool() {
        return mutantService.getMutantsInSchool();
    }
}
