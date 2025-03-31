package br.com.example.wallpark.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.example.wallpark.models.Carro;


@Repository
public interface RepositorioCarro extends CrudRepository<Carro, Integer>{
    @Query(value= "SELECT * FROM carro WHERE placa = :placa", nativeQuery = true)
    Optional<Carro> findByPlaca(@Param("placa") String placa);


    @Modifying
    @Query(value= "DELETE FROM carro WHERE id = :id", nativeQuery= true)
    void delCar(@Param("id") int id);

    @Query(value= "UPDATE carro SET id_VAGA = :id_vaga WHERE id = :id", nativeQuery= true)
    void updateVaga(@Param("id_vaga") Integer id_vaga, @Param("id") Integer id);
}
