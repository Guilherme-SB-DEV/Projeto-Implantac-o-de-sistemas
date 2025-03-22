package br.com.example.wallpark.controllers.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.wallpark.models.Usuario;
import br.com.example.wallpark.repositorio.RepositorioUsr;

@RestController
public class Cadastro {
    @Autowired
    private RepositorioUsr repositorioUsr;
    
    private ModelAndView mv = new ModelAndView();
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
    
}
