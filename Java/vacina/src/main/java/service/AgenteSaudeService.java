package service;

import dao.AgenteSaudeDAO;
import model.AgenteSaude;

import java.sql.SQLException;
import java.util.List;

public class AgenteSaudeService {

    private final AgenteSaudeDAO agenteSaudeDAO;

    public AgenteSaudeService() throws SQLException {
        this.agenteSaudeDAO = new AgenteSaudeDAO();
    }

    public void save(AgenteSaude agente) throws SQLException {
        agenteSaudeDAO.salvar(agente);
    }

    public List<AgenteSaude> getAll() throws SQLException {
        return agenteSaudeDAO.listarTodos();
    }

    public AgenteSaude getById(Long id) throws SQLException {
        return agenteSaudeDAO.buscarPorId(id);
    }

    public void fecharConexao() {
        agenteSaudeDAO.fecharConexao();
    }
}
