package com.FIAP.EnergyWise.repositories;

import com.FIAP.EnergyWise.models.Cidade;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long>
{
    @NotNull Cidade findCidadeByNome(String nomeCidade);
}
