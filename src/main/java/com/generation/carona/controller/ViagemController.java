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

import com.generation.carona.model.Viagem;
import com.generation.carona.repository.VeiculoRepository;
import com.generation.carona.repository.ViagemRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/viagens")
@CrossOrigin(origins = "*", allowedHeaders = "*") // libera requisições de qualquer origem e qualquer cabeçalho
public class ViagemController {

	@Autowired
	private ViagemRepository viagemRepository; // injecao de dependencia

	@Autowired
	private VeiculoRepository veiculoRepository;

	
	// Lista todas as viagens
	@GetMapping
	public ResponseEntity<List<Viagem>> getAll() {
		return ResponseEntity.ok(viagemRepository.findAll());

		// findAll é o equivalente a SELECT * FROM tb_viagens;

	}

	// Procura por ID
	@GetMapping("/{id}") // para procurar a variavel no endereço, chamada de variavel de caminho
	public ResponseEntity<Viagem> getById(@PathVariable Long id) { // o parametro pega o id do endereço e joga no método
																	// para procurar
		return viagemRepository.findById(id) // nao pode retornar ResponseEntity pq ñ pode retornar valor nulo
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

		// findById é o equivalente a SELECT * FROM tb_viagens WHERE id = ?;
	}

	// Procura por destino
	@GetMapping("/destino/{destino}")
	public ResponseEntity<List<Viagem>> getAllByDestino(@PathVariable String destino) {
		return ResponseEntity.ok(viagemRepository.findAllByDestinoContainingIgnoreCase(destino));

	}

	// Procura por partida
	@GetMapping("/partida/{partida}")
	public ResponseEntity<List<Viagem>> getAllByPartida(@PathVariable String partida) {
		return ResponseEntity.ok(viagemRepository.findAllByPartidaContainingIgnoreCase(partida));

	}

	// Cria viagem
	@PostMapping
	public ResponseEntity<Viagem> post(@Valid @RequestBody Viagem viagem) { 
																			
																			
		if (veiculoRepository.existsById(viagem.getVeiculo().getId())) {
			viagem.setId(null); // seta o id como nulo para não criar nenhum ID aqui
			return ResponseEntity.status(HttpStatus.CREATED).body(viagemRepository.save(viagem));
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O veiculo não existe", null);
	}

	// Atualiza viagem
	@PutMapping
	public ResponseEntity<Viagem> put(@Valid @RequestBody Viagem viagem) {

		if (viagemRepository.existsById(viagem.getId())) {

			if (veiculoRepository.existsById(viagem.getVeiculo().getId())) {

				return ResponseEntity.status(HttpStatus.OK).body(viagemRepository.save(viagem));
			}

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O veiculo não existe", null);

		}
		return ResponseEntity.notFound().build();
	}

	// Deleta viagem
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

		Optional<Viagem> viagem = viagemRepository.findById(id); // verifica se a viagem existe
		if (viagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); // se nao encontrar a viagem vai mostrar a msg NOT
																		// FOUND

		viagemRepository.deleteById(id); // se encontrar vai deletar

	}

}