package dao;

import model.Agenda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {
    private final Connection connection;

    public AgendaDAO() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/alfacare", "root", "");
    }

    public void salvar(Agenda agenda) throws SQLException {
        if (agenda.getId() == 0) {
            inserir(agenda);
        } else {
            atualizar(agenda);
        }
    }

    private void inserir(Agenda agenda) throws SQLException {
        String sql = "INSERT INTO agenda (data, horario, 'agente-saude_id', vacina, nome, cpf, cep, telefone, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(agenda.getData()));
            stmt.setTime(2, Time.valueOf(agenda.getHorario()));
            stmt.setLong(3, agenda.getAgenteSaudeId());
            stmt.setString(4, agenda.getVacina());
            stmt.setString(5, agenda.getNome());
            stmt.setString(6, agenda.getCpf());
            stmt.setString(7, agenda.getCep());
            stmt.setString(8, agenda.getTelefone());
            stmt.setTimestamp(9, Timestamp.valueOf(agenda.getCreatedAt()));
            stmt.setTimestamp(10, Timestamp.valueOf(agenda.getUpdatedAt()));
            stmt.executeUpdate();
        }
    }

    private void atualizar(Agenda agenda) throws SQLException {
        String sql = "UPDATE agenda SET data = ?, horario = ?, 'agente-saude_id' = ?, vacina = ?, nome = ?, cpf = ?, cep = ?, telefone = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(agenda.getData()));
            stmt.setTime(2, Time.valueOf(agenda.getHorario()));
            stmt.setLong(3, agenda.getAgenteSaudeId());
            stmt.setString(4, agenda.getVacina());
            stmt.setString(5, agenda.getNome());
            stmt.setString(6, agenda.getCpf());
            stmt.setString(7, agenda.getCep());
            stmt.setString(8, agenda.getTelefone());
            stmt.setTimestamp(9, Timestamp.valueOf(agenda.getUpdatedAt()));
            stmt.setLong(10, agenda.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(long id) throws SQLException {
        String sql = "DELETE FROM agenda WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Agenda> listarTodos() throws SQLException {
        String sql = "SELECT * FROM agenda";
        List<Agenda> agendas = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Agenda agenda = extrairAgenda(rs);
                agendas.add(agenda);
            }
        }
        return agendas;
    }

    public List<Agenda> buscarPorCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM agenda WHERE cpf = ?";
        List<Agenda> agendas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agenda agenda = extrairAgenda(rs);
                    agendas.add(agenda);
                }
            }
        }
        return agendas;
    }

    public long getAgenteSaudeIdPorNome(String nome) throws SQLException {
        String sql = "SELECT id FROM `agente-saude` WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                } else {
                    throw new SQLException("Agente de saúde não encontrado.");
                }
            }
        }
    }

    public List<String> listarNomesAgentes() throws SQLException {
        String sql = "SELECT nome FROM `agente-saude`";
        List<String> nomes = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                nomes.add(rs.getString("nome"));
            }
        }
        return nomes;
    }

    public List<String> listarNomesVacinas() throws SQLException {
        String sql = "SELECT nome FROM vacina";
        List<String> nomesVacinas = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                nomesVacinas.add(rs.getString("nome"));
            }
        }
        return nomesVacinas;
    }

    public String getNomeAgentePorId(long agenteId) throws SQLException {
        String sql = "SELECT nome FROM `agente-saude` WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, agenteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nome");
                } else {
                    throw new SQLException("Agente de saúde não encontrado.");
                }
            }
        }
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

    private Agenda extrairAgenda(ResultSet rs) throws SQLException {
        return new Agenda(
                rs.getLong("id"),
                rs.getDate("data").toLocalDate(),
                rs.getTime("horario").toLocalTime(),
                rs.getLong("agente-saude_id"),
                rs.getString("vacina"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("cep"),
                rs.getString("telefone"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    // Novo método para listar alertas e lembretes
    public List<String> listarAlertasLembretes() throws SQLException {
        String sql = "SELECT data, vacina, nome FROM agenda";
        List<String> alertasLembretes = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Date data = rs.getDate("data");
                String vacina = rs.getString("vacina");
                String nome = rs.getString("nome");
                alertasLembretes.add("Lembrete: Vacina de " + vacina + " para " + nome + " em " + data.toString());
            }
        }
        return alertasLembretes;
    }
}
