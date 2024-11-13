package com.FIAP.EnergyWise.services;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeRequestDTO;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeRequestUpdateDTO;
import com.FIAP.EnergyWise.DTOS.comunidade.ComunidadeResponseDTO;
import com.FIAP.EnergyWise.exception.ResourceNotFoundException;
import com.FIAP.EnergyWise.models.Comunidade;
import com.FIAP.EnergyWise.repositories.CalculoArmazenamentoRepository;
import com.FIAP.EnergyWise.repositories.CidadeRepository;
import com.FIAP.EnergyWise.repositories.ComunidadePopulacaoRepository;
import com.FIAP.EnergyWise.repositories.ComunidadeRepository;
import com.FIAP.EnergyWise.repositories.ConsumoRepository;
import com.FIAP.EnergyWise.repositories.TipoPlacaSolarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private ComunidadePopulacaoRepository comuPopulacaoRepository;

    @Autowired
    private ConsumoRepository consumoRepository;

    @Autowired
    private TipoPlacaSolarRepository tipoPlacaSolarRepository;

    @Autowired
    private CalculoArmazenamentoRepository calculoArmazenamentoRepository;

    private static final BigDecimal CONSUMO_MEDIO_PESSOA_KWH =
            BigDecimal.valueOf(170);


    public Comunidade createComunidade(ComunidadeRequestDTO comunidadeRequestDTO) {

        if (comunidadeRequestDTO.getNumPopulacao() <= 0) {
            throw new ResourceNotFoundException("A comunidade deve ter pelo menos uma pessoa");
        }


        Comunidade comunidade =
                modelMapper.map(comunidadeRequestDTO, Comunidade.class);
        comunidade.setCidade(cidadeRepository.findCidadeByNome(
                comunidadeRequestDTO.getNomeCidade().toUpperCase())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cidade não encontrada. " +
                                "A cidade não está cadastrada ou o nome da cidade está incorreto" +
                                "Por favor, verifique o nome da cidade, incluindo acentos e caracteres especiais.")));

        int idComunidadeCadastrada =
                comunidadeRepository.inserirComunidade(comunidade.getNome(),
                        comunidade.getNumPopulacao(),
                        comunidade.getCidade().getId(),
                        LocalDate.now());

        Comunidade comunidadeCadastrada =
                comunidadeRepository.findById((long) idComunidadeCadastrada).get();

        return comunidadeCadastrada;
    }

    public List<ComunidadeResponseDTO> findAllComunidades() {

        List<Comunidade> comunidades = comunidadeRepository.findAll();

        if (comunidades.isEmpty() ) {
            throw new ResourceNotFoundException("Nenhuma comunidade cadastrada");
        }

        List<ComunidadeResponseDTO> comunidadesResponse =
                comunidades.stream().map(comunidade -> {
                    ComunidadeResponseDTO dto = modelMapper.map(comunidade,
                            ComunidadeResponseDTO.class);
                    dto.setNomeCidade(comunidade.getCidade()
                            .getNome()); // Definindo apenas o nome da cidade
                    return dto;
                }).collect(Collectors.toList());

        return comunidadesResponse;
    }

    public ComunidadeResponseDTO findById(Long id) {
        Comunidade comunidade = comunidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada"));
        ComunidadeResponseDTO dto =
                modelMapper.map(comunidade, ComunidadeResponseDTO.class);
        dto.setNomeCidade(comunidade.getCidade().getNome());
        return dto;
    }

    @Transactional
    public ComunidadeResponseDTO updateComunidade(Long id,
                                                  ComunidadeRequestUpdateDTO comunidadeRequestDTO) {
        Comunidade comunidade = comunidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada"));

        comunidade.setNome(comunidadeRequestDTO.getNome());
        comunidade.setNumPopulacao(comunidadeRequestDTO.getNumPopulacao());

        comunidadeRepository.save(comunidade);

        ComunidadeResponseDTO dto =
                modelMapper.map(comunidade, ComunidadeResponseDTO.class);
        dto.setNomeCidade(comunidade.getCidade().getNome());
        return dto;

    }

    public ComunidadeResponseDTO deleteComunidade(Long id) {
        Comunidade comunidade = comunidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comunidade não encontrada"));
        comunidadeRepository.delete(comunidade);

        ComunidadeResponseDTO dto =
                modelMapper.map(comunidade, ComunidadeResponseDTO.class);
        dto.setNomeCidade(comunidade.getCidade().getNome());
        return dto;
    }


}
