package Business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private int ID;
    private LocalDate data;
    private int avaliacao;
    private double valorTotal;
    private Cliente cliente;
    private List<Produto> produtos = new ArrayList<>();

    public Pedido(int ID, LocalDate data, Cliente cliente){
        this.setID(ID);
        this.setData(data);
        this.setCliente(cliente);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        cliente.addPedido(this);
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public double valorTotal(){
        double total = 0;

        for(Produto produto: produtos)
            total += produto.precoVenda();

        return total;
    }

    public String toString(){
        return String.format("%s - %s", ID, data);
    }
}