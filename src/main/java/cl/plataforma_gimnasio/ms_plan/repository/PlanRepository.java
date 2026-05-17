package cl.plataforma_gimnasio.ms_plan.repository;

import cl.plataforma_gimnasio.ms_plan.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

}
