package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.ProdutoDTO;
import com.api.sistemachamados.entity.Produto;
import com.api.sistemachamados.service.ProdutoService;
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
@RequestMapping("/v1/produto")
@AllArgsConstructor
@PreAuthorize("hasPermission('null', {'ROLE_USER', 'PERM_PROCURACAO'})")
@SecurityRequirement(name = "sistemachamadosapi")
@Tag(name = "Produto", description = "Operação relacioada a persistencia de produtos")
public class ProdutoController {
    final ProdutoService produtoService;

    @Operation(description = "Salva Produto no banco de dados")
    @PostMapping("/salvar-produto")
    public ResponseEntity<Object> entradaProduto(@RequestBody @Valid ProdutoDTO produtoDTO) {
        return new ResponseEntity<>(produtoService.salvarProduto(produtoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/produtos")
    public ResponseEntity<Page<Produto>> buscarProdutos(
        @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProdutoId(@PathVariable(value = "id") Long id) throws NotFoundException {
        return produtoService.buscarPorId(id).<ResponseEntity<Object>>map(
                produto -> ResponseEntity.status(HttpStatus.OK).body(produto))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("produto.naoEncontrado"));
    }

    @GetMapping("/{nomeProduto}")
    public ResponseEntity<Object> buscarNomeProduto(@PathVariable(value = "nomeProduto") String nomeProduto) throws NotFoundException {
        return produtoService.buscarNomeProduto(nomeProduto).<ResponseEntity<Object>>map(
                produto -> ResponseEntity.status(HttpStatus.OK).body(produto))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("produto.naoEncontrado"));
    }

    @DeleteMapping("/{id}")
    // TODO: 02/09/2022 Fazer validação por quantidade e por relacionamento com ordem de serviço ou histórico de entrada e saída
    public ResponseEntity<Object> deletarProduto(@PathVariable(value = "id") Long id) throws NotFoundException {
        Optional<Produto>
            produtoOptional = produtoService.buscarPorId(id);
        if (produtoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("produto.naoEncontrado");
        }
        produtoService.deletar(produtoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("produto.deletado");
    }

}
