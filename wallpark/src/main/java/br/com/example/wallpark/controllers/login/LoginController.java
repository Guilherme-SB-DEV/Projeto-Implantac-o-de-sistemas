package br.com.example.wallpark.controllers.login;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.wallpark.controllers.login.dtos.LoginResponse;
import br.com.example.wallpark.models.Usuario;
import br.com.example.wallpark.repositorio.RepositorioUsr;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    private final RepositorioUsr repositorioUsr;
    
    public LoginController(  RepositorioUsr repositorioUsr) {
        this.repositorioUsr = repositorioUsr;
    }

    private ModelAndView mv = new ModelAndView();

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Usuario usuario) {
        Optional<Usuario> usr = repositorioUsr.findByEmail(usuario.getEmail());
        if (usr.isPresent() && usr.get().getSenha().equals(usuario.getSenha())) {


            return ResponseEntity.ok(new LoginResponse(usr.get().getId()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/login")
    public ModelAndView login() {
        mv.setViewName("login");
        return mv;
    }


}
