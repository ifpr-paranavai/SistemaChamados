package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.ClienteDTO;
import com.api.sistemachamados.entity.Cliente;
import com.api.sistemachamados.service.ClienteService;
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
@RequestMapping("/v1/cliente")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Cliente", description = "Operação relacionada a criação de Cliente")
public class ClienteController {
    final ClienteService clienteService;

    @Operation(description = "Salva Cliente no banco de dados")
    @PostMapping("/salvar-cliente")
    public ResponseEntity<Object> salvarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        return new ResponseEntity<>(clienteService.salvar(clienteDTO), HttpStatus.CREATED);
    }

    @GetMapping("/clientes")
    public ResponseEntity<Page<Cliente>> buscarClientes(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarClienteId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return clienteService.buscarPorId(id).<ResponseEntity<Object>>map(
                cliente -> ResponseEntity.status(HttpStatus.OK).body(cliente))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("cliente.naoEncontrado"));
    }

    @GetMapping("/buscarPorDoc/{cpfCnpj}")
    public ResponseEntity<Object> buscarCpfCnpj(@PathVariable(value = "cpfCnpj") String documento) throws NotFoundException {
        return clienteService.buscarClienteCpfCnpj(documento).<ResponseEntity<Object>>map(
                cliente -> ResponseEntity.status(HttpStatus.OK).body(cliente))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("cliente.naoEncontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable(value = "id") Long id) throws NotFoundException {
        Optional<Cliente>
            clienteOptional = clienteService.buscarPorId(id);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cliente.naoEncontrado");
        }
        clienteService.deletar(clienteOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("cliente.deletado");
    }

}
