package ProjetoGCES.Business.Produtos;

public enum OpcoesPratoPersonalizado {
    ARROZ(5),
    FEIJAO(4),
    BATATA_FRITA(6),
    SALADA(3),
    MACARRAO(5),
    FRANGO(7),
    BOI(7),
    PORCO(7);

    private double preco;

    private OpcoesPratoPersonalizado(double preco) {
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
