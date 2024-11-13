package com.FIAP.EnergyWise.services;

import com.FIAP.EnergyWise.DTOS.consumo.ConsumoRequestDTO;
import com.FIAP.EnergyWise.DTOS.consumo.ConsumoResponseDTO;
import com.FIAP.EnergyWise.models.Comunidade;
import com.FIAP.EnergyWise.models.Consumo;
import com.FIAP.EnergyWise.repositories.ComunidadeRepository;
import com.FIAP.EnergyWise.repositories.ConsumoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumoService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ConsumoRepository consumoRepository;

    @Autowired
    private ComunidadeRepository comunidadeRepository;

    public ConsumoResponseDTO createConsumo(ConsumoRequestDTO consumoRequestDTO, Long idComunidade) {
        Comunidade comunidade = comunidadeRepository.findById(idComunidade)
                .orElseThrow(() -> new RuntimeException("Comunidade não encontrada"));

        Consumo consumo = modelMapper.map(consumoRequestDTO, Consumo.class);
        consumo.setDataConsumo(Timestamp.valueOf(LocalDateTime.now()));

        consumo.setComunidade(comunidade);
        consumoRepository.save(consumo);
        ConsumoResponseDTO consumoResponseDTO = modelMapper.map(consumo, ConsumoResponseDTO.class);
        consumoResponseDTO.setNomeComunidade(comunidade.getNome());
        return consumoResponseDTO;
    }

    public List<ConsumoResponseDTO> findAllConsumos() {
        List<Consumo> consumo = consumoRepository.findAll();

        if (consumo.isEmpty()) {
            throw new RuntimeException("Nenhum consumo encontrado");
        }

        List<ConsumoResponseDTO> consumoResponse = consumo.stream().map(c -> {
            ConsumoResponseDTO consumoResponseDTO = modelMapper.map(c, ConsumoResponseDTO.class);
            consumoResponseDTO.setNomeComunidade(c.getComunidade().getNome());
            return consumoResponseDTO;
        }).collect(Collectors.toList());
        return consumoResponse;
    }

    public List<ConsumoResponseDTO> findConsumoByComunidade(Long idComunidade) {
        List<Consumo> consumo = consumoRepository.findConsumoByComunidadeId(idComunidade)
                .orElseThrow(() -> new RuntimeException("Nenhum foi encontrado para a comunidade"));
        List<ConsumoResponseDTO> consumoResponse = consumo.stream().map(c -> {
            ConsumoResponseDTO consumoResponseDTO = modelMapper.map(c, ConsumoResponseDTO.class);
            consumoResponseDTO.setNomeComunidade(c.getComunidade().getNome());
            return consumoResponseDTO;
        }).collect(Collectors.toList());
        return consumoResponse;
    }


}
