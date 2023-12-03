package ProjetoGCES.Business.Venda.Fidelidade;

import ProjetoGCES.Business.Venda.Pedido;

public class FPreto implements IFidelidade {
    public static final double VALOR_DESC = 0.1F;

    @Override
    public void calcularDesconto(Pedido pedido) {
        pedido.setValorPago(pedido.valorTotal() * (1 - VALOR_DESC));
    }
}