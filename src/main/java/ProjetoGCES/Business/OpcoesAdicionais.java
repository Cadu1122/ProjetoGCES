package ProjetoGCES.Business;

public enum OpcoesAdicionais {
    PEPPERONI(4),
    BACON(3),
    PALMITO(3),
    QUEIJO(2),
    PICLES(2),
    OVO(2),
    BATATA_PALHA(2);

    private double preco;

    private OpcoesAdicionais(double preco) {
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
