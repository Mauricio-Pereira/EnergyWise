package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.DTOS.ComunidadeRequestCreateDTO;
import com.FIAP.EnergyWise.DTOS.ComunidadeResponseDTO;
import com.FIAP.EnergyWise.models.Comunidade;
import com.FIAP.EnergyWise.services.ComunidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comunidade")
@Tag(name = "Comunidade", description = "API de comunidades")
public class ComunidadeController {


    @Autowired
    private ComunidadeService comunidadeService;


    @Operation(summary = "Cria uma comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comunidade criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping
    public ResponseEntity<Comunidade> createComunidade(@RequestBody
                                                      ComunidadeRequestCreateDTO comunidadeRequestCreateDTO) {
        Comunidade comunidade = comunidadeService.createComunidade(comunidadeRequestCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comunidade);
    }

    @Operation(summary = "Busca todas as comunidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidades encontradas"),
            @ApiResponse(responseCode = "404", description = "Comunidades não encontradas")
    })
    @GetMapping
    public ResponseEntity<List<ComunidadeResponseDTO>> findAllComunidades() {
        List<ComunidadeResponseDTO> comunidades = comunidadeService.findAllComunidades();
        return ResponseEntity.ok(comunidades);
    }
}
