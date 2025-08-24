package com.mjwsolucoes.sistemanutricao.repository;

import com.mjwsolucoes.sistemanutricao.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByNutricionistaId(Long nutricionistaId);

    List<Receita> findByCategoria(String categoria);

    List<Receita> findByNomeContainingIgnoreCase(String nome);


    @Query("SELECT r FROM Receita r JOIN r.ingredientesReceita ri WHERE ri.ingrediente.id = :ingredienteId")
    List<Receita> findByIngredienteId(@Param("ingredienteId") Long ingredienteId);

    boolean existsByNomeAndNutricionistaId(String nome, Long nutricionistaId);
}
