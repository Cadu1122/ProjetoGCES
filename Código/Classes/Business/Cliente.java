import java.util.HashSet;
import java.util.Set;

public class Cliente implements IFidelidade {
    public enum Finalidade {
        BRANCO, PRATA, PRETO, FEV
    }

    private Finalidade finalidade = Finalidade.BRANCO;
    private String nome;
    private String cpf;
    private Set<Pedido> pedidos = new HashSet<Pedido>();

    public Cliente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    public void addPedido(Pedido pedido){
        this.pedidos.add(pedido);
    }

    public double mediaAvaliacao(){
        double total = 0;

        for(Pedido pedido : pedidos)
            total += pedido.getAvaliacao();
        
        return total / pedidos.size();
    }

    public double calcularDesconto(Pedido pedido){
        switch(finalidade){
            case BRANCO:
                return 0;

            case PRATA:
                return 5;

            case PRETO:
                return 10;

            case FEV:
                return 20;
        }

        return 0;
    }
}