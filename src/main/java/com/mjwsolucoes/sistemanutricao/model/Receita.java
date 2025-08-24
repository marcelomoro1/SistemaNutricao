package com.mjwsolucoes.sistemanutricao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaReceita categoria;

    @Column(nullable = false, columnDefinition = "TEXT") 
    private String modoPreparo;

    @Column(nullable = false)
    private Integer tempoPreparo; 

    @Column(nullable = false)
    private Double pesoPorcao; 

    @Column(nullable = false)
    private Double rendimento;

    @Column(nullable = false)
    private String equipamentos;

    @Column(nullable = false)
    private Integer numeroPorcoes;

    @Column(nullable = false)
    private Double fcc; 

    @Column(nullable = false)
    private String medidaCaseira;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id", nullable = false)
    private User nutricionista;

    @OneToOne(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    private PerfilNutricional perfilNutricional;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceitaIngrediente> ingredientesReceita = new ArrayList<>();
}
