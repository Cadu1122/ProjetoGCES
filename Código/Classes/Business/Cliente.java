package Business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Cliente implements Serializable {
    private IFidelidade fidelidade;
    private String nome;
    private String cpf;
    private Set<Pedido> pedidos = new HashSet<Pedido>();

    public Cliente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
        this.fidelidade = new FBranco();
    }

    public void addPedido(Pedido pedido){
        this.pedidos.add(pedido);
        verificarFidelidade();
    }

    public double mediaAvaliacoes(){
        double total = 0;

        for(Pedido pedido : pedidos)
            total += pedido.getAvaliacao();
        
        return total / pedidos.size();
    }

    public double calcularDesconto(Pedido pedido) {
        return fidelidade.calcularDesconto(pedido);
    }

    private void verificarFidelidade() {
        if (isFidelidadeFEV()) {
            this.fidelidade = new FFEV();
        } else if (isFidelidadePreto()) {
            this.fidelidade = new FPreto();
        } else if (isFidelidadePrata()) {
            this.fidelidade = new FPrata();
        } else {
            this.fidelidade = new FBranco();
        }
    }

    private boolean isFidelidadeFEV() {
        Set<Pedido> filtro = pedidos.stream()
            .collect(Collectors.filtering(pedido -> pedido.getData().isAfter(LocalDate.now().minus(6, ChronoUnit.MONTHS)), Collectors.toSet()));
        double somaValores = filtro.stream()
            .mapToDouble(pedido -> pedido.getValorTotal())
            .sum();
        int qtdPedidos = pedidos.size();
        if(somaValores >= 600 || qtdPedidos >= 50) {
            return true;
        }
        return false;
    }

    private boolean isFidelidadePreto() {
        Set<Pedido> filtro = pedidos.stream()
            .collect(Collectors.filtering(pedido -> pedido.getData().isAfter(LocalDate.now().minus(2, ChronoUnit.MONTHS)), Collectors.toSet()));
        double somaValores = filtro.stream()
            .mapToDouble(pedido -> pedido.getValorTotal())
            .sum();
        int qtdPedidos = pedidos.size();
        if(somaValores >= 250 || qtdPedidos >= 10) {
            return true;
        }
        return false;
    }

    private boolean isFidelidadePrata() {
        Set<Pedido> filtro = pedidos.stream()
            .collect(Collectors.filtering(pedido -> pedido.getData().isAfter(LocalDate.now().minus(1, ChronoUnit.MONTHS)), Collectors.toSet()));
        double somaValores = filtro.stream()
            .mapToDouble(pedido -> pedido.getValorTotal())
            .sum();
        int qtdPedidos = pedidos.size();
        if(somaValores >= 100 || qtdPedidos >= 4) {
            return true;
        }
        return false;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public IFidelidade getFidelidade() {
        return fidelidade;
    }

    @Override
    public String toString() {
        return "nome: " + this.nome + "; CPF:" + this.cpf;
    }
}