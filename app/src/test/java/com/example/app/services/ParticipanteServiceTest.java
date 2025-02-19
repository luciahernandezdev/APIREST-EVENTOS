package com.example.app.services;

import com.example.app.dtos.ParticipanteDTO;
import com.example.app.entities.Evento;
import com.example.app.entities.Participante;
import com.example.app.repositories.EventoRepository;
import com.example.app.repositories.ParticipanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipanteServiceTest {

    @Mock
    private ParticipanteRepository participanteRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private ParticipanteService participanteService;

    private Participante participante;
    private Evento evento;

    @BeforeEach
    void setUp() {
        evento = new Evento();
        evento.setId(1L);
        evento.setNombre("Concierto de Rock");
        evento.setDescripcion("Concierto de Rock en vivo");
        evento.setFechaInicio(LocalDateTime.of(2025, 5, 10, 18, 0));
        evento.setFechaFin(LocalDateTime.of(2025, 5, 10, 22, 0));

        participante = new Participante();
        participante.setId(1L);
        participante.setNombre("Ana");
        participante.setApellido("García");
        participante.setEmail("ana.garcia@example.com");
        participante.setFechaRegistro(LocalDateTime.of(2025, 5, 1, 10, 0));
        participante.setEvento(evento);
    }

    @Test
    void testListarTodosLosParticipantes() {
        List<Participante> participantes = List.of(participante);
        when(participanteRepository.findAll()).thenReturn(participantes);

        List<ParticipanteDTO> result = participanteService.obtenerTodosLosParticipantes();
        assertEquals(1, result.size());
        verify(participanteRepository, times(1)).findAll();
    }

    @Test
    void testFiltrarPorFechaRegistro() {
        List<Participante> participantes = List.of(participante);
        when(participanteRepository.findByFechaRegistroBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(participantes);

        List<ParticipanteDTO> result = participanteService.obtenerParticipantePorFechaRegistro(
                LocalDateTime.of(2025, 5, 1, 0, 0),
                LocalDateTime.of(2025, 5, 1, 23, 59)
        );

        assertEquals(1, result.size());
        verify(participanteRepository, times(1)).findByFechaRegistroBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testCrearParticipante() {
        when(eventoRepository.findById(anyLong())).thenReturn(Optional.of(evento));
        when(participanteRepository.save(any(Participante.class))).thenReturn(participante);

        ParticipanteDTO participanteDTO = new ParticipanteDTO();
        participanteDTO.setNombre(participante.getNombre());
        participanteDTO.setApellido(participante.getApellido());
        participanteDTO.setEmail(participante.getEmail());
        participanteDTO.setFechaRegistro(participante.getFechaRegistro());
        participanteDTO.setEventoId(evento.getId());

        ParticipanteDTO result = participanteService.crearParticipante(participanteDTO);
        assertNotNull(result);
        assertEquals(participanteDTO.getNombre(), result.getNombre());
        verify(participanteRepository, times(1)).save(any(Participante.class));
    }

    @Test
    void testActualizarParticipante() {
        when(participanteRepository.findById(anyLong())).thenReturn(Optional.of(participante));
        when(eventoRepository.findById(anyLong())).thenReturn(Optional.of(evento));
        when(participanteRepository.save(any(Participante.class))).thenReturn(participante);

        ParticipanteDTO participanteDTO = new ParticipanteDTO();
        participanteDTO.setNombre("Ana Actualizada");
        participanteDTO.setApellido("García Actualizada");
        participanteDTO.setEmail("ana.actualizada@example.com");
        participanteDTO.setFechaRegistro(LocalDateTime.of(2025, 5, 2, 11, 0));
        participanteDTO.setEventoId(evento.getId());

        ParticipanteDTO result = participanteService.actualizarParticipante(1L, participanteDTO);
        assertEquals("Ana Actualizada", result.getNombre());
        assertEquals("García Actualizada", result.getApellido());
        verify(participanteRepository, times(1)).findById(1L);
        verify(eventoRepository, times(1)).findById(evento.getId());
        verify(participanteRepository, times(1)).save(any(Participante.class));
    }
}
