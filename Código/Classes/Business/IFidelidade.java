package Business;

import java.io.Serializable;

public interface IFidelidade extends Serializable {
    public double calcularDesconto(Pedido pedido);
}