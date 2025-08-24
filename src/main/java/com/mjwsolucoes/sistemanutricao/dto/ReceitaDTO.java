
package com.mjwsolucoes.sistemanutricao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class ReceitaDTO {
    private Long id;

    @NotBlank(message = "Nome da receita é obrigatório")
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria; // Corresponde ao nome do Enum (ex: "ENTRADA")

    @NotBlank(message = "Equipamento é obrigatório")
    private String equipamentos;

    @NotBlank(message = "Modo de preparo é obrigatório")
    private String modoPreparo;

    @NotNull(message = "Tempo de preparo é obrigatório")
    @Positive(message = "Tempo de preparo deve ser positivo")
    private Integer tempoPreparo;

    @NotNull(message = "Peso da porção é obrigatório")
    @Positive(message = "Peso da porção deve ser positivo")
    private Double pesoPorcao; // Tipo Double no DTO

    @NotNull(message = "Rendimento é obrigatório")
    @Positive(message = "Rendimento deve ser positivo")
    private Double rendimento; // Tipo Double no DTO

    @NotNull(message = "Número de porções é obrigatório")
    @Positive(message = "Número de porções deve ser positivo")
    private Integer numeroPorcoes;

    private Double fcc;
    private String medidaCaseira;

    private Long userId; 
    private String nutricionistaUsername;

    private PerfilNutricionalDTO perfilNutricional;
    private List<ReceitaIngredienteInputDTO> ingredientes;
}
