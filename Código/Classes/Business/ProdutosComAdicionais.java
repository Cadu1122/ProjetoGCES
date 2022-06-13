package Business;

import java.util.Collections;
import java.util.Map;

public class ProdutosComAdicionais extends Produto {
	private String adicionais;
	public static final Map<String, Double> PRECO_ADC = Collections.unmodifiableMap(Map.of("peperoni", 4d,
	"bacon", 3d, "palmito", 3d, "queijo", 2d, "picles", 2d, "ovo",
	2d, "batata palha", 2d));
	
	public ProdutosComAdicionais() {
	}

	public ProdutosComAdicionais(String adicionais) {
		this.adicionais = adicionais;
	}

	public ProdutosComAdicionais(String nomeDoProduto, double precoBase) {
		super(nomeDoProduto, precoBase);
	}

	public String getAdicionais() {
		return adicionais;
	}

	public void setAdicionais(String adicionais) {
		this.adicionais = adicionais;
	}

	public double precoVenda() {
		return PRECO_ADC+getPrecoBase();
	}
	
	public String toString() {
		return "Produto" +getNome()+ "com os adicionais"+adicionais+"ficou em:"+precoVenda();
	}
}
