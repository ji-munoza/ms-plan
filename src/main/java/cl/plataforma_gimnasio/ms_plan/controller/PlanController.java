package cl.plataforma_gimnasio.ms_plan.controller;

import cl.plataforma_gimnasio.ms_plan.dto.PlanRequestDTO;
import cl.plataforma_gimnasio.ms_plan.dto.PlanResponseDTO;
import cl.plataforma_gimnasio.ms_plan.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gimnasio/planes")
@RequiredArgsConstructor
@Slf4j
public class PlanController {

    private final PlanService planService;

    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> obtenerTodos() {
        log.info("Solicitud recibida para obtener todos los planes");
        List<PlanResponseDTO> planes = planService.obtenerTodos();
        if (planes.isEmpty()) {
            log.warn("No se obtuvo ningun plan, la lista esta vacia");
            return ResponseEntity.noContent().build();
        }
        log.info("Obtenidos {} planes", planes.size());
        return ResponseEntity.ok(planes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("Solicitud recibida para obtener plan con ID: {}", id);
        PlanResponseDTO plan = planService.obtenerPorId(id);
        log.info("Obtenido plan con ID: {}", id);
        return ResponseEntity.ok(plan);
    }

    @PostMapping
    public ResponseEntity<PlanResponseDTO> guardar(@Valid @RequestBody PlanRequestDTO dto) {
        log.info("Solicitud recibida para crear plan: {}", dto.getNombre());

        // Regla de negocio: Asegurar beneficio minimo obligatorio
        if (dto.getBeneficios() == null || dto.getBeneficios().trim().isEmpty()) {
            dto.setBeneficios("Acceso al gimnasio");
        } else if (!dto.getBeneficios().toLowerCase().contains("acceso al gimnasio")) {
            dto.setBeneficios("Acceso al gimnasio, " + dto.getBeneficios());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(planService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody PlanRequestDTO dto) {
        log.info("Solicitud recibida para actualizar plan con ID: {}", id);

        // Regla de negocio: Mantener beneficio minimo obligatorio al editar
        if (dto.getBeneficios() == null || dto.getBeneficios().trim().isEmpty()) {
            dto.setBeneficios("Acceso al gimnasio");
        } else if (!dto.getBeneficios().toLowerCase().contains("acceso al gimnasio")) {
            dto.setBeneficios("Acceso al gimnasio, " + dto.getBeneficios());
        }

        PlanResponseDTO planActualizado = planService.actualizar(id, dto);
        log.info("Plan con ID {} modificado exitosamente.", id);
        return ResponseEntity.ok(planActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.warn("Solicitud recibida para eliminar plan con ID: {}", id);
        planService.eliminar(id);
        log.info("Eliminado plan con ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}