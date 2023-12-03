package ProjetoGCES.App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ProjetoGCES.Business.Venda.Cliente;
import ProjetoGCES.Business.Venda.Fidelidade.FBranco;
import ProjetoGCES.Business.Venda.Fidelidade.FFEV;
import ProjetoGCES.Business.Venda.Fidelidade.FPrata;
import ProjetoGCES.Business.Venda.Fidelidade.FPreto;
import ProjetoGCES.Business.Venda.Pedido;
import ProjetoGCES.Business.Produtos.Pizza;
import ProjetoGCES.Business.Produtos.PratoFeito;
import ProjetoGCES.Business.Produtos.Sanduiche;

public class ClienteTest {
    
    private Cliente cliente;

    public Pedido mockPedido(int avaliacao) {
        Random random = new Random();
        Pedido pedido = new Pedido(random.nextInt(), LocalDate.now().minusDays(2), new Sanduiche(true));
        pedido.setAvaliacao(avaliacao);
        return pedido;
    }

    public Pedido mockPedidoCaro() {
        Random random = new Random();
        Pedido pedido = new Pedido(random.nextInt(), LocalDate.now().minusDays(3), new Pizza(true));
        pedido.addProduto(new Sanduiche(true));
        pedido.addProduto(new PratoFeito());
        return pedido;
    }

    @BeforeEach
    public void criarCliente() {
        cliente = new Cliente("Fernando", "123.456.789-45");
    }

    @Test
    public void inserirPedidoTest() {
        Pedido pedido = mockPedido(2);
        cliente.addPedido(pedido);
        assertTrue(cliente.getPedidos().contains(pedido));
    }

    @Test
    public void mediaUmaAvaliacaoTest() {
        cliente.addPedido(mockPedido(5));
        assertEquals(5.0, cliente.mediaAvaliacoes());
    }

    @Test
    public void mediaDuasAvaliacoesTest() {
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedido(1));
        assertEquals(3.0, cliente.mediaAvaliacoes());
    }

    @Test
    public void mediaPedidoSemAvaliacaoTest() {
        cliente.addPedido(new Pedido(0, LocalDate.now(), new Sanduiche(false)));
        assertEquals(0, cliente.mediaAvaliacoes());
    }

    @Test
    public void menorDuasAvaliacoesTest() {
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedido(1));
        assertEquals(1.0, cliente.menorAvaliacao());
    }

    @Test
    public void menorAvaliacaoPedidosSemTest() {
        cliente.addPedido(new Pedido(0, LocalDate.now(), new Sanduiche(false)));
        assertEquals(0, cliente.menorAvaliacao());
    }

    @Test
    public void maiorAvaliacaoPedidosSemTest() {
        cliente.addPedido(new Pedido(0, LocalDate.now(), new Sanduiche(false)));
        assertEquals(0, cliente.maiorAvaliacao());
    }

    @Test
    public void maiorDuasAvaliacoesTest() {
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedido(1));
        assertEquals(5.0, cliente.maiorAvaliacao());
    }

    @Test
    public void clienteSemPedidoMediaAvaliacaoTest() {
        assertEquals(0.0, cliente.mediaAvaliacoes());
    }

    @Test
    public void clienteSemPedidoMenorAvaliacaoTest() {
        assertEquals(0.0, cliente.menorAvaliacao());
    }

    @Test
    public void clienteSemPedidoMaiorAvaliacaoTest() {
        assertEquals(0.0, cliente.maiorAvaliacao());
    }

    @Test
    public void pedidoMaisCaroTest() {
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(62.0, cliente.pedidoMaisCaro());
    }

    @Test
    public void pedidoMaisBaratoTest() {
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(14.0, cliente.pedidoMaisBarato());
    }

    @Test
    public void clienteSemPedidoMaisCaroTest() {
        assertEquals(0.0, cliente.pedidoMaisCaro());
    }

    @Test
    public void clienteSemPedidoMaisBaratoTest() {
        assertEquals(0.0, cliente.pedidoMaisBarato());
    }

    @Test
    public void fidelidadeBrancoTest() {
        assertEquals(FBranco.class, cliente.getFidelidade().getClass());
    }

    @Test
    public void fidelidadePrataQuantidadeTest() {
        for(int i = 0; i < 4; i++) {
            cliente.addPedido(mockPedido(3));
        }
        assertEquals(FPrata.class, cliente.getFidelidade().getClass());
    }

    @Test
    public void fidelidadePrataPrecoTest() {
        for(int i = 0; i < 2; i++) {
            cliente.addPedido(mockPedidoCaro());
        }
        assertEquals(FPrata.class, cliente.getFidelidade().getClass());
    }

    @Test
    public void fidelidadePretoQuantidadeTest() {
        for(int i = 0; i < 10; i++) {
            cliente.addPedido(mockPedido(2));
        }
        assertEquals(FPreto.class, cliente.getFidelidade().getClass());
    }

    @Test
    public void fidelidadePretoPrecoTest() {
        for(int i = 0; i < 5; i++) {
            cliente.addPedido(mockPedidoCaro());
        }
        assertEquals(FPreto.class, cliente.getFidelidade().getClass());
    }

    @Test
    public void fidelidadeFEVQuantidadeTest() {
        for(int i = 0; i < 50; i++) {
            cliente.addPedido(mockPedido(2));
        }
        assertEquals(FFEV.class, cliente.getFidelidade().getClass());
    }

    @Test
    public void fidelidadeFEVPrecoTest() {
        for(int i = 0; i < 10; i++) {
            cliente.addPedido(mockPedidoCaro());
        }
        assertEquals(FFEV.class, cliente.getFidelidade().getClass());
    }

    @Test
    public void maiorPedidoTest() {
        cliente.addPedido(mockPedido(1));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(3, cliente.maiorPedido());
    }

    @Test
    public void menorPedidoTest() {
        cliente.addPedido(mockPedido(1));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(1, cliente.menorPedido());
    }

    @Test
    public void clienteSemPedidoMaiorTest() {
        assertEquals(0, cliente.maiorPedido());
    }

    @Test
    public void clienteSemPedidoMenorTest() {
        assertEquals(0, cliente.menorPedido());
    }

    @Test
    public void pedidoMaisAntigoTest() {
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(LocalDate.now().minusDays(3), cliente.pedidoMaisAntigo());
    }

    @Test
    public void clienteSemPedidoAntigoTest() {
        assertEquals(null, cliente.pedidoMaisAntigo());
    }

    @Test
    public void pedidoMaisNovoTest() {
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(LocalDate.now().minusDays(2), cliente.pedidoMaisNovo());
    }

    @Test
    public void clienteSemPedidoNovoTest() {
        assertEquals(null, cliente.pedidoMaisNovo());
    }

    @Test
    public void mediaGastosTest() {
        cliente.addPedido(mockPedido(2));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(38, cliente.mediaGastos());
    }

    @Test
    public void mediaGastosSemPedidoTest() {
        assertEquals(0, cliente.mediaGastos());
    }

    @Test
    public void idDuplicadoTest() {
        cliente.addPedido(new Pedido(0, LocalDate.now(), new Sanduiche(false)));
        assertThrowsExactly(IllegalStateException.class, () -> cliente.addPedido(new Pedido(0, LocalDate.now(), new Sanduiche(false))));
    }

    @Test
    public void totalGastosTest() {
        cliente.addPedido(mockPedido(2));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(76.0, cliente.totalGastos());
    }

    @Test
    public void semPedidosTotalGastosTest() {
        assertEquals(0, cliente.totalGastos());
    }

    @Test
    public void totalPedidosTest() {
        cliente.addPedido(mockPedido(1));
        cliente.addPedido(mockPedido(2));
        cliente.addPedido(mockPedido(3));
        assertEquals(3, cliente.totalPedidos());
    }

    @Test
    public void semPedidosTotalTest() {
        assertEquals(0, cliente.totalPedidos());
    }

    @Test
    public void totalPedidosAvaliadosTest() {
        cliente.addPedido(mockPedido(3));
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedidoCaro());
        assertEquals(2, cliente.totalPedidosAvaliados());
    }

    @Test
    public void semPedidosTotalAvaliadosTest() {
        assertEquals(0, cliente.totalPedidosAvaliados());
    }
}
