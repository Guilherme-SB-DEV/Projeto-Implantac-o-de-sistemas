package br.com.example.wallpark.controllers.edits.dtos;

public record EditRequest(
        Integer id, // id do item editado
        String value, // valor a ser atribuido
        String field) { // campo a ser editado
}
