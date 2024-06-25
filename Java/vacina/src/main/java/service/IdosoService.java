package service;

import dao.IdosoDAO;
import model.Idoso;

import java.sql.SQLException;
import java.util.List;

public class IdosoService {
    private IdosoDAO idosoDAO;

    public IdosoService() throws SQLException {
        this.idosoDAO = new IdosoDAO();
    }

    public void salvarIdoso(Idoso idoso) throws SQLException {
        idosoDAO.inserir(idoso);
    }

    public void atualizarIdoso(Idoso idoso) throws SQLException {
        idosoDAO.atualizar(idoso);
    }

    public void deletar(int id) throws SQLException {
        idosoDAO.deletar(id);
    }

    public List<Idoso> findAll() throws SQLException {
        return idosoDAO.listarTodos();
    }

    public Idoso buscarPorCPF(String cpf) throws SQLException {
        return idosoDAO.buscarPorCPF(cpf);
    }

    public Idoso buscarPorId(int id) throws SQLException {
        return idosoDAO.buscarPorId(id);
    }
}
