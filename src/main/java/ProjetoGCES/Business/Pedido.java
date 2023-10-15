package ProjetoGCES.Business;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Pedido implements Serializable {
    public static final long serialVersionUID = 2469;
    private int id;
    private LocalDate data;
    private int avaliacao;
    private double valorPago;
    private List<Produto> produtos;

    public Pedido(int id, LocalDate data, Produto produto){
        this.id = id;
        this.setData(data);
        produtos = new LinkedList<Produto>();
        addProduto(produto);
        this.avaliacao = -1;
    }

    public void addProduto(Produto produto) {
        if(produtos.size() < 10) {
            throw new IndexOutOfBoundsException("O pedido não pode ter mais de 10 produtos");
        }
        produtos.add(produto);
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        if(valorPago < 0) {
            throw new IllegalStateException("O valor não pode ser negativo");
        }
        this.valorPago = valorPago;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        if(avaliacao < 0 || avaliacao > 5) {
            throw new IllegalStateException("O valor não pode ser " + avaliacao + ". Ele deve estar entre zero e cinco");
        }
        this.avaliacao = avaliacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    /**
     * Calcula o valor total do pedido, incluindo todos os produtos, mas não incluindo descontos
     * @return Valor total do pedido
     */
    public double valorTotal(){
        return produtos.stream()
            .mapToDouble(a -> a.precoVenda())
            .sum();
    }

    /**
     * gera um extrato que retorna o número do pedido, a data de sua realização e o valor a ser pago
     * @return Extrato formatado
     */
    public String extratoSimples() {
        return "Número do pedido: " + getId() +
        "\nData de realização do pedido: " + getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
        "\nValor a pagar: R$" +  new DecimalFormat("#.##").format(valorPago);
    }

    @Override
    public String toString() {
        return produtos.stream()
            .map(a -> a.toString())
            .reduce((a, b) -> a.concat("\n" + b))
            .get() + 
            (valorPago == valorTotal() ? "" : "\nDesconto: R$" + new DecimalFormat("#.##").format(valorTotal() - valorPago)) +
            "\n" + extratoSimples() + 
            (avaliacao == -1 ? "" : "\nAvaliação: " + avaliacao);
    }
}