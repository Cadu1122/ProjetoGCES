public class Sanduiche extends ProdutosComAdicionais {
	private boolean paoArtesanal;
	
	public Sanduiche() {
	}

	public Sanduiche(boolean bordaRecheada, double precoAdicional) {
		this.paoArtesanal = bordaRecheada;

	}

	public Sanduiche(double pRECO_ADC, String adicionais, boolean bordaRecheada) {
		super(pRECO_ADC, adicionais);
		this.paoArtesanal = bordaRecheada;
	}
	
	public boolean isBordaRecheada() {
		return paoArtesanal;
	}

	public void setBordaRecheada(boolean bordaRecheada) {
		this.paoArtesanal = bordaRecheada;
	}

	public double precoVenda() {
		return getPrecoBase()+getPRECO_ADC();
	}
	
	public String toString() {
		return "Preço do Sanduiche com pão artesanal:"+precoVenda();
	}
}

