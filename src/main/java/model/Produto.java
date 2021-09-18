package model;

import java.io.Serializable;
import java.time.*;
import java.sql.*;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String DESCRICAO_PADRAO = "Novo Produto";
    public static final int MAX_ESTOQUE = 1000;

    private int id;
    private String descricao;
    private float preco;
    private int quantidade;
    private LocalDateTime dataFabricacao;
    private LocalDate dataValidade;

    public Produto(int id, String descricao, float preco, int quantidade, LocalDateTime fabricacao, LocalDate v) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.dataFabricacao = fabricacao;
        this.dataValidade = v;
    }

    public Produto() {
        this(-1, DESCRICAO_PADRAO, 0.01F, 0, LocalDateTime.now(), LocalDate.now().plusMonths(6));
    }

    public Produto(int id, String descricao, float preco, int quantidade, Timestamp fabricacao, Date v) {
        this(id, descricao, preco, quantidade, fabricacao.toLocalDateTime(), v.toLocalDate());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao.length() >= 3)
            this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        if (preco > 0)
            this.preco = preco;
    }

    public int getQuant() {
        return quantidade;
    }

    public void setQuant(int quantidade) {
        if (quantidade >= 0 && quantidade <= MAX_ESTOQUE)
            this.quantidade = quantidade;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public LocalDateTime getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(LocalDateTime dataFabricacao) {
        // Pega a Data Atual
        LocalDateTime agora = LocalDateTime.now();
        // Garante que a data de fabricação não pode ser futura
        if (agora.compareTo(dataFabricacao) >= 0)
            this.dataFabricacao = dataFabricacao;
    }

    public void setDataValidade(LocalDate dataValidade) {
        // a data de fabricação deve ser anterior é data de validade.
        if (getDataFabricacao().isBefore(dataValidade.atStartOfDay()))
            this.dataValidade = dataValidade;
    }

    public boolean emValidade() {
        return LocalDateTime.now().isBefore(this.getDataValidade().atTime(23, 59));
    }

    /**
     * Método sobreposto da classe Object. É executado quando um objeto precisa ser
     * exibido na forma de String.
     */
    @Override
    public String toString() {
        return "Produto: " + descricao + "   Preço: R$" + preco + "   Quant.: " + quantidade + "   Fabricação: "
                + dataFabricacao + "   Data de Validade: " + dataValidade;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Produto) obj).getId());
    }
}
