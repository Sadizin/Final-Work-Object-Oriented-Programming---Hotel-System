package main.trivago.userInterface;

import main.trivago.entities.Cliente;
import main.trivago.entities.Hotel;
import main.trivago.entities.Quarto;
import main.trivago.entities.QuartoSuite;

import java.util.Scanner;

public class Menu {
    private Scanner sc;
    Hotel hotel;

    public Menu(Hotel hotel) {
        this.hotel = hotel;
        this.setScanner(new Scanner(System.in));
    }
    public void setScanner(Scanner newScanner){
        this.sc = newScanner;
    }


    public void showMenu() {

        while (true) {
            int opt;
            System.out.println("*************- T R I V A G O -************");
            System.out.println(" ________________________________________ ");
            System.out.println("|                                        | ");
            System.out.println("| 1 |   Cadastrar um novo cliente        |");
            System.out.println("| 2 |   Listar Clientes presentes        |");
            System.out.println("| 3 |   Listar Quartos                   |");
            System.out.println("| 4 |   Checkout                         |");
            System.out.println("| 5 |   Sair                             |");
            System.out.println("|________________________________________|");
            System.out.print("Digite o número da opção: ");
            opt = this.sc.nextInt();
            this.sc.nextLine();

            switch (opt) {
                case 1:
                    this.checkin();
                    break;

                case 2:
                    this.listarClientes();
                    break;

                case 3:
                    this.listarQuartos();
                    break;

                case 4:
                    this.checkoutMenu();
                    break;

                case 5:
                    return;

            }
        }
    }


    public void checkoutMenu() {
        System.out.println("Digite o número do quarto: ");
        int numeroQuarto = this.sc.nextInt();

        if (this.hotel.isQuartoLivre(numeroQuarto)) {
            System.out.println("Quarto vazio");
            return;
        }

        System.out.println("Digite o tempo da estadia (em dias): ");
        int horas = this.sc.nextInt();

        System.out.println("O valor total da estadia é: " + this.hotel.checkoutCliente(numeroQuarto, horas));
        System.out.println("O checkout foi realizado. Volte sempre!");
    }


    public void listarClientes() {
        for (Quarto quarto : this.hotel.getQuartos()) {
            if (quarto.getCliente() != null) {
                System.out.println("#############################################");
                this.imprimirInformacoesQuarto(quarto);
                this.imprimirClienteCompleto(quarto.getCliente());
            }
        }
    }

    public void checkin() {

        if (!this.hotel.temVagas()) {
            System.out.println("O hotel está cheio, não é possível realizar cadastro.");
        }

        // Colhendo informa��es do cliente
        System.out.print("Digite o nome do cliente: ");
        String nome = this.sc.nextLine();

        System.out.print("Digite a idade do cliente: ");
        int idade = this.sc.nextInt();

        System.out.print("Digite o CPF do cliente: ");
        String cpf = this.sc.next();

        System.out.print("O cliente possue carro? (S/N) ");
        char carro = this.sc.next().charAt(0);

        System.out.print("O cliente possue familiares?(S/N) ");
        char familia = this.sc.next().charAt(0);

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCarro(carro == 'S');
        cliente.setCpf(cpf);
        cliente.setFamilia(familia == 'S');
        cliente.setIdade(idade);


        do {
            System.out.print("Digite o número do quarto: ");
            int numeroQuarto = this.sc.nextInt();
            if (this.hotel.isQuartoLivre(numeroQuarto)) {
                this.hotel.checkinCliente(cliente, numeroQuarto);
                break;
            } else {
                System.out.println("Quarto ocupado, digite outro número.");
            }
        } while (true);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void listarQuartos() {
        for (Quarto quarto : this.hotel.getQuartos()) {
            if (quarto.getClass() == QuartoSuite.class) {
                System.out.print("Suite  ");
            } else {
                System.out.print("Quarto ");
            }
            System.out.print("N: " + quarto.getNumero());
            if (quarto.isOcupado()) {
                System.out.println(" Ocupado");
            } else {
                System.out.println(" Disponível");
            }
        }
        System.out.println("Vagas Estacionamento: " + this.hotel.getEstacionamento().getVagasLivres());
    }

    public void imprimirClienteCompleto(Cliente cliente) {
        System.out.println("========Dados do cliente==========");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Idade: " + cliente.getIdade());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Possui carro? " + (cliente.hasCarro() ? "Sim" : "Não"));
        System.out.println("Possui familiares? " + (cliente.hasFamilia() ? "Sim" : "Não"));
    }

    public void imprimirInformacoesQuarto(Quarto quarto) {
        System.out.print("N " + quarto.getNumero());
        System.out.print(" R$" + String.format("%.2f ", quarto.getValorDiaria()));
        if (quarto.getClass() == QuartoSuite.class) {
            System.out.print("Suite ");
        }
        System.out.println(quarto.isOcupado() ? "Ocupado" : "Disponível");

    }
}
