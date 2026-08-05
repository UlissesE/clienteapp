package br.com.clienteapp.ui;

import br.com.clienteapp.exception.ClienteNaoEncontradoException;
import br.com.clienteapp.exception.CpfCnpjJaCadastradoException;
import br.com.clienteapp.exception.ValidacaoException;
import br.com.clienteapp.model.Cliente;
import br.com.clienteapp.model.ClientePF;
import br.com.clienteapp.model.ClientePJ;
import br.com.clienteapp.service.ClienteService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private final ClienteService service;
    private final Scanner scanner;
    private static final DateTimeFormatter FMT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyy");

    public Menu(ClienteService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {

        System.out.println("\n════════════════════════════════════════");
        System.out.println("     SISTEMA DE CADASTRO DE CLIENTES    ");
        System.out.println("════════════════════════════════════════\n");

        boolean executando = true;

        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarClientePF();
                case 2 -> cadastrarClientePJ();
                case 3 -> listarClientes();
                case 4 -> pesquisarPorNome();
                case 5 -> buscarPorId();
                case 6 -> atualizarContato();
                case 7 -> desativarCliente();
                case 8 -> exibirEstatisticas();
                case 0 -> {
                    System.out.println("\n👋 Encerrando programa...");
                    executando = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente");
            }
        }
        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│         MENU PRINCIPAL           │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│  1. Cadastrar Cliente PF         │");
        System.out.println("│  2. Cadastrar Cliente PJ         │");
        System.out.println("│  3. Listar todos os clientes     │");
        System.out.println("│  4. Pesquisar por nome           │");
        System.out.println("│  5. Buscar por ID                │");
        System.out.println("│  6. Atualizar contato            │");
        System.out.println("│  7. Desativar cliente            │");
        System.out.println("│  8. Estatísticas                 │");
        System.out.println("│  0. Sair e salvar                │");
        System.out.println("└─────────────────────────────────┘");
    }

    private int lerInteiro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private String lerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String lerTextoOpcional(String prompt) {
        System.out.print(prompt);
        String linha = scanner.nextLine().trim();
        return linha.isEmpty() ? null : linha;
    }

    private LocalDate lerData(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine().trim(), FMT_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/MM/yyy");
            }
        }
    }

    // ── CADASTRO PF ─────────────────────────────────────────────────────────

    public void cadastrarClientePF() {
        System.out.println("\n── CADASTRAR PESSOA FÍSICA ──");
        try {
            String nome = lerTexto("Nome completo: ");
            String email = lerTexto("Email: ");
            String telefone = lerTexto("Telefone (ex.: (11) 99999-0000): ");
            String cpf = lerTexto("CPF (ex: 123.456.789-00): ");
            LocalDate dataNascimento = lerData("Data de nascimento (dd/MM/yyy): ");

            ClientePF cliente = service.cadastrarPF(nome, email, telefone, dataNascimento, cpf);
            System.out.println("\nCliente cadastrado com sucesso!!!");
            System.out.println("   ID: " + cliente.getId());
            System.out.println("   " + cliente.getResumo());
        } catch (ValidacaoException e) {
            System.out.println("Erro na validação do campo [" + e.getCampo() + "]: " + e.getMessage());
        } catch (CpfCnpjJaCadastradoException e) {
            System.out.println(e.getMessage());
        }
    }

    // ── CADASTRO PJ ─────────────────────────────────────────────────────────

    public void cadastrarClientePJ() {
        System.out.println("\n── CADASTRAR PESSOA JURÍDICA ──");
        try {
            String razao = lerTexto("Razão Social: ");
            String nomeFantasia = lerTexto("Nome Fantasia: ");
            String cnpj = lerTexto("CNPJ (ex: 12.345.678/0001-99): ");
            String email = lerTexto("Email: ");
            String telefone = lerTexto("Telefone (ex.: (11) 99999-0000): ");
            String contato = lerTexto("Nome de contato: ");
            LocalDate dataAbertura = lerData("Data de abertura (dd/MM/yyy): ");

            ClientePJ cliente = service.cadastrarPJ(contato, email, telefone, dataAbertura, cnpj, razao, nomeFantasia);
            System.out.println("\nEmpresa cadastrada com sucesso!!!");
            System.out.println("   ID: " + cliente.getId());
            System.out.println("   " + cliente.getResumo());
        } catch (ValidacaoException e) {
            System.out.println("Erro na validação do campo [" + e.getCampo() + "]: " + e.getMessage());
        } catch (CpfCnpjJaCadastradoException e) {
            System.out.println(e.getMessage());
        }
    }

    // ── LISTAGEM ─────────────────────────────────────────────────────────────

    private void listarClientes() {
        List<Cliente> clientes = service.listarAtivos();
        if (clientes.isEmpty()) {
            System.out.println("\nNenhum cliente ativo cadastrado");
            return;
        }
        System.out.println("\n── CLIENTES ATIVOS (" + clientes.size() + ") ──");
        System.out.println("─".repeat(90));
        clientes.forEach(c -> System.out.println(" " + c.getResumo()));
        System.out.println("─".repeat(90));
    }

    // ── PESQUISA ─────────────────────────────────────────────────────────────

    private void pesquisarPorNome() {
        String termo = lerTexto("\nDigite o nome ou parte do nome: ");
        List<Cliente> resultado = service.pesquisarPorNome(termo);

        if (resultado.isEmpty()) {
            System.out.println("Nenhum cliente encontrado para " + termo);
            return;
        }

        System.out.println("\n── RESULTADO (" + resultado.size() + ") ──");
        resultado.forEach(c -> {
            System.out.println("   ID: " + c.getId());
            System.out.println("   " + c.getResumo());
            System.out.println("   " + "·".repeat(80));
        });
    }

    // ── BUSCA POR ID ─────────────────────────────────────────────────────────

    private void buscarPorId() {
        String id = lerTexto("\nDigite o ID do cliente: ");
        try {
            Cliente c = service.buscarPorId(id); System.out.println("\n── CLIENTE ENCONTRADO ──");
            System.out.println("  Nome:     " + c.getNome());
            System.out.println("  Tipo:     " + c.getTipo().getDescricao());
            System.out.println("  Documento:" + c.getDocumento());
            System.out.println("  Email:    " + c.getEmail());
            System.out.println("  Telefone: " + c.getTelefone());
            System.out.println("  Ativo:    " + (c.isAtivo() ? "Sim" : "Não"));
            System.out.println("  Cadastro: " + c.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } catch (ClienteNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

    }

    // ── ATUALIZAÇÃO ──────────────────────────────────────────────────────────

    private void atualizarContato() {
        String id = lerTexto("\nID do cliente a atualizar: ");
        try {
            Cliente atual = service.buscarPorId(id);
            System.out.println("Atualizando: " + atual.getNome());
            System.out.println("(Pressione ENTER para manter valor atual)");

            String novoEmail = lerTextoOpcional("Novo email [" + atual.getEmail() + "]: ");
            String novoTelefone = lerTextoOpcional("Novo telefone [" + atual.getTelefone() + "]: ");

            service.atualizarContato(id, novoEmail, novoTelefone);
            System.out.println("Dados atualizados com sucesso!");
        } catch (ClienteNaoEncontradoException | ValidacaoException e) {
            System.out.println(e.getMessage());
        }
    }

    // ── DESATIVAR ────────────────────────────────────────────────────────────

    private void desativarCliente() {
        String id = lerTexto("\nID do cliente a desativar: ");
        System.out.print("Tem certeza? (s/n): ");
        String confirmacao = scanner.nextLine().trim();
        if (!confirmacao.equalsIgnoreCase("s")) {
            System.out.println("Operação concelada.");
            return;
        }
        try {
            service.desativarCliente(id);
            System.out.println("Cliente desativado com sucesso!");
        } catch (ClienteNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    // ── ESTATÍSTICAS ─────────────────────────────────────────────────────────

    private void exibirEstatisticas() {
        Map<String, Long> stats = service.estatisticas();
        System.out.println("\n── ESTATÍSTICAS DO SISTEMA ──");
        System.out.println("─".repeat(40));
        stats.forEach((chave, valor) ->
                System.out.printf("  %-25s %d%n", chave + ":", valor));
        System.out.println("─".repeat(40));
    }
}
