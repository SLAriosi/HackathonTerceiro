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

    public void salvarAgenda(Agenda agenda) throws SQLException {
        agendaDAO.inserir(agenda);
    }

    public void atualizarAgenda(Agenda agenda) throws SQLException {
        agendaDAO.atualizar(agenda);
    }

    public void deletarAgenda(Long id) throws SQLException {
        agendaDAO.excluir(id);
    }

    public List<Agenda> listarTodasAgendas() throws SQLException {
        return agendaDAO.listarTodos();
    }

    public Agenda buscarAgendaPorId(Long id) throws SQLException {
        return agendaDAO.buscarPorId(id);
    }

    public List<Agenda> pesquisar(String termo) throws SQLException {
        return agendaDAO.pesquisar(termo);
    }

    public void fecharConexao() {
        agendaDAO.fecharConexao();
    }
}
