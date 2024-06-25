package view;

import dao.HistoricoDAO;
import model.Historico;
import model.AgenteSaude;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class HistoricoView extends JFrame {
    private final HistoricoDAO historicoDAO;
    private JComboBox<AgenteSaude> comboBoxAgenteSaude;

    private JLabel labelId, labelIdosoId, labelAgendaId, labelVacinaId, labelSearch;
    private JTextField campoId, campoAgendaId, campoVacinaId, campoSearch;
    private JButton botaoSalvar, botaoCancelar, botaoExcluir, botaoBuscar;
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
        carregarAgenteSaude();

        pack();
    }

    private void initComponents() {
        labelId = new JLabel("ID:");
        campoId = new JTextField(10);
        campoId.setEditable(false);

        labelIdosoId = new JLabel("Agente de Saúde:");
        comboBoxAgenteSaude = new JComboBox<>();

        labelAgendaId = new JLabel("ID da Agenda:");
        campoAgendaId = new JTextField(10);

        labelVacinaId = new JLabel("ID da Vacina:");
        campoVacinaId = new JTextField(10);

        labelSearch = new JLabel("Buscar:");
        campoSearch = new JTextField(20);
        botaoBuscar = new JButton("Buscar");

        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");
        botaoExcluir = new JButton("Excluir");

        tabelaHistorico = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Agente de Saúde", "ID da Agenda", "ID da Vacina"}
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
        painelFormulario.add(comboBoxAgenteSaude, constraints);

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

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(labelSearch);
        painelBusca.add(campoSearch);
        painelBusca.add(botaoBuscar);

        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelBusca, BorderLayout.NORTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painelPrincipal, BorderLayout.CENTER);

        setMinimumSize(new Dimension(600, 400));
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

        botaoBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarHistorico();
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
                        historico.getAgenteSaude().getNome(),
                        historico.getAgendaId(),
                        historico.getVacinaId()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarAgenteSaude() {
        try {
            List<AgenteSaude> agentes = historicoDAO.listarAgentesSaude();
            comboBoxAgenteSaude.removeAllItems();
            for (AgenteSaude agente : agentes) {
                comboBoxAgenteSaude.addItem(agente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar agentes de saúde: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarOuAtualizarHistorico() {
        int id = campoId.getText().isEmpty() ? 0 : Integer.parseInt(campoId.getText());
        AgenteSaude agenteSaude = (AgenteSaude) comboBoxAgenteSaude.getSelectedItem();
        int agendaId = Integer.parseInt(campoAgendaId.getText());
        int vacinaId = Integer.parseInt(campoVacinaId.getText());

        Historico historico = new Historico(id, agenteSaude.getId(), agendaId, vacinaId);

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
            historicoDAO.excluir(id);
            limparCampos();
            carregarHistorico();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarHistorico() {
        String query = campoSearch.getText();
        try {
            List<Historico> historicos = historicoDAO.buscar(query);
            DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();
            model.setRowCount(0);
            for (Historico historico : historicos) {
                model.addRow(new Object[]{
                        historico.getId(),
                        historico.getAgenteSaude().getNome(),
                        historico.getAgendaId(),
                        historico.getVacinaId()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoId.setText("");
        comboBoxAgenteSaude.setSelectedIndex(0);
        campoAgendaId.setText("");
        campoVacinaId.setText("");
        campoSearch.setText("");
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
