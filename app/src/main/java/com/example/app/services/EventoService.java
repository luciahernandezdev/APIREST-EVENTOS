package com.example.app.services;

import com.example.app.entities.Evento;
import com.example.app.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {
    @Autowired
    public EventoRepository eventoRepository;

    public Evento crearEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public List<Evento> obtenerTodosLosEventos() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> obtenerEventoPorId(Long id) {
        return eventoRepository.findById(id);
    }

    public Evento actualizarEvento(Long id, Evento eventoDetalles) {
        Evento evento = obtenerEventoPorId(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        evento.setNombre(eventoDetalles.getNombre());
        evento.setDescripcion(eventoDetalles.getDescripcion());
        evento.setFechaInicio(eventoDetalles.getFechaInicio());
        evento.setFechaFin(eventoDetalles.getFechaFin());
        return eventoRepository.save(evento);
    }

    public void eliminarEvento(Long id) {
        Evento evento = obtenerEventoPorId(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        eventoRepository.delete(evento);
    }

    public List<Evento> obtenerEventoPorFechaInicioYFin(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return eventoRepository.findByFechaInicioBetween(fechaInicio, fechaFin);
    }
}
