package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.ServicoDTO;
import com.api.sistemachamados.entity.Servico;
import com.api.sistemachamados.service.ServicoService;
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
@RequestMapping("/v1/servico")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Servico", description = "Operação relacioada a criação de Servico")
public class ServicoController {
    final ServicoService servicoService;

    @Operation(description = "Salva Servico no banco de dados")
    @PostMapping("/salvar-servico")
    public ResponseEntity<Object> salvarServico(@RequestBody @Valid ServicoDTO servicoDTO) {
        return new ResponseEntity<>(servicoService.salvar(servicoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/servicos")
    public ResponseEntity<Page<Servico>> buscarServicos(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(servicoService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarServicoId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return servicoService.buscarPorId(id).<ResponseEntity<Object>>map(
                servico -> ResponseEntity.status(HttpStatus.OK).body(servico))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("servico.naoEncontrado"));
    }


    @GetMapping("/{nomeServico}")
    public ResponseEntity<Object> buscarServicoNome(@PathVariable(value = "nomeServico") String nome) throws NotFoundException {
        return servicoService.buscarNomeServico(nome).<ResponseEntity<Object>>map(
                servico -> ResponseEntity.status(HttpStatus.OK).body(servico))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("servico.naoEncontrado"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarServico(@PathVariable(value = "id") Long id) throws NotFoundException {
        Optional<Servico> servicoOptional = servicoService.buscarPorId(id);
        if (servicoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("servico.naoEncontrado");
        }
        servicoService.deletar(servicoOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("servico.deletado");
    }

}
