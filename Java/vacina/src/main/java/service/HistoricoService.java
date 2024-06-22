package service;

import dao.HistoricoDAO;
import model.Historico;

import java.sql.SQLException;
import java.util.List;

public class HistoricoService {

    private final HistoricoDAO historicoDAO;

    public HistoricoService() throws SQLException {
        this.historicoDAO = new HistoricoDAO();
    }

    public void salvar(Historico historico) throws SQLException {
        historicoDAO.inserir(historico);
    }

    public void atualizar(Historico historico) throws SQLException {
        historicoDAO.atualizar(historico);
    }

    public void deletar(Long id) throws SQLException {
        historicoDAO.excluir(id);
    }

    public List<Historico> getAll() throws SQLException {
        return historicoDAO.listarTodos();
    }

    public Historico getById(Long id) throws SQLException {
        return historicoDAO.buscarPorId(id);
    }

    public void fecharConexao() {
        historicoDAO.fecharConexao();
    }
}
