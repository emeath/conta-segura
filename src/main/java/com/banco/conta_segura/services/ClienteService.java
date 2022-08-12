package com.banco.conta_segura.services;

import com.banco.conta_segura.misc.RespostasErros;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.banco.conta_segura.models.ClienteModel;
import com.banco.conta_segura.repositories.ClienteRepository;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public boolean existsByCpf(String cpf) {
		return this.clienteRepository.existsByCpf(cpf);
	}

	@Transactional
	public Object save(ClienteModel clienteModel) {
		if (clienteModel.getProfissao().trim().toLowerCase().equals("admin")) {
			return RespostasErros.ProfissaoInvalida;
		}
		if(clienteModel.getNome().trim().toLowerCase().equals("xyzxyz")) {
			return RespostasErros.NomeInvalido;
		}
		//throw new RuntimeException("Profissão do cliente não pode ser admin!");
		//return "Profissão do cliente não poder ser admin!";
		return this.clienteRepository.save(clienteModel);
	}

	public Page<ClienteModel> findAll(Pageable pageable) {
		return this.clienteRepository.findAll(pageable);
	}

    public Optional<ClienteModel> findById(Long id) {
		return this.clienteRepository.findById(id);
    }

	@Transactional
	public void deleteById(Long id) {
		this.clienteRepository.deleteById(id);
	}
}
