package com.FIAP.EnergyWise.services;

import com.FIAP.EnergyWise.DTOS.calculoArmazenamento.CalculoArmazenamentoResponseDTO;
import com.FIAP.EnergyWise.exception.ResourceNotFoundException;
import com.FIAP.EnergyWise.models.CalculoArmazenamento;
import com.FIAP.EnergyWise.models.Comunidade;
import com.FIAP.EnergyWise.models.Consumo;
import com.FIAP.EnergyWise.models.TipoPlacaSolar;
import com.FIAP.EnergyWise.repositories.CalculoArmazenamentoRepository;
import com.FIAP.EnergyWise.repositories.ComunidadeRepository;
import com.FIAP.EnergyWise.repositories.ConsumoRepository;
import com.FIAP.EnergyWise.repositories.TipoPlacaSolarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CalculoArmazenamentoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComunidadeRepository comunidadeRepository;

    @Autowired
    private CalculoArmazenamentoRepository calculoArmazenamentoRepository;

    @Autowired
    private TipoPlacaSolarRepository tipoPlacaSolarRepository;

    @Autowired
    private ConsumoRepository consumoRepository;

    public CalculoArmazenamentoResponseDTO calcularPlacasNecessarias(
            Long comunidadeId, Long tipoPlacaId) {
        // Verificar se a comunidade existe
        Comunidade comunidade =
                comunidadeRepository.findById(comunidadeId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Comunidade não encontrada."));



        // Obter detalhes do tipo de placa solar
        TipoPlacaSolar tipoPlaca =
                tipoPlacaSolarRepository.findById(tipoPlacaId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Tipo de placa solar não encontrado."));


        Consumo ultimoConsumo =
                consumoRepository.findConsumoByComunidadeIdAndLastData(comunidadeId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Consumo não encontrado."));

        // Calcular a quantidade de placas necessárias para gerar a energia consumida e mais 20% para armazenamento
        BigDecimal consumoTotal = ultimoConsumo.getEnergiaConsumida()
                .multiply(BigDecimal.valueOf(1.2));

        var producaoMensal =
                tipoPlaca.getPotenciaWatt().doubleValue() / 1000 / 1000 * 5 *
                        0.8 *
                        30; // 5 horas de sol por dia, 80% de eficiência e 30 dias no mês

        var qtdPlacas =
                consumoTotal.divide(BigDecimal.valueOf(producaoMensal), 2,
                        BigDecimal.ROUND_UP);

        var custoPlacas = qtdPlacas.multiply(tipoPlaca.getPrecoUnitario());

        CalculoArmazenamento calculo = new CalculoArmazenamento();
        calculo.setComunidade(comunidade);
        calculo.setTipoPlacaSolar(tipoPlaca);
        calculo.setCapacidadeArmazenamento(consumoTotal);
        calculo.setQtdPlacas(qtdPlacas);
        calculo.setCustoPlacas(custoPlacas);
        calculo.setDataCalculo(Timestamp.valueOf(LocalDateTime.now()));
        calculo.setObservacao(
                "Cálculo realizado automaticamente pelo sistema no dia " +
                        Timestamp.valueOf(LocalDateTime.now()));

        CalculoArmazenamento calculoArmazenamento =
                calculoArmazenamentoRepository.save(calculo);

        CalculoArmazenamentoResponseDTO dto =
                modelMapper.map(calculoArmazenamento,
                        CalculoArmazenamentoResponseDTO.class);
        dto.setComunidade(comunidade.getNome());
        dto.setTipoPlacaSolar(tipoPlaca.getNome());

        return dto;
    }

    public List<CalculoArmazenamentoResponseDTO> findAllCalculosByComunidade(Long comunidadeId) {
        Comunidade comunidade =
                comunidadeRepository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException(
                        "Comunidade não encontrada."));

        List<CalculoArmazenamento> calculos =
                calculoArmazenamentoRepository.findCalculoArmazenamentoByComunidadeId(
                        comunidadeId).orElseThrow(() -> new ResourceNotFoundException(
                        "Cálculos não encontrados para a comunidade."));

        List<CalculoArmazenamentoResponseDTO> dtos = calculos.stream().map(c -> {
            CalculoArmazenamentoResponseDTO dto =
                    modelMapper.map(c, CalculoArmazenamentoResponseDTO.class);
            dto.setComunidade(c.getComunidade().getNome());
            dto.setTipoPlacaSolar(c.getTipoPlacaSolar().getNome());
            return dto;
        }).collect(toList());

        return dtos;
    }
}
