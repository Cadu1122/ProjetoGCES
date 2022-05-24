public class Pizza extends ProdutosComAdicionais {
	private boolean bordaRecheada;
	
	public Pizza() {
		
	}
	
	public Pizza(boolean bordaRecheada) {
		super();
		this.bordaRecheada = bordaRecheada;
	}

	public boolean isBordaRecheada() {
		return bordaRecheada;
	}

	public void setBordaRecheada(boolean bordaRecheada) {
		this.bordaRecheada = bordaRecheada;
	}
	
	public double precoVenda() {
		return 0;
	}
}
