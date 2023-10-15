package Business.Venda.Fidelidade;

import Business.Venda.Pedido;

public class FBranco implements IFidelidade {
    @Override
    public void calcularDesconto(Pedido pedido) {
        pedido.setValorPago(pedido.valorTotal());
    }
}
