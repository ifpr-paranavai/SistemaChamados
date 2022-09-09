package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.OrdemServicoDTO;
import com.api.sistemachamados.entity.OrdemServico;
import com.api.sistemachamados.service.OrdemServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/ordem-servico")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Ordem - Servico", description = "Operação relacionada a criação de uma OrdemServico")
public class OrdemServicoController {
    final OrdemServicoService ordemServicoService;

    @Operation(description = "Salva OrdemServico no banco de dados")
    @PostMapping("/salvar-ordem-servico")
    public ResponseEntity<Object> salvarOrdemServico(@RequestBody @Valid OrdemServicoDTO ordemServicoDTO) {
        return new ResponseEntity<>(ordemServicoService.salvar(ordemServicoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/ordem-servicos")
    public ResponseEntity<Page<OrdemServico>> buscarOrdemServicos(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(ordemServicoService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarOrdemServicoId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return ordemServicoService.buscarPorId(id).<ResponseEntity<Object>>map(
                ordemServico -> ResponseEntity.status(HttpStatus.OK).body(ordemServico))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("ordemServico.naoEncontrado"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarOrdemServico(@PathVariable(value = "id") Long id) throws NotFoundException {
        Optional<OrdemServico>
            ordemServicoOptional = ordemServicoService.buscarPorId(id);
        if (ordemServicoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ordemServico.naoEncontrado");
        }
        ordemServicoService.deletar(ordemServicoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("ordemServico.deletado");
    }

}
