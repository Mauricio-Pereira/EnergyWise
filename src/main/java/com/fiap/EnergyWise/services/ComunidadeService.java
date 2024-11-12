package com.FIAP.EnergyWise.services;

import com.FIAP.EnergyWise.DTOS.ComunidadeRequestCreateDTO;
import com.FIAP.EnergyWise.DTOS.ComunidadeResponseDTO;
import com.FIAP.EnergyWise.models.Comunidade;
import com.FIAP.EnergyWise.repositories.CidadeRepository;
import com.FIAP.EnergyWise.repositories.ComunidadeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComunidadeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComunidadeRepository comunidadeRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    public Comunidade createComunidade(ComunidadeRequestCreateDTO comunidadeRequestCreateDTO) {
        Comunidade comunidade = modelMapper.map(comunidadeRequestCreateDTO, Comunidade.class);
        comunidade.setIdCidade(cidadeRepository.findCidadeByNome(comunidadeRequestCreateDTO.getNomeCidade().toUpperCase()));

        int idComunidadeCadastrada = comunidadeRepository.inserirComunidade(comunidade.getNome(), comunidade.getNumPopulacao(), comunidade.getIdCidade().getId(),
                LocalDate.now());

        Comunidade comunidadeCadastrada = comunidadeRepository.findById((long) idComunidadeCadastrada).get();

        return comunidadeCadastrada;
    }

    public List<ComunidadeResponseDTO> findAllComunidades() {

        List<Comunidade> comunidades = comunidadeRepository.findAll();
        List<ComunidadeResponseDTO> comunidadesResponse = modelMapper.map(comunidades, List.class);

        System.out.println(comunidadesResponse);
        return comunidadesResponse;
    }


}
