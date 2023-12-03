package ProjetoGCES.App;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ProjetoGCES.Business.Produtos.Pizza;
import ProjetoGCES.Business.Produtos.Produto;

public class PizzaTest {
    private Produto pizzaSemBorda;
    private Produto pizzaComBorda;

    @BeforeEach
    public void setup() {
        pizzaSemBorda = new Pizza(false);
        pizzaComBorda = new Pizza(true);
    }

    @Test
    public void pizzaValorSemBordaTest() {
        assertEquals(25.0, pizzaSemBorda.precoVenda());
    }

    @Test
    public void pizzaValorComBordaTest() {
        assertEquals(33.0, pizzaComBorda.precoVenda());
    }
}
