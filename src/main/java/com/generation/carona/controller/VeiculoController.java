package com.generation.carona.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.carona.model.Veiculo;
import com.generation.carona.repository.VeiculoRepository;

import jakarta.validation.Valid;
@RestController //Controlador REST
@RequestMapping("/veiculos") //define a URL base para todos os endpoints da classe
@CrossOrigin(origins = "*", allowedHeaders = "*") //Permite que qualquer aplicação acesse os endpoints e também permite todos os cabeçalhos

public class VeiculoController {
	
	@Autowired //Injeta automaticamente uma instancia de VeiculoRepository
	private VeiculoRepository veiculoRepository;
	
	//Lista todos os veiculos
	@GetMapping
	public ResponseEntity<List<Veiculo>> getAll(){
		return ResponseEntity.ok(veiculoRepository.findAll()); //Retorna status 200 com os dados no corpo da resposta
	}

	//Busca veiculo por ID
	@GetMapping("/{id}")
	public ResponseEntity<Veiculo> getById(@PathVariable Long id){ //Captura o id da URL
		return veiculoRepository.findById(id) //retorna um Optional<Veiculo> (pode existir ou não)
				.map(resposta -> ResponseEntity.ok(resposta))//Se existir, retorna status 200 
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());//se não existir retorna 404 NOT FOUND
	}
	
	//Busca por placa	
	@GetMapping("/placa/{placa}")
	public ResponseEntity<List<Veiculo>> getAllByPlaca(@PathVariable String placa){
		return ResponseEntity.ok(veiculoRepository.findAllByPlacaContainingIgnoreCase(placa));
	}
	
	//Cria novo veiculo
	@PostMapping
	public ResponseEntity<Veiculo> post(@Valid @RequestBody Veiculo veiculo){
		veiculo.setId(null);//garante que o ID seja nulo para o banco gerar automaticamente
		return ResponseEntity.status(HttpStatus.CREATED).body(veiculoRepository.save(veiculo));
	}
	
	//Atualiza veiculo
	@PutMapping
	public ResponseEntity<Veiculo> put(@Valid @RequestBody Veiculo veiculo){
		return veiculoRepository.findById(veiculo.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(veiculoRepository.save(veiculo)))
						.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	//Deleta veiculo
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		Optional<Veiculo> veiculo = veiculoRepository.findById(id);
		if(veiculo.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		veiculoRepository.deleteById(id);
	}
	
}
