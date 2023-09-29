package Business.Produtos;

public enum OpcoesBebida {
    CERVEJA(8),
    AGUA(2),
    SUCO(5),
    REFRIGERANTE(5);

    private double preco;
    
    private OpcoesBebida(double preco) {
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