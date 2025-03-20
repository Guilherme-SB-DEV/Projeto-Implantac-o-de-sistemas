package br.com.example.wallpark.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.wallpark.models.Usuario;
import br.com.example.wallpark.repositorio.RepositorioCarro;
import br.com.example.wallpark.repositorio.RepositorioUsr;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class Controller {
    @Autowired
    private RepositorioCarro repositorio;
    @Autowired
    private RepositorioUsr repositorioUsr;

    private ModelAndView mv = new ModelAndView();

    // GETS
    @GetMapping("/init/{id}")
    public ModelAndView init(@PathVariable String id) {
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
    public ResponseEntity<Usuario> cadastro(@RequestBody  Usuario usuario) {
        if(repositorioUsr.findByEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        };
        repositorioUsr.save(usuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
        Optional<Usuario> usr = repositorioUsr.findByEmail(usuario.getEmail()); 
        if(usr.isPresent() && usr.get().getSenha().equals(usuario.getSenha()) ){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    
}
