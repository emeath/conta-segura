package com.banco.conta_segura.services;

import com.banco.conta_segura.models.ContaModel;
import com.banco.conta_segura.repositories.ContaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }
    public boolean existsByAgencia(String agencia) {
        return this.contaRepository.existsByAgencia(agencia);
    }

    public boolean existsByConta(String conta) {
        return this.contaRepository.existsByConta(conta);
    }

    public ContaModel save(ContaModel contaModel) {
        return this.contaRepository.save(contaModel);
    }

    public Page<ContaModel> findAll(Pageable pageable) {
        return contaRepository.findAll(pageable);
    }

    public Optional<ContaModel> findById(Long id) {
        return contaRepository.findById(id);
    }

    public void deleteById(Long id) {
        contaRepository.deleteById(id);
    }
}
