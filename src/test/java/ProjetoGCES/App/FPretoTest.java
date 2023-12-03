package ProjetoGCES.App;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ProjetoGCES.Business.Venda.Fidelidade.FPreto;
import ProjetoGCES.Business.Venda.Pedido;
import ProjetoGCES.Business.Produtos.Produto;

public class FPretoTest {
    private FPreto fPreto;

    @BeforeEach
    public void setUp() {
        fPreto = new FPreto();
    }

    @Test
    public void testCalcularDesconto_FPreto() {
        Produto produto = new Produto(110.0) {
            @Override
            public double precoVenda() {
                return getPrecoBase();
            }
        };
        Pedido pedido = new Pedido(1, LocalDate.now(), produto);
        fPreto.calcularDesconto(pedido);

        double expectedDiscount = pedido.valorTotal() * 0.1;

        assertEquals(pedido.getValorPago(), pedido.valorTotal() - expectedDiscount, 0.01);
    }
}
