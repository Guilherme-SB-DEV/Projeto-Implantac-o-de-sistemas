package br.com.example.wallpark.controllers.pagamento;

import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.controllers.pagamento.dtos.PagamentoResponse;
import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.repositorio.RepositorioCarro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PagamentoController {
    @Autowired
    private RepositorioCarro repositorioCarro;
    
    @GetMapping("/payment")
    public ResponseEntity<PagamentoResponse> payment(@RequestParam Integer id) throws IOException {
        Optional<Carro> carro = repositorioCarro.findById(id);
        if (!carro.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime carTime = carro.get().getTempo();
        Duration duracao = Duration.between(now, carTime);
        long horas = duracao.toHours();
        long dias = duracao.toDays();
        Double valor;
        switch (carro.get().getPorte()) {
            case GRANDE:
                valor = ((dias * 24.00) + horas) * 30.00;
                break;
            case MEDIO:
                valor = ((dias * 24.00) + horas) * 20.00;
                break;
            case PEQUENO:
                valor = ((dias * 24.00) + horas) * 10.00;
                break;
            default:
                valor = 0.00;
                break;
        }
        
        File file = new File("src/main/resources/static/files/arquivo.txt");
        FileWriter writer = new FileWriter(file);
        writer.write("valor: "+valor.toString());
        writer.close(); 
        PagamentoResponse response = new PagamentoResponse(carro.get(), valor);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
