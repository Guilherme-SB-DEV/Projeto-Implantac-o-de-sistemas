package br.com.example.wallpark.controllers.pagamento;

import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.repositorio.RepositorioCarro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PagamentoController {
    @Autowired
    private RepositorioCarro repositorioCarro;

    @GetMapping("/payment")
    public ResponseEntity<InputStreamResource> payment(@RequestParam Integer id) throws IOException {
        Optional<Carro> carro = repositorioCarro.findById(id);
        if (!carro.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ZonedDateTime now = ZonedDateTime.now();
        Integer carTime = carro.get().getTempo().getHour();
        Integer horas = now.getHour() - carTime;
        Integer dias = now.getDayOfYear() - carro.get().getTempo().getDayOfYear();
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
        
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream);
        writer.write(
                "NOTA FISCAL \n" + //
                "Data de Emissão: " + now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear() + "\n" + //
                "\n" + //
                "Emitente:\n" + //
                "Nome: Estacionamento WallPark LTDA\n" + //
                "CNPJ: 12.345.678/0001-90\n" + //
                "Endereço: Rua Almirante, 123 - Salvador, BA, CEP 01000-000\n" + //
                "Telefone: (11) 1234-5678\n" + //
                "\n" + //
                "Informações do veículo:\n" + //
                "Placa: " + carro.get().getPlaca() + "\n" + //
                "Modelo: " + carro.get().getModelo() + "\n" + //
                "Porte: " + carro.get().getPorte() + "\n" + //
                "Telefone: (11) 98765-4321\n" + //
                "\n" + //
                "Descrição do Serviço:\n" + //
                "\n" + //
                "----------------------------------------------------------------------\n"
                + "Codigo: " + carro.get().getId() +" | Período: "+((Integer)(dias*24+horas))+"h | Valor: R$ " + valor + "\n" + //
                "\n" + //
                "Total Geral: R$ " + valor + "\n" + //
                "\n" + //
                "Forma de Pagamento: Dinheiro\n" + //
                "Vencimento: "+now.plusDays(15).getDayOfMonth() +"/"+now.plusDays(15).getMonthValue()+"/"+now.plusDays(15).getYear()+"\n" + //
                "");
        writer.flush();

        byte[] fileContent = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);

        InputStreamResource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=arquivo-gerado.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(fileContent.length)
                .body(resource);
    }

}
