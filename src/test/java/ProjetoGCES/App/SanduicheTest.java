package ProjetoGCES.App;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ProjetoGCES.Business.Produtos.Produto;
import ProjetoGCES.Business.Produtos.Sanduiche;

public class SanduicheTest {
    private Produto sanduicheNormal;
    private Produto sanduicheArtesanal;

    @BeforeEach
    public void setup() {
        sanduicheNormal = new Sanduiche(false);
        sanduicheArtesanal = new Sanduiche(true);
    }

    @Test
    public void sanduicheValorNormalTest() {
        assertEquals(12, sanduicheNormal.precoVenda());
    }

    @Test
    public void sanduicheValorArtesanalTest() {
        assertEquals(14, sanduicheArtesanal.precoVenda());
    }
}
