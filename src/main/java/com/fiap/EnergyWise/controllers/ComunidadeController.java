package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeRequestDTO;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeRequestUpdateDTO;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeResponseDTO;
import com.FIAP.EnergyWise.services.ComunidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/comunidade")
@Tag(name = "Comunidade", description = "API de comunidades")
public class ComunidadeController {


    @Autowired
    private ComunidadeService comunidadeService;

    @Autowired
    private PagedResourcesAssembler<ComunidadeResponseDTO> pagedResourcesAssembler;



    @Operation(summary = "Cria uma comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comunidade criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping
    public ResponseEntity<ComunidadeResponseDTO> createComunidade(@RequestBody
                                                       ComunidadeRequestDTO comunidadeRequestDTO) {
        ComunidadeResponseDTO comunidade = comunidadeService.createComunidade(
                comunidadeRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(comunidade);
    }

    @Operation(summary = "Busca todas as comunidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidades encontradas"),
            @ApiResponse(responseCode = "404", description = "Comunidades não encontradas")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ComunidadeResponseDTO>>> findAllComunidades(
            @ParameterObject Pageable pageable) {

        Page<ComunidadeResponseDTO> comunidadesPage = comunidadeService.findAllComunidades(pageable);

        PagedModel<EntityModel<ComunidadeResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(comunidadesPage,
                comunidade -> EntityModel.of(comunidade,
                        linkTo(methodOn(ComunidadeController.class).findComunidadeById(comunidade.getId())).withSelfRel()));

        return ResponseEntity.ok(pagedModel);
    }


    @Operation(summary = "Busca uma comunidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade encontrada"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ComunidadeResponseDTO>> findComunidadeById(@PathVariable Long id) {
        ComunidadeResponseDTO comunidade = comunidadeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EntityModel.of(comunidade, linkTo(methodOn(ComunidadeController.class)
                .findComunidadeById(id)).withSelfRel()));
    }


    @Operation(summary = "Atualiza uma comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ComunidadeResponseDTO> updateComunidade(@PathVariable Long id, @RequestBody
    ComunidadeRequestUpdateDTO comunidadeRequestDTO) {
        ComunidadeResponseDTO comunidade = comunidadeService.updateComunidade(id,
                comunidadeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(comunidade);
    }

    @Operation(summary = "Deleta uma comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ComunidadeResponseDTO> deleteComunidade(@PathVariable Long id) {
        ComunidadeResponseDTO comunidade = comunidadeService.deleteComunidade(id);
        return ResponseEntity.status(HttpStatus.OK).body(comunidade);
    }


}
