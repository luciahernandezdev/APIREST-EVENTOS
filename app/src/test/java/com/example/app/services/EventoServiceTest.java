package com.example.app.services;

import com.example.app.dtos.EventoDTO;
import com.example.app.entities.Evento;
import com.example.app.repositories.EventoRepository;
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
class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoService eventoService;

    private Evento evento;

    @BeforeEach
    void setUp() {
        evento = new Evento();
        evento.setId(1L);
        evento.setNombre("Concierto de Rock");
        evento.setDescripcion("Concierto de Rock en vivo");
        evento.setFechaInicio(LocalDateTime.of(2025, 5, 10, 18, 0));
        evento.setFechaFin(LocalDateTime.of(2025, 5, 10, 22, 0));
    }

    @Test
    void testListarTodosLosEventos() {
        List<Evento> eventos = List.of(evento);
        when(eventoRepository.findAll()).thenReturn(eventos);

        List<EventoDTO> result = eventoService.obtenerTodosLosEventos();
        assertEquals(1, result.size());
        verify(eventoRepository, times(1)).findAll();
    }

    @Test
    void testFiltrarPorFechaInicioYFechaFin() {
        List<Evento> eventos = List.of(evento);
        when(eventoRepository.findByFechaInicioBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(eventos);

        List<EventoDTO> result = eventoService.obtenerEventoPorFechaInicioYFin(
                LocalDateTime.of(2025, 5, 10, 0, 0),
                LocalDateTime.of(2025, 5, 10, 23, 59)
        );

        assertEquals(1, result.size());
        verify(eventoRepository, times(1)).findByFechaInicioBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testCrearEvento() {
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre(evento.getNombre());
        eventoDTO.setDescripcion(evento.getDescripcion());
        eventoDTO.setFechaInicio(evento.getFechaInicio());
        eventoDTO.setFechaFin(evento.getFechaFin());

        EventoDTO result = eventoService.crearEvento(eventoDTO);
        assertNotNull(result);
        assertEquals(eventoDTO.getNombre(), result.getNombre());
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void testActualizarEvento() {
        when(eventoRepository.findById(anyLong())).thenReturn(Optional.of(evento));
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre("Concierto Actualizado");
        eventoDTO.setDescripcion("Concierto de Rock en vivo actualizado");
        eventoDTO.setFechaInicio(LocalDateTime.of(2025, 5, 10, 19, 0));
        eventoDTO.setFechaFin(LocalDateTime.of(2025, 5, 10, 23, 0));

        EventoDTO result = eventoService.actualizarEvento(1L, eventoDTO);
        assertEquals("Concierto Actualizado", result.getNombre());
        assertEquals("Concierto de Rock en vivo actualizado", result.getDescripcion());
        verify(eventoRepository, times(1)).findById(1L);
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }
}
