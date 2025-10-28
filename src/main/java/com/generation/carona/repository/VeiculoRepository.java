package com.generation.carona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.carona.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{ //interface herda de JpaRepsitory que irá fornecer métodos CRUD sem precisar escrever SQL
	
	public List<Veiculo> findAllByPlacaContainingIgnoreCase(String placa);
	

}