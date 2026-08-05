package br.com.clienteapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private final TipoCliente tipo;
    private boolean ativo;
    private final LocalDateTime dataCadastro;

    public Cliente(String nome, String email, String telefone, LocalDate dataNascimento, TipoCliente tipo) {
        this.id = UUID.randomUUID().toString(); // ID único automático
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.tipo = tipo;
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
    }

    public abstract String getDocumento();
    public abstract String getResumo();

    // Getters
    public String getId() {return id;}
    public String getNome() {return nome;}
    public String getEmail() {return email;}
    public String getTelefone() {return telefone;}
    public LocalDate getDataNascimento() {return dataNascimento;}
    public TipoCliente getTipo() {return tipo;}
    public boolean isAtivo() {return ativo;}
    public LocalDateTime getDataCadastro() {return dataCadastro;}

    // Setters, apenas para campos que podem mudar
    public void setNome(String nome) {this.nome = nome;}
    public void setEmail(String email) {this.email = email;}
    public void setTelefone(String telefone) {this.telefone = telefone;}
    public void setDataNascimento(LocalDate dataNascimento) {this.dataNascimento = dataNascimento;}
    public void setAtivo(boolean ativo) {this.ativo = ativo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %s | Ativo: %s",
                tipo.getDescricao(), nome, getDocumento(), email, ativo ? "Sim" : "Não");
    }
}
