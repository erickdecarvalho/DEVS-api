package devs.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MutantLoginDTO(@NotNull UUID id, @NotBlank String name, @NotBlank String password, @NotNull Integer power, @NotNull Integer age, @NotNull Integer enemiesDefeated) {
}
