package com.banco.conta_segura.repositories;

import com.banco.conta_segura.models.ClienteModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long>{

	boolean existsByCpf(String cpf);

}
