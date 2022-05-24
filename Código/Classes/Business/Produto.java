public class Produto {
	public static float PRECO_BASE[];
	private String tipo;
	private String nome;
	private double precoBase;

	public Produto(){}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPrecoBase() {
		return precoBase;
	}

	public void setPrecoBase(double precoBase) {
		this.precoBase = precoBase;
	}

	public Produto(String nome, double precoBase) {
		this.setNome(nome);
		this.setPrecoBase(precoBase);
	}
	

	public static float[] getPRECO_BASE() {
		return PRECO_BASE;
	}


	public static void setPRECO_BASE(float[] pRECO_BASE) {
		PRECO_BASE = pRECO_BASE;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double precoVenda() {
		return getPrecoBase();
	}

	@Override
	public String toString() {
		return "Produto [PRECO_BASE=" + PRECO_BASE + ", tipo=" + tipo + "]";
	}
}
