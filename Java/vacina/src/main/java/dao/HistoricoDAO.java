package dao;

import model.Historico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO historico (idoso_id, agenda_id, vacina_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, historico.getIdosoId());
            ps.setLong(2, historico.getAgendaId());
            ps.setLong(3, historico.getVacinaId());
            ps.execute();
        }
    }

    public void atualizar(Historico historico) throws SQLException {
        String sql = "UPDATE historico SET idoso_id = ?, agenda_id = ?, vacina_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, historico.getIdosoId());
            ps.setLong(2, historico.getAgendaId());
            ps.setLong(3, historico.getVacinaId());
            ps.setLong(4, historico.getId());
            ps.execute();
        }
    }

    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM historico WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.execute();
        }
    }


    public List<Historico> listarTodos() throws SQLException {
        List<Historico> historicos = new ArrayList<>();
        String sql = "SELECT * FROM historico";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                historicos.add(new Historico(
                        rs.getLong("id"),
                        rs.getLong("idoso_id"),
                        rs.getLong("agenda_id"),
                        rs.getLong("vacina_id")
                ));
            }
        }

        return historicos;
    }

    public Historico buscarPorId(Long id) throws SQLException {
        Historico historico = null;
        String sql = "SELECT * FROM historico WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    historico = new Historico(
                            rs.getLong("id"),
                            rs.getLong("idoso_id"),
                            rs.getLong("agenda_id"),
                            rs.getLong("vacina_id")
                    );
                }
            }
        }

        return historico;
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
