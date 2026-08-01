package br.com.clienteapp.repository;

import br.com.clienteapp.model.Cliente;
import br.com.clienteapp.model.ClientePF;
import br.com.clienteapp.model.ClientePJ;
import br.com.clienteapp.model.TipoCliente;

import java.util.*;
import java.util.stream.Collectors;

public class ClienteRepository {

    private final List<Cliente> clientes = new ArrayList<>();

    // ── CREATE ──────────────────────────────────────────────────────────────
    public void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    // ── READ ────────────────────────────────────────────────────────────────
    public Optional<Cliente> buscarPorId(String id) {
        return clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clientes.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Optional<ClientePF> buscarPorCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return clientes.stream()
                .filter(c -> c instanceof ClientePF)
                .map(c -> (ClientePF) c)
                .filter(pf -> pf.getCpf().replaceAll("[^0-9]", "").equals(cpfLimpo))
                .findFirst();
    }

    public Optional<ClientePJ> buscarPorCnpj(String cnpj) {
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");
        return clientes.stream()
                .filter(c -> c instanceof ClientePJ)
                .map(c -> (ClientePJ) c)
                .filter(pj -> pj.getCnpj().replaceAll("[^0-9]", "").equals(cnpjLimpo))
                .findFirst();
    }

    public List<Cliente> buscarPorNome(String nome) {
        String nomeLower = nome.toLowerCase();
        return clientes.stream()
                .filter(c -> c.getNome().toLowerCase().contains(nomeLower))
                .collect(Collectors.toList());
    }

    public List<Cliente> listarTodos() {
        // retorna uma cópia imutável da lista, protegendo contra modificação externa
        return Collections.unmodifiableList(clientes);
    }

    public List<Cliente> listarAtivos() {
        return clientes.stream()
                .filter(Cliente::isAtivo)
                .collect(Collectors.toList());
    }

    public List<Cliente> listarPorTipo(TipoCliente tipo) {
        return clientes.stream()
                .filter(c -> c.getTipo().equals(tipo))
                .collect(Collectors.toList());
    }

}
