package com.fiap.EnergyWise.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "GS1_COMUNIDADE_POPULACAO")
public class Gs1ComunidadePopulacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMUNIDADE_POPULACAO", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "QTD_POPULACAO", nullable = false, precision = 12, scale = 2)
    private BigDecimal qtdPopulacao;

    @NotNull
    @Column(name = "DATA_MARCACAO", nullable = false)
    private LocalDate dataMarcacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_COMUNIDADE", nullable = false)
    private Gs1Comunidade idComunidade;

}