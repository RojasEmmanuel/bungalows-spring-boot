package com.emmadev.bungalows.service;

import com.emmadev.bungalows.entity.Colaborador;
import com.emmadev.bungalows.repository.ColaboradorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColaboradorQueryServiceTest {

    @Mock
    private ColaboradorRepository repository;

    @InjectMocks
    private ColaboradorQueryService queryService;

    @Test
    void debeObtenerColaboradorCuandoExiste(){
        Long id = 1L;

        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Juan");
        colaborador.setAp("López");
        colaborador.setAm("Martínez");

        when(repository.findById(id)).thenReturn(Optional.of(colaborador));

        Colaborador resultado = queryService.getById(id);

        assertNotNull(resultado);
        assertSame(colaborador, resultado);

        verify(repository).findById(id);
    }

    @Test
    void debeLanzarExcepcionCuandoElColaboradorNoExiste(){
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                ()->queryService.getById(id)
        );

        assertEquals(
                "No existe un colaborador con este ID",
                excepcion.getMessage()
        );

        verify(repository).findById(id);
    }
}