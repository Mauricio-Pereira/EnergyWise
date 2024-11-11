package com.fiap.EnergyWise.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "GS1_CALCULO_ARMAZENAMENTO")
public class Gs1CalculoArmazenamento {
    @Id
    @Column(name = "ID_CALCULO_ARMAZENAMENTO", nullable = false, precision = 12, scale = 2)
    private BigDecimal id;

    @Column(name = "DATA_CALCULO")
    private LocalDate dataCalculo;

    @Column(name = "CAPACIDADE_ARM_SOLAR", precision = 12, scale = 2)
    private BigDecimal capacidadeArmSolar;

    @Column(name = "CAPACIDADE_ARM_EOLICA", precision = 12, scale = 2)
    private BigDecimal capacidadeArmEolica;

    @Size(max = 500)
    @Column(name = "OBSERVACAO", length = 500)
    private String observacao;

}