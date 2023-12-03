package ProjetoGCES.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.Set;

import ProjetoGCES.Business.Venda.Cliente;
import ProjetoGCES.App.Tela;

public class DAOManager {
    public static final File ARQUIVO_ARMAZENAMENTO = new File("historico.dat");
    public static int idGenerator = 1;

    /**
     * Carrega os clientes do banco de dados
     */
    @SuppressWarnings("unchecked")
    public static Set<Cliente> recuperarClientes() {
        Set<Cliente> clientes = new HashSet<>();
        
        try {
            if(ARQUIVO_ARMAZENAMENTO.exists()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ARQUIVO_ARMAZENAMENTO));
                clientes = (Set<Cliente>) objectInputStream.readObject();
                idGenerator = clientes.stream()
                    .mapToInt(a -> a.getPedidos().stream()
                        .mapToInt(b -> b.getId())
                        .max()
                        .orElse(0))
                    .max()
                    .orElse(0) + 1;
                objectInputStream.close();
            }
        } catch (SecurityException e) {
            System.err.println("O acesso ao arquivo foi negado");
            Tela.esperar();
        } catch (OptionalDataException e) {
            System.err.println("Os dados foram armazenados de forma incorreta no ultimo salvamento");
            Tela.esperar();
        } catch (StreamCorruptedException e) {
            System.err.println("Ocorreu um erro ao ler os arquivos");
            Tela.esperar();
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao acessar os arquivos");
            Tela.esperar();
        } catch (Exception e) {
            System.err.println("Um erro inesperado ocorreu");
            Tela.esperar();
        }
        return clientes;
    }

    /**
     * Salva clientes no banco de dados
     */
    public static void armazenarClientes(Set<Cliente> clientes) {
        try {
            if(!ARQUIVO_ARMAZENAMENTO.exists()) {
                ARQUIVO_ARMAZENAMENTO.createNewFile();
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ARQUIVO_ARMAZENAMENTO));
            objectOutputStream.writeObject(clientes);
            objectOutputStream.close();
        } catch (SecurityException e) {
            System.err.println("O acesso ao arquivo foi negado.");
            Tela.esperar();
        } catch (StreamCorruptedException e) {
            System.err.println("Ocorreu um erro ao salvar os arquivos.");
            Tela.esperar();
        } catch (NotSerializableException e) {
            System.err.println("Não foi possivel tranferir os dados para o arquivo.");
            Tela.esperar();
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao acessar os arquivos.");
            Tela.esperar();
        } catch (Exception e) {
            System.err.println("Um erro inesperado ocorreu.");
            Tela.esperar();
        }
    }
}
