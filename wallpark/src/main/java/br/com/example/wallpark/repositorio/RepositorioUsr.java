package br.com.example.wallpark.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.example.wallpark.models.Usuario;

@Repository
public interface RepositorioUsr extends CrudRepository<Usuario, Integer>{
    
    Optional<Usuario> findByEmail(String email);   
}
