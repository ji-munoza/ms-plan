package cl.plataforma_gimnasio.ms_plan.service;

import cl.plataforma_gimnasio.ms_plan.dto.PlanRequestDTO;
import cl.plataforma_gimnasio.ms_plan.dto.PlanResponseDTO;
import cl.plataforma_gimnasio.ms_plan.exception.ResourceNotFoundException;
import cl.plataforma_gimnasio.ms_plan.model.Plan;
import cl.plataforma_gimnasio.ms_plan.repository.PlanRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias para PlanService")
class PlanServiceTest {

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private PlanService planService;

    private Plan planMock;
    private PlanRequestDTO planRequestDTO;

    @BeforeEach
    void setUp() {
        planMock = new Plan(1, "Plan Hulk Anual", 365, 250000, "Acceso al gimnasio, Sauna libre");
        planRequestDTO = new PlanRequestDTO("Plan Hulk Anual", 365, 250000, "Acceso al gimnasio, Sauna libre");
    }

    @Test
    @DisplayName("Debe retornar lista de DTOs cuando existen planes")
    void obtenerTodos_DebeRetornarListaDePlanes() {
        when(planRepository.findAll()).thenReturn(List.of(planMock));

        List<PlanResponseDTO> resultado = planService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Plan Hulk Anual", resultado.get(0).getNombrePlan());
        verify(planRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar lista vacía si no hay registros en la BD")
    void obtenerTodos_DebeRetornarListaVacia_CuandoNoHayPlanes() {
        when(planRepository.findAll()).thenReturn(Collections.emptyList());

        List<PlanResponseDTO> resultado = planService.obtenerTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(planRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar el plan mapeado cuando el ID existe")
    void obtenerPorId_DebeRetornarPlan_CuandoIdExiste() {
        when(planRepository.findById(1)).thenReturn(Optional.of(planMock));

        PlanResponseDTO resultado = planService.obtenerPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdPlan());
        assertEquals("Plan Hulk Anual", resultado.getNombrePlan());
        assertEquals(250000, resultado.getValorPlan());
        verify(planRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el ID no existe")
    void obtenerPorId_DebeLanzarExcepcion_CuandoIdNoExiste() {
        when(planRepository.findById(99)).thenReturn(Optional.empty());

        ResourceNotFoundException excepcion = assertThrows(ResourceNotFoundException.class, () -> {
            planService.obtenerPorId(99);
        });

        assertEquals("El plan con ID 99 no existe.", excepcion.getMessage());
        verify(planRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Debe guardar un plan y retornar su respectivo DTO con ID")
    void guardar_DebeRegistrarPlanExitosamente() {
        when(planRepository.save(any(Plan.class))).thenReturn(planMock);

        PlanResponseDTO resultado = planService.guardar(planRequestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdPlan());
        assertEquals("Plan Hulk Anual", resultado.getNombrePlan());
        verify(planRepository, times(1)).save(any(Plan.class));
    }

    @Test
    @DisplayName("Debe actualizar campos correctamente si el ID a editar existe")
    void actualizar_DebeModificarPlan_CuandoIdExiste() {
        when(planRepository.findById(1)).thenReturn(Optional.of(planMock));
        when(planRepository.save(any(Plan.class))).thenReturn(planMock);

        planRequestDTO.setNombrePlan("Plan Hulk Smart");
        planMock.setNombrePlan("Plan Hulk Smart");

        PlanResponseDTO resultado = planService.actualizar(1, planRequestDTO);

        assertNotNull(resultado);
        assertEquals("Plan Hulk Smart", resultado.getNombrePlan());
        verify(planRepository, times(1)).findById(1);
        verify(planRepository, times(1)).save(any(Plan.class));
    }

    @Test
    @DisplayName("Debe eliminar el registro si el método existsById devuelve verdadero")
    void eliminar_DebeBorrarPlan_CuandoIdExiste() {
        when(planRepository.existsById(1)).thenReturn(true);
        doNothing().when(planRepository).deleteById(1);

        assertDoesNotThrow(() -> planService.eliminar(1));

        verify(planRepository, times(1)).existsById(1);
        verify(planRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException al intentar borrar un ID inexistente")
    void eliminar_DebeLanzarExcepcion_CuandoIdNoExiste() {
        when(planRepository.existsById(99)).thenReturn(false);

        ResourceNotFoundException excepcion = assertThrows(ResourceNotFoundException.class, () -> {
            planService.eliminar(99);
        });

        assertEquals("El plan con ID 99 no existe.", excepcion.getMessage());
        verify(planRepository, times(1)).existsById(99);
        verify(planRepository, never()).deleteById(anyInt());
    }
}