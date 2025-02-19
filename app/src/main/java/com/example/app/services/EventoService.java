package com.example.app.services;

import com.example.app.dtos.EventoDTO;
import com.example.app.entities.Evento;
import com.example.app.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    public List<EventoDTO> obtenerTodosLosEventos() {
        return eventoRepository.findAll().stream()
                .map(this::convertirAEventoDTO)
                .collect(Collectors.toList());
    }

    public Optional<EventoDTO> obtenerEventoPorId(Long id) {
        return eventoRepository.findById(id).map(this::convertirAEventoDTO);
    }

    public EventoDTO crearEvento(EventoDTO eventoDTO) {
        Evento evento = convertirAEvento(eventoDTO);
        Evento eventoGuardado = eventoRepository.save(evento);
        return convertirAEventoDTO(eventoGuardado);
    }

    public EventoDTO actualizarEvento(Long id, EventoDTO nuevoEventoDTO) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        evento.setNombre(nuevoEventoDTO.getNombre());
        evento.setDescripcion(nuevoEventoDTO.getDescripcion());
        evento.setFechaInicio(nuevoEventoDTO.getFechaInicio());
        evento.setFechaFin(nuevoEventoDTO.getFechaFin());
        Evento eventoActualizado = eventoRepository.save(evento);
        return convertirAEventoDTO(eventoActualizado);
    }

    public void eliminarEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    public List<EventoDTO> obtenerEventoPorFechaInicioYFin(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return eventoRepository.findByFechaInicioBetween(fechaInicio, fechaFin).stream()
                .map(this::convertirAEventoDTO)
                .collect(Collectors.toList());
    }

    private EventoDTO convertirAEventoDTO(Evento evento) {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(evento.getId());
        eventoDTO.setNombre(evento.getNombre());
        eventoDTO.setDescripcion(evento.getDescripcion());
        eventoDTO.setFechaInicio(evento.getFechaInicio());
        eventoDTO.setFechaFin(evento.getFechaFin());
        return eventoDTO;
    }

    private Evento convertirAEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        evento.setId(eventoDTO.getId());
        evento.setNombre(eventoDTO.getNombre());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        evento.setFechaFin(eventoDTO.getFechaFin());
        return evento;
    }
}
