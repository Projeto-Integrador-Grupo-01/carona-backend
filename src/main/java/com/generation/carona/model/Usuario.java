package com.generation.carona.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@NotBlank(message = "O nome do usuário é obrigatório.")
		private String nome;
		
		@Size(max = 5000, message = "O link da foto deve ter no máximo 5000 caracteres")
		private String foto;
		
		@Schema(example = "email@email.com.br")
		@NotBlank(message = "O email do usuário é obrigatório.")
		@Email(message = "O email deve ser válido.")
		private String email;
		
		@NotBlank(message = "A senha do usuário é obrigatória.")
		@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
		private String senha;
		
		@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
		@JsonIgnoreProperties(value = "usuario", allowSetters = true)
		private List<Viagem> viagem;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getFoto() {
			return foto;
		}

		public void setFoto(String foto) {
			this.foto = foto;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public List<Viagem> getViagem() {
			return viagem;
		}

		public void setViagem(List<Viagem> viagem) {
			this.viagem = viagem;
		}


}
