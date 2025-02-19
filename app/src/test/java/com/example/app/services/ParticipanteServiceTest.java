package com.example.app.services;

import com.example.app.entities.Participante;
import com.example.app.repositories.ParticipanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticipanteServiceTest {

    @Mock
    private ParticipanteRepository participanteRepository;

    @InjectMocks
    private ParticipanteService participanteService;

    private Participante participante;

    @BeforeEach
    void setUp() {
        participante = new Participante();
        participante.setId(1L);
        participante.setNombre("Juan");
        participante.setApellido("Pérez");
        participante.setEmail("juan.perez@example.com");
        participante.setFechaRegistro(LocalDateTime.of(2025, 1, 1, 12, 0));
    }

    @Test
    void testListarTodosLosParticipantes() {
        List<Participante> participantes = List.of(participante);
        when(participanteRepository.findAll()).thenReturn(participantes);

        List<Participante> result = participanteService.obtenerTodosLosParticipantes();
        assertEquals(1, result.size());
        verify(participanteRepository, times(1)).findAll();
    }

    @Test
    void testFiltrarPorFechaRegistro() {
        //Implementar según sea necesario en el servicio y repositorio
    }

    @Test
    void testCrearParticipante() {
        when(participanteRepository.save(any(Participante.class))).thenReturn(participante);

        Participante result = participanteService.crearParticipante(participante);
        assertEquals(participante.getNombre(), result.getNombre());
        verify(participanteRepository, times(1)).save(participante);
    }

    @Test
    void testActualizarParticipante() {
        when(participanteRepository.findById(anyLong())).thenReturn(Optional.of(participante));
        when(participanteRepository.save(any(Participante.class))).thenReturn(participante);

        Participante participanteActualizado = new Participante();
        participanteActualizado.setNombre("Juan Actualizado");

        Participante result = participanteService.actualizarParticipante(1L, participanteActualizado);
        assertEquals("Juan Actualizado", result.getNombre());
        verify(participanteRepository, times(1)).findById(1L);
        verify(participanteRepository, times(1)).save(participante);
    }
}
