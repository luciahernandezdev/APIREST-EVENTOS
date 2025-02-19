package com.example.app.services;

import com.example.app.dtos.ParticipanteDTO;
import com.example.app.entities.Evento;
import com.example.app.entities.Participante;
import com.example.app.repositories.EventoRepository;
import com.example.app.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public List<ParticipanteDTO> obtenerTodosLosParticipantes() {
        return participanteRepository.findAll().stream()
                .map(this::convertirAParticipanteDTO)
                .collect(Collectors.toList());
    }

    public Optional<ParticipanteDTO> obtenerParticipantePorId(Long id) {
        return participanteRepository.findById(id).map(this::convertirAParticipanteDTO);
    }

    public ParticipanteDTO crearParticipante(ParticipanteDTO participanteDTO) {
        Participante participante = convertirAParticipante(participanteDTO);
        Participante participanteGuardado = participanteRepository.save(participante);
        return convertirAParticipanteDTO(participanteGuardado);
    }

    public ParticipanteDTO actualizarParticipante(Long id, ParticipanteDTO nuevoParticipanteDTO) {
        Participante participante = participanteRepository.findById(id).orElseThrow(() -> new RuntimeException("Participante no encontrado"));
        participante.setNombre(nuevoParticipanteDTO.getNombre());
        participante.setApellido(nuevoParticipanteDTO.getApellido());
        participante.setEmail(nuevoParticipanteDTO.getEmail());
        participante.setFechaRegistro(nuevoParticipanteDTO.getFechaRegistro());
        if (nuevoParticipanteDTO.getEventoId() != null) {
            Evento evento = eventoRepository.findById(nuevoParticipanteDTO.getEventoId())
                    .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
            participante.setEvento(evento);
        }
        Participante participanteActualizado = participanteRepository.save(participante);
        return convertirAParticipanteDTO(participanteActualizado);
    }

    public void eliminarParticipante(Long id) {
        participanteRepository.deleteById(id);
    }

    public List<ParticipanteDTO> obtenerParticipantePorFechaRegistro(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return participanteRepository.findByFechaRegistroBetween(fechaInicio, fechaFin).stream()
                .map(this::convertirAParticipanteDTO)
                .collect(Collectors.toList());
    }

    private ParticipanteDTO convertirAParticipanteDTO(Participante participante) {
        ParticipanteDTO participanteDTO = new ParticipanteDTO();
        participanteDTO.setId(participante.getId());
        participanteDTO.setNombre(participante.getNombre());
        participanteDTO.setApellido(participante.getApellido());
        participanteDTO.setEmail(participante.getEmail());
        participanteDTO.setFechaRegistro(participante.getFechaRegistro());
        participanteDTO.setEventoId(participante.getEvento().getId());
        return participanteDTO;
    }

    private Participante convertirAParticipante(ParticipanteDTO participanteDTO) {
        Participante participante = new Participante();
        participante.setId(participanteDTO.getId());
        participante.setNombre(participanteDTO.getNombre());
        participante.setApellido(participanteDTO.getApellido());
        participante.setEmail(participanteDTO.getEmail());
        participante.setFechaRegistro(participanteDTO.getFechaRegistro());
        if (participanteDTO.getEventoId() != null) {
            Evento evento = eventoRepository.findById(participanteDTO.getEventoId())
                    .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
            participante.setEvento(evento);
        }
        return participante;
    }
}
