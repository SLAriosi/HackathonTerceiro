package service;

import dao.AgendaDAO;
import model.Agenda;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AgendaService {
    private final AgendaDAO agendaDAO;

    public AgendaService() throws SQLException {
        this.agendaDAO = new AgendaDAO();
    }

    public void salvar(Agenda agenda) throws SQLException {
        if (agenda.getId() == 0) {
            agenda.setCreatedAt(LocalDateTime.now());
            agenda.setUpdatedAt(LocalDateTime.now());
            agendaDAO.inserir(agenda);
        } else {
            agenda.setUpdatedAt(LocalDateTime.now());
            agendaDAO.atualizar(agenda);
        }
    }

    public void deletar(long id) throws SQLException {
        agendaDAO.excluir(id);
    }

    public List<Agenda> listar() throws SQLException {
        return agendaDAO.listarTodos();
    }

    public List<Agenda> buscarPorCPF(String cpf) throws SQLException {
        return agendaDAO.buscarPorCPF(cpf);
    }

    public long getAgenteSaudeIdPorNome(String nome) throws SQLException {
        return agendaDAO.getAgenteSaudeIdPorNome(nome);
    }

    public List<String> listarNomesAgentes() throws SQLException {
        return agendaDAO.listarNomesAgentes();
    }

    public void fecharConexao() {
        agendaDAO.fecharConexao();
    }
}
