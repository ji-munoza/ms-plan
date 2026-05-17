package cl.plataforma_gimnasio.ms_plan.controller;


import cl.plataforma_gimnasio.ms_plan.model.Plan;
import cl.plataforma_gimnasio.ms_plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gimnasio/planes")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @GetMapping
    public ResponseEntity<List<Plan>> obtenerTodos(){
        List<Plan> planes = planService.obtenerTodos();
        if (planes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(planes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPorId(@PathVariable Integer id){
        return planService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Plan> guardar(@RequestBody Plan plan){
        return ResponseEntity.status(201).body(planService.guardar(plan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        if (planService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
