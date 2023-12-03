package ProjetoGCES.Business.Produtos;

import java.util.HashSet;
import java.util.Set;

public class PratoPersonalizado extends Produto {
    private Set<OpcoesPratoPersonalizado> comidas;

    public PratoPersonalizado(OpcoesPratoPersonalizado opcao) {
        super(OpcoesComida.PRATO_PERSONALIZADO.getPreco());
        comidas = new HashSet<OpcoesPratoPersonalizado>();
        comidas.add(opcao);
    }

    public void addComida(OpcoesPratoPersonalizado opcao) {
        comidas.add(opcao);
    }

    public boolean hasComida(OpcoesPratoPersonalizado opcao) {
        return comidas.contains(opcao);
    }

    @Override
    public double precoVenda() {
        return getPrecoBase() + comidas.stream()
                                    .mapToDouble(a -> a.getPreco())
                                    .sum();
    }

    @Override
    public String toString() {
        return "Produto: Prato personalizado; Itens: " + 
		comidas.stream()
			.map(a -> a.toString())
			.reduce((a, b) -> a.concat(", " + b))
			.orElse("") + 
		"; Preço: R$" + this.precoVenda();
    }
}
