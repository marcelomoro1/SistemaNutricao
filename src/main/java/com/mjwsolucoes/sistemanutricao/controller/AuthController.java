package com.mjwsolucoes.sistemanutricao.controller;

import com.mjwsolucoes.sistemanutricao.dto.RegistroDTO; // Remova LoginDTO e AuthService se não forem mais usados aqui
import com.mjwsolucoes.sistemanutricao.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login") 
    public String showLoginForm() {
        return "index";
    }


    @GetMapping("/registro")
    public String showRegisterForm() {
        return "registro";
    }

    @PostMapping("/registro")
    public String processRegister(@ModelAttribute RegistroDTO registroDTO) {
        if (authService.registrar(registroDTO)) {
            return "redirect:/login?registered";
        }
        return "redirect:/registro?error";
    }
}
