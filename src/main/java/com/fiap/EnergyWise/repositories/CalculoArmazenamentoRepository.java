package com.FIAP.EnergyWise.repositories;

import com.FIAP.EnergyWise.models.CalculoArmazenamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalculoArmazenamentoRepository extends JpaRepository<CalculoArmazenamento, Long>
{
    Optional<List<CalculoArmazenamento>> findCalculoArmazenamentoByComunidadeId(Long idComunidade);
}
