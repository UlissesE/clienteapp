package br.com.clienteapp;

import br.com.clienteapp.repository.ClienteRepository;
import br.com.clienteapp.service.ClienteService;
import br.com.clienteapp.ui.Menu;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ClienteRepository repository = new ClienteRepository();
        ClienteService service       = new ClienteService(repository);
        Menu menu                    = new Menu(service);

        menu.iniciar();
    }
}
