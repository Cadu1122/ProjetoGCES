package Business;

public class FPrata implements IFidelidade {
    public static final double VALOR_DESC = 0.05F;

    @Override
    public void calcularDesconto(Pedido pedido) {
        pedido.setValorPago(pedido.valorTotal() * (1 - VALOR_DESC));
    }
}
