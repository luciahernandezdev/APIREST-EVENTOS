package com.example.app.services;

import com.example.app.entities.Participante;
import com.example.app.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    public Participante crearParticipante(Participante participante) {
        return participanteRepository.save(participante);
    }

    public List<Participante> obtenerTodosLosParticipantes() {
        return participanteRepository.findAll();
    }

    public Optional<Participante> obtenerParticipantePorId(Long id) {
        return participanteRepository.findById(id);
    }

    public Participante actualizarParticipante(Long id, Participante participanteDetalles) {
        Participante participante = obtenerParticipantePorId(id).orElseThrow(() -> new RuntimeException("Participante no encontrado"));
        participante.setNombre(participanteDetalles.getNombre());
        participante.setApellido(participanteDetalles.getApellido());
        participante.setEmail(participanteDetalles.getEmail());
        participante.setFechaRegistro(participanteDetalles.getFechaRegistro());
        return participanteRepository.save(participante);
    }

    public void eliminarParticipante(Long id) {
        participanteRepository.deleteById(id);
    }

    public List<Participante> obtenerParticipantePorFechaRegistro(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return participanteRepository.findByFechaRegistroBetween(fechaInicio, fechaFin);
    }
}
