package com.api.sistemachamados.controller;

import com.api.sistemachamados.entity.Estado;
import com.api.sistemachamados.service.EstadoService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/estado")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Estado", description = "Operação relacionada a listagem de Estado")
public class EstadoController {
    final EstadoService estadoService;

    @GetMapping("/estados")
    public ResponseEntity<Page<Estado>> buscarEstados(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(estadoService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarEstadoId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return estadoService.buscarPorId(id).<ResponseEntity<Object>>map(
                estado -> ResponseEntity.status(HttpStatus.OK).body(estado))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("estado.naoEncontrado"));
    }
}
