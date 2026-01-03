package com.challenge.backend.UsuarioInsignia.entity;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInsigniaId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer usuario;
    private Integer insignia;
}