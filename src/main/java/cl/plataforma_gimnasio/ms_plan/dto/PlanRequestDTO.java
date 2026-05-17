package cl.plataforma_gimnasio.ms_plan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PlanRequestDTO {

    @NotBlank(message = "El nombre del plan es obligatorio.")
    @Size(max = 50, message = "El nombre del plan no puede superar los 50 caracteres.")
    private String nombre;

    @NotNull(message = "La duración en días es obligatoria.")
    @Positive(message = "La duración en días debe ser un número mayor a cero.")
    private Integer duracion;

    @NotNull(message = "El valor del plan es obligatorio.")
    @Min(value = 5000, message = "El valor mínimo de un plan debe ser de $5.000.")
    private Integer valor;

    @NotBlank(message = "Los beneficios del plan son obligatorios.")
    @Size(max = 255, message = "Los beneficios no pueden superar los 255 caracteres.")
    private String beneficios;
}