package Testes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Business.FPrata;
import Business.Pedido;
import Business.Produto;

public class FPrataTest {
    private FPrata fPrata;

    @BeforeEach
    public void setUp() {
        fPrata = new FPrata();
    }

    @Test
    public void testCalcularDesconto_FPrata() {
        Produto produto = new Produto(55.0) {
            @Override
            public double precoVenda() {
                return getPrecoBase();
            }
        };  

        Pedido pedido = new Pedido(1, LocalDate.now(), produto);
        fPrata.calcularDesconto(pedido);

        double expectedDiscount = pedido.valorTotal() * 0.05;

        assertEquals(pedido.getValorPago(), pedido.valorTotal() - expectedDiscount, 0.01);
    }
}
