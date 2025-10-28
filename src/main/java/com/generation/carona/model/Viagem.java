package com.generation.carona.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_viagens")
public class Viagem {
	
		@Id
		@GeneratedValue (strategy = GenerationType.IDENTITY) //auto incremento
		private Long id;  //Hibernate gera o valor; Long corresponde bem ao BIGINT no banco
		
		@Column(length = 200)
		@NotBlank(message = "O atributo destino é obrigatório!")
		@Size(min = 2, max = 500, message = "O atributo descrição deve conter no mínimo 10 e no máximo 500 caracteres")
		
		private String destino;
		
		@Column(length = 200)
		@NotBlank(message = "O atributo partida é obrigatório!")
		@Size(min = 2, max = 500, message = "O atributo descrição deve conter no mínimo 10 e no máximo 500 caracteres")
		
		private String partida;
		
		@NotNull(message = "O preço é obrigatório!")
	    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero!")
	    @Digits(integer = 10, fraction = 2, message = "O preço deve ter no máximo 10 dígitos inteiros e 2 decimais")
	    @Column(nullable = false, precision = 12, scale = 2)
	    private BigDecimal preco;
		
		@Column(length = 200)
		@NotBlank(message = "O atributo data é obrigatório!")
		private  LocalDateTime data;
		
		@Column(length = 200)
		@NotBlank(message = "O atributo tempo é obrigatório!")
		private  LocalDateTime tempo;
		
		@ManyToOne
		@JsonIgnoreProperties("veiculo") //pra não entrar num loop infinito
		private Veiculo veiculo;
		
		@ManyToOne
		@JsonIgnoreProperties("usuario") //pra não entrar num loop infinito
		private Usuario usuario;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDestino() {
			return destino;
		}

		public void setDestino(String destino) {
			this.destino = destino;
		}

		public String getPartida() {
			return partida;
		}

		public void setPartida(String partida) {
			this.partida = partida;
		}

		public BigDecimal getPreco() {
			return preco;
		}

		public void setPreco(BigDecimal preco) {
			this.preco = preco;
		}

		public LocalDateTime getData() {
			return data;
		}

		public void setData(LocalDateTime data) {
			this.data = data;
		}

		public LocalDateTime getTempo() {
			return tempo;
		}

		public void setTempo(LocalDateTime tempo) {
			this.tempo = tempo;
		}

		public Veiculo getVeiculo() {
			return veiculo;
		}

		public void setVeiculo(Veiculo veiculo) {
			this.veiculo = veiculo;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}
		
	
}
