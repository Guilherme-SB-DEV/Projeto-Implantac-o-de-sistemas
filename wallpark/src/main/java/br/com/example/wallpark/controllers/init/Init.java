package br.com.example.wallpark.controllers.init;


import java.util.Optional;
import br.com.example.wallpark.repositorio.RepositorioVaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.models.Usuario;
import br.com.example.wallpark.models.Vaga;
import br.com.example.wallpark.repositorio.RepositorioCarro;
import br.com.example.wallpark.repositorio.RepositorioUsr;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Init {

    @Autowired
    private RepositorioCarro repositorioCarro;
    @Autowired
    private RepositorioUsr repositorioUsr;
    @Autowired
    private RepositorioVaga repositorioVaga;

    private ModelAndView mv = new ModelAndView();



    // GETS
    @GetMapping("/init/{id}")
    public ModelAndView init(@PathVariable Integer id) {
        Optional<Usuario> usr = repositorioUsr.findById(id);
        if(!usr.isPresent()){
            mv.setViewName("error");
            mv.addObject("msg","Usuário não encontrado.");            
            return mv;
        }
        mv.setViewName("init");
        Iterable<Vaga> vagas = repositorioVaga.findAll();
        Iterable<Carro> carros = repositorioCarro.findAll();
        System.out.println(usr);
        mv.addObject("usr",usr.get());
        mv.addObject("carros", carros);
        mv.addObject("vagas", vagas);
        return mv;
    }
    
 
   
}
