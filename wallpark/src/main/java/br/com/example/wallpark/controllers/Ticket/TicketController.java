package br.com.example.wallpark.controllers.Ticket;

import org.springframework.web.bind.annotation.RestController;

import br.com.example.wallpark.models.Vaga;
import br.com.example.wallpark.repositorio.RepositorioVaga;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TicketController {
    @Autowired
    private RepositorioVaga repositorio;

    @GetMapping("/ticket")
    public ResponseEntity<InputStreamResource> ticket(@RequestParam Integer id) throws IOException {
        Optional<Vaga> vaga = repositorio.findById(id);
        if (!vaga.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream);
        writer.write(
                "Ticket de Vaga:\n" + //
                        "ID: " + vaga.get().getId() + "\n" + //
                        "Porte: " + vaga.get().getPorte().toString() + "\n" + //
                        "Piso: " + vaga.get().getPiso() + "\n" +
                        "Coordenadas: " + vaga.get().getCoordenadas());
        writer.flush();
        byte[] fileContent = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream input = new ByteArrayInputStream(fileContent);
        InputStreamResource resource = new InputStreamResource(input);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=nota-fiscal.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(fileContent.length)
                .body(resource);
    }

}
