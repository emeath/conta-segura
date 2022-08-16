package com.banco.conta_segura.controllers;

import com.banco.conta_segura.dtos.ContaDTO;
import com.banco.conta_segura.models.ClienteModel;
import com.banco.conta_segura.models.ContaModel;
import com.banco.conta_segura.services.ClienteService;
import com.banco.conta_segura.services.ContaService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<ContaModel>> obterTodasAsContas(
            @PageableDefault(page = 0, size = 5, sort = {"agencia", "conta"}, direction = Sort.Direction.ASC) Pageable pageable
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(contaService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterContaPorId(@PathVariable(value = "id") Long id) {
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()){
            String mensagemDeRetorno = String.format("Conta de id: %d não localizada!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemDeRetorno);
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarConta(@PathVariable(value = "id") Long id,
                                                 @RequestBody @Valid ContaDTO contaDTO) {
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if(!contaModelOptional.isPresent()){
            String mensagemDeRetorno = String.format("Conta de id: %d não localizada!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemDeRetorno);
        }
        ContaModel contaModel = new ContaModel();
        contaModel.setId(contaModelOptional.get().getId());

        // Validando se id cliente existe
        Optional<ClienteModel> clienteModelOptional = clienteService.findById(contaDTO.getIdCliente());
        if(!clienteModelOptional.isPresent()){
            String mensagemDeRetorno = String.format("Cliente de id: %d não existe.", contaDTO.getIdCliente());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemDeRetorno);
        }

        BeanUtils.copyProperties(contaDTO, contaModel);
        return ResponseEntity.status(HttpStatus.OK).body(contaService.save(contaModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarContaPorId(@PathVariable(value = "id") Long id) {
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()){
            String mensagemDeRetorno = String.format("Conta de id: %d não localizada!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemDeRetorno);
        }
        contaService.deleteById(id);
        String mensagemAposDelete = String.format("Conta de id: %d deletada com sucesso!", id);
        return ResponseEntity.status(HttpStatus.OK).body(mensagemAposDelete);
    }

}
