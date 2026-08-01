package br.com.clienteapp.util;

import br.com.clienteapp.exception.ValidacaoException;

public class Validador {

    public Validador() {
    }

    public static void validarNome(String nome) {
        if ((nome == null) || nome.isBlank()) {
            throw new ValidacaoException("nome", "Nome não pode estar vazio.");
        }
        if (nome.trim().length() < 3) {
            throw new ValidacaoException("nome", "Nome deve ter ao menos 3 caracteres.");
        }
    }

    public static void validarEmail(String email) {
        if ((email == null) || email.isBlank()) {
            throw new ValidacaoException("email", "Email não pode estar vazio.");
        }
        // Regex simples. Não é perfeito, mas cobre os casos comuns
        if (!email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new ValidacaoException("email", "Email inválido: " + email);
        }
    }

    public static void validarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new ValidacaoException("telefone", "Telefone não pode estar vazio.");
        }
        // Remove formatação e valida só os dígitos
        String digitos = telefone.replaceAll("[^0-9]", "");
        if (digitos.length() < 10 || digitos.length() > 11) {
            throw new ValidacaoException("telefone", "Telefone inválido. Use o formato (XX) XXXXX-XXXX.");
        }
    }

    public static void validarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new ValidacaoException("cpf", "O CPF não pode estar vazio.");
        }
        String digitos = cpf.replaceAll("[^0-9]", "");
        if (digitos.length() != 11) {
            throw new ValidacaoException("cpf", "CPF deve ter 11 dígitos.");
        }
        // Rejeita CPFs com todos os dígitos iguais (ex: 111.111.111-11)
        if (digitos.chars().distinct().count() == 1) {
            throw new ValidacaoException("cpf", "CPF Inválido");
        }
    }

    public static void valirCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new ValidacaoException("cnpj", "O CNPJ não pode estar vazio.");
        }
        String digitos = cnpj.replaceAll("[^0-9]", "");
        if (digitos.length() != 14) {
            throw new ValidacaoException("cnpj", "CNPJ deve ter 14 dígitos.");
        }
    }

    public static void validarCampoObrigatorio(String valor, String nomeDoCampo) {
        if (valor == null || valor.isBlank()) {
            throw new ValidacaoException(nomeDoCampo, nomeDoCampo + " é obrigatório.");
        }
    }
}
