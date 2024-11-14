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
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.StoredProcedureQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
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
    private ConsumoRepository consumoRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public CalculoArmazenamentoResponseDTO calcularPlacasNecessarias(Long comunidadeId, Long tipoPlacaId) {
        // Verificar se existem consumos para a comunidade
        consumoRepository.findConsumoByComunidadeId(comunidadeId)
                .orElseThrow(() -> new ResourceNotFoundException("Consumos não encontrados para a comunidade."));

        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("INSERIR_CALCULO_ARMAZENAMENTO");

            // Registrar os parâmetros de entrada
            query.registerStoredProcedureParameter("p_comunidade_id", Long.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_tipo_placa_id", Long.class, ParameterMode.IN);

            // Registrar os parâmetros de saída
            query.registerStoredProcedureParameter("p_comunidade_nome", String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_tipo_placa_nome", String.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_qtd_placas", BigDecimal.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_custo_placas", BigDecimal.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_armazenamento_mensal", BigDecimal.class, ParameterMode.OUT);
            query.registerStoredProcedureParameter("p_observacao", String.class, ParameterMode.OUT);

            // Definir os parâmetros de entrada
            query.setParameter("p_comunidade_id", comunidadeId);
            query.setParameter("p_tipo_placa_id", tipoPlacaId);

            // Executar a procedure
            query.execute();

            // Obter os parâmetros de saída
            String comunidadeNome = (String) query.getOutputParameterValue("p_comunidade_nome");
            String tipoPlacaNome = (String) query.getOutputParameterValue("p_tipo_placa_nome");
            BigDecimal qtdPlacas = (BigDecimal) query.getOutputParameterValue("p_qtd_placas");
            BigDecimal custoPlacas = (BigDecimal) query.getOutputParameterValue("p_custo_placas");
            BigDecimal armazenamentoMensal = (BigDecimal) query.getOutputParameterValue("p_armazenamento_mensal");
            String observacao = (String) query.getOutputParameterValue("p_observacao");

            // Busca o cálculo mais recente e lança exceção se não encontrado
            CalculoArmazenamento calculoArmazenamento = calculoArmazenamentoRepository.findLastCalculoArmazenamentoByComunidadeId(comunidadeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cálculo não encontrado."));

            // Montar o DTO de resposta
            CalculoArmazenamentoResponseDTO dto = new CalculoArmazenamentoResponseDTO();
            dto.setId(calculoArmazenamento.getId());
            dto.setDataCalculo(calculoArmazenamento.getDataCalculo());
            dto.setComunidade(comunidadeNome);
            dto.setTipoPlacaSolar(tipoPlacaNome);
            dto.setQtdPlacas(qtdPlacas);
            dto.setCustoPlacas(custoPlacas);
            dto.setCapacidadeArmazenamento(armazenamentoMensal);
            dto.setObservacao(observacao);

            return dto;

        } catch (PersistenceException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                SQLException sqlException = (SQLException) cause;
                // Verificar se é o erro específico ORA-20003
                if ("20003".equals(sqlException.getSQLState()) || sqlException.getErrorCode() == 20003) {
                    throw new ResourceNotFoundException("Nenhum consumo de energia registrado para a comunidade.");
                }
            }
            // Para outros erros, lançar uma exceção genérica
            throw new RuntimeException("Erro ao executar a procedure INSERIR_CALCULO_ARMAZENAMENTO: " + e.getMessage(), e);
        }
    }



    public List<CalculoArmazenamentoResponseDTO> findAllCalculosByComunidade(Long comunidadeId) {
        Comunidade comunidade =
                comunidadeRepository.findById(comunidadeId).orElseThrow(() -> new ResourceNotFoundException(
                        "Comunidade não encontrada."));


        List<CalculoArmazenamento> calculos =
                calculoArmazenamentoRepository.findCalculoArmazenamentoByComunidadeId(
                                comunidadeId, Sort.by(Sort.Direction.DESC, "dataCalculo"))
                        .orElseThrow(() -> new ResourceNotFoundException("Cálculos não encontrados para a comunidade."));


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
