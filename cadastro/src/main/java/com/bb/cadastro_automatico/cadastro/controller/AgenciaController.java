package com.bb.cadastro_automatico.cadastro.controller;

import com.bb.cadastro_automatico.cadastro.model.Agencia;
import com.bb.cadastro_automatico.cadastro.service.AgenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agencias")
public class AgenciaController {

    private final AgenciaService service;

    public AgenciaController(AgenciaService service) {
        this.service = service;
    }

    @PostMapping
    public Agencia salvar(@RequestBody Agencia agencia) {
        return service.salvar(agencia);
    }

    @GetMapping
    public List<Agencia> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Agencia buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}