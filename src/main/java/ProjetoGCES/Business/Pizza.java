package ProjetoGCES.Business;

public class Pizza extends ProdutosComAdicionais {
	public static final double PRECO_BORDA_RECHEADA = 8;
	private boolean bordaRecheada;
	
	public Pizza(boolean bordaRecheada) {
		super(OpcoesComida.PIZZA.getPreco());
		this.bordaRecheada = bordaRecheada;
	}

	public boolean isBordaRecheada() {
		return bordaRecheada;
	}
	
	public double precoVenda() {
		return getPrecoBase() + (precoAdicionais() * 2) + (isBordaRecheada() ? PRECO_BORDA_RECHEADA : 0);
	}

	@Override
	public String toString() {
		return "Produto: pizza" +
		"; Adicionais: " + this.getAdicionais().stream()
			.map(a -> a.toString())
			.reduce((a, b) -> a.concat(", " + b))
			.orElse("sem adicionais") + 
		"; Preço: R$" + this.precoVenda();
	}
}
