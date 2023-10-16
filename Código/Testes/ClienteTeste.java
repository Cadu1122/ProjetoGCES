package Testes;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Business.Cliente;
import Business.FBranco;
import Business.Pedido;
import Business.Pizza;

public class ClienteTeste {
    
    private Cliente cliente;

    public Pedido mockPedido(int avaliacao) {
        Random random = new Random();
        Pedido pedido = new Pedido(random.nextInt(), LocalDate.now(), new Pizza(true));
        pedido.setAvaliacao(avaliacao);
        return pedido;
    }

    @BeforeEach
    public void criarCliente() {
        cliente = new Cliente("Fernando", "123.456.789-45");
    }

    @Test
    public void inserirPedidoTeste() {
        Pedido pedido = mockPedido(2);
        cliente.addPedido(pedido);
        assertTrue(cliente.getPedidos().contains(pedido));
    }

    @Test
    public void mediaUmaAvaliacaoTeste() {
        cliente.addPedido(mockPedido(5));
        assertEquals(5.0, cliente.mediaAvaliacoes());
    }

    @Test
    public void mediaDuasAvaliacoesTeste() {
        cliente.addPedido(mockPedido(5));
        cliente.addPedido(mockPedido(1));
        assertEquals(3.0, cliente.mediaAvaliacoes());
    }

    @Test
    public void fidelidadeBrancoTeste() {
        assertEquals(FBranco.class, cliente.getFidelidade().getClass());
    }
}
