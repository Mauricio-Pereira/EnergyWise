package com.FIAP.EnergyWise.controllers;

import com.FIAP.EnergyWise.models.TipoPlacaSolar;
import com.FIAP.EnergyWise.services.TipoPlacaSolarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipoPlacaSolar")
@Tag(name = "TipoPlacaSolar", description = "API de tipos de placas solares")
public class TipoPlacaSolarController {

    @Autowired
    private TipoPlacaSolarService tipoPlacaSolarService;

    @Operation(summary = "Busca todos os tipos de placas solares")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de placas solares encontrados"),
            @ApiResponse(responseCode = "404", description = "Tipos de placas solares não encontrados")
    })
    @GetMapping
    public ResponseEntity<List<TipoPlacaSolar>> findAllPlacasSolares() {
        List<TipoPlacaSolar> placasSolares = tipoPlacaSolarService.findAllPlacasSolares();
        if (placasSolares.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(placasSolares);
    }

    @Operation(summary = "Busca um tipo de placa solar por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de placa solar encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de placa solar não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoPlacaSolar> findById(Long id) {
        TipoPlacaSolar tipoPlacaSolar = tipoPlacaSolarService.findById(id);
        if (tipoPlacaSolar == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(tipoPlacaSolar);
    }
}
