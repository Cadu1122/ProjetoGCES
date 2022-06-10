package Business;

public class ProdutosComAdicionais extends Produto {
	private double PRECO_ADC;
	private String adicionais;
	
	public ProdutosComAdicionais() {
		
	}

	public ProdutosComAdicionais(double PRECO_ADC, String adicionais) {
		PRECO_ADC = PRECO_ADC;
		this.adicionais = adicionais;
	}

	public ProdutosComAdicionais(String nomeDoProduto, double precoBase) {
		super(nomeDoProduto, precoBase);
	}
	
	public double getPRECO_ADC() {
		return PRECO_ADC;
	}

	public void setPRECO_ADC(double pRECO_ADC) {
		PRECO_ADC = pRECO_ADC;
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
