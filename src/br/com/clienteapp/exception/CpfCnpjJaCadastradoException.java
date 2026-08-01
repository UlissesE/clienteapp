package br.com.clienteapp.exception;

public class CpfCnpjJaCadastradoException extends RuntimeException {
    public CpfCnpjJaCadastradoException(String documento) {
        super("Documento já cadastrado no sistema: " + documento);
    }
}
