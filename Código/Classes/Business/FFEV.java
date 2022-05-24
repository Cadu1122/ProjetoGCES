public class FFEV implements IFidelidade {
    public static final float VALOR_DESC = 0.2F;

    @Override
    public double calcularDesconto(Pedido pedido) {
        return pedido.valorTotal() * (1 - VALOR_DESC);
    }
}
