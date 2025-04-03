package br.com.example.wallpark.controllers.vagas;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.models.Vaga;
import br.com.example.wallpark.repositorio.RepositorioCarro;
import br.com.example.wallpark.repositorio.RepositorioVaga;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class VagaController {
    @Autowired
    private RepositorioVaga repositorio;

    @Autowired
    private RepositorioCarro repositorioCarro;

    @PostMapping("/cadVaga")
    public ResponseEntity<Vaga> cadVaga(@RequestBody Vaga vaga) {
        Iterable<Vaga> vagaList = repositorio.findAll();
        for(Vaga vagaEach : vagaList){
            if(vaga.getCoordenadas().equals(vagaEach.getCoordenadas())){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } 
        repositorio.save(vaga);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/delVaga")
    @Transactional
    public ResponseEntity<Vaga> postMethodName(@RequestParam Integer id) {
        Optional<Vaga> vaga = repositorio.findById(id);
        if (!vaga.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Iterable<Carro> carros = repositorioCarro.findAll();
        for(Carro carro : carros){
            if(carro.getVaga().getId() == id){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        repositorio.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
