package com.mjwsolucoes.sistemanutricao.controller;

import com.mjwsolucoes.sistemanutricao.dto.ReceitaDetalhadaDTO;
import com.mjwsolucoes.sistemanutricao.service.ReceitaService;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receita")
public class ReceitaViewController {

    private final ReceitaService receitaService;

    public ReceitaViewController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @GetMapping("/detalhes/{id}")
    public String exibirDetalhesReceita(@PathVariable Long id, Model model) {
        ReceitaDetalhadaDTO detalhes = receitaService.buscarDetalhesReceita(id);

        model.addAttribute("receitaDetalhes", detalhes); 

        
        return "detalhesReceita";
    }

   
}
