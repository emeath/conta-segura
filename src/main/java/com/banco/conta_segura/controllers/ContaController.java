package com.banco.conta_segura.controllers;

import com.banco.conta_segura.dtos.ContaDTO;
import com.banco.conta_segura.models.ClienteModel;
import com.banco.conta_segura.models.ContaModel;
import com.banco.conta_segura.services.ClienteService;
import com.banco.conta_segura.services.ContaService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;
    private final ClienteService clienteService;

    public ContaController(ContaService contaService, ClienteService clienteService) {
        this.contaService = contaService;
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Object> adicionarConta(@RequestBody @Valid ContaDTO contaDTO) {
        if (this.contaService.existsByAgencia(contaDTO.getAgencia()) &&
                this.contaService.existsByConta(contaDTO.getConta())) {
            String returnMessage = String.format("Agencia: %s | Conta: %s já cadastrados!",
                                                    contaDTO.getAgencia(), contaDTO.getConta());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(returnMessage);
        }
        // Validar se o id do do cliente informado na conta existe no banco
        Optional<ClienteModel> clienteModelOptional = this.clienteService.findById(contaDTO.getIdCliente());
        if(!clienteModelOptional.isPresent()) {
            String returnMessage = String.format("Não é possível adicionar a conta pois o cliente de id: %s não existe!",
                    contaDTO.getIdCliente());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(returnMessage);
        }
        ContaModel contaModel = new ContaModel();
        contaModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        contaModel.setCliente(clienteModelOptional.get());
        BeanUtils.copyProperties(contaDTO, contaModel);
        return ResponseEntity.status(HttpStatus.OK).body(this.contaService.save(contaModel));
    }

}
