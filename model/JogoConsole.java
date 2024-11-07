package model;

public class JogoConsole extends Jogo {
    private String console;

    // Construtor
    public JogoConsole(String nome, String descricao, String genero, String dataLancamento, double preco, String imagem, String console, String vendedorNome) {
        super(nome, descricao, genero, dataLancamento, preco, imagem, vendedorNome);
        this.console = console;
    }

    // Getter e Setter para console
    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    @Override
    public String toTexto() {
        return String.join(",", "Console", getNome(), getDescricao(), getGenero(), getDataLancamento(), String.valueOf(getPreco()), getImagem(), getVendedorNome(), console);
    }

    // Método fromTexto para criar instâncias de JogoConsole
    public static JogoConsole fromTexto(String linha) {
        String[] partes = linha.split(",");
        if (partes.length < 9) {
            return null;
        }

        String nome = partes[1];
        String descricao = partes[2];
        String genero = partes[3];
        String dataLancamento = partes[4];
        double preco = Double.parseDouble(partes[5]);
        String imagem = partes[6];
        String vendedorNome = partes[7];
        String console = partes[8];

        return new JogoConsole(nome, descricao, genero, dataLancamento, preco, imagem, console, vendedorNome);
    }
}
