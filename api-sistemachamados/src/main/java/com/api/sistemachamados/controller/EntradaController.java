package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.EntradaDTO;
import com.api.sistemachamados.dto.ValidList;
import com.api.sistemachamados.entity.Entrada;
import com.api.sistemachamados.service.EntradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/v1/entrada")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Entrada", description = "Operação relacioada a persistencia de Entradas")
public class EntradaController {
    final EntradaService entradaService;

    @Operation(description = "Salva Entrada no banco de dados")
    @PostMapping("/salvar-entrada")
    public ResponseEntity<Object> entradaEntrada(@RequestBody @Valid ValidList<EntradaDTO> entradaDTO) {
        return new ResponseEntity<>(entradaService.salvarEntrada(entradaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/entradas")
    public ResponseEntity<Page<Entrada>> buscarEntradas(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(entradaService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarEntradaId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return entradaService.buscarPorId(id).<ResponseEntity<Object>>map(
                entrada -> ResponseEntity.status(HttpStatus.OK).body(entrada))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("entrada.naoEncontrado"));
    }

    @GetMapping("/entrada-entrada/{data}")
    public ResponseEntity<Object> buscarEntradaEntradaPorData(
        @PathVariable(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) throws NotFoundException {
        return entradaService.buscarEntradaPorData(data).<ResponseEntity<Object>>map(
                entrada -> ResponseEntity.status(HttpStatus.OK).body(entrada))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("entrada.naoEncontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarEntrada(@PathVariable(value = "id") Long id) throws NotFoundException {
        Optional<Entrada>
            entradaOptional = entradaService.buscarPorId(id);
        if (entradaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("entrada.naoEncontrado");
        }
        entradaService.deletar(entradaOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("entrada.deletado");
    }

}
