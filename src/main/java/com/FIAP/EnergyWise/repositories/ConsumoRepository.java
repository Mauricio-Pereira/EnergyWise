package com.FIAP.EnergyWise.repositories;

import com.FIAP.EnergyWise.models.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConsumoRepository extends JpaRepository<Consumo, Long>
{
    Optional<List<Consumo>> findConsumoByComunidadeId(long idComunidade);

    @Query(value = "SELECT * FROM GS1_CONSUMO WHERE ID_COMUNIDADE = :idComunidade AND DATA_CONSUMO = (SELECT MAX(DATA_CONSUMO) FROM GS1_CONSUMO WHERE ID_COMUNIDADE = :idComunidade)", nativeQuery = true)
    Optional<Consumo> findConsumoByComunidadeIdAndLastData(long idComunidade);
}
