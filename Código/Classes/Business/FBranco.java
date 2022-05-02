public class FBranco implements IFidelidade {
    @Override
    public float calcularDesconto(Pedido pedido) {
        return pedido.valorTotal();
    }
}
