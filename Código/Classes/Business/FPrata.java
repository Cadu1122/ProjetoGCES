public class FPrata implements IFidelidade {
    public static final float VALOR_DESC = 0.05F;

    @Override
    public float calcularDesconto(Pedido pedido) {
        return pedido.valorTotal() * (1 - VALOR_DESC);
    }
}
