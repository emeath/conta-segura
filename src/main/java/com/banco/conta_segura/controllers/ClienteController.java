package com.banco.conta_segura.controllers;

import java.lang.annotation.Repeatable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import com.banco.conta_segura.misc.RespostasErros;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banco.conta_segura.dtos.ClienteDTO;
import com.banco.conta_segura.models.ClienteModel;
import com.banco.conta_segura.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteService clienteService;
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;	
	}
	
	@PostMapping
	public ResponseEntity<Object> adicionarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
		if(clienteService.existsByCpf(clienteDTO.getCpf())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF " + clienteDTO.getCpf() + " já cadastrado!");
		}
		ClienteModel clienteModel = new ClienteModel();
		clienteModel.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
		BeanUtils.copyProperties(clienteDTO, clienteModel);

		Object object = this.clienteService.save(clienteModel);

		if (object == RespostasErros.ProfissaoInvalida){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profissão inválida!");
		} else if(object == RespostasErros.NomeInvalido) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome inválido!");
		}

		return ResponseEntity.status(HttpStatus.OK).body(object);
	}
	
	@GetMapping
	public ResponseEntity<Page<ClienteModel>> obterTodosClientes(
			@PageableDefault(page = 0, size = 5, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(this.clienteService.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> obterClientePorId(@PathVariable(value = "id") Long id){
		Optional<ClienteModel> clienteModelOptional = this.clienteService.findById(id);
		if (!clienteModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente de id: " + id + " não encontrado!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(clienteModelOptional.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarClientePorId(
			@PathVariable(value = "id") Long id,
			@RequestBody @Valid ClienteDTO clienteDTO) {
		Optional<ClienteModel> clienteModelOptional = this.clienteService.findById(id);
		if (!clienteModelOptional.isPresent()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente de id: " + id + " não encontrado!");
		}
		ClienteModel clienteModel = new ClienteModel();
		clienteModel.setId(clienteModelOptional.get().getId());
		clienteModel.setDataCadastro(clienteModelOptional.get().getDataCadastro());
		BeanUtils.copyProperties(clienteDTO, clienteModel);
		return ResponseEntity.status(HttpStatus.OK).body(this.clienteService.save(clienteModel));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarClientePorId(@PathVariable(value = "id") Long id) {
		Optional<ClienteModel> clienteModelOptional = this.clienteService.findById(id);
		if (!clienteModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente de id: " + id + " não encontrado!");
		}
		this.clienteService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Cliente de id: " + id + " deletado!");
	}
	
}
