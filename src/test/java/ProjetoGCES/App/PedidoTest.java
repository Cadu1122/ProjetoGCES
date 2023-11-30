package ProjetoGCES.App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ProjetoGCES.Business.Produtos.Pizza;
import ProjetoGCES.Business.Produtos.Produto;
import ProjetoGCES.Business.Produtos.Sanduiche;
import ProjetoGCES.Business.Venda.Pedido;

public class PedidoTest {
    private Pedido pedido;

    @BeforeEach
    public void setup() {
        pedido = new Pedido(0, LocalDate.of(2023, 9, 30), new Sanduiche(false));
    }

    public Produto mockProdutoIntoPedido() {
        Produto produto = new Pizza(false);
        pedido.addProduto(produto);
        return produto;
    }

    @Test
    public void addProdutoTest() {
        Produto produto = mockProdutoIntoPedido();
        assertTrue(pedido.hasProduto(produto));
    }

    @Test
    public void tamanhoTest() {
        assertEquals(1, pedido.tamanho());
    }

    @Test
    public void tamanhoDoisPedidosTest() {
        mockProdutoIntoPedido();
        assertEquals(2, pedido.tamanho());
    }

    @Test
    public void maisDeDezPedidosTest() {
        for (int i = 0; i < 9; i++) {
            mockProdutoIntoPedido();
        }
        assertThrowsExactly(IndexOutOfBoundsException.class, (() -> mockProdutoIntoPedido()));
    }

    @Test
    public void precoTotalTest() {
        assertEquals(12, pedido.valorTotal());
    }

    @Test
    public void precoTotalDoisItensTest() {
        mockProdutoIntoPedido();
        assertEquals(37, pedido.valorTotal());
    }

    @Test

    public void valorPagoTest() {
        pedido.setValorPago(24);
        assertEquals(24, pedido.getValorPago());
    }

    @Test
    public void valorPagoNegativoTest() {
        assertThrowsExactly(IllegalStateException.class, () -> pedido.setValorPago(-15));
    }

    @Test

    public void avaliacaoTest() {
        pedido.setAvaliacao(3);
        assertEquals(3, pedido.getAvaliacao());
    }

    @Test
    public void avaliacaoNegativaTest() {
        assertThrowsExactly(IllegalStateException.class, () -> pedido.setAvaliacao(-1));
    }

    @Test
    public void avaliacaoMaiorQuePermitidaTest() {
        assertThrowsExactly(IllegalStateException.class, () -> pedido.setAvaliacao(6));
    }

    @Test
    public void pedidoSemAvaliacaoTest() {
        assertEquals(-1, pedido.getAvaliacao());
    }

    @Test
    public void dataTest() {
        assertEquals(LocalDate.of(2023, 9, 30), pedido.getData());
    }

    @Test
    public void dataFuturaTest() {
        assertThrowsExactly(IllegalStateException.class, () -> pedido.setData(LocalDate.of(4023, 9, 30)));
    }
}
