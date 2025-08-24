package com.mjwsolucoes.sistemanutricao.dto;

import lombok.Data;

@Data
public class ReceitaResumoDTO {
    private Long id;
    private String nome;
    private String categoria;
    private Integer tempoPreparo;
    private Integer numeroPorcoes;
    private Double totalKcal; 
    private String nutricionistaUsername;
}
