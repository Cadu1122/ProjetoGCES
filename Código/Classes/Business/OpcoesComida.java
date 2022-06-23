package Business;

public enum OpcoesComida {
    PRATO_FEITO(15),
    SANDUICHE(12),
    PIZZA(25);

    private double preco;

    private OpcoesComida(double preco) {
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase().replaceAll("_", " ");
    }
}
