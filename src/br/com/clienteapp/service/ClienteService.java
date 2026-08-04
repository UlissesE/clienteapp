package br.com.clienteapp.service;

import br.com.clienteapp.exception.ClienteNaoEncontradoException;
import br.com.clienteapp.exception.CpfCnpjJaCadastradoException;
import br.com.clienteapp.model.Cliente;
import br.com.clienteapp.model.ClientePF;
import br.com.clienteapp.model.ClientePJ;
import br.com.clienteapp.repository.ClienteRepository;
import br.com.clienteapp.util.Validador;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    // ── LISTAGENS ────────────────────────────────────────────────────────────

    public List<Cliente> listarTodos() {
        return repository.listarTodos();
    }

    public List<Cliente> listarAtivos() {
        return repository.listarAtivos();
    }

    public List<Cliente> pesquisarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return repository.listarAtivos();
        }
        return repository.buscarPorNome(nome);
    }

    // ── BUSCA ────────────────────────────────────────────────────────────────

    public Cliente buscarPorId(String id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    // ── ATUALIZAÇÃO ──────────────────────────────────────────────────────────

    public Cliente atualizarContato(
            String id, String novoEmail, String novoTelefone) {

        Cliente cliente = buscarPorId(id);

        // Verifica se tem usuário com email cadastrado
        if (novoEmail != null && !novoEmail.isBlank()) {
            Validador.validarEmail(novoEmail);

            Optional<Cliente> comEmail = repository.buscarPorEmail(novoEmail);
            if (comEmail.isPresent() && !comEmail.get().getId().equals(id)) {
                throw new CpfCnpjJaCadastradoException("O email já está em uso. Tente novamente.");
            }
            cliente.setEmail(novoEmail);
        }

        if (novoTelefone != null && novoTelefone.isBlank()) {
            Validador.validarTelefone(novoTelefone);
            cliente.setTelefone(novoTelefone);
        }

        return cliente;
    }

    // ── DESATIVAÇÃO ──────────────────────────────────────────────────────────

    public void desativarCliente(String id) {
        boolean desativado = repository.desativar(id);
        if (!desativado) {
            throw new ClienteNaoEncontradoException(id);
        }
    }

}
