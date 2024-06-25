package service;

import dao.AgendaDAO;
import model.Agenda;

import java.sql.SQLException;
import java.util.List;

public class AgendaService {

    private final AgendaDAO agendaDAO;

    public AgendaService() throws SQLException {
        this.agendaDAO = new AgendaDAO();
    }

    public void salvar(Agenda agenda) throws SQLException {
        agendaDAO.salvar(agenda);
    }

    public void excluir(long id) throws SQLException {
        agendaDAO.excluir(id);
    }

    public List<Agenda> listarTodos() throws SQLException {
        return agendaDAO.listarTodos();
    }

    public String getNomeAgentePorId(long id) throws SQLException {
        return agendaDAO.getNomeAgentePorId(id);
    }

    public long getAgenteSaudeIdPorNome(String nome) throws SQLException {
        return agendaDAO.getAgenteSaudeIdPorNome(nome);
    }

    public void fecharConexao() {
        agendaDAO.fecharConexao();
    }

    public List<String> listarNomesAgentes() throws SQLException {
        return agendaDAO.listarNomesAgentes();
    }

    public List<String> listarNomesVacinas() throws SQLException {
        return agendaDAO.listarNomesVacinas();
    }

    // Método para buscar agendamentos por CPF
    public List<Agenda> buscarPorCPF(String cpf) throws SQLException {
        return agendaDAO.buscarPorCPF(cpf);
    }
}
