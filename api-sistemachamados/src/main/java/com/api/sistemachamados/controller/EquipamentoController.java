package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.EquipamentoDTO;
import com.api.sistemachamados.entity.Equipamento;
import com.api.sistemachamados.service.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/v1/equipamento")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Equipamento", description = "Operação relacionado a criação de Equipamento")
public class EquipamentoController {
    final EquipamentoService equipamentoService;

    @Operation(description = "Salva Equipamento no banco de dados")
    @PostMapping("/salvar-equipamento")
    public ResponseEntity<Object> salvarEquipamento(@RequestBody @Valid EquipamentoDTO equipamentoDTO) {
        return new ResponseEntity<>(equipamentoService.salvar(equipamentoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/equipamentos")
    public ResponseEntity<Page<Equipamento>> buscarEquipamentos(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(equipamentoService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarEquipamentoId(@PathVariable(value = "id") Long id) {
        return equipamentoService.buscarPorId(id).<ResponseEntity<Object>>map(
                equipamento -> ResponseEntity.status(HttpStatus.OK).body(equipamento))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("equipamento.naoEncontrado"));
    }

    @GetMapping("/{numeroSerie}")
    public ResponseEntity<Object> buscarEquipamentoNumeroSerie(@PathVariable(value = "numeroSerie") String numeroSerie) {
        return equipamentoService.buscarNumeroSerie(numeroSerie).<ResponseEntity<Object>>map(
                equipamento -> ResponseEntity.status(HttpStatus.OK).body(equipamento))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("equipamento.naoEncontrado"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarEquipamento(@PathVariable(value = "id") Long id) {
        Optional<Equipamento> equipamentoOptional = equipamentoService.buscarPorId(id);
        if (equipamentoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("equipamento.naoEncontrado");
        }
        equipamentoService.deletar(equipamentoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("equipamento.deletado");
    }

}
