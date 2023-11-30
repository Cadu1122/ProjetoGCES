package ProjetoGCES.App;

import java.util.Scanner;

public class Tela {
    public static Scanner teclado = new Scanner(System.in);

    /**
     * Dá ao usuário tempo para ler a mensagem na tela
     */
    public static void esperar() {
        System.out.println("---------------------------------------------");
        System.out.println("Pressione enter para continuar");
        teclado.nextLine();
    }
}
