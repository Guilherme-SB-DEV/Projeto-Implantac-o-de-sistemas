package br.com.example.wallpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.wallpark.models.Usuario;
import br.com.example.wallpark.repositorio.Repositorio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class Controller {
    @Autowired
    private Repositorio repositorio;
    private ModelAndView mv = new ModelAndView();

    // GETS
    @GetMapping("/init")
    public ModelAndView init() {
        mv.setViewName("init");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        mv.setViewName("login");
        return mv;
    }

    // cadastro
    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        mv.setViewName("cadastro");
        return mv;
    }

    // POSTS
    @PostMapping("/cadastro")
    public Usuario cadastro(@RequestBody  Usuario usuario) {
        // TODO: process POST request

        return usuario;
    }
    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        //TODO: process POST request
        
        return usuario;
    }
    
}
