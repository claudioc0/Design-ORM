package model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Ou outra estratégia de herança que preferir
public abstract class Jogo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto incremento
    private Long id;  // Identificador da entidade

    private String nome;
    private String descricao;
    private String genero;
    private String dataLancamento;
    private double preco;
    private String imagem;
    private String vendedorNome;

    // Construtor
    public Jogo(String nome, String descricao, String genero, String dataLancamento, double preco, String imagem, String vendedorNome) {
        this.nome = nome;
        this.descricao = descricao;
        this.genero = genero;
        this.dataLancamento = dataLancamento;
        this.preco = preco;
        this.imagem = imagem;
        this.vendedorNome = vendedorNome;
    }

    // Getter e Setter para 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters e Setters para outros campos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getVendedorNome() {
        return vendedorNome;
    }

    public void setVendedorNome(String vendedorNome) {
        this.vendedorNome = vendedorNome;
    }

    // Métodos abstratos
    public abstract String toTexto();

    public static Jogo fromTexto(String linha) {
        // Implementação do fromTexto permanece
        return null;  // Exemplo de retorno
    }
}