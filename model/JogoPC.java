package model;

public class JogoPC extends Jogo {
    private String requisitosPc;

    // Construtor
    public JogoPC(String nome, String descricao, String genero, String dataLancamento, double preco, String imagem, String requisitosPc, String vendedorNome) {
        super(nome, descricao, genero, dataLancamento, preco, imagem, vendedorNome);
        this.requisitosPc = requisitosPc;
    }

    // Getter e Setter para requisitosPc
    public String getRequisitosPc() {
        return requisitosPc;
    }

    public void setRequisitosPc(String requisitosPc) {
        this.requisitosPc = requisitosPc;
    }

    @Override
    public String toTexto() {
        return String.join(",", "PC", getNome(), getDescricao(), getGenero(), getDataLancamento(), String.valueOf(getPreco()), getImagem(), getVendedorNome(), requisitosPc);
    }

    // Método fromTexto para criar instâncias de JogoPC
    public static JogoPC fromTexto(String linha) {
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
        String requisitosPc = partes[8];

        return new JogoPC(nome, descricao, genero, dataLancamento, preco, imagem, requisitosPc, vendedorNome);
    }
}
