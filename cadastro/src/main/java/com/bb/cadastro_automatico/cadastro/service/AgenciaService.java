package com.bb.cadastro_automatico.cadastro.service;

import com.bb.cadastro_automatico.cadastro.model.Agencia;
import com.bb.cadastro_automatico.cadastro.repository.AgenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgenciaService {

    private final AgenciaRepository repository;

    public AgenciaService(AgenciaRepository repository) {
        this.repository = repository;
    }

    public Agencia salvar(Agencia agencia) {
        return repository.save(agencia);
    }

    public List<Agencia> listarTodas() {
        return repository.findAll();
    }

    public Agencia buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}