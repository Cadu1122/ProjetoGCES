package ProjetoGCES.App;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import ProjetoGCES.Business.Produtos.Bebida;
import ProjetoGCES.Business.Venda.Cliente;
import ProjetoGCES.Business.Produtos.OpcoesAdicionais;
import ProjetoGCES.Business.Produtos.OpcoesBebida;
import ProjetoGCES.Business.Produtos.OpcoesPratoPersonalizado;
import ProjetoGCES.Business.Venda.Pedido;
import ProjetoGCES.DAO.DAOManager;
import ProjetoGCES.Business.Produtos.Pizza;
import ProjetoGCES.Business.Produtos.PratoFeito;
import ProjetoGCES.Business.Produtos.PratoPersonalizado;
import ProjetoGCES.Business.Produtos.Produto;
import ProjetoGCES.Business.Produtos.Sanduiche;

public class App {
    public static Set<Cliente> clientes;
    public static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        clientes = DAOManager.recuperarClientes();
        menu();
        DAOManager.armazenarClientes(clientes);
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
            System.out.println("6 - Mostrar dados das avaliações de um cliente");
            System.out.println("7 - Mostrar dados dos pedidos de um cliente");
            System.out.println("8 - Mostrar dados dos pedidos de todos os clientes");
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
                        dadosAvaliacao();
                        break;
                    case 7:
                        dadosPedido();
                        break;
                    case 8:
                        dadosGeraisPedido();
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
                System.out.println("5 - Prato personalizado");
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
                    case 5:
                        aAdicionar = adicionarPratoPersonalizado();
                    default:
                        System.err.println("Essa opção não existe");
                        esperar();
                }
                if (aAdicionar != null) {
                    if(pedido == null) {
                        pedido = new Pedido(DAOManager.idGenerator++, LocalDate.now(), aAdicionar);
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
            System.out.println("5 - Água com gás");
            System.out.println("6 - Água tônica");
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
                case 5:
                    bebida = new Bebida(OpcoesBebida.AGUA_COM_GAS);
                    break;
                case 6:
                    bebida = new Bebida(OpcoesBebida.AGUA_TONICA);
                    break;
                default:
                    System.err.println("Essa opção não existe");
                    limparTela();
            }
        } while (opcao < 1 || opcao > 6);
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
        Pizza pizza = new Pizza(opcao == 2);
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

    public static Produto adicionarPratoPersonalizado() {
        OpcoesPratoPersonalizado opcoesPrato;
        PratoPersonalizado pratoPersonalizado = null;
        do {
            opcoesPrato = acrescentarItem();
            if(opcoesPrato != null) {
                if(pratoPersonalizado == null) {
                    pratoPersonalizado = new PratoPersonalizado(opcoesPrato);
                } else {
                    pratoPersonalizado.addComida(opcoesPrato);
                }
            }
        } while(opcoesPrato != null);
        return pratoPersonalizado;
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
            System.out.println("8 - Catupiry");
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
                case 8:
                    opcoesAdicionais = OpcoesAdicionais.CATUPIRY;
                    break;
                default:
                    System.err.println("Essa opção não existe");
                    esperar();
            }
        } while (opcao < 0 || opcao > 8);
        return opcoesAdicionais;
    }

    public static OpcoesPratoPersonalizado acrescentarItem() {
        OpcoesPratoPersonalizado opcaoPrato = null;
        int opcao = 0;
        do {
            limparTela();
            System.out.println("---------------------------------------------");
            System.out.println("Insira as opções que deseja");
            System.out.println("0 - Parar de selecionar");
            System.out.println("1 - Arroz");
            System.out.println("2 - Feijão");
            System.out.println("3 - Batata Frita");
            System.out.println("4 - Salada");
            System.out.println("5 - Macarrão");
            System.out.println("6 - Frango");
            System.out.println("7 - Boi");
            System.out.println("8 - Porco");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 0:
                    break;
                case 1:
                    opcaoPrato = OpcoesPratoPersonalizado.ARROZ;
                    break;
                case 2:
                    opcaoPrato = OpcoesPratoPersonalizado.FEIJAO;
                    break;
                case 3:
                    opcaoPrato = OpcoesPratoPersonalizado.BATATA_FRITA;
                    break;
                case 4:
                    opcaoPrato = OpcoesPratoPersonalizado.SALADA;
                    break;
                case 5:
                    opcaoPrato = OpcoesPratoPersonalizado.MACARRAO;
                    break;
                case 6:
                    opcaoPrato = OpcoesPratoPersonalizado.FRANGO;
                    break;
                case 7:
                    opcaoPrato = OpcoesPratoPersonalizado.BOI;
                    break;
                case 8:
                    opcaoPrato = OpcoesPratoPersonalizado.PORCO;
                    break;
                default:
                    System.err.println("Essa opção não existe");
                    esperar();
            }
        } while (opcao != 0);
        return opcaoPrato;
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
            System.out.println("Digite quantas estrelas, de 0 a 5, serão dadas a esse pedido");
            avaliacao = teclado.nextInt();
            teclado.nextLine();
            if (avaliacao >= 0 && avaliacao <= 5) {
                pedido.setAvaliacao(avaliacao);
                System.out.println("Obrigado pela avaliação");
            } else {
                System.err.println("Esse valor não é suportado");
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

    public static void dadosAvaliacao() {
        int opcao = 0;
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Dados das avaliações do cliente");
        System.out.println("---------------------------------------------");
        Cliente cliente = localizarCliente();
        if(cliente != null) {
            do {
                limparTela();
                System.out.println("---------------------------------------------");
                System.out.println("Dados das avaliações do cliente");
                System.out.println("---------------------------------------------");
                System.out.println("Selecione o dado que deseja acessar para " + cliente);
                System.out.println("0 - Retornar ao menu");
                System.out.println("1 - Mostrar avaliação média de um cliente");
                System.out.println("2 - Mostrar menor avaliação de um cliente");
                System.out.println("3 - Mostrar maior avaliação de um cliente");
                opcao = teclado.nextInt();
                teclado.nextLine();
                switch (opcao) {
                    case 0:
                        break;
                    case 1:
                        avaliacaoMedia(cliente);
                        break;
                    case 2:
                        menorAvaliacao(cliente);
                        break;
                    case 3:
                        maiorAvaliacao(cliente);
                        break;
                    default:
                        System.err.println("Essa opção não existe");
                        esperar();
                }
            } while (opcao != 0);
        } 
    }

    /**
     * Mostra as avaliações médias de um determinado cliente
     */
    public static void avaliacaoMedia(Cliente cliente) {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar avaliação média de um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Avaliação média: " + cliente.mediaAvaliacoes());
        esperar();
    }

    public static void menorAvaliacao(Cliente cliente) {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar menor avaliação de um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Menor avaliação: " + cliente.menorAvaliacao());
        esperar();
    }

    public static void maiorAvaliacao(Cliente cliente) {
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar maior avaliação de um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Maior avaliação: " + cliente.maiorAvaliacao());
        esperar();
    }

    public static void dadosPedido() {
        int opcao = 0;
        limparTela();
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar dados dos pedidos de um cliente");
        System.out.println("---------------------------------------------");
        Cliente cliente = localizarCliente();
        if(cliente != null) {
            do {
                limparTela();
                System.out.println("---------------------------------------------");
                System.out.println("Mostrar dados dos pedidos de um cliente");
                System.out.println("---------------------------------------------");
                System.out.println("Selecione o dado que desenja acessar");
                System.out.println("0 - Retornar ao menu");
                System.out.println("1 - Mostrar pedido mais caro feito por um cliente");
                System.out.println("2 - Mostrar pedido mais barato feito por um cliente");
                System.out.println("3 - Mostrar número de itens do maior pedido feito por um cliente");
                System.out.println("4 - Mostrar número de itens do menor pedido feito por um cliente");
                System.out.println("5 - Mostrar data do pedido mais antigo feito por um cliente");
                System.out.println("6 - Mostrar data do pedido mais recente feito por um cliente");
                System.out.println("7 - Mostrar média de gastos dos pedidos feitos por um cliente");
                System.out.println("8 - Mostrar total de gastos dos pedidos feitos por um cliente");
                System.out.println("9 - Mostrar total de pedidos feitos por um cliente");
                System.out.println("10 - Mostrar total de pedidos avaliados por um cliente");
                switch (opcao) {
                    case 0:
                        break;
                    case 1:
                        pedidoMaisCaro(cliente);
                        break;
                    case 2:
                        pedidoMaisBarato(cliente);
                        break;
                    case 3:
                        maiorPedido(cliente);
                        break;
                    case 4:
                        menorPedido(cliente);
                        break;
                    case 5:
                        pedidoMaisAntigo(cliente);
                        break;
                    case 6:
                        pedidoMaisNovo(cliente);
                        break;
                    case 7:
                        mediaGastos(cliente);
                        break;
                    case 8:
                        totalGastos(cliente);
                        break;
                    case 9:
                        totalPedidos(cliente);
                        break;
                    case 10:
                        totalPedidosAvaliados(cliente);
                        break;
                    default:
                        System.err.println("Essa opção não existe");
                        esperar();
                }
            } while (opcao != 0);
        }
    }

    public static void pedidoMaisCaro(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar pedido mais caro feito por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Pedido mais caro: " + cliente.pedidoMaisCaro());
        esperar();
    }

    public static void pedidoMaisBarato(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar pedido mais barato feito por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Pedido mais barato: " + cliente.pedidoMaisBarato());
        esperar();
    }

    public static void maiorPedido(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar número de itens do maior pedido feito por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Maior pedido: " + cliente.maiorPedido());
        esperar();
    }

    public static void menorPedido(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar número de itens do menor pedido feito por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Menor pedido: " + cliente.menorPedido());
        esperar();
    }

    public static void pedidoMaisAntigo(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar pedido mais antigo feito por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Pedido mais antigo: " + cliente.pedidoMaisAntigo());
        esperar();
    }

    public static void pedidoMaisNovo(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar pedido mais recente feito por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Pedido mais recente: " + cliente.pedidoMaisNovo());
        esperar();
    }

    public static void mediaGastos(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar média de gastos dos pedidos feitos por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Média de gastos: " + cliente.mediaGastos());
        esperar();
    }

    public static void totalGastos(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar total de gastos dos pedidos feitos por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Total de gastos: " + cliente.totalGastos());
        esperar();
    }

    public static void totalPedidos(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar total de pedidos feitos por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Total de pedidos: " + cliente.totalPedidos());
        esperar();
    }

    public static void totalPedidosAvaliados(Cliente cliente) {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar total de pedidos avaliados por um cliente");
        System.out.println("---------------------------------------------");
        System.out.println("Total de pedidos: " + cliente.totalPedidosAvaliados());
        esperar();
    }

    public static void dadosGeraisPedido() {
        int opcao = 0;
        do{
            limparTela();
            System.out.println("---------------------------------------------");
            System.out.println("Mostrar dados dos pedidos de todos os clientes");
            System.out.println("---------------------------------------------");
            System.out.println("Selecione o dado que deseja acessar");
            System.out.println("0 - Retornar ao menu");
            System.out.println("1 - Mostrar pedido mais caro já feito");
            System.out.println("2 - Mostrar pedido mais barato já feito");
            System.out.println("3 - Mostrar número de itens do maior pedido já feito");
            System.out.println("4 - Mostrar número de itens do menor pedido já feito");
            System.out.println("5 - Mostrar pedido mais antigo já feito");
            System.out.println("6 - Mostrar pedido mais recente já feito");
            System.out.println("7 - Mostrar gstos médios dos clientes");
            System.out.println("8 - Mostrar gastos totais dos clientes");
            System.out.println("9 - Mostrar total de pedidos feitos");
            System.out.println("10 - Mostrar total de pedidos avaliados");
            switch (opcao) {
                case 0:
                    break;
                case 1:
                    pedidoMaisCaroGeral();
                    break;
                case 2:
                    pedidoMaisBaratoGeral();
                    break;
                case 3:
                    maiorPedidoGeral();
                    break;
                case 4:
                    menorPedidoGeral();
                    break;
                case 5:
                    pedidoMaisAntigoGeral();
                    break;
                case 6:
                    pedidoMaisNovoGeral();
                    break;
                case 7:
                    mediaGastosGeral();
                    break;
                case 8:
                    totalGastosGeral();
                    break;
                case 9:
                    totalPedidosGeral();
                    break;
                case 10:
                    totalPedidosAvaliadosGeral();
                    break;
                default:
                    System.err.println("Essa opção não existe");
                    esperar();
            }
            opcao = teclado.nextInt();
            teclado.nextLine();
        } while (opcao != 0);
    }

    public static void pedidoMaisCaroGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar pedido mais caro já feito");
        System.out.println("---------------------------------------------");
        System.out.println("Pedido mais caro: " + clientes.stream()
                                                        .mapToDouble(a -> a.pedidoMaisCaro())
                                                        .max()
                                                        .orElse(0));
        esperar();
    }

    public static void pedidoMaisBaratoGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar pedido mais barato já feito");
        System.out.println("---------------------------------------------");
        System.out.println("Pedido mais barato: " + clientes.stream()
                                                        .mapToDouble(a -> a.pedidoMaisBarato())
                                                        .min()
                                                        .orElse(0));
        esperar();
    }

    public static void maiorPedidoGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar número de itens do maior pedido já feito");
        System.out.println("---------------------------------------------");
        System.out.println("Maior pedido: " + clientes.stream()
                                                    .mapToInt(a -> a.maiorPedido())
                                                    .max()
                                                    .orElse(0));
        esperar();
    }

    public static void menorPedidoGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar número de itens do menor pedido já feito");
        System.out.println("---------------------------------------------");
        System.out.println("Menor pedido: " + clientes.stream()
                                                    .mapToInt(a -> a.menorPedido())
                                                    .min()
                                                    .orElse(0));
        esperar();
    }

    public static void pedidoMaisAntigoGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar pedido mais antigo já feito");
        System.out.println("---------------------------------------------");
        System.out.println("Pedido mais antigo: " + clientes.stream()
                                                            .map(a -> a.pedidoMaisAntigo())
                                                            .reduce((a, b) -> a.compareTo(b) < 0 ? a : b)
                                                            .orElse(null));
        esperar();
    }

    public static void pedidoMaisNovoGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar pedido mais novo já feito");
        System.out.println("---------------------------------------------");
        System.out.println("Pedido mais novo: " + clientes.stream()
                                                            .map(a -> a.pedidoMaisNovo())
                                                            .reduce((a, b) -> a.compareTo(b) < 0 ? b : a)
                                                            .orElse(null));
        esperar();
    }

    public static void mediaGastosGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar gastos médios dos clientes");
        System.out.println("---------------------------------------------");
        System.out.println("Média de gastos: " + clientes.stream()
                                                .mapToDouble(a -> a.mediaGastos())
                                                .average()
                                                .orElse(0));
        esperar();
    }

    public static void totalGastosGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar gastos totais dos clientes");
        System.out.println("---------------------------------------------");
        System.out.println("Total de gastos: " + clientes.stream()
                                                .mapToDouble(a -> a.totalGastos())
                                                .sum());
    }

    public static void totalPedidosGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar total de pedidos feitos");
        System.out.println("---------------------------------------------");
        System.out.println("Total de pedidos: " + clientes.stream()
                                                        .mapToInt(a -> a.totalPedidos())
                                                        .sum());
        esperar();
    }

    public static void totalPedidosAvaliadosGeral() {
        System.out.println("---------------------------------------------");
        System.out.println("Mostrar total de pedidos avaliados");
        System.out.println("---------------------------------------------");
        System.out.println("Total de pedidos: " + clientes.stream()
                                                        .mapToInt(a -> a.totalPedidosAvaliados())
                                                        .sum());
        esperar();
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
}
