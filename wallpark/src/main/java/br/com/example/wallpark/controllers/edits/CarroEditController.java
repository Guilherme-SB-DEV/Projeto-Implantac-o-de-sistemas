package br.com.example.wallpark.controllers.edits;

import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.controllers.edits.dtos.EditRequest;
import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.models.Vaga;
import br.com.example.wallpark.repositorio.RepositorioCarro;
import br.com.example.wallpark.repositorio.RepositorioVaga;
import br.com.example.wallpark.utils.Porte;
import br.com.example.wallpark.utils.ValidInteger;

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
    private ValidInteger validate;

    @PostMapping("/edit")
    public ResponseEntity<Void> edit(@RequestBody EditRequest request) {
        if (request.mode() == "carro") {
            Optional<Carro> carro = repositorioCarro.findById(request.id());
            if (carro.isPresent()) {
                switch (request.field()) {
                    case "placa":
                        carro.get().setPlaca(request.edit());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "modelo":
                        carro.get().setModelo(request.edit());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "porte":
                        carro.get().setPorte(Porte.valueOf(request.edit()));
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "ano":
                        carro.get().setAno(Integer.parseInt(request.edit()));
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "vaga_id":
                        if (validate.isValidInteger(request.edit())) {
                            if (repositorioVaga.findById(Integer.parseInt(request.edit())).isPresent()) {
                                repositorioCarro.updateVaga(Integer.parseInt(request.edit()), carro.get().getId());
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
        if (request.mode() == "vaga") {
            Optional<Vaga> vaga = repositorioVaga.findById(request.id());
            if (vaga.isPresent()) {
                switch (request.field()) {
                    case "porte":
                        vaga.get().setPorte(Porte.valueOf(request.edit()));
                        return ResponseEntity.status(HttpStatus.OK).build();
                    case "coordenadas":
                        if (validate.isValidInteger(Character.toString(request.edit().charAt(0))) &&
                            validate.isValidInteger(Character.toString(request.edit().charAt(2)))) {
                            vaga.get().setCoordenadas(request.edit());
                            return ResponseEntity.status(HttpStatus.OK).build();
                        }
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

                    case "piso":
                        vaga.get().setPiso(request.edit());
                        return ResponseEntity.status(HttpStatus.OK).build();
                    default:
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
