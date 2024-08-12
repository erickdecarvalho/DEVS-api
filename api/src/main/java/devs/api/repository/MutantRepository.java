package devs.api.repository;

import devs.api.model.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MutantRepository extends JpaRepository<Mutant, UUID> {
    List<Mutant> findByIsInSchoolTrue();
    long countByIsInSchoolTrue();

}
