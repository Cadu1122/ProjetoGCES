package Business.Produtos;

public class Sanduiche extends ProdutosComAdicionais {
	public static final double PRECO_PAO_ARTESANAL = 2d;

	private boolean paoArtesanal;

	public Sanduiche(boolean paoArtesanal) {
		super(OpcoesComida.SANDUICHE.getPreco());
		this.paoArtesanal = paoArtesanal;
	}
	
	public boolean isPaoArtesanal() {
		return paoArtesanal;
	}

	public double precoVenda() {
		return getPrecoBase() + precoAdicionais() + (isPaoArtesanal() ? PRECO_PAO_ARTESANAL : 0);
	}

	@Override
	public String toString() {
		return "Produto: Sanduíche; Adicionais: " + 
		this.getAdicionais().stream()
			.map(a -> a.toString())
			.reduce((a, b) -> a.concat(", " + b))
			.orElse("sem adicionais") + 
		"; Preço: R$" + this.precoVenda();
	}
}

