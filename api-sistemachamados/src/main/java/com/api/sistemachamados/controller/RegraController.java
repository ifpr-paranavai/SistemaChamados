package com.api.sistemachamados.controller;

import com.api.sistemachamados.dto.RegraDTO;
import com.api.sistemachamados.dto.RoleDTO;
import com.api.sistemachamados.entity.Role;
import com.api.sistemachamados.service.RegraService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/regra")
@AllArgsConstructor
@SecurityRequirement(name = "sistemachamadosapi")
public class RegraController {

    final RegraService regraService;

    @PostMapping("/criar-role")
    public ResponseEntity<Object> criarRole(@RequestBody @Valid RegraDTO roleDTO) {
        var role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        return new ResponseEntity<>(regraService.salvar(role), HttpStatus.CREATED);
    }

    @GetMapping(path = "/bucar-regras")
    public ResponseEntity<Page<Role>> buscarTodasRegras(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(regraService.buscarTodos(pageable));
    }
}
