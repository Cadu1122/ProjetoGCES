package Business.Venda;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.Set;

import Business.Venda.Fidelidade.FFEV;
import Business.Venda.Fidelidade.FPrata;
import Business.Venda.Fidelidade.FPreto;
import Business.Venda.Fidelidade.FBranco;
import Business.Venda.Fidelidade.IFidelidade;

public class Cliente implements Serializable {
    public static final long serialVersionUID = 2496;
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
        calcularDesconto(pedido);
        this.pedidos.add(pedido);
    }

    /**
     * Calcula a média de avaliações feitas por esse cliente retorna 0 caso o cliente não tenha avaliado nenhum pedido
     * @return Media das avaliações
     */
    public double mediaAvaliacoes(){
        return pedidos.stream()
            .mapToInt(a -> a.getAvaliacao())
            .average()
            .orElse(0);
    }

    /**
     * Calcula o desconto do cliente baseado em seu tipo de fidelidade
     * @param pedido Pedido que se deseja calcular o desconto
     */
    private void calcularDesconto(Pedido pedido) {
        verificarFidelidade();
        fidelidade.calcularDesconto(pedido);
    }

    /**
     * Verifica em qual fidelidade o cliente se encontra baseado na regra de negócio de cada um
     */
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

    /**
     * Verifica se o cliente deveria estar na fidelidade F&V ou não
     * @return true caso o cliente deveria estar nessa fidelidade, false caso contrário
     */
    private boolean isFidelidadeFEV() {
        DoubleSummaryStatistics estatisticasValores = pedidos.stream()
            .filter(pedido -> pedido.getData().isAfter(LocalDate.now().minus(6, ChronoUnit.MONTHS)))
            .mapToDouble(pedido -> pedido.valorTotal())
            .summaryStatistics();
        if(estatisticasValores.getSum() >= 600 || estatisticasValores.getCount() >= 50) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se o cliente deveria estar na fidelidade preto ou não
     * @return true caso o cliente deveria estar nessa fidelidade, false caso contrário
     */
    private boolean isFidelidadePreto() {
        DoubleSummaryStatistics estatisticasValores = pedidos.stream()
            .filter(pedido -> pedido.getData().isAfter(LocalDate.now().minus(2, ChronoUnit.MONTHS)))
            .mapToDouble(pedido -> pedido.valorTotal())
            .summaryStatistics();
        if(estatisticasValores.getSum() >= 250 || estatisticasValores.getCount() >= 10) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se o cliente deveria estar na fidelidade prata ou não
     * @return true caso o cliente deveria estar nessa fidelidade, false caso contrário
     */
    private boolean isFidelidadePrata() {
        DoubleSummaryStatistics estatisticasValores = pedidos.stream()
            .filter(pedido -> pedido.getData().isAfter(LocalDate.now().minus(1, ChronoUnit.MONTHS)))
            .mapToDouble(pedido -> pedido.valorTotal())
            .summaryStatistics();
        if(estatisticasValores.getSum() >= 100 || estatisticasValores.getCount() >= 4) {
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

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public String toString() {
        return "nome: " + this.nome + "; CPF:" + this.cpf;
    }
}