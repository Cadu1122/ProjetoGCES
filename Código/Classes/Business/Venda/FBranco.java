package Business.Venda;

public class FBranco implements IFidelidade {
    @Override
    public void calcularDesconto(Pedido pedido) {
        pedido.setValorPago(pedido.valorTotal());
    }
}
