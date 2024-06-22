package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Agenda;
import service.AgendaService;

public class AgendaView extends JFrame {
    private final AgendaService agendaService;

    private JLabel labelId, labelIdosoId, labelDataVisita, labelPesquisa;
    private JTextField campoId, campoIdosoId, campoPesquisa;
    private JFormattedTextField campoDataVisita;
    private JButton botaoSalvar, botaoCancelar, botaoExcluir, botaoPesquisar;
    private JTable tabelaAgenda;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AgendaView() throws SQLException {
        agendaService = new AgendaService();

        initComponents();
        setupLayout();
        setupListeners();

        setTitle("Agenda de Visitas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600); // Define um tamanho inicial
        setLocationRelativeTo(null); // Centraliza a janela na tela

        carregarAgenda();
    }

    private void initComponents() {
        labelId = new JLabel("ID:");
        campoId = new JTextField(10);
        campoId.setEditable(false);

        labelIdosoId = new JLabel("ID do Idoso:");
        campoIdosoId = new JTextField(10);

        labelDataVisita = new JLabel("Data da Visita:");
        campoDataVisita = new JFormattedTextField(createFormatter("##/##/####"));
        campoDataVisita.setColumns(10);

        labelPesquisa = new JLabel("Pesquisar:");
        campoPesquisa = new JTextField(20);
        campoPesquisa.setToolTipText("Pesquisar por ID do Idoso ou Data da Visita");

        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");
        botaoExcluir = new JButton("Excluir");
        botaoPesquisar = new JButton("Pesquisar");

        tabelaAgenda = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "ID do Idoso", "Data da Visita"}
        ));
    }

    private void setupLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

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
        painelFormulario.add(labelDataVisita, constraints);

        constraints.gridy = 3;
        painelFormulario.add(labelPesquisa, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        painelFormulario.add(campoId, constraints);

        constraints.gridy = 1;
        painelFormulario.add(campoIdosoId, constraints);

        constraints.gridy = 2;
        painelFormulario.add(campoDataVisita, constraints);

        constraints.gridy = 3;
        constraints.gridwidth = 2;
        painelFormulario.add(campoPesquisa, constraints);

        constraints.gridx = 2;
        painelFormulario.add(botaoPesquisar, constraints);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoExcluir);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createTitledBorder("Agenda de Visitas"));
        painelTabela.add(new JScrollPane(tabelaAgenda), BorderLayout.CENTER);

        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painelPrincipal, BorderLayout.CENTER);
    }

    private void setupListeners() {
        botaoSalvar.addActionListener(e -> salvarOuAtualizarAgenda());
        botaoCancelar.addActionListener(e -> limparCampos());
        botaoExcluir.addActionListener(e -> deletarAgenda());
        botaoPesquisar.addActionListener(e -> pesquisarAgenda());
    }

    private void carregarAgenda() {
        try {
            List<Agenda> agendas = agendaService.listarTodasAgendas();
            DefaultTableModel model = (DefaultTableModel) tabelaAgenda.getModel();
            model.setRowCount(0);
            for (Agenda agenda : agendas) {
                model.addRow(new Object[]{
                        agenda.getId(),
                        agenda.getAgenteSaudeId(),
                        DATE_FORMATTER.format(agenda.getData()) // Formata LocalDate para String
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar agenda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarOuAtualizarAgenda() {
        try {
            Long id = campoId.getText().isEmpty() ? null : Long.parseLong(campoId.getText());
            Long idosoId = Long.parseLong(campoIdosoId.getText());
            LocalDate dataVisita = LocalDate.parse(campoDataVisita.getText(), DATE_FORMATTER); // Converte String para LocalDate

            Agenda agenda = new Agenda(id, dataVisita, null, idosoId, null, null, null, null, null); // Ajuste o construtor conforme necessário

            if (id == null) {
                agendaService.salvarAgenda(agenda);
            } else {
                agendaService.atualizarAgenda(agenda);
            }
            limparCampos();
            carregarAgenda();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar agenda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarAgenda() {
        try {
            long id = Long.parseLong(campoId.getText());
            agendaService.deletarAgenda(id);
            limparCampos();
            carregarAgenda();
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir agenda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoIdosoId.setText("");
        campoDataVisita.setValue(null); // Limpa o campo de data
    }

    private MaskFormatter createFormatter(String pattern) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(pattern);
            formatter.setPlaceholderCharacter('_');
        } catch (java.text.ParseException exc) {
            System.err.println("Formatter is bad: " + exc.getMessage());
        }
        return formatter;
    }

    private void pesquisarAgenda() {
        try {
            String termoPesquisa = campoPesquisa.getText().trim();
            List<Agenda> agendas;

            if (termoPesquisa.isEmpty()) {
                agendas = agendaService.listarTodasAgendas();
            } else {
                agendas = agendaService.pesquisar(termoPesquisa); // Implemente o método de pesquisa no serviço
            }

            DefaultTableModel model = (DefaultTableModel) tabelaAgenda.getModel();
            model.setRowCount(0);
            for (Agenda agenda : agendas) {
                model.addRow(new Object[]{
                        agenda.getId(),
                        agenda.getAgenteSaudeId(),
                        DATE_FORMATTER.format(agenda.getData()) // Formata LocalDate para String
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar agenda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new AgendaView().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
