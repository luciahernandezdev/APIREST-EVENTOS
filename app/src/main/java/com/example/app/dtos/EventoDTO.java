package com.example.app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private Long id;

    @JsonProperty("evento")
    private String nombre;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("fechainicio")
    private LocalDateTime fechaInicio;

    @JsonProperty("fechafin")
    private LocalDateTime fechaFin;
}
