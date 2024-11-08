// JogoFacade.java
import model.Jogo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JogoFacade implements Subject {
    private static final String ARQUIVO_JOGOS_ANUNCIADOS = "jogos_anunciados.txt";
    private static final String ARQUIVO_HISTORICO_VENDAS = "historico_vendas.txt";
    private static final String ARQUIVO_HISTORICO_COMPRAS = "_historico_compras.txt";

    // Singleton Instance
    private static JogoFacade instance;

    private List<Observer> observers; // Lista de observadores

    private JogoFacade() {
        observers = new ArrayList<>();
    }

    public static JogoFacade getInstance() {
        if (instance == null) {
            instance = new JogoFacade();
        }
        return instance;
    }

    // Implementação dos métodos da interface Subject
    @Override
    public void addObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Jogo novoJogo) {
        for (Observer o : observers) {
            o.update(novoJogo);
        }
    }

    // Carregar Jogos Anunciados
    public List<Jogo> carregarJogosAnunciados() {
        List<Jogo> jogos = new ArrayList<>();
        File arquivo = new File(ARQUIVO_JOGOS_ANUNCIADOS);
        if (!arquivo.exists()) {
            return jogos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Jogo jogo = Jogo.fromTexto(linha);
                if (jogo != null) {
                    jogos.add(jogo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jogos;
    }

    // Salvar Jogos Anunciados
    public void salvarJogosAnunciados(List<Jogo> jogosAnunciados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_JOGOS_ANUNCIADOS))) {
            for (Jogo jogo : jogosAnunciados) {
                writer.write(jogo.toTexto());
                writer.newLine();
                notifyObservers(jogo); // Notifica os observadores sobre o novo jogo
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Registrar Venda
    public void registrarVenda(Cliente cliente, String metodoPagamento, Jogo jogo, String infoPagamento) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_HISTORICO_VENDAS, true))) { // Modo append
            String registro = "Cliente: " + cliente.getNome() +
                    ", model.Jogo: " + jogo.getNome() +
                    ", Data: " + LocalDate.now() +
                    ", Preço: R$" + jogo.getPreco() +
                    ", Vendedor: " + jogo.getVendedorNome() +
                    ", Método de Pagamento: " + metodoPagamento +
                    ", Info Pagamento: " + infoPagamento;
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Registrar Compra no Histórico do Cliente
    public void registrarCompra(Cliente cliente, Jogo jogo, String metodoPagamento) {
        String arquivoHistoricoCliente = cliente.getNome().replaceAll("\\s+", "_") + ARQUIVO_HISTORICO_COMPRAS;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoHistoricoCliente, true))) { // Modo append
            String registro = "model.Jogo: " + jogo.getNome() +
                    ", Data: " + LocalDate.now() +
                    ", Preço: R$" + jogo.getPreco() +
                    ", Método de Pagamento: " + metodoPagamento;
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
