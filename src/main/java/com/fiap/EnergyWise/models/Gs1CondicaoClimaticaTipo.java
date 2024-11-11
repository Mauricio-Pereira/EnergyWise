package com.fiap.EnergyWise.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GS1_CONDICAO_CLIMATICA_TIPO")
public class Gs1CondicaoClimaticaTipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONDICAO_CLIMATICA_TIPO", nullable = false)
    private Long id;

    //TODO [JPA Buddy] generate columns from DB
}