package cl.plataforma_gimnasio.ms_plan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planes")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer idPlan;


    @Column(name = "nombre_plan", nullable = false, length = 50)
    private String nombrePlan;


    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionPlan;

    @Column(name = "valor_plan", nullable = false)
    private Integer valorPlan;

    @Column(name = "beneficios_plan", nullable = false, length = 200)
    private String beneficiosPlan;
}
