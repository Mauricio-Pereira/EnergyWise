package com.FIAP.EnergyWise.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class CalculoArmazenamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALCULO_ARMAZENAMENTO", nullable = false)
    private Long id;

    @Column(name = "DATA_CALCULO")
    private LocalDate dataCalculo;

    @Column(name = "CAPACIDADE_ARMAZENAMENTO", precision = 12, scale = 2)
    private BigDecimal capacidadeArmazenamento;

    @Size(max = 500)
    @Column(name = "OBSERVACAO", length = 500)
    private String observacao;

    @Column(name = "QTD_PLACAS", precision = 12, scale = 2)
    private BigDecimal qtdPlacas;

    @Column(name = "CUSTO_PLACAS", precision = 12, scale = 2)
    private BigDecimal custoPlacas;

}