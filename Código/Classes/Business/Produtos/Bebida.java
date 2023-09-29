package Business.Produtos;

public class Bebida extends Produto {
    private OpcoesBebida tipo;

    public Bebida (OpcoesBebida tipo) {
        super(tipo.getPreco());
        this.tipo = tipo;
    }

    public OpcoesBebida getTipo() {
        return tipo;
    }

    @Override
	public String toString() {
		return "Produto: " + this.tipo + "; Preço: R$" + this.precoVenda();
	}
}
