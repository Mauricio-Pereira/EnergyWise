package com.FIAP.EnergyWise.repositories;

import com.FIAP.EnergyWise.models.CalculoArmazenamento;
import org.hibernate.result.Output;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CalculoArmazenamentoRepository extends JpaRepository<CalculoArmazenamento, Long>
{
    Optional<List<CalculoArmazenamento>> findCalculoArmazenamentoByComunidadeId(Long idComunidade,
                                                                                Sort id);

    @Query(value = "SELECT * FROM GS1_CALCULO_ARMAZENAMENTO WHERE ID_COMUNIDADE = :idComunidade AND DATA_CALCULO = (SELECT MAX(DATA_CALCULO) FROM GS1_CALCULO_ARMAZENAMENTO WHERE ID_COMUNIDADE = :idComunidade)", nativeQuery = true)
    Optional<CalculoArmazenamento> findLastCalculoArmazenamentoByComunidadeId(Long idComunidade);

    @Procedure(name = "CalculoArmazenamento.calcularPlacasNecessarias")
    void calcularPlacasNecessarias(
            @Param("p_comunidade_id") Long comunidadeId,
            @Param("p_tipo_placa_id") Long tipoPlacaId,
            @Param("p_comunidade_nome") Output comunidadeNome,
            @Param("p_tipo_placa_nome") Output tipoPlacaNome,
            @Param("p_qtd_placas") Output qtdPlacas,
            @Param("p_custo_placas") Output custoPlacas,
            @Param("p_armazenamento_mensal") Output armazenamentoMensal,
            @Param("p_observacao") Output observacao
    );
}
