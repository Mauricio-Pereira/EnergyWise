package com.FIAP.EnergyWise.DTOS.comunidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComunidadeResponseDTO
{
    Long id;
    String nome;
    Integer numPopulacao;
    String nomeCidade;
    String dataCadastro;
}
