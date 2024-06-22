package dao;

import model.Agenda;

import java.sql.*;
import java.time.LocalDate;
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
        String sql = "INSERT INTO agenda (data, horario, agente-saude_id, vacina, nome, cpf, cep, telefone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, agenda.getData());
            stmt.setObject(2, agenda.getHorario());
            stmt.setLong(3, agenda.getAgenteSaudeId());
            stmt.setString(4, agenda.getVacina());
            stmt.setString(5, agenda.getNome());
            stmt.setString(6, agenda.getCpf());
            stmt.setString(7, agenda.getCep());
            stmt.setString(8, agenda.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Agenda agenda) throws SQLException {
        String sql = "UPDATE agenda SET data = ?, horario = ?, agente-saude_id = ?, vacina = ?, nome = ?, cpf = ?, cep = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, agenda.getData());
            ps.setObject(2, agenda.getHorario());
            ps.setLong(3, agenda.getAgenteSaudeId());
            ps.setString(4, agenda.getVacina());
            ps.setString(5, agenda.getNome());
            ps.setString(6, agenda.getCpf());
            ps.setString(7, agenda.getCep());
            ps.setString(8, agenda.getTelefone());
            ps.setLong(9, agenda.getId());
            ps.execute();
        }
    }

    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM agenda WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.execute();
        }
    }

    public List<Agenda> listarTodos() throws SQLException {
        List<Agenda> agendas = new ArrayList<>();
        String sql = "SELECT * FROM agenda";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LocalDate data = rs.getObject("data", LocalDate.class);
                LocalTime horario = rs.getObject("horario", LocalTime.class);
                long agenteSaudeId = rs.getLong("agente-saude_id");
                String vacina = rs.getString("vacina");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String cep = rs.getString("cep");
                String telefone = rs.getString("telefone");
                agendas.add(new Agenda(
                        rs.getLong("id"),
                        data,
                        horario,
                        agenteSaudeId,
                        vacina,
                        nome,
                        cpf,
                        cep,
                        telefone
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return agendas;
    }

    public Agenda buscarPorId(Long id) throws SQLException {
        Agenda agenda = null;
        String sql = "SELECT * FROM agenda WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LocalDate data = rs.getObject("data", LocalDate.class);
                    LocalTime horario = rs.getObject("horario", LocalTime.class);
                    long agenteSaudeId = rs.getLong("agente-saude_id");
                    String vacina = rs.getString("vacina");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String cep = rs.getString("cep");
                    String telefone = rs.getString("telefone");
                    agenda = new Agenda(
                            rs.getLong("id"),
                            data,
                            horario,
                            agenteSaudeId,
                            vacina,
                            nome,
                            cpf,
                            cep,
                            telefone
                    );
                }
            }
        }

        return agenda;
    }

    public List<Agenda> pesquisar(String termo) throws SQLException {
        List<Agenda> agendas = new ArrayList<>();
        String sql = "SELECT * FROM agenda WHERE vacina LIKE ? OR nome LIKE ? OR cpf LIKE ? OR cep LIKE ? OR telefone LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchTerm = "%" + termo + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);
            stmt.setString(4, searchTerm);
            stmt.setString(5, searchTerm);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate data = rs.getObject("data", LocalDate.class);
                    LocalTime horario = rs.getObject("horario", LocalTime.class);
                    long agenteSaudeId = rs.getLong("agente-saude_id");
                    String vacina = rs.getString("vacina");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String cep = rs.getString("cep");
                    String telefone = rs.getString("telefone");
                    agendas.add(new Agenda(
                            rs.getLong("id"),
                            data,
                            horario,
                            agenteSaudeId,
                            vacina,
                            nome,
                            cpf,
                            cep,
                            telefone
                    ));
                }
            }
        }
        return agendas;
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
