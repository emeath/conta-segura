package com.banco.conta_segura.repositories;

import com.banco.conta_segura.models.ContaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaModel, Long> {
    boolean existsByAgencia(String agencia);
    boolean existsByConta(String conta);
}
