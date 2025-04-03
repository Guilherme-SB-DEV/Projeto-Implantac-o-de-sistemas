package br.com.example.wallpark.controllers.edits;

import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.controllers.edits.dtos.EditRequest;
import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.repositorio.RepositorioCarro;
import br.com.example.wallpark.repositorio.RepositorioVaga;
import br.com.example.wallpark.utils.Porte;
import br.com.example.wallpark.utils.ValidInteger;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CarroEditController {
    @Autowired
    private RepositorioCarro repositorioCarro;
    @Autowired
    private RepositorioVaga repositorioVaga;
    @Autowired
    private ValidInteger validate;

    @Transactional
    @PostMapping("/edit")
    public ResponseEntity<Void> edit(@RequestBody EditRequest request) {

  
            Optional<Carro> carro = repositorioCarro.findById(request.id());

            if (carro.isPresent()) {
                switch (request.field()) {
                    case "placa":
                        carro.get().setPlaca(request.value());
                        repositorioCarro.save(carro.get());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "modelo":
                        carro.get().setModelo(request.value());
                        repositorioCarro.save(carro.get());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "porte":
                        carro.get().setPorte(Porte.valueOf(request.value()));
                        repositorioCarro.save(carro.get());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "ano":
                        carro.get().setAno(Integer.parseInt(request.value()));
                        repositorioCarro.save(carro.get());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "id_vaga":
                        if (validate.isValidInteger(request.value())) {
                            if (repositorioVaga.findById(Integer.parseInt(request.value())).isPresent()) {
                                Iterable<Carro> carros = repositorioCarro.findAll();
                                for(Carro carroItem : carros){
                                    if(carroItem.getVaga().getId() == Integer.parseInt(request.value())){
                                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                                    }
                                    
                                }
                                repositorioCarro.updateVaga(Integer.parseInt(request.value()), carro.get().getId());
                                return ResponseEntity.status(HttpStatus.OK).build();
                            }
                        }
                        break;
                    default:
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
