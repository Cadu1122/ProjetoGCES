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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import Business.Bebida;
import Business.Cliente;
import Business.OpcoesAdicionais;
import Business.OpcoesBebida;
import Business.Pedido;
import Business.Pizza;
import Business.PratoFeito;
import Business.Produto;
import Business.Sanduiche;

public class App {
    public static final File ARQUIVO_ARMAZENAMENTO = new File("historico.dat");
    public static Set<Cliente> clientes = new HashSet<Cliente>();
    public static Scanner teclado = new Scanner(System.in);
    public static int idGenerator = 1;

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
        try {
            if(ARQUIVO_ARMAZENAMENTO.exists()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ARQUIVO_ARMAZENAMENTO));
                clientes = (Set<Cliente>) objectInputStream.readObject();
                idGenerator = clientes.stream()
                    .mapToInt(a -> a.getPedidos().stream()
                        .mapToInt(b -> b.getId())
                        .max()
                        .orElse(0))
                    .max()
                    .orElse(0) + 1;
                objectInputStream.close();
            }
        } catch (SecurityException e) {
            System.err.println("O acesso ao arquivo foi negado");
            esperar();
        } catch (OptionalDataException e) {
            System.err.println("Os dados foram armazenados de forma incorreta no ultimo salvamento");
            esperar();
        } catch (StreamCorruptedException e) {
            System.err.println("Ocorreu um erro ao ler os arquivos");
            esperar();
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao acessar os arquivos");
            esperar();
        } catch (Exception e) {
            System.err.println("Um erro inesperado ocorreu");
            esperar();
        }
    }

    /**
     * Interface principal da apilcação
     */
    public static void menu() {
        int comando = 1;
        do {
            limparTela();
            System.out.println("---------------------------------------------");
            System.out.println("Selecione a operação a ser executada");
            System.out.println("---------------------------------------------");
            System.out.println("0 - Salvar dados e sair");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Mostrar lista de clientes");
            System.out.println("3 - Realizar pedido");
            System.out.println("4 - Gerar extrato resumido dos pedidos de um cliente");
            System.out.println("5 - Gerar extrato detalhado de um dos pedidos de um cliente");
            System.out.println("6 - Mostrar avaliação média de um cliente");
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
                    case 3:
                        cadastrarPedido();
                        break;
                    case 4:
                        mostrarPedidos();
                        break;
                    case 5:
                        extratoDetalhado();
                        break;
                    case 6:
                        avaliacaoMedia();
                        break;
                    default:
                        System.err.println("O comando inserido não é reconhecido");
                }
            } catch (InputMismatchException e) {
                System.err.println("O comando inserido não é reconhecido");
                esperar();
            } catch (NoSuchElementException e) {
                System.err.println("O comando não foi encontrado");
                esperar();
            } catch (Exception e) {
                System.err.println("Um erro inesperado ocorreu");
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
            .reduce((a, b) -> a.concat("\n" + b))
            .ifPresent(a -> System.out.println(a));
        esperar();
    }

    /**
     * Cadastra pedido associado a um cliente
     */
    public static void cadastrarPedido() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Realizar pedido");
        System.out.println("---------------------------------------------");
        Cliente cliente = localizarCliente();
        int qtdPedidosFaltantes = 10;
        if(cliente != null) {
            int numPedido = 0;
            Pedido pedido = null;
            Produto aAdicionar = null;
            do {
                limparTela();
                System.out.println("Insira o número do próximo pedido. É possível fazer mais " + qtdPedidosFaltantes + " pedidos");
                System.out.println("0 - Parar de inserir produtos");
                System.out.println("1 - Prato Feito");
                System.out.println("2 - Bebida");
                System.out.println("3 - Pizza");
                System.out.println("4 - Sanduiche");
                numPedido = teclado.nextInt();
                teclado.nextLine();
                switch (numPedido) {
                    case 0:
                        if(pedido == null) {
                            System.err.println("Nenhum produto foi inserido. O pedido não será criado.");
                        } else {
                            aAdicionar = null;
                        }
                        break;
                    case 1:
                        aAdicionar = new PratoFeito();
                        break;
                    case 2:
                        aAdicionar = adicionarBebida();
                        break;
                    case 3:
                        aAdicionar = adicionarPizza();
                        break;
                    case 4:
                        aAdicionar = adicionarSanduiche();
                        break;
                    default:
                        System.err.println("Essa opção não existe");
                        esperar();
                }
                if (aAdicionar != null) {
                    if(pedido == null) {
                        pedido = new Pedido(idGenerator++, LocalDate.now(), aAdicionar);
                    } else {
                        pedido.addProduto(aAdicionar);
                    }
                }
                qtdPedidosFaltantes--;
            } while(numPedido != 0 && qtdPedidosFaltantes > 0);
            if(pedido != null) {
                cliente.addPedido(pedido);
                gerarNotaFiscal(pedido);
                avaliarPedido(pedido);
            }
        }
    }

    /**
     * Permite a seleção de uma bebida para incluir ao pedido
     * @return Bebida selecionada
     */
    public static Bebida adicionarBebida() {
        int opcao = 0;
        Bebida bebida = null;
        do {
            limparTela();
            System.out.println("---------------------------------------------");
            System.out.println("Insira o tipo de bebida");
            System.out.println("1 - Refrigerante");
            System.out.println("2 - Suco");
            System.out.println("3 - Água");
            System.out.println("4 - Cerveja");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1:
                    bebida = new Bebida(OpcoesBebida.REFRIGERANTE);
                    break;
                case 2:
                    bebida = new Bebida(OpcoesBebida.SUCO);
                    break;
                case 3:
                    bebida = new Bebida(OpcoesBebida.AGUA);
                    break;
                case 4:
                    bebida = new Bebida(OpcoesBebida.CERVEJA);
                    break;
                default:
                    System.err.println("Essa opção não existe");
                    limparTela();
            }
        } while (opcao < 1 || opcao > 4);
        return bebida;
    }

    /**
     * Permite a montagem de uma pizza para incluir ao pedido
     * @return Pizza montada
     */
    public static Pizza adicionarPizza() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Insira o tipo de pizza");
        System.out.println("1 - Sem borda recheada");
        System.out.println("2 - Com borda recheada");
        int opcao = 0;
        do {
            opcao = teclado.nextInt();
            teclado.nextLine();
            if(opcao < 0 || opcao > 2) {
                System.err.println("Essa opção não existe");
                esperar();
            }
            limparTela();
        } while (opcao < 0 || opcao > 2);
        Pizza pizza  = new Pizza(opcao == 2);
        OpcoesAdicionais opcoesAdicionais = null;
        do {
            opcoesAdicionais = adicionarAdicionais();
            if(opcoesAdicionais != null) {
                pizza.addAdicional(opcoesAdicionais);
            }
        } while(opcoesAdicionais != null);
        return pizza;
    } 

    /**
     * Permite a montagem de um sanduiche para incluir ao pedido
     * @return Sanduiche montado
     */
    public static Sanduiche adicionarSanduiche() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Insira o tipo de sanduíche");
        System.out.println("1 - Sem pão artesanal");
        System.out.println("2 - Com pão artesanal");
        int opcao = 0;
        do {
            opcao = teclado.nextInt();
            teclado.nextLine();
            if(opcao < 0 || opcao > 2) {
                System.err.println("Essa opção não existe");
                esperar();
            }
            limparTela();
        } while (opcao < 0 || opcao > 2);
        Sanduiche sanduiche  = new Sanduiche(opcao == 2);
        OpcoesAdicionais opcoesAdicionais = null;
        do {
            opcoesAdicionais = adicionarAdicionais();
            if(opcoesAdicionais != null) {
                sanduiche.addAdicional(opcoesAdicionais);
            }
        } while(opcoesAdicionais != null);
        return sanduiche;
    } 

    /**
     * Permite a seleção de um adicional para incluir a um produto
     * @return Adicional selecionado
     */
    public static OpcoesAdicionais adicionarAdicionais() {
        int opcao = 0;
        OpcoesAdicionais opcoesAdicionais = null;
        do {
            limparTela();
            System.out.println("---------------------------------------------");
            System.out.println("Insira os adicionais que deseja");
            System.out.println("0 - Parar de pedir adicionais");
            System.out.println("1 - Pepperoni");
            System.out.println("2 - Bacon");
            System.out.println("3 - Palmito");
            System.out.println("4 - Queijo");
            System.out.println("5 - Picles");
            System.out.println("6 - Ovo");
            System.out.println("7 - Batata palha");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 0:
                    break;
                case 1:
                    opcoesAdicionais = OpcoesAdicionais.PEPPERONI;
                    break;
                case 2:
                    opcoesAdicionais = OpcoesAdicionais.BACON;
                    break;
                case 3:
                    opcoesAdicionais = OpcoesAdicionais.PALMITO;
                    break;
                case 4:
                    opcoesAdicionais = OpcoesAdicionais.QUEIJO;
                    break;
                case 5:
                    opcoesAdicionais = OpcoesAdicionais.PICLES;
                    break;
                case 6:
                    opcoesAdicionais = OpcoesAdicionais.OVO;
                    break;
                case 7:
                    opcoesAdicionais = OpcoesAdicionais.BATATA_PALHA;
                    break;
                default:
                    System.err.println("Essa opção não existe");
                    esperar();
            }
        } while (opcao < 0 || opcao > 7);
        return opcoesAdicionais;
    }

    /**
     * Gera a nota fiscal, incluindo o preço individual de cada produto, o número do pedido, 
     * a data de realização, o valor total e o valor a ser pago pelo cliente
     */
    public static void gerarNotaFiscal(Pedido pedido) {
        System.out.println("---------------------------------------------");
        System.out.println(pedido);
        esperar();
        limparTela();
    }

    /**
     * Permite avaliar um pedido específico
     * @param pedido Pedido a ser avaliado
     */
    public static void avaliarPedido(Pedido pedido) {
        int avaliacao = 0;
        do {
            limparTela();
            System.out.println("Digite quantas estrelas, de 0 a 5, será dada a esse pedido");
            avaliacao = teclado.nextInt();
            teclado.nextLine();
            if (avaliacao >= 0 && avaliacao <= 5) {
                pedido.setAvaliacao(avaliacao);
                System.out.println("Obrigado pela avaliação");
            } else {
                System.err.println("Esse valor não é suportada");
            }
            esperar();
        } while (avaliacao < 0 || avaliacao > 5);
    }

    /**
     * Gera um extrato simples de todos os pedidos de um determinado cliente
     */
    public static void mostrarPedidos() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Gerar extrato resumido dos pedidos de um cliente");
        System.out.println("---------------------------------------------");
        Cliente cliente = localizarCliente();
        if(cliente !=null) {
            System.out.println("---------------------------------------------");
            cliente.getPedidos().stream()
                .map(p -> p.extratoSimples())
                .reduce((a, b) -> a.concat("\n---------------------------------------------\n" + b))
                .ifPresent(a -> System.out.println(a));
            esperar();
        }
    }

    /**
     * Gera um extrato detalhado de um dos pedidos de um cliente
     */
    public static void extratoDetalhado() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Gerar extrato detalhado de um dos pedidos de um cliente");
        System.out.println("---------------------------------------------");
        Cliente cliente = localizarCliente();
        if(cliente != null) {
            limparTela();
            Set<Pedido> pedidos = cliente.getPedidos();
            System.out.println("Digite o número do pedido que deseja ver o extrato");
            int id = teclado.nextInt();
            teclado.nextLine();
            System.out.println("---------------------------------------------");
            pedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .ifPresentOrElse(p -> System.out.println(p), () -> System.out.println("Pedido não encontrado"));
            esperar();
        }
    }

    /**
     * Mostra as avaliações médias de um determinado cliente
     */
    public static void avaliacaoMedia() {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar avaliação média de um cliente");
        System.out.println("---------------------------------------------");
        Cliente cliente = localizarCliente();
        if(cliente != null) {
            System.out.println("---------------------------------------------");
            System.out.println("Avaliação média: " + cliente.mediaAvaliacoes());
            esperar();
        }
    }

    /**
     * Localiza o cliente pelo seu cpf
     * @return Cliente selecionado
     */
    public static Cliente localizarCliente() {
        System.out.println("Insira o cpf do cliente");
        String cpf = teclado.nextLine();
        Cliente cliente = clientes.stream()
            .filter(a -> a.getCpf().equals(cpf))
            .findFirst()
            .orElse(null);
        if (cliente == null) {
            System.out.println("Cliente não encontrado");
            esperar();
        }
        return cliente;
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
            esperar();
        } catch (StreamCorruptedException e) {
            System.err.println("Ocorreu um erro ao salvar os arquivos.");
            esperar();
        } catch (NotSerializableException e) {
            System.err.println("Não foi possivel tranferir os dados para o arquivo.");
            esperar();
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao acessar os arquivos.");
            esperar();
        } catch (Exception e) {
            System.err.println("Um erro inesperado ocorreu.");
            esperar();
        }
    }
}
