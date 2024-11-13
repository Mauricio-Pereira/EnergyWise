package com.FIAP.EnergyWise.services;

import com.FIAP.EnergyWise.exception.ResourceNotFoundException;
import com.FIAP.EnergyWise.models.TipoPlacaSolar;
import com.FIAP.EnergyWise.repositories.TipoPlacaSolarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TipoPlacaSolarService {

    @Autowired
    private TipoPlacaSolarRepository tipoPlacaSolarRepository;


    public List<TipoPlacaSolar> findAllPlacasSolares() {
        List<TipoPlacaSolar> tipoPlacaSolares = tipoPlacaSolarRepository.findAll();
        if (tipoPlacaSolares.isEmpty()) {
            new ResourceNotFoundException("Nenhum tipo de placa solar encontrado");
        }
        return tipoPlacaSolares;
    }

    public TipoPlacaSolar findById(Long id) {
        TipoPlacaSolar tipoPlacaSolar = tipoPlacaSolarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de placa solar não encontrado"));
        return tipoPlacaSolar;
    }

    public void inicializarTiposPlacaSolar() {
        if(tipoPlacaSolarRepository.findByNome("Placa 340W").isEmpty()) {
            TipoPlacaSolar placa340W = new TipoPlacaSolar();
            placa340W.setNome("Placa 340W");
            placa340W.setPotenciaWatt(BigDecimal.valueOf(340.00));
            placa340W.setPrecoUnitario(BigDecimal.valueOf(689.45));
            tipoPlacaSolarRepository.save(placa340W);
        }

        if(tipoPlacaSolarRepository.findByNome("Placa 400W").isEmpty()) {
            TipoPlacaSolar placa400W = new TipoPlacaSolar();
            placa400W.setNome("Placa 400W");
            placa400W.setPotenciaWatt(BigDecimal.valueOf(400.00));
            placa400W.setPrecoUnitario(BigDecimal.valueOf(946.86));
            tipoPlacaSolarRepository.save(placa400W);
        }

        if(tipoPlacaSolarRepository.findByNome("Placa 500W").isEmpty()) {
            TipoPlacaSolar placa450W = new TipoPlacaSolar();
            placa450W.setNome("Placa 500W");
            placa450W.setPotenciaWatt(BigDecimal.valueOf(500.00));
            placa450W.setPrecoUnitario(BigDecimal.valueOf(1345.25));
            tipoPlacaSolarRepository.save(placa450W);
        }



    }
}
