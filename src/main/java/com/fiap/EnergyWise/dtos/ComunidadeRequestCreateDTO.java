package com.FIAP.EnergyWise.DTOS;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ComunidadeRequestCreateDTO {
    @NotNull
    private String nome;
    @NotNull
    private int numPopulacao;
    @NotNull
    @NotEmpty
    private String nomeCidade;
}
