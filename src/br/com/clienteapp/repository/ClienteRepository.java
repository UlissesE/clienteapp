package br.com.clienteapp.repository;

import br.com.clienteapp.model.Cliente;
import br.com.clienteapp.model.ClientePF;
import br.com.clienteapp.model.ClientePJ;
import br.com.clienteapp.model.TipoCliente;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClienteRepository {

    private final List<Cliente> clientes = new ArrayList<>();

    private static final String ARQUIVO_DADOS = "clientes.dat";

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

    // ── UPDATE ──────────────────────────────────────────────────────────────

        // Update é realizado diretamente na instância do objeto.
        // Como a lista armazena referências, alterações feitas na entidade
        // são refletidas automaticamente no repositório.

    // ── DELETE (lógico) ─────────────────────────────────────────────────────

    public boolean desativar(String id) {
        Optional<Cliente> clienteOPT = buscarPorId(id);
        clienteOPT.ifPresent(c -> c.setAtivo(false));
        return clienteOPT.isPresent();
    }

    // ── DELETE (Físico) ─────────────────────────────────────────────────────
        // Remove da lista

    public boolean remover(String id) {
        return clientes.removeIf(c -> c.getId().equals(id));
    }

    // ── CONTAGEM ────────────────────────────────────────────────────────────

    public long contarTotal() { return clientes.size(); }
    public long contarAtivos() { return clientes.stream().filter(Cliente::isAtivo).count(); }
    public long contarPorTipo(TipoCliente tipo) {
        return clientes.stream()
                .filter(c -> c.getTipo() == tipo)
                .count();
    }

    // ── PERSISTÊNCIA ────────────────────────────────────────────────────────

    public void salvarEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(ARQUIVO_DADOS)))) {
            oos.writeObject(clientes);
            System.out.println("Dados salvos em " + ARQUIVO_DADOS);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDeArquivo() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            System.out.println("Nenhum arquivo de dados encontrado. Iniciando com lista vazia...");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(ARQUIVO_DADOS)))) {
            List<Cliente> carregados = (List<Cliente>) ois.readObject();
            clientes.addAll(carregados);
            System.out.println(carregados.size() + " cliente(s) carregado(s) com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

}