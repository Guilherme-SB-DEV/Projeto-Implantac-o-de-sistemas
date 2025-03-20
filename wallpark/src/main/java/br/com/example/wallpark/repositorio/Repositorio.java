package br.com.example.wallpark.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.example.wallpark.models.Carro;

@Repository
public interface Repositorio extends CrudRepository<Carro, Integer>{
    
}
