package br.com.example.wallpark.controllers.login;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.example.wallpark.controllers.login.dtos.LoginResponse;
import br.com.example.wallpark.models.Usuario;
import br.com.example.wallpark.repositorio.RepositorioUsr;

@RestController
public class LoginController {
    private final RepositorioUsr repositorioUsr;
    private final JwtEncoder jwtEncoder;
    private BCryptPasswordEncoder encoder;

    public LoginController(JwtEncoder jwtEncoder, BCryptPasswordEncoder encoder, RepositorioUsr repositorioUsr) {
        this.jwtEncoder = jwtEncoder;
        this.encoder = encoder;
        this.repositorioUsr = repositorioUsr;
    }

    private ModelAndView mv = new ModelAndView();

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Usuario usuario) {
        Optional<Usuario> usr = repositorioUsr.findByEmail(usuario.getEmail());
        if (usr.isPresent() && usr.get().getSenha().equals(usuario.getSenha())) {

            var now = Instant.now();
            var expiresIn = now.plusSeconds(3600);

            var claims = JwtClaimsSet.builder()
                    .issuer("wallpark")
                    .subject(Integer.toString(usr.get().getId()))
                    .issuedAt(now)
                    .expiresAt(expiresIn)
                    .build();
            var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            
            return ResponseEntity.ok(new LoginResponse(token, expiresIn));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/login")
    public ModelAndView login() {
        mv.setViewName("login");
        return mv;
    }

}
