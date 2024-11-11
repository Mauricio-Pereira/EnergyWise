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
@Table(name = "GS1_TIPO_ARMAZENAMENTO")
public class Gs1TipoArmazenamento {
    @Id
    @Column(name = "ID_TIPO_ARMAZENAMENTO", nullable = false)
    private Short id;

    @Size(max = 200)
    @NotNull
    @Column(name = "NOME", nullable = false, length = 200)
    private String nome;

}