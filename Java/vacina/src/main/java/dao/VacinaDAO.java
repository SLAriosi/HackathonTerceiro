package dao;

import model.Vacina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacinaDAO {

    private final Connection connection;

    public VacinaDAO() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alfacare?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void inserir(Vacina vacina) {
        String sql = "INSERT INTO Vacina (nome) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vacina.getNome());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir vacina", e);
        }
    }

    public List<Vacina> listarTodos() {
        List<Vacina> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM Vacina";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Vacina vacina = new Vacina();
                vacina.setId(rs.getLong("id"));
                vacina.setNome(rs.getString("nome"));
                vacinas.add(vacina);
            }
            return vacinas; // Retornar a lista de vacinas após o loop
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vacinas", e);
        }
    }

    public void atualizar(Vacina vacina) {
        String sql = "UPDATE Vacina SET nome = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vacina.getNome());
            ps.setLong(2, vacina.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar vacina", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM Vacina WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar vacina", e);
        }
    }

    public void fecharConexao() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar conexão com o banco de dados", e);
            }
        }
    }
}
