package Business;

public class PratoFeito extends Produto {
    public PratoFeito() {
        super(OpcoesComida.PRATO_FEITO.getPreco());
    }

    @Override
	public String toString() {
		return "Produto: prato feito; Preço: R$" + this.precoVenda();
	}
}
