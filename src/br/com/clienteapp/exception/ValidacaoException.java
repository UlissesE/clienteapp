package br.com.clienteapp.exception;

public class ValidacaoException extends RuntimeException {

    private final String campo;

    public ValidacaoException(String campo, String message) {
        super(message);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
