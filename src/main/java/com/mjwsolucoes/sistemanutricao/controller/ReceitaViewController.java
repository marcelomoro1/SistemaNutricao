package com.mjwsolucoes.sistemanutricao.controller;

import com.mjwsolucoes.sistemanutricao.dto.ReceitaDetalhadaDTO;
import com.mjwsolucoes.sistemanutricao.service.ReceitaService;
import org.springframework.stereotype.Controller; // Use @Controller para retornar views, não @RestController
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

    // Este é o método que você precisa adicionar
    @GetMapping("/detalhes/{id}")
    public String exibirDetalhesReceita(@PathVariable Long id, Model model) {
        ReceitaDetalhadaDTO detalhes = receitaService.buscarDetalhesReceita(id);

        // Adiciona os detalhes ao modelo para que o Thymeleaf possa acessá-los
        model.addAttribute("receitaDetalhes", detalhes); 

        
        return "detalhesReceita"; // Certifique-se de que este é o nome do seu arquivo HTML
    }

   
}
