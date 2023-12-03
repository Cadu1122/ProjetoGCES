package ProjetoGCES.Business.Venda.Fidelidade;

import ProjetoGCES.Business.Venda.Pedido;

public class FBranco implements IFidelidade {
    @Override
    public void calcularDesconto(Pedido pedido) {
        pedido.setValorPago(pedido.valorTotal());
    }
}
