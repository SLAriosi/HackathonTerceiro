package dao;

import model.AgenteSaude;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteSaudeDAO {

    private final Connection connection;

    public AgenteSaudeDAO() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alfacare?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public void salvar(AgenteSaude agente) {
        String sql = "INSERT INTO `agente-saude` (nome, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, agente.getNome());
            ps.setString(2, agente.getUsername());
            // Hashing the password before saving
            String hashedPassword = BCrypt.hashpw(agente.getPassword(), BCrypt.gensalt());
            ps.setString(3, hashedPassword);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                agente.setId(id); // Define o ID gerado no objeto AgenteSaude
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar agente de saúde", e);
        }
    }


    public List<AgenteSaude> listarTodos() {
        String sql = "SELECT * FROM `agente-saude`";
        List<AgenteSaude> agentes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AgenteSaude agente = new AgenteSaude(rs.getLong("id"), rs.getString("nome"), rs.getString("username"), rs.getString("password"));
                agentes.add(agente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agentes de saúde", e);
        }
        return agentes;
    }


    public AgenteSaude buscarPorId(Long id) {
        AgenteSaude agente = null;
        String sql = "SELECT * FROM `agente-saude` WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    agente = new AgenteSaude(rs.getLong("id"), rs.getString("nome"), rs.getString("username"), rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar agente de saúde por ID", e);
        }
        return agente;
    }


    public void atualizar(AgenteSaude agente) {
        String sql = "UPDATE `agente-saude` SET nome = ?, username = ?, password = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, agente.getNome());
            ps.setString(2, agente.getUsername());
            // Hashing the password before updating
            String hashedPassword = BCrypt.hashpw(agente.getPassword(), BCrypt.gensalt());
            ps.setString(3, hashedPassword);
            ps.setLong(4, agente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar agente de saúde", e);
        }
    }



    public void deletar(Long id) {
        String sql = "DELETE FROM `agente-saude` WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar agente de saúde", e);
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
