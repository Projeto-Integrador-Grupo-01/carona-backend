package com.generation.carona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.carona.model.Viagem;

public interface ViagemRepository extends JpaRepository<Viagem, Long>{
	
	public List<Viagem> findAllByDestinoContainingIgnoreCase(String destino);
	
}
