package br.com.example.wallpark.repositorio;

public enum UsrRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    public String getRole() {
        return role;
    }

    UsrRole(String role) {
        this.role = role;
    }

}
