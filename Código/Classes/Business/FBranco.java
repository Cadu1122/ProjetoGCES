package Business;

public class FBranco implements IFidelidade {
    @Override
    public double calcularDesconto(Pedido pedido) {
        return pedido.valorTotal();
    }
}
