package com.example.app.controllers;

import com.example.app.dtos.ParticipanteDTO;
import com.example.app.services.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> obtenerTodosLosParticipantes() {
        List<ParticipanteDTO> participantes = participanteService.obtenerTodosLosParticipantes();
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> obtenerParticipantePorId(@PathVariable Long id) {
        Optional<ParticipanteDTO> participante = participanteService.obtenerParticipantePorId(id);
        return participante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParticipanteDTO> crearParticipante(@RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO nuevoParticipante = participanteService.crearParticipante(participanteDTO);
        return ResponseEntity.ok(nuevoParticipante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> actualizarParticipante(@PathVariable Long id, @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO participanteActualizado = participanteService.actualizarParticipante(id, participanteDTO);
        return ResponseEntity.ok(participanteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarParticipante(@PathVariable Long id) {
        participanteService.eliminarParticipante(id);
        return ResponseEntity.noContent().build();
    }
}
