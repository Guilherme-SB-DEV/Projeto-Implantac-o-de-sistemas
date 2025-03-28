package br.com.example.wallpark.controllers.pagamento.dtos;

import br.com.example.wallpark.models.Carro;

public record PagamentoResponse(Carro carro, Double preco) {
    
}
