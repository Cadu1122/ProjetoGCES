package Business;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class Produto implements Serializable {
	public static final Map<String, Double> PRECO_BASE = Collections.unmodifiableMap(Map.of("agua", 2d,
			"suco", 5d, "refrigerante", 5d, "cerveja", 8d, "prato feito", 15d, "pizza",
			25d, "sanduiche", 12d));
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
