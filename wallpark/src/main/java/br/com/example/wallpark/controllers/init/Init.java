package br.com.example.wallpark.controllers.init;

import br.com.example.wallpark.models.Carro;
import br.com.example.wallpark.models.Usuario;
import br.com.example.wallpark.models.Vaga;
import br.com.example.wallpark.repositorio.RepositorioCarro;
import br.com.example.wallpark.repositorio.RepositorioUsr;
import br.com.example.wallpark.repositorio.RepositorioVaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class Init {

    @Autowired
    private RepositorioCarro repositorioCarro;
    @Autowired
    private RepositorioUsr repositorioUsr;
    @Autowired
    private RepositorioVaga repositorioVaga;

    // GET /init/{id} - Protegido por JWT
    @GetMapping("/init/{id}")
    public ModelAndView init(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        Optional<Usuario> usr = repositorioUsr.findById(id);

        if (!usr.isPresent()) {
            mv.setViewName("error");
            mv.addObject("msg", "Usuário não encontrado.");
            return mv;
        }
        mv.setViewName("init");
        Iterable<Vaga> vagas = repositorioVaga.findAll();
        Iterable<Carro> carros = repositorioCarro.findAll();
        ArrayList<Vaga> vagasOcupadas = new ArrayList<>();
        ArrayList<Vaga> vagasLivres = new ArrayList<>();
        

        // Itera sobre todos os carros
        for (Vaga vaga : vagas) {
            for (Carro carro : carros) {
                // Itera sobre todas as vagas
                if (carro.getVaga().getId() == vaga.getId()) {
                    vagasOcupadas.add(vaga);
                }
            }
        }
        for (Vaga vaga : vagas) {
            if(!vagasOcupadas.contains(vaga)){
                vagasLivres.add(vaga);
            }
        }
        mv.addObject("usr", usr.get());
        mv.addObject("carros", carros);
        mv.addObject("vagasOcupadas", vagasOcupadas);
        mv.addObject("vagasLivres", vagasLivres);
        return mv;
    }

}
