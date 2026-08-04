package br.com.clienteapp.service;

import br.com.clienteapp.exception.CpfCnpjJaCadastradoException;
import br.com.clienteapp.model.ClientePF;
import br.com.clienteapp.model.ClientePJ;
import br.com.clienteapp.repository.ClienteRepository;
import br.com.clienteapp.util.Validador;

import java.time.LocalDate;

public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    // ── CADASTRO PF ─────────────────────────────────────────────────────────

    public ClientePF cadastrarPF(
            String nome,
            String email,
            String telefone,
            LocalDate dataNascimento,
            String cpf) {

        Validador.validarNome(nome);
        Validador.validarEmail(email);
        Validador.validarTelefone(telefone);
        Validador.validarCpf(cpf);

        if (repository.buscarPorCpf(cpf).isPresent()) {
            throw new CpfCnpjJaCadastradoException(cpf);
        }
        if (repository.buscarPorEmail(email).isPresent()) {
            throw new CpfCnpjJaCadastradoException("Email já cadastrado: " + email);
        }

        ClientePF cliente = new ClientePF(nome, email, telefone, dataNascimento, cpf);
        repository.salvar(cliente);
        return cliente;
    }

    // ── CADASTRO PJ ─────────────────────────────────────────────────────────

    public ClientePJ cadastrarPJ(
            String nomeContato,
            String email,
            String telefone,
            LocalDate dataAbertura,
            String cnpj,
            String razaoSocial,
            String nomeFantasia) {

        Validador.validarNome(razaoSocial);
        Validador.validarEmail(email);
        Validador.validarTelefone(telefone);
        Validador.valirCnpj(cnpj);
        Validador.validarCampoObrigatorio(razaoSocial, "Razão Social");

        if (repository.buscarPorCnpj(cnpj).isPresent()) {
            throw new CpfCnpjJaCadastradoException(cnpj);
        }

        ClientePJ cliente = new ClientePJ(nomeContato, email, telefone, dataAbertura, cnpj, razaoSocial, nomeFantasia);
        repository.salvar(cliente);
        return cliente;
    }



}
