package dao;

import model.Produto;
import java.sql.*;

public class ProdutoDAO {
    private Connection conexao;

    public ProdutoDAO() throws Exception {
        String driverName = "org.postgresql.Driver";

        String server = "localhost";
        int porta = 5432;

        String username = "postgres";
        String password = "1234";

        String mydatabase = "postgres";

        String url = "jdbc:postgresql://" + server + ":" + porta + "/" + mydatabase;
        Class.forName(driverName);

        conexao = DriverManager.getConnection(url, username, password);
    }

    public int getRandomId() {
        return (int) (Math.random() * (1000));
    }

    public boolean close() {
        boolean status = false;

        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean inserirProduto(Produto produto) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO produto (id, descricao, preco, quantidade, dataFabricacao, dataValidade) "
                    + "VALUES (" + produto.getId() + ", '" + produto.getDescricao() + "', '" + produto.getPreco()
                    + "', '" + produto.getQuant() + "', '" + produto.getDataFabricacao() + "', '"
                    + produto.getDataValidade() + "');");
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean atualizarProduto(Produto produto) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE produto SET descricao = '" + produto.getDescricao() + "', preco = '"
                    + produto.getPreco() + "', quantidade = '" + produto.getQuant() + "', dataFabricacao = '"
                    + produto.getDataFabricacao() + "', dataValidade = '" + produto.getDataValidade() + "'"
                    + " WHERE id = " + produto.getId();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean excluirProduto(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM produto WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Produto[] getProdutos() {
        Produto[] produtos = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM produto");
            if (rs.next()) {
                rs.last();
                produtos = new Produto[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    produtos[i] = new Produto(rs.getInt("id"), rs.getString("descricao"), rs.getFloat("preco"),
                    rs.getInt("quantidade"), rs.getTimestamp("dataFabricacao"), rs.getDate("dataValidade"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return produtos;
    }

    public Produto getProduto(int id) {
        Produto produto = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM produto WHERE produto.id = " + id);
            if (rs.first()) {
                produto = new Produto(rs.getInt("id"), rs.getString("descricao"), rs.getFloat("preco"),
                        rs.getInt("quantidade"), rs.getTimestamp("dataFabricacao"), rs.getDate("dataValidade"));
            }
            st.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return produto;
    }
}
