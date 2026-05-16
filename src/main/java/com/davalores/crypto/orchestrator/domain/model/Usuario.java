package com.davalores.crypto.orchestrator.domain.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String descripcion;
	private String clave;
	
	private Boolean habilitado;
	private Boolean dfa;
	
	@ManyToMany
    @JoinTable(
        name = "usuarios_perfiles", // Name of the join table
        joinColumns = @JoinColumn(name = "id_usuario"), // FK to Usuario
        inverseJoinColumns = @JoinColumn(name = "id_perfil") // FK to Perfil
    )
	private Set<Perfil> perfiles = new HashSet<>(); 
	
	private String escoId; 
	private String ripioId; 
	private Boolean ripioHabilitado; 
	
}
