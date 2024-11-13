package com.FIAP.EnergyWise.config;

import com.FIAP.EnergyWise.services.TipoPlacaSolarService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private TipoPlacaSolarService tipoPlacaSolarService;

    @PostConstruct
    public void init() {
        // Inicializa os tipos de placa solar
        tipoPlacaSolarService.inicializarTiposPlacaSolar();
    }
}
