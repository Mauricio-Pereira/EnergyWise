package com.FIAP.EnergyWise.models;

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
@Table(name = "GS1_ENERGIA_GERADA")
public class EnergiaGerada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENERGIA_GERADA", nullable = false)
    private Long id;

    @Column(name = "DATA_MEDICAO")
    private LocalDate dataMedicao;

    @Column(name = "ENERGIA_SOLAR_GERADA", precision = 12, scale = 2)
    private BigDecimal energiaSolarGerada;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_COMUNIDADE", nullable = false)
    private Comunidade idComunidade;

}