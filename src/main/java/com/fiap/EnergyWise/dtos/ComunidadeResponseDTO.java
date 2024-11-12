package com.FIAP.EnergyWise.DTOS;

public record ComunidadeResponseDTO (
        Long id,
        String nome,
        Integer numPopulacao,
        String nomeCidade,
        String dataCadastro
){
}
