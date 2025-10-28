package com.generation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_veiculos")
public class Veiculo {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) //auto incremento
	private Long id;  //Hibernate gera o valor; Long corresponde bem ao BIGINT no banco
	
	@Column(length = 200)
	@NotBlank(message = "O atributo modelo é obrigatório!")
	@Size(min = 2, max = 500, message = "O atributo descrição deve conter no mínimo 10 e no máximo 500 caracteres")
	
	private String modelo;
	
	@Column(length = 200)
	@NotBlank(message = "O atributo placa é obrigatório!")
	@Size(min = 2, max = 500, message = "O atributo descrição deve conter no mínimo 10 e no máximo 500 caracteres")
	
	private String placa;
	
	@Column(length = 200)
	@NotBlank(message = "O atributo foto é obrigatório!")
	@Size(min = 2, max = 500, message = "O atributo descrição deve conter no mínimo 10 e no máximo 500 caracteres")
	private String foto;
	
	@OneToMany
	@JsonIgnoreProperties("viagem") //pra não entrar num loop infinito
	private List<Viagem> viagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Viagem> getViagem() {
		return viagem;
	}

	public void setViagem(List<Viagem> viagem) {
		this.viagem = viagem;
	}
	
}