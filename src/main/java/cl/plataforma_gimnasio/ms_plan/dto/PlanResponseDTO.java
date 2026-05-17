package cl.plataforma_gimnasio.ms_plan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponseDTO {
    private Integer idPlan;
    private String nombrePlan;
    private Integer duracionPlan;
    private Integer valorPlan;
    private String beneficiosPlan;
}