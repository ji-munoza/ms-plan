package cl.plataforma_gimnasio.ms_plan.service;

import cl.plataforma_gimnasio.ms_plan.model.Plan;
import cl.plataforma_gimnasio.ms_plan.repository.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public List<Plan> obtenerTodos(){
        return planRepository.findAll();
    }

    public Optional<Plan> obtenerPorId(Integer id){
        return planRepository.findById(id);
    }

    public Plan guardar(Plan plan) {
        return planRepository.save(plan);
    }

    public void eliminar(Integer id) {
        planRepository.deleteById(id);
    }

}
