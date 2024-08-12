package devs.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MutanteCreateDTO(@NotBlank String name, @NotNull Integer power, @NotNull Integer age, @NotNull Integer enemiesDefeated) {
}
