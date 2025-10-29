package com.generation.carona.controller;

import java.util.List;
import java.util.Map;
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

import com.generation.carona.model.Veiculo;
import com.generation.carona.model.Viagem;
import com.generation.carona.service.ViagemService;
import com.generation.carona.repository.ViagemRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/viagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @Autowired
    private ViagemRepository viagemRepository;

    @GetMapping
    public ResponseEntity<List<Viagem>> getAll() {
        return ResponseEntity.ok(viagemService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viagem> getById(@PathVariable Long id) {
        return viagemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/destino/{destino}")
    public ResponseEntity<List<Viagem>> getByDestino(@PathVariable String destino) {
        return ResponseEntity.ok(viagemService.buscarPorDestino(destino));
    }

    @GetMapping("/partida/{partida}")
    public ResponseEntity<List<Viagem>> getByPartida(@PathVariable String partida) {
        return ResponseEntity.ok(viagemService.buscarPorPartida(partida));
    }

    @PostMapping
    public ResponseEntity<Viagem> post(@Valid @RequestBody Viagem viagem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemService.criar(viagem));
    }

    @PutMapping
    public ResponseEntity<Viagem> put(@Valid @RequestBody Viagem viagem) {
        return ResponseEntity.ok(viagemService.atualizar(viagem));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        viagemService.deletar(id);
    }

    // ✅ Cálculo com retorno formatado
    @PostMapping("/{id}/calcular-tempo")
    public ResponseEntity<String> calcularTempo(@PathVariable Long id, @RequestBody Map<String, Double> dados) {
        Double distancia = dados.get("distancia");

        Optional<Viagem> viagemOpt = viagemRepository.findById(id);
        if (viagemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada!");
        }

        Viagem viagem = viagemOpt.get();
        Veiculo veiculo = viagem.getVeiculo();

        if (veiculo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A viagem não possui veículo associado!");
        }

        // Velocidade média padrão 
        Double velocidadeMedia = 80.0;

        Double tempoHoras = viagemService.calcularTempoViagem(distancia, velocidadeMedia);

        // Converte para horas e minutos
        int horas = (int) Math.floor(tempoHoras);
        int minutos = (int) Math.round((tempoHoras - horas) * 60);

        String tempoFormatado = String.format("%dh %02dmin", horas, minutos);

        // Salva o valor bruto (horas) no banco
        viagem.setTempo(tempoHoras);
        viagemRepository.save(viagem);

        return ResponseEntity.ok(tempoFormatado);
    }
}
