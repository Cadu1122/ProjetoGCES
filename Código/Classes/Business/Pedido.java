import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int ID;
    private LocalDateTime data;
    private int avaliacao;
    private int valorTotal;
    private Cliente cliente;
    private List<Produto> produtos = new ArrayList<>();

    public Pedido(int ID, LocalDateTime data, Cliente cliente){
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

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
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

        total -= ((total * cliente.calcularDesconto(this)) / 100);

        return total;
    }

    public String toString(){
        return String.format("%s - %s", ID, data);
    }
}