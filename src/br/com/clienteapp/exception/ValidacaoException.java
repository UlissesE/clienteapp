package br.com.clienteapp.exception;

public class ValidacaoException extends RuntimeException {

    private final String campo;

    public ValidacaoException(String message, String campo) {
        super(message);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
