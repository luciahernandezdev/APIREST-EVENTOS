package com.example.app.controllers;

import com.example.app.entities.Participante;
import com.example.app.services.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {
    @Autowired
    private ParticipanteService participanteService;

    @PostMapping
    public ResponseEntity<Participante> crearParticipante(@RequestBody Participante participante) {
        Participante nuevoParticipante = participanteService.crearParticipante(participante);
        return new ResponseEntity<>(nuevoParticipante, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Participante>> obtenerTodosLosParticipantes() {
        List<Participante> participantes = participanteService.obtenerTodosLosParticipantes();
        return new ResponseEntity<>(participantes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participante> obtenerParticipantePorId(@PathVariable Long id) {
        Optional<Participante> participante = participanteService.obtenerParticipantePorId(id);
        return participante.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participante> actualizarParticipante(@PathVariable Long id, @RequestBody Participante participanteDetalles) {
        Participante participanteActualizado = participanteService.actualizarParticipante(id, participanteDetalles);
        return new ResponseEntity<>(participanteActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarParticipante(@PathVariable Long id) {
        participanteService.eliminarParticipante(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
