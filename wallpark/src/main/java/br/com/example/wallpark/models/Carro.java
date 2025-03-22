package br.com.example.wallpark.models;

import br.com.example.wallpark.utils.Porte;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "placa")
    private String placa;
    private String modelo;
    @Enumerated(EnumType.STRING)
    private Porte porte;
    
    @OneToOne
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Vaga getVaga() {
        return vaga;
    }
    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }
    private Double total;
    
    
    
    public Porte getPorte() {
        return porte;
    }
    public void setPorte(Porte porte) {
        this.porte = porte;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    private int ano;
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    
}
