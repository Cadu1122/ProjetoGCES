package App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import Business.Cliente;
import Business.Produto;

public class App {
    public static final File ARQUIVO_ARMAZENAMENTO = new File("historico.dat");
    public static List<Cliente> clientes = new LinkedList<Cliente>();
    public static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        recuperarClientes();
        menu();
        armazenarClientes();
    }

    /**
     * Carrega os clientes do banco de dados
     */
    @SuppressWarnings("unchecked")
    public static void recuperarClientes() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ARQUIVO_ARMAZENAMENTO))) {
            if(ARQUIVO_ARMAZENAMENTO.exists()) {
                clientes = (List<Cliente>) objectInputStream.readObject();
            }
        } catch (SecurityException e) {
            System.err.println("O acesso ao arquivo foi negado.");
        } catch (OptionalDataException e) {
            System.err.println("Os dados foram armazenados de forma incorreta no ultimo salvamento");
        } catch (StreamCorruptedException e) {
            System.err.println("Ocorreu um erro ao ler os arquivos.");
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao acessar os arquivos.");
        } catch (Exception e) {
            System.err.println("Um erro inesperado ocorreu.");
        }
    }

    /**
     * Interface principal da apilcação
     */
    public static void menu() {
        int comando = 1;
        limparTela();
        do {
            System.out.println("---------------------------------------------");
            System.out.println("Selecione a operação a ser executada");
            System.out.println("---------------------------------------------");
            System.out.println("0 - Salvar dados e sair");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Mostrar lista de clientes");
            try {
                comando = teclado.nextInt();
                teclado.nextLine();
                switch (comando) {
                    case 0:
                        break;
                    case 1:
                        cadastrarCliente();
                        break;
                    case 2:
                        mostrarClientes();
                        break;
                    default:
                        System.err.println("O comando inserido não é reconhecido.");
                }
            } catch (InputMismatchException e) {
                System.err.println("O comando inserido não é reconhecido.");
                esperar();
            } catch (NoSuchElementException e) {
                System.err.println("O comando não foi encontrado");
                esperar();
            } catch (Exception e) {
                System.err.println("Um erro inesperado ocorreu.");
                esperar();
            }
        } while (comando != 0);
    }

    /**
     * Permite cadastrar clientes no sistema
     */
    public static void cadastrarCliente() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Cadastrar cliente");
        System.out.println("---------------------------------------------");
        String campo = null;
        try {
            System.out.println("Digite o nome do cliente");
            campo = "nome";
            String nome = teclado.nextLine();
            System.out.println("Digite o CPF do cliente");
            campo = "CPF";
            String cpf = teclado.nextLine();
            clientes.add(new Cliente(nome, cpf));
            System.out.println("---------------------------------------------");
            System.out.println("Cliente salvo com sucesso");
        } catch (NoSuchElementException e) {
            System.err.println("O campo " + campo + " não foi encontrado.");
        } catch (Exception e) {
            System.err.println("Um erro inesperado ocorreu.");
        }
        esperar();
        limparTela();
    }

    /**
     * Imprime na tela todos os clientes cadastrados
     */
    public static void mostrarClientes() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar lista de clientes");
        System.out.println("---------------------------------------------");
        clientes.stream()
            .map(a -> a.toString())
            .reduce((a, b) -> a.concat("/n" + b))
            .ifPresent(a -> System.out.println(a));
        esperar();
        limparTela();
    }

    public static void cadastrarPedido() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Cadastrar pedido");
        System.out.println("---------------------------------------------");
        int numPedido = 0;
        do {
            List<Produto> produtos = new LinkedList<Produto>();
            System.out.println("Insira o número do próximo pedido");
            System.out.println("0 - Parar de inserir produtos");
            System.out.println("1 - Prato Feito");
            System.out.println("2 - Refrigerante");
            System.out.println("3 - Suco");
            numPedido = teclado.nextInt();
            teclado.nextLine();
            switch (numPedido) {
                case 0:
                    break;
                case 1:
            }
        } while(numPedido != 0);
    }

    /**
     * Dá ao usuário tempo para ler a mensagem na tela
     */
    public static void esperar() {
        System.out.println("---------------------------------------------");
        System.out.println("Pressione enter para continuar");
        teclado.nextLine();
    }

    /**
     * Limpa o console
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Salva clientes no banco de dados
     */
    public static void armazenarClientes() {
        try {
            if(!ARQUIVO_ARMAZENAMENTO.exists()) {
                ARQUIVO_ARMAZENAMENTO.createNewFile();
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ARQUIVO_ARMAZENAMENTO));
            objectOutputStream.writeObject(clientes);
            objectOutputStream.close();
        } catch (SecurityException e) {
            System.err.println("O acesso ao arquivo foi negado.");
        } catch (StreamCorruptedException e) {
            System.err.println("Ocorreu um erro ao salvar os arquivos.");
        } catch (NotSerializableException e) {
            System.err.println("Não foi possivel tranferir os dados para o arquivo.");
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao acessar os arquivos.");
        } catch (Exception e) {
            System.err.println("Um erro inesperado ocorreu.");
        }
    }
}
