package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.RegraDTO;
import com.api.sistemachamados.entity.Role;
import com.api.sistemachamados.service.RegraService;
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
@RequestMapping("/v1/regra")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Regras", description = "Operação relacioada a criação de Regras")
public class RoleController {

    final RegraService regraService;

    @PostMapping("/criar-regra")
    public ResponseEntity<Object> criarRegra(@RequestBody @Valid RegraDTO regraDTO) {
        return new ResponseEntity<>(regraService.salvar(regraDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/bucar-regras")
    public ResponseEntity<Page<Role>> buscarTodasRegras(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(regraService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarRoleId(@PathVariable(value = "id") Long id) {
        return regraService.buscarPorId(id).<ResponseEntity<Object>>map(
                role -> ResponseEntity.status(HttpStatus.OK).body(role))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("role.naoEncontrado"));
    }


    @GetMapping("/{nome}")
    public ResponseEntity<Object> buscarRoleNome(@PathVariable(value = "nome") String nome) {
        return regraService.buscarPorNome(nome).<ResponseEntity<Object>>map(
                role -> ResponseEntity.status(HttpStatus.OK).body(role))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("role.naoEncontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarRole(@PathVariable(value = "id") Long id) {
        Optional<Role> roleOptional = regraService.buscarPorId(id);
        if (roleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("role.naoEncontrado");
        }
        regraService.deletar(roleOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("role.deletado");
    }
}
