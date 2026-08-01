package br.com.clienteapp.model;

import java.time.LocalDate;

public class ClientePJ extends Cliente {

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;

    public ClientePJ(
            String nome,
            String email,
            String telefone,
            LocalDate dataCadastroEmpresa,
            String cnpj,
            String razaoSocial,
            String nomeFantasia)
    {
        super(nome, email, telefone, dataCadastroEmpresa, TipoCliente.PESSOA_JURIDICA);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
    }

    @Override
    public String getDocumento() {
        return "CNPJ: " + cnpj;
    }

    @Override
    public String getResumo() {
        return String.format("%-30s | CNPJ: %-18s | Fantasia: %-20s | Email: %s", getRazaoSocial(), cnpj, nomeFantasia, getEmail());
    }

    public String getCnpj()                             {return cnpj;}
    public String getRazaoSocial()                      {return razaoSocial;}
    public String getNomeFantasia()                     {return nomeFantasia;}
    public void setCnpj(String cnpj)                    {this.cnpj = cnpj;}
    public void setRazaoSocial(String razaoSocial)      {this.razaoSocial = razaoSocial;}
    public void setNomeFantasia(String nomeFantasia)    {this.nomeFantasia = nomeFantasia;}
}
