package dao;

import model.Idoso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IdosoDAO {

    private final Connection connection;

    public IdosoDAO() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alfacare?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public void inserir(Idoso idoso) throws SQLException {
        String sql = "INSERT INTO idoso (nome, cpf, cep, telefone, numero_casa, condicoes, data_nascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idoso.getNome());
            ps.setString(2, idoso.getCpf());
            ps.setString(3, idoso.getCep());
            ps.setString(4, idoso.getTelefone());
            ps.setString(5, idoso.getNumero_casa());
            ps.setString(6, idoso.getCondicoes());
            ps.setDate(7, new java.sql.Date(idoso.getDataNascimento().getTime()));
            ps.executeUpdate(); // Usar executeUpdate para INSERT
        }
    }


    public void atualizar(Idoso idoso) throws SQLException {
        String sql = "UPDATE idoso SET nome = ?, cpf = ?, cep = ?, telefone = ?, numero_casa = ?, condicoes = ?, data_nascimento = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idoso.getNome());
            ps.setString(2, idoso.getCpf());
            ps.setString(3, idoso.getCep());
            ps.setString(4, idoso.getTelefone());
            ps.setString(5, idoso.getNumero_casa());
            ps.setString(6, idoso.getCondicoes());
            ps.setDate(7, new java.sql.Date(idoso.getDataNascimento().getTime()));
            ps.setInt(8, idoso.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM idoso WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Idoso> listarTodos() throws SQLException {
        List<Idoso> idosos = new ArrayList<>();
        String sql = "SELECT * FROM idoso";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Idoso idoso = new Idoso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("cep"),
                        rs.getString("telefone"),
                        rs.getString("numero_casa"),
                        rs.getString("condicoes"),
                        rs.getDate("data_nascimento")
                );
                idosos.add(idoso);
            }
        }

        return idosos;
    }

    public void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão com banco de dados: " + e.getMessage());
        }
    }
}
