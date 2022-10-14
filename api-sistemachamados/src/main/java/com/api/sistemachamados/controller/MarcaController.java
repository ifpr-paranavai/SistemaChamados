package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.MarcaDTO;
import com.api.sistemachamados.entity.Marca;
import com.api.sistemachamados.service.MarcaService;
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
@RequestMapping("/v1/marca")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Marca", description = "Operação relacionada a criação de Marca")
public class MarcaController {
    final MarcaService marcaService;

    @Operation(description = "Salva Marca no banco de dados")
    @PostMapping("/salvar-marca")
    public ResponseEntity<Object> salvarMarca(@RequestBody @Valid MarcaDTO marcaDTO) {
        return new ResponseEntity<>(marcaService.salvar(marcaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/marcas")
    public ResponseEntity<Page<Marca>> buscarMarcas(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(marcaService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarMarcaId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return marcaService.buscarPorId(id).<ResponseEntity<Object>>map(
                marca -> ResponseEntity.status(HttpStatus.OK).body(marca))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("marca.naoEncontrado"));
    }

    @GetMapping("/{nomeMarca}")
    public ResponseEntity<Object> buscarNomeMarca(@PathVariable(value = "nomeMarca") String nomeMarca) throws NotFoundException {
        return marcaService.buscarNomeMarca(nomeMarca).<ResponseEntity<Object>>map(
                marca -> ResponseEntity.status(HttpStatus.OK).body(marca))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("marca.naoEncontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarMarca(@PathVariable(value = "id") Long id) throws NotFoundException {
        Optional<Marca> marcaOptional = marcaService.buscarPorId(id);
        if (marcaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("marca.naoEncontrado");
        }
        marcaService.deletar(marcaOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("marca.deletado");
    }

}
