package dao;

import model.Agenda;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {
    private final Connection connection;

    public AgendaDAO() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alfacare?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public void inserir(Agenda agenda) throws SQLException {
        String sql = "INSERT INTO agenda (data, horario, `agente-saude_id`, vacina, nome, cpf, cep, telefone, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            preencherStatement(stmt, agenda);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Agenda agenda) throws SQLException {
        String sql = "UPDATE agenda SET data = ?, horario = ?, `agente-saude_id` = ?, vacina = ?, nome = ?, cpf = ?, cep = ?, telefone = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            preencherStatement(stmt, agenda);
            stmt.setLong(10, agenda.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM agenda WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Agenda> listarTodos() throws SQLException {
        List<Agenda> agendas = new ArrayList<>();
        String sql = "SELECT * FROM agenda";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                agendas.add(criarAgenda(rs));
            }
        }
        return agendas;
    }

    public List<String> listarNomesAgentes() throws SQLException {
        List<String> nomesAgentes = new ArrayList<>();
        String sql = "SELECT nome FROM `agente-saude`";  // Use crases (`) ao redor do nome da tabela
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nomesAgentes.add(rs.getString("nome"));
            }
        }
        return nomesAgentes;
    }


    public List<Agenda> buscarPorCPF(String cpf) throws SQLException {
        List<Agenda> agendas = new ArrayList<>();
        String sql = "SELECT * FROM agenda WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    agendas.add(criarAgenda(rs));
                }
            }
        }
        return agendas;
    }

    public long getAgenteSaudeIdPorNome(String nome) throws SQLException {
        String sql = "SELECT id FROM `agente-saude` WHERE nome = ?";  // Use crases (`) ao redor do nome da tabela
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                } else {
                    throw new SQLException("Agente de saúde não encontrado com o nome: " + nome);
                }
            }
        }
    }

    private void preencherStatement(PreparedStatement stmt, Agenda agenda) throws SQLException {
        stmt.setObject(1, agenda.getData());
        stmt.setObject(2, agenda.getHorario());
        stmt.setLong(3, agenda.getAgenteSaudeId());
        stmt.setString(4, agenda.getVacina());
        stmt.setString(5, agenda.getNome());
        stmt.setString(6, agenda.getCpf());
        stmt.setString(7, agenda.getCep());
        stmt.setString(8, agenda.getTelefone());
        stmt.setObject(9, agenda.getCreatedAt());
        stmt.setObject(10, agenda.getUpdatedAt());
    }

    private Agenda criarAgenda(ResultSet rs) throws SQLException {
        LocalDate data = rs.getObject("data", LocalDate.class);
        LocalTime horario = rs.getObject("horario", LocalTime.class);
        long agenteSaudeId = rs.getLong("agente-saude_id");
        String vacina = rs.getString("vacina");
        String nome = rs.getString("nome");
        String cpf = rs.getString("cpf");
        String cep = rs.getString("cep");
        String telefone = rs.getString("telefone");
        LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
        LocalDateTime updatedAt = rs.getObject("updated_at", LocalDateTime.class);
        return new Agenda(rs.getLong("id"), data, horario, agenteSaudeId, vacina, nome, cpf, cep, telefone, createdAt, updatedAt);
    }

    public void fecharConexao() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
