package com.davalores.crypto.orchestrator.infra.adapter.out.entity;

import java.util.HashSet;
import java.util.Set;

import com.davalores.crypto.orchestrator.domain.model.Perfil;

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
public class UsuarioEntity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String usuario;
	private String clave;
	private String descripcion;
	
	private Boolean habilitado;
	private Boolean dfa;
	private String dfaSemilla;
	
	@ManyToMany
    @JoinTable(
        name = "usuarios_perfiles", // Name of the join table
        joinColumns = @JoinColumn(name = "id_usuario"), // FK to Usuario
        inverseJoinColumns = @JoinColumn(name = "id_perfil") // FK to Perfil
    )
	private Set<PerfilEntity> perfiles = new HashSet<>(); 
	
	private String escoId;
	private String claveEsco; 
	private String ripioId; 
	private Boolean ripioHabilitado; 
	
}