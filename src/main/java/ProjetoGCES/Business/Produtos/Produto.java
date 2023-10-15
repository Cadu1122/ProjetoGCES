package Business.Produtos;

import java.io.Serializable;

public abstract class Produto implements Serializable {
	private double preco;

	public Produto(double preco) {
		this.setPreco(preco);
	}

	public double getPrecoBase() {
		return preco;
	}

	private void setPreco(double preco) {
		this.preco = preco;
	}

	/**
	 * Calcula o preco de venda do produto
	 * @return Preco de venda total
	 */
	public double precoVenda() {
		return getPrecoBase();
	}
}
