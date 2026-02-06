package com.bb.cadastro_automatico.cadastro.repository;

import com.bb.cadastro_automatico.cadastro.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}