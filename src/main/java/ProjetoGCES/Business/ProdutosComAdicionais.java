package ProjetoGCES.Business;

import java.util.HashSet;
import java.util.Set;

public abstract class ProdutosComAdicionais extends Produto {
	private Set<OpcoesAdicionais> adicionais;
	
	public ProdutosComAdicionais(double preco) {
		super(preco);
		this.adicionais = new HashSet<OpcoesAdicionais>();
	}

	public Set<OpcoesAdicionais> getAdicionais() {
		return adicionais;
	}

	public void addAdicional(OpcoesAdicionais adicional) {
		adicionais.add(adicional);
	}
	
	public abstract double precoVenda();

	public double precoAdicionais() {
		return adicionais.stream()
			.mapToDouble(a -> a.getPreco())
			.sum();
	}
}
