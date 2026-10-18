package com.emmadev.bungalows.service;

import com.emmadev.bungalows.entity.Puesto;
import com.emmadev.bungalows.repository.PuestoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.emmadev.bungalows.dto.Puesto.PuestoResponse;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias de puesto service")
class PuestoServiceTest {

    @Mock
    private PuestoRepository repository;

    @InjectMocks
    private PuestoService service;

    private Puesto puestoEjemplo;

    @BeforeEach
    void setup(){
        puestoEjemplo = new Puesto();
        puestoEjemplo.setId(1L);
        puestoEjemplo.setNombre("Desarrollador");
        puestoEjemplo.setDescripcion("Desarrolla software");
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay puestos")
    void listaVacia(){
        when(repository.findAll()).thenReturn(List.of());

        List<PuestoResponse> puestos = service.getPuestos();

        assertNotNull(puestos);
        assertTrue(puestos.isEmpty());
        verify(repository).findAll();
    }


    @Test
    @DisplayName("Mapea correctamente cada entidad a PuestoResponse")
    void mapeaCorrectamente() {
        Puesto otro = new Puesto();
        otro.setId(2L);
        otro.setNombre("QA");
        otro.setDescripcion("Testing");

        when(repository.findAll()).thenReturn(List.of(puestoEjemplo, otro));

        List<PuestoResponse> resultado = service.getPuestos();

        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).id());
        assertEquals("Desarrollador", resultado.get(0).nombre());
        assertEquals("Desarrolla software", resultado.get(0).descripcion());
        assertEquals(2L, resultado.get(1).id());
        assertEquals("QA", resultado.get(1).nombre());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Preserva el orden devuelto por el repositorio")
    void preservaOrden() {
        Puesto a = new Puesto(); a.setId(10L); a.setNombre("A"); a.setDescripcion("a");
        Puesto b = new Puesto(); b.setId(20L); b.setNombre("B"); b.setDescripcion("b");

        when(repository.findAll()).thenReturn(List.of(b, a));

        List<PuestoResponse> resultado = service.getPuestos();

        assertEquals(20L, resultado.get(0).id());
        assertEquals(10L, resultado.get(1).id());
    }

    @Test
    @DisplayName("Devuelve el puesto cuando existe")
    void existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(puestoEjemplo));

        Puesto resultado = service.getById(1L);

        assertSame(puestoEjemplo, resultado);
        verify(repository).findById(1L);
    }
}