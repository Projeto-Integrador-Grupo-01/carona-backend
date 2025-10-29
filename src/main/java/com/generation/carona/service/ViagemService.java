package com.generation.carona.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.carona.model.Viagem;
import com.generation.carona.repository.VeiculoRepository;
import com.generation.carona.repository.ViagemRepository;

import jakarta.validation.Valid;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;


    public List<Viagem> listarTodas() {
        return viagemRepository.findAll();
    }

    public Optional<Viagem> buscarPorId(Long id) {
        return viagemRepository.findById(id);
    }

    public List<Viagem> buscarPorDestino(String destino) {
        return viagemRepository.findAllByDestinoContainingIgnoreCase(destino);
    }
    
    public List<Viagem> buscarPorPartida(String partida) {
        return viagemRepository.findAllByPartidaContainingIgnoreCase(partida);
    }

    public Viagem criar(@Valid Viagem viagem) {
        if (!veiculoRepository.existsById(viagem.getVeiculo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não existe!");
        }

        return viagemRepository.save(viagem);
    }

    public Viagem atualizar(@Valid Viagem viagem) {
        if (!viagemRepository.existsById(viagem.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada!");
        }

        if (!veiculoRepository.existsById(viagem.getVeiculo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não existe!");
        }

        return viagemRepository.save(viagem);
    }

    public void deletar(Long id) {
        Optional<Viagem> viagem = viagemRepository.findById(id);

        if (viagem.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada!");
        }

        viagemRepository.deleteById(id);
    }
}