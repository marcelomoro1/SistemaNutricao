package com.mjwsolucoes.sistemanutricao.repository;

import com.mjwsolucoes.sistemanutricao.model.PerfilNutricional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importe Optional

@Repository
public interface PerfilNutricionalRepository extends JpaRepository<PerfilNutricional, Long> {

    Optional<PerfilNutricional> findByReceitaId(Long receitaId);

    boolean existsByReceitaId(Long receitaId);

    void deleteByReceitaId(Long receitaId);
}
