package com.api.sistemachamados.controller;

import com.api.sistemachamados.entity.Cidade;
import com.api.sistemachamados.service.CidadeService;
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
@RequestMapping("/v1/cidade")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Cidade", description = "Operação relacionada a criação de Cidade")
public class CidadeController {
    final CidadeService cidadeService;


    @GetMapping("/cidades")
    public ResponseEntity<Page<Cidade>> buscarCidades(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarCidadeId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return cidadeService.buscarPorId(id).<ResponseEntity<Object>>map(
                cidade -> ResponseEntity.status(HttpStatus.OK).body(cidade))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("cidade.naoEncontrado"));
    }

    @GetMapping("/buscar-por-idEstado/{idEstado}")
    public ResponseEntity<Object> buscarCidadePorIdEstado(@PathVariable(value = "idEstado") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeService.buscarPorIdEstado(id));
    }
}
