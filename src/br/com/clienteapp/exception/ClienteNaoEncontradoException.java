package br.com.clienteapp.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException (String identificador) {
        super("Cliente não encontrado: " + identificador);
    }

}
