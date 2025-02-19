package com.example.app.repositories;

import com.example.app.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByFechaInicioBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
