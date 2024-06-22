package view;

import dao.HistoricoDAO;
import model.Historico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class HistoricoView extends JFrame {
    private final HistoricoDAO historicoDAO;

    private JLabel labelId, labelIdosoId, labelAgendaId, labelVacinaId;
    private JTextField campoId, campoIdosoId, campoAgendaId, campoVacinaId;
    private JButton botaoSalvar, botaoCancelar, botaoExcluir;
    private JTable tabelaHistorico;

    public HistoricoView() throws SQLException {
        historicoDAO = new HistoricoDAO();

        initComponents();
        setupLayout();
        setupListeners();

        setTitle("Histórico de Vacinação");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarHistorico();

        pack(); // Chamar pack() após configurar a interface
    }

    private void initComponents() {
        labelId = new JLabel("ID:");
        campoId = new JTextField(10);
        campoId.setEditable(false);

        labelIdosoId = new JLabel("ID do Idoso:");
        campoIdosoId = new JTextField(10);

        labelAgendaId = new JLabel("ID da Agenda:");
        campoAgendaId = new JTextField(10);

        labelVacinaId = new JLabel("ID da Vacina:");
        campoVacinaId = new JTextField(10);

        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");
        botaoExcluir = new JButton("Excluir");

        tabelaHistorico = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "ID do Idoso", "ID da Agenda", "ID da Vacina"}
        ));
    }

    private void setupLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        // Labels e campos de texto
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelFormulario.add(labelId, constraints);

        constraints.gridy = 1;
        painelFormulario.add(labelIdosoId, constraints);

        constraints.gridy = 2;
        painelFormulario.add(labelAgendaId, constraints);

        constraints.gridy = 3;
        painelFormulario.add(labelVacinaId, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        painelFormulario.add(campoId, constraints);

        constraints.gridy = 1;
        painelFormulario.add(campoIdosoId, constraints);

        constraints.gridy = 2;
        painelFormulario.add(campoAgendaId, constraints);

        constraints.gridy = 3;
        painelFormulario.add(campoVacinaId, constraints);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoExcluir);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createTitledBorder("Histórico de Vacinação"));
        painelTabela.add(new JScrollPane(tabelaHistorico), BorderLayout.CENTER);

        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painelPrincipal, BorderLayout.CENTER);

        setMinimumSize(new Dimension(600, 400)); // Definir tamanho mínimo da janela
    }

    private void setupListeners() {
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarOuAtualizarHistorico();
            }
        });

        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarHistorico();
            }
        });
    }

    private void carregarHistorico() {
        try {
            List<Historico> historicos = historicoDAO.listarTodos();
            DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();
            model.setRowCount(0);
            for (Historico historico : historicos) {
                model.addRow(new Object[]{
                        historico.getId(),
                        historico.getIdosoId(),
                        historico.getAgendaId(),
                        historico.getVacinaId()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarOuAtualizarHistorico() {
        int id = campoId.getText().isEmpty() ? 0 : Integer.parseInt(campoId.getText());
        int idosoId = Integer.parseInt(campoIdosoId.getText());
        int agendaId = Integer.parseInt(campoAgendaId.getText());
        int vacinaId = Integer.parseInt(campoVacinaId.getText());

        Historico historico = new Historico(id, idosoId, agendaId, vacinaId);

        try {
            if (id == 0) {
                historicoDAO.inserir(historico);
            } else {
                historicoDAO.atualizar(historico);
            }
            limparCampos();
            carregarHistorico();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarHistorico() {
        long id = Long.parseLong(campoId.getText());
        try {
            historicoDAO.excluir(id); // Alterado para chamar excluir(Long id)
            limparCampos();
            carregarHistorico();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoIdosoId.setText("");
        campoAgendaId.setText("");
        campoVacinaId.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new HistoricoView().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
