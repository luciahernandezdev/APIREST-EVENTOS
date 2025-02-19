package com.example.app.services;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoService eventoService;

    private Evento evento;

    @BeforeEach
    void setUp() {
        evento = new Evento();
        evento.setId(1L);
        evento.setNombre("Concierto");
        evento.setDescripcion("Concierto de Rock");
        evento.setFechaInicio(LocalDateTime.of(2025, 5, 10, 18, 0));
        evento.setFechaFin(LocalDateTime.of(2025, 5, 10, 22, 0));
    }

    @Test
    void testListarTodosLosEventos() {
        List<Evento> eventos = List.of(evento);
        when(eventoRepository.findAll()).thenReturn(eventos);

        List<Evento> result = eventoService.obtenerTodosLosEventos();
        assertEquals(1, result.size());
        verify(eventoRepository, times(1)).findAll();
    }

    @Test
    void testFiltrarPorFechaInicioYFechaFin() {
        //Implementar según sea necesario en el servicio y repositorio
    }

    @Test
    void testCrearEvento() {
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        Evento result = eventoService.crearEvento(evento);
        assertEquals(evento.getNombre(), result.getNombre());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    void testActualizarEvento() {
        when(eventoRepository.findById(anyLong())).thenReturn(Optional.of(evento));
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        Evento eventoActualizado = new Evento();
        eventoActualizado.setNombre("Concierto Actualizado");
        eventoActualizado.setDescripcion("Concierto de Rock en vivo actualizado");
        eventoActualizado.setFechaInicio(LocalDateTime.of(2025, 5, 10, 19, 0));
        eventoActualizado.setFechaFin(LocalDateTime.of(2025, 5, 10, 23, 0 ));

        Evento result = eventoService.actualizarEvento(1L, eventoActualizado);
        assertEquals("Concierto Actualizado", result.getNombre());
        assertEquals("Concierto de Rock en vivo actualizado", result.getDescripcion());
        assertEquals(LocalDateTime.of(2025, 5, 10, 19, 0), result.getFechaInicio());
        assertEquals(LocalDateTime.of(2025, 5, 10, 23, 0), result.getFechaFin());
        verify(eventoRepository, times(1)).findById(1L);
        verify(eventoRepository, times(1)).save(evento);
    }
}
