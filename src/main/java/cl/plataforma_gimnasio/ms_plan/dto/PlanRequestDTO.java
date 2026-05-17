package cl.plataforma_gimnasio.ms_plan.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDTO {

    @NotBlank(message = "El nombre del plan es obligatorio.")
    @Size(min = 3, max = 50, message = "El nombre del plan debe tener entre 3 y 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9.\\s]+$", message = "El nombre del plan solo puede contener letras, numeros y espacios.")
    private String nombrePlan;

    @NotNull(message = "La duración en días es obligatoria.")
    @Min(value = 1, message = "La duracion minima debe ser de al menos 1 dia.")
    @Max(value = 365, message = "La duracion maxima no puede superar los 365 dias (1 año).")
    private Integer duracionPlan;

    @NotNull(message = "El valor del plan es obligatorio.")
    @Min(value = 5000, message = "El valor mínimo de un plan debe ser de $5.000.")
    @Max(value = 1000000, message = "El valor maximo de un plan no puede superar el de $1.000.000.")
    private Integer valorPlan;

    @NotBlank(message = "Los beneficios del plan son obligatorios.")
    @Size(min = 5, max = 200, message = "Los beneficios deben tener entre 5 y 200 caracteres.")
    private String beneficiosPlan;
}