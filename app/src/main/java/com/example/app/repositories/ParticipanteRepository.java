package com.example.app.repositories;

import com.example.app.entities.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    List<Participante> findByFechaRegistroBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}

