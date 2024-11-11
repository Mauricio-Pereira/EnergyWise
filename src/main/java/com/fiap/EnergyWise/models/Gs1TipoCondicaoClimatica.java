package com.fiap.EnergyWise.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GS1_TIPO_CONDICAO_CLIMATICA")
public class Gs1TipoCondicaoClimatica {
    @Id
    @Column(name = "ID_TIPO_CONDICAO_CLIMATICA", nullable = false)
    private Short id;

    @Size(max = 100)
    @NotNull
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

}