package br.com.clienteapp.model;

import java.time.LocalDate;
import java.time.Period;

public class ClientePF extends Cliente {

    private String cpf;

    public ClientePF(String nome, String email, String telefone, LocalDate dataNascimento, String cpf) {
        super(nome, email, telefone, dataNascimento, TipoCliente.PESSOA_FISICA);
        this.cpf = cpf;
    }

    @Override
    public String getDocumento() {
        return "CPF: " + cpf;
    }

    @Override
    public String getResumo() {
        int idade = Period.between(getDataNascimento(), LocalDate.now()).getYears();
        return String.format("%-30s | CPF: %-19s | Idade: %d anos | Email: %s",
                getNome(), cpf, idade, getEmail());
    }

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

}
