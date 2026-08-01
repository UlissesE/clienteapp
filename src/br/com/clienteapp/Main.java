package br.com.clienteapp;

import br.com.clienteapp.model.Cliente;
import br.com.clienteapp.model.ClientePF;
import br.com.clienteapp.model.ClientePJ;
import br.com.clienteapp.model.TipoCliente;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Cliente pf = new ClientePF(
                "Eduardo Ulisses",
                "eduardo@email.com",
                "(11) 99999-0001",
                LocalDate.of(2006, 8, 27),
                "123.456.789-00"
        );

        Cliente pj = new ClientePJ(
                "contato@techcorp.com",
                "contato@techcorp.com",
                "(11) 3333-0001",
                LocalDate.of(2010, 3, 20),
                "12.345.678/0001-99",
                "Tech Corp Ltda",
                "TechCorp"
        );

        System.out.println(pf.getResumo());
        System.out.println(pj.getResumo());

        System.out.println("\nTipo do pf: " + pf.getTipo());
        System.out.println("É PF? " + (pf.getTipo() == TipoCliente.PESSOA_FISICA));

        if (pf instanceof ClientePF clientePF) {
            System.out.println("CPF: " + clientePF.getCpf());
        }
    }
}
