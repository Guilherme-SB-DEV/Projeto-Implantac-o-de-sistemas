package br.com.example.wallpark.controllers.vagas;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.models.Vaga;
import br.com.example.wallpark.repositorio.RepositorioVaga;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class VagaController {
    @Autowired
    private RepositorioVaga repositorio;

    @PostMapping("/cadVaga")
    public ResponseEntity<Vaga> cadVaga(@RequestBody Vaga vaga) {
        repositorio.save(vaga);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/delVaga")
    public ResponseEntity<Vaga> postMethodName(@RequestBody Integer id) {
        Optional<Vaga> vaga = repositorio.findById(id);
        if (!vaga.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repositorio.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
