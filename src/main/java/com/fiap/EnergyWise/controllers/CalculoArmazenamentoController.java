package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.DTOS.calculoArmazenamento.CalculoArmazenamentoResponseDTO;
import com.FIAP.EnergyWise.services.CalculoArmazenamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculoArmazenamento")
@Tag(name = "CalculoArmazenamento", description = "API de cálculo de armazenamento, numero de placas solares e valor de placas solares")
public class CalculoArmazenamentoController {

    @Autowired
    private CalculoArmazenamentoService calculoArmazenamentoService;



    @Operation(summary = "Calcula a quantidade de placas solares necessárias para uma comunidade considerando o consumo médio mensal.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cálculo realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping("/{idComunidade}/{idTipoPlaca}")
    public ResponseEntity<CalculoArmazenamentoResponseDTO> calcularPlacasNecessarias(@PathVariable
                                                                                     Long idComunidade, @PathVariable Long idTipoPlaca) {
        CalculoArmazenamentoResponseDTO calculo = calculoArmazenamentoService.calcularPlacasNecessarias(idComunidade, idTipoPlaca);
        return ResponseEntity.status(HttpStatus.CREATED).body(calculo);
    }

    @Operation(summary = "Mostra todos os cálculos de armazenamento realizados em uma comunidade.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculos encontrados"),
            @ApiResponse(responseCode = "404", description = "Cálculos não encontrados")
    })
    @GetMapping("/{idComunidade}")
    public ResponseEntity<List<CalculoArmazenamentoResponseDTO>> findCalculoByComunidade(@PathVariable Long idComunidade) {
        List<CalculoArmazenamentoResponseDTO> calculo = calculoArmazenamentoService.findAllCalculosByComunidade(idComunidade);
        return ResponseEntity.status(HttpStatus.OK).body(calculo);
    }
}
