package dao;

import model.Historico;
import model.AgenteSaude;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {

    private final Connection connection;

    public HistoricoDAO() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alfacare?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public void inserir(Historico historico) throws SQLException {
        String sql = "INSERT INTO `historico` (`idoso_id`, `agenda_id`, `vacina_id`) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, historico.getIdosoId());
            ps.setLong(2, historico.getAgendaId());
            ps.setLong(3, historico.getVacinaId());
            ps.executeUpdate();
        }
    }

    public void atualizar(Historico historico) throws SQLException {
        String sql = "UPDATE `historico` SET `idoso_id` = ?, `agenda_id` = ?, `vacina_id` = ? WHERE `id` = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, historico.getIdosoId());
            ps.setLong(2, historico.getAgendaId());
            ps.setLong(3, historico.getVacinaId());
            ps.setLong(4, historico.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM `historico` WHERE `id` = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<Historico> listarTodos() throws SQLException {
        List<Historico> historicos = new ArrayList<>();
        String sql = "SELECT h.id, h.idoso_id, h.agenda_id, h.vacina_id, a.nome, a.username, a.password " +
                "FROM `historico` h " +
                "JOIN `agente-saude` a ON h.idoso_id = a.id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                historicos.add(new Historico(
                        rs.getLong("id"),
                        rs.getLong("idoso_id"),
                        rs.getLong("agenda_id"),
                        rs.getLong("vacina_id"),
                        new AgenteSaude(
                                rs.getLong("idoso_id"),
                                rs.getString("nome"),
                                rs.getString("username"),
                                rs.getString("password")
                        )
                ));
            }
        }

        return historicos;
    }

    public Historico buscarPorId(Long id) throws SQLException {
        Historico historico = null;
        String sql = "SELECT h.id, h.idoso_id, h.agenda_id, h.vacina_id, a.nome, a.username, a.password " +
                "FROM `historico` h " +
                "JOIN `agente-saude` a ON h.idoso_id = a.id " +
                "WHERE h.id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    historico = new Historico(
                            rs.getLong("id"),
                            rs.getLong("idoso_id"),
                            rs.getLong("agenda_id"),
                            rs.getLong("vacina_id"),
                            new AgenteSaude(
                                    rs.getLong("idoso_id"),
                                    rs.getString("nome"),
                                    rs.getString("username"),
                                    rs.getString("password")
                            )
                    );
                }
            }
        }

        return historico;
    }

    public List<AgenteSaude> listarAgentesSaude() throws SQLException {
        List<AgenteSaude> agentes = new ArrayList<>();
        String sql = "SELECT `id`, `nome`, `username`, `password` FROM `agente-saude`";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                agentes.add(new AgenteSaude(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        }

        return agentes;
    }

    public List<Historico> buscar(String query) throws SQLException {
        List<Historico> historicos = new ArrayList<>();
        String sql = "SELECT h.id, h.idoso_id, h.agenda_id, h.vacina_id, a.nome, a.username, a.password " +
                "FROM `historico` h " +
                "JOIN `agente-saude` a ON h.idoso_id = a.id " +
                "WHERE a.nome LIKE ? OR h.vacina_id LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historicos.add(new Historico(
                            rs.getLong("id"),
                            rs.getLong("idoso_id"),
                            rs.getLong("agenda_id"),
                            rs.getLong("vacina_id"),
                            new AgenteSaude(
                                    rs.getLong("idoso_id"),
                                    rs.getString("nome"),
                                    rs.getString("username"),
                                    rs.getString("password")
                            )
                    ));
                }
            }
        }

        return historicos;
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
