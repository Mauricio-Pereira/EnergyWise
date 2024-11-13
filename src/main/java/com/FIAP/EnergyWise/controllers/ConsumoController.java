package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.DTOS.consumo.ConsumoRequestDTO;
import com.FIAP.EnergyWise.DTOS.consumo.ConsumoResponseDTO;
import com.FIAP.EnergyWise.services.ConsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumo")
@Tag(name = "Consumo", description = "API de consumo de energia")
public class ConsumoController {

    @Autowired
    private ConsumoService consumoService;

    @Operation(summary = "Cria um consumo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping({"/{idComunidade}"})
    public ResponseEntity<ConsumoResponseDTO> createConsumo(@RequestBody
                                                            ConsumoRequestDTO consumoRequestDTO, Long idComunidade) {
        if (idComunidade == null) {
            return ResponseEntity.status(400).build();
        }
        ConsumoResponseDTO consumo = consumoService.createConsumo(consumoRequestDTO, idComunidade);
        return ResponseEntity.status(201).body(consumo);
    }

    @Operation(summary = "Busca todos os consumos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumos encontrados"),
            @ApiResponse(responseCode = "404", description = "Consumos não encontrados")
    })
    @GetMapping
    public ResponseEntity<List<ConsumoResponseDTO>> findAllConsumos() {
        List<ConsumoResponseDTO> consumos = consumoService.findAllConsumos();
        if (consumos.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(consumos);
    }

    @Operation(summary = "Busca os consumos de uma comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumo encontrado"),
            @ApiResponse(responseCode = "404", description = "Consumo não encontrado")
    })
    @GetMapping("/{idComunidade}")
    public ResponseEntity<List<ConsumoResponseDTO>> findConsumoByComunidade(Long idComunidade) {
        List<ConsumoResponseDTO> consumos = consumoService.findConsumoByComunidade(idComunidade);
        if (consumos.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(consumos);
    }


}
