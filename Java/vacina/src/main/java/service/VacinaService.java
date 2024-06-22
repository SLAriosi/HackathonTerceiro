package service;

import dao.VacinaDAO;
import model.Vacina;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class VacinaService {

    private final VacinaDAO dao;

    public VacinaService() throws SQLException {
        this.dao = new VacinaDAO();
    }

    public void salvar(Vacina vacina) {
        if (vacina.getId() == null) {
            dao.inserir(vacina);
        } else {
            dao.atualizar(vacina);
        }
    }

    public void deletar(Long id) throws SQLException {
        dao.deletar(id);
    }

    public List<Vacina> listarVacinas() throws SQLException {
        return dao.listarTodos();
    }

    public void fecharConexao() {
        dao.fecharConexao();
    }
}
