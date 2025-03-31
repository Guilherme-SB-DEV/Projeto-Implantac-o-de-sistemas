package br.com.example.wallpark.controllers.carros;

import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.controllers.carros.dtos.CarroRequest;
import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.models.Vaga;
import br.com.example.wallpark.repositorio.RepositorioCarro;
import br.com.example.wallpark.repositorio.RepositorioVaga;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CarroController {
    @Autowired
    private RepositorioCarro repositorio;
    @Autowired
    private RepositorioVaga repositorioVaga;

    @PostMapping("/cadCar")
    public ResponseEntity<Carro> cadCar(@RequestBody CarroRequest carroRequest) {
        Carro carro = carroRequest.carro();
        Integer id = carroRequest.id();
        Optional<Carro> car = repositorio.findByPlaca(carro.getPlaca());
        Optional<Vaga> vaga = repositorioVaga.findById(id);
        if (car.isPresent() || vaga.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if ((vaga.get().getPorte().toString().equals("PEQUENO") && carro.getPorte().toString().equals("GRANDE")) ||
            (vaga.get().getPorte().toString().equals("PEQUENO") && carro.getPorte().toString().equals("MEDIO")) ||
            (vaga.get().getPorte().toString().equals("MEDIO") && carro.getPorte().toString().equals("GRANDE"))) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        System.out.println(vaga.get().getPorte());
        System.out.println(carro.getPorte());
        carroRequest.carro().setVaga(vaga.get());
        repositorio.save(carroRequest.carro());
        return ResponseEntity.status(200).build();
    }
    @PostMapping("/delCar")
    @Transactional
    public ResponseEntity<Carro> delCar(@RequestParam Integer id) {
        Optional<Carro> carro = repositorio.findById(id);
        if(carro.isPresent()){
            if(carro.get().getVaga() != null){
                carro.get().setVaga(null);
            }
            repositorio.delCar(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
