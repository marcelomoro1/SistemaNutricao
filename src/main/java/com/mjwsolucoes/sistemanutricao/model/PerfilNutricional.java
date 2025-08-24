package com.mjwsolucoes.sistemanutricao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilNutricional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double perCapita; 

    @Column(nullable = false)
    private Double totalGramas;

    @Column(nullable = false)
    private Double totalKcal; 

    @Column(nullable = false)
    private Double totalPorcentagem;

    @Column(nullable = false)
    private Double vct;

    @OneToOne
    @JoinColumn(name = "receita_id", unique = true, nullable = false) // Garante que uma Receita tenha apenas um PerfilNutricional
    private Receita receita;


}
