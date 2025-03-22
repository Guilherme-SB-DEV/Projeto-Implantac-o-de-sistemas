package br.com.example.wallpark.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.example.wallpark.models.Vaga;

@Repository
public interface RepositorioVaga extends CrudRepository<Vaga, Integer>{
    
}
