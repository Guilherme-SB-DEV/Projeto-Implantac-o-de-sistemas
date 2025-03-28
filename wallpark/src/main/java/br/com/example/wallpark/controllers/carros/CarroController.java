package br.com.example.wallpark.controllers.carros;

import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.controllers.carros.dtos.CarroRequest;
import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.models.Vaga;
import br.com.example.wallpark.repositorio.RepositorioCarro;
import br.com.example.wallpark.repositorio.RepositorioVaga;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CarroController {
    @Autowired
    private RepositorioCarro repositorio;
    @Autowired
    private RepositorioVaga repositorioVaga;

    @PostMapping("/cadCar")
    public ResponseEntity<Carro> postMethodName(@RequestBody CarroRequest carroRequest) {
        Carro carro = carroRequest.carro();
        Integer id = carroRequest.id();
        Optional<Carro> car = repositorio.findByPlaca(carro.getPlaca());
        if (car.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Optional<Vaga> vaga = repositorioVaga.findById(id);
        if (vaga.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Vaga não encontrada
        }
        if(vaga.get().getPorte().equals(carro.getPorte()) || vaga.get().getPorte().equals("GRANDE") || (vaga.get().getPorte().equals("MEDIO") && carro.getPorte().equals("PEQUENO"))){
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Vaga não é do tipo do carro
        }
        carroRequest.carro().setVaga(vaga.get());
        repositorio.save(carroRequest.carro());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
