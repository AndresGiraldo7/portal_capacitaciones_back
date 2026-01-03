package com.challenge.backend.ProgresoCurso.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearProgresoDTO {
    private Integer idUsuario;
    private Integer idCurso;
    private String estado; 
    private String fechaInicio; 
}