package cl.plataforma_gimnasio.ms_plan.service;

import cl.plataforma_gimnasio.ms_plan.dto.PlanRequestDTO;
import cl.plataforma_gimnasio.ms_plan.dto.PlanResponseDTO;
import cl.plataforma_gimnasio.ms_plan.exception.ResourceNotFoundException;
import cl.plataforma_gimnasio.ms_plan.model.Plan;
import cl.plataforma_gimnasio.ms_plan.repository.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlanService {

    private final PlanRepository planRepository;

    public List<PlanResponseDTO> obtenerTodos() {
        log.info("Iniciando obtencion de todos los planes");
        return planRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public PlanResponseDTO obtenerPorId(Integer id) {
        log.info("Iniciando obtencion de plan con ID: {}", id);
        return planRepository.findById(id)
                .map(this::convertirAResponseDTO)
                .orElseThrow(() -> {
                    log.error("Error al obtener plan: El ID {} no existe.", id);
                    return new ResourceNotFoundException("El plan con ID " + id + " no existe.");
                });
    }

    public PlanResponseDTO guardar(PlanRequestDTO dto) {
        log.info("Iniciando registro de nuevo plan: '{}' con valor de ${}", dto.getNombre(), dto.getValor());

        Plan plan = new Plan();
        // Mapeo manual: desde el DTO limpio hacia el Model con sufijos de columna
        plan.setNombrePlan(dto.getNombre());
        plan.setDuracionPlan(dto.getDuracion());
        plan.setValorPlan(dto.getValor());
        plan.setBeneficiosPlan(dto.getBeneficios());

        Plan planGuardado = planRepository.save(plan);
        log.info("Plan guardado con exito. Nuevo ID asignado: {}", planGuardado.getIdPlan());

        return convertirAResponseDTO(planGuardado);
    }

    public PlanResponseDTO actualizar(Integer id, PlanRequestDTO dto) {
        log.info("Iniciando actualizacion de plan con ID: {}", id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: El ID {} no existe.", id);
                    return new ResourceNotFoundException("El plan con ID " + id + " no existe.");
                });

        // Actualizamos los campos cruzando los datos limpios del DTO
        plan.setNombrePlan(dto.getNombre());
        plan.setDuracionPlan(dto.getDuracion());
        plan.setValorPlan(dto.getValor());
        plan.setBeneficiosPlan(dto.getBeneficios());

        Plan planActualizado = planRepository.save(plan);
        log.info("Plan con ID {} actualizado con exito.", id);

        return convertirAResponseDTO(planActualizado);
    }

    public void eliminar(Integer id) {
        log.warn("Iniciando eliminacion de plan con ID: {}", id);

        if (!planRepository.existsById(id)) {
            log.error("Error al eliminar: No existe el plan con ID: {}", id);
            throw new ResourceNotFoundException("El plan con ID " + id + " no existe.");
        }

        planRepository.deleteById(id);
        log.info("Plan con ID {} eliminado correctamente.", id);
    }

    // Mapeador simétrico al de SocioService (Transforma Entidad con sufijos a DTO limpio)
    private PlanResponseDTO convertirAResponseDTO(Plan plan) {
        PlanResponseDTO response = new PlanResponseDTO();
        response.setIdPlan(plan.getIdPlan());
        response.setNombre(plan.getNombrePlan());
        response.setDuracion(plan.getDuracionPlan());
        response.setValor(plan.getValorPlan());
        response.setBeneficios(plan.getBeneficiosPlan());
        return response;
    }
}