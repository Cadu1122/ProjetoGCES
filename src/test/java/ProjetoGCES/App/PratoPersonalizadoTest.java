package ProjetoGCES.App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ProjetoGCES.Business.Produtos.OpcoesPratoPersonalizado;
import ProjetoGCES.Business.Produtos.PratoPersonalizado;

public class PratoPersonalizadoTest {
    private PratoPersonalizado pratoPersonalizado;

    @BeforeEach
    public void setup() {
        pratoPersonalizado = new PratoPersonalizado(OpcoesPratoPersonalizado.ARROZ);
    }

    @Test
    public void addItemTest() {
        pratoPersonalizado.addComida(OpcoesPratoPersonalizado.FEIJAO);
        assertTrue(pratoPersonalizado.hasComida(OpcoesPratoPersonalizado.FEIJAO));
    }

    @Test
    public void precoTest() {
        assertEquals(10.0, pratoPersonalizado.precoVenda());
    }

    @Test
    public void precoCumulativoTest() {
        pratoPersonalizado.addComida(OpcoesPratoPersonalizado.FEIJAO);
        assertEquals(14.0, pratoPersonalizado.precoVenda());
    }
}
