package Testes;

import Business.FFEV;
import Business.IFidelidade;
import Business.Produto;
import Business.Pedido;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class FFEVTest {

    @Test
    public void calcularDesconto_deveAplicarDescontoCorretamente() {
        // Arrange
        FFEV ffev = new FFEV();

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

        // Calculamos o valor total esperado com o desconto
        double valorTotalEsperado = (10.0 + 20.0) * (1 - FFEV.VALOR_DESC);

        // Act
        ffev.calcularDesconto(pedido);

        // Assert
        assertEquals(valorTotalEsperado, pedido.getValorPago(), 0.01); // Usamos delta para lidar com erros de precisão de ponto flutuante
    }
}