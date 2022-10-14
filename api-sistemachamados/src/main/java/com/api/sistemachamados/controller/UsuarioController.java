package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.UsuarioDTO;
import com.api.sistemachamados.entity.Usuario;
import com.api.sistemachamados.service.UsuarioService;
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
@RequestMapping("/v1/usuario")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Usuário", description = "Operação relacioada a criação de um Usuário")
public class UsuarioController {
    final UsuarioService usuarioService;

    @PostMapping("/salvar-usuario")
    public ResponseEntity<Object> salvarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.salvar(usuarioDTO), HttpStatus.CREATED);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Page<Usuario>> buscarUsuarios(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarUsuarioId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return usuarioService.buscarPorId(id).<ResponseEntity<Object>>map(
                usuario -> ResponseEntity.status(HttpStatus.OK).body(usuario))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario.naoEncontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "id") Long id) throws NotFoundException {
        Optional<Usuario> usuarioOptional = usuarioService.buscarPorId(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario.naoEncontrado");
        }
        usuarioService.deletar(usuarioOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("usuario.deletado");
    }

}
