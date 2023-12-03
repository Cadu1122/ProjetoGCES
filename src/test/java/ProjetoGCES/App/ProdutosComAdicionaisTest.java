package ProjetoGCES.App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ProjetoGCES.Business.Produtos.OpcoesAdicionais;
import ProjetoGCES.Business.Produtos.ProdutosComAdicionais;

public class ProdutosComAdicionaisTest {
    private ProdutosComAdicionais produtoComAdicionais;

    @BeforeEach
    public void setUp() {
        produtoComAdicionais = new ProdutosComAdicionais(10.0) {
            @Override
            public double precoVenda() {
                return getPrecoBase() + precoAdicionais();
            }
        };
    }

    @Test
    public void testAddAdicional() {
        OpcoesAdicionais adicional = OpcoesAdicionais.QUEIJO;
        produtoComAdicionais.addAdicional(adicional);

        assertTrue(produtoComAdicionais.getAdicionais().contains(adicional));
    }

    @Test
    public void testPrecoAdicionais() {
        OpcoesAdicionais queijo = OpcoesAdicionais.QUEIJO;
        OpcoesAdicionais bacon = OpcoesAdicionais.BACON;

        produtoComAdicionais.addAdicional(queijo);
        produtoComAdicionais.addAdicional(bacon);

        double precoEsperado = queijo.getPreco() + bacon.getPreco();

        assertEquals(precoEsperado, produtoComAdicionais.precoAdicionais());
    }

    @Test
    public void testPrecoVendaComAdicionais() {
        OpcoesAdicionais queijo = OpcoesAdicionais.QUEIJO;
        OpcoesAdicionais bacon = OpcoesAdicionais.BACON;

        produtoComAdicionais.addAdicional(queijo);
        produtoComAdicionais.addAdicional(bacon);

        double precoEsperado = produtoComAdicionais.getPrecoBase() + queijo.getPreco() + bacon.getPreco();

        assertEquals(precoEsperado, produtoComAdicionais.precoVenda());
    }
}