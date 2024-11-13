package com.FIAP.EnergyWise.repositories;


import com.FIAP.EnergyWise.models.TipoPlacaSolar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoPlacaSolarRepository extends JpaRepository<TipoPlacaSolar, Long>
{
    Optional<TipoPlacaSolar> findByNome(String nome);
}
