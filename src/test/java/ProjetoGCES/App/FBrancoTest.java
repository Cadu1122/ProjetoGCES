package ProjetoGCES.App;

import ProjetoGCES.Business.Venda.Fidelidade.FBranco;
import ProjetoGCES.Business.Venda.Pedido;
import ProjetoGCES.Business.Produtos.Produto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class FBrancoTest {

    @Test
    public void calcularDescontoDeveDefinirValorPagoComValorTotal() {
        // Arrange
        FBranco fBranco = new FBranco();
        Produto produto1 = new Produto(10.0) {
            @Override
            public double precoVenda() {
                return getPrecoBase();
            }
        };

        Produto produto2 = new Produto(20.0) {
            @Override
            public double precoVenda() {
                return getPrecoBase();
            }
        };

        Pedido pedido = new Pedido(1, LocalDate.now(), produto1);

        pedido.addProduto(produto2);

        // Act
        fBranco.calcularDesconto(pedido);

        // Assert
        assertEquals(30.0, pedido.getValorPago(), 0.01);
    }
}