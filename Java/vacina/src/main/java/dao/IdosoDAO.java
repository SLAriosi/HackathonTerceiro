package dao;

import model.Idoso;

import java.sql.*;
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
        String sql = "INSERT INTO idoso (nome, cpf, cep, telefone, numero_casa, condicoes, data_nascimento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, idoso.getNome());
            stmt.setString(2, idoso.getCpf());
            stmt.setString(3, idoso.getCep());
            stmt.setString(4, idoso.getTelefone());
            stmt.setString(5, idoso.getNumeroCasa());
            stmt.setString(6, idoso.getCondicoes());
            stmt.setDate(7, new Date(idoso.getDataNascimento().getTime()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir o idoso.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idoso.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para o idoso.");
                }
            }
        }
    }

    public void atualizar(Idoso idoso) throws SQLException {
        String sql = "UPDATE idoso SET nome=?, cpf=?, cep=?, telefone=?, numero_casa=?, condicoes=?, data_nascimento=? " +
                "WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, idoso.getNome());
            stmt.setString(2, idoso.getCpf());
            stmt.setString(3, idoso.getCep());
            stmt.setString(4, idoso.getTelefone());
            stmt.setString(5, idoso.getNumeroCasa());
            stmt.setString(6, idoso.getCondicoes());
            stmt.setDate(7, new Date(idoso.getDataNascimento().getTime()));
            stmt.setInt(8, idoso.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar o idoso.");
            }
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM idoso WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao excluir o idoso.");
            }
        }
    }

    public List<Idoso> listarTodos() throws SQLException {
        List<Idoso> idosos = new ArrayList<>();
        String sql = "SELECT * FROM idoso";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Idoso idoso = construirIdoso(rs);
                idosos.add(idoso);
            }
        }
        return idosos;
    }

    public Idoso buscarPorCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM idoso WHERE cpf=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirIdoso(rs);
                }
            }
        }
        return null;
    }

    public Idoso buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM idoso WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirIdoso(rs);
                }
            }
        }
        return null;
    }

    private Idoso construirIdoso(ResultSet rs) throws SQLException {
        return new Idoso(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("cep"),
                rs.getString("cpf"),
                rs.getString("numero_casa"),
                rs.getString("condicoes"),
                rs.getDate("data_nascimento")
        );
    }

    public void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar conexão com banco de dados: " + e.getMessage());
        }
    }
}
