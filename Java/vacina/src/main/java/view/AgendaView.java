package view;

import model.Agenda;
import service.AgendaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AgendaView extends JFrame {
    private JTextField dataField;
    private JComboBox<String> horarioComboBox;
    private JComboBox<String> agenteSaudeComboBox;
    private JTextField vacinaField;
    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JFormattedTextField cepField;
    private JFormattedTextField telefoneField;
    private JTable table;
    private DefaultTableModel tableModel;
    private AgendaService agendaService;

    public AgendaView() {
        try {
            agendaService = new AgendaService();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage());
            System.exit(1);
        }

        setTitle("Gerenciamento de Agendas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels e campos de entrada para data e horário
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Data (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dataField = new JTextField(15);
        formPanel.add(dataField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Horário:"), gbc);
        gbc.gridx = 1;
        horarioComboBox = new JComboBox<>(new String[]{
                "08:00:00", "09:00:00", "10:00:00", "11:00:00",
                "12:00:00", "13:00:00", "14:00:00", "15:00:00",
                "16:00:00", "17:00:00", "18:00:00"
        });
        formPanel.add(horarioComboBox, gbc);

        // ComboBox para selecionar Agente de Saúde
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Agente de Saúde:"), gbc);
        gbc.gridx = 1;
        agenteSaudeComboBox = new JComboBox<>();
        carregarAgentesSaude(); // Método para carregar os agentes disponíveis
        formPanel.add(agenteSaudeComboBox, gbc);

        // Labels e campos de entrada para Vacina, Nome, CPF, CEP, Telefone
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Vacina:"), gbc);
        gbc.gridx = 1;
        vacinaField = new JTextField(15);
        formPanel.add(vacinaField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        nomeField = new JTextField(15);
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        cpfField = new JFormattedTextField(createFormatter("###.###.###-##"));
        cpfField.setColumns(15);
        formPanel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("CEP:"), gbc);
        gbc.gridx = 1;
        cepField = new JFormattedTextField(createFormatter("#####-###"));
        cepField.setColumns(15);
        formPanel.add(cepField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        telefoneField = new JFormattedTextField(createFormatter("(##) #####-####"));
        telefoneField.setColumns(15);
        formPanel.add(telefoneField, gbc);

        // Botões de ação (Adicionar, Pesquisar, Deletar)
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton addButton = new JButton("Adicionar");
        formPanel.add(addButton, gbc);

        gbc.gridy++;
        JButton searchButton = new JButton("Pesquisar");
        formPanel.add(searchButton, gbc);

        gbc.gridy++;
        JButton deleteButton = new JButton("Deletar");
        formPanel.add(deleteButton, gbc);

        contentPane.add(formPanel, BorderLayout.NORTH);

        // Tabela para exibir os registros
        tableModel = new DefaultTableModel(new Object[]{"ID", "Data", "Horário", "Agente Saúde", "Vacina", "Nome", "CPF", "CEP", "Telefone"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Listeners para os botões de ação
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarRegistro();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorCPF();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarRegistro();
            }
        });

        setVisible(true);
        atualizarTabela();
    }

    private void carregarAgentesSaude() {
        try {
            List<String> nomesAgentes = agendaService.listarNomesAgentes();
            for (String nome : nomesAgentes) {
                agenteSaudeComboBox.addItem(nome);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar agentes de saúde: " + e.getMessage());
        }
    }

    private void adicionarRegistro() {
        try {
            if (verificarCamposVazios()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            String data = dataField.getText();
            String horario = (String) horarioComboBox.getSelectedItem();
            String agenteSaudeNome = (String) agenteSaudeComboBox.getSelectedItem();
            String vacina = vacinaField.getText();
            String nome = nomeField.getText();
            String cpf = cpfField.getText().replaceAll("[^\\d]", "");
            String cep = cepField.getText().replaceAll("[^\\d]", "");
            String telefone = telefoneField.getText().replaceAll("[^\\d]", "");
            LocalDateTime now = LocalDateTime.now();

            if (cpf.length() != 11 || cep.length() != 8 || telefone.length() != 11) {
                JOptionPane.showMessageDialog(this, "CPF, CEP ou Telefone inválido.");
                return;
            }

            // Recupera o ID do agente de saúde usando o nome selecionado
            long agenteSaudeId = agendaService.getAgenteSaudeIdPorNome(agenteSaudeNome);

            Agenda agenda = new Agenda(0, LocalDate.parse(data), LocalTime.parse(horario), agenteSaudeId, vacina, nome, cpf, cep, telefone, now, now);
            agendaService.salvar(agenda);
            atualizarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar registro: " + ex.getMessage());
        }
    }

    private void buscarPorCPF() {
        try {
            String cpf = JOptionPane.showInputDialog(this, "Digite o CPF:");
            if (cpf != null && !cpf.trim().isEmpty()) {
                cpf = cpf.replaceAll("[^\\d]", "");
                if (cpf.length() != 11) {
                    JOptionPane.showMessageDialog(this, "CPF inválido.");
                    return;
                }
                List<Agenda> agendas = agendaService.buscarPorCPF(cpf);
                if (agendas.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum registro encontrado para o CPF informado.");
                } else {
                    exibirRegistros(agendas);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar por CPF: " + ex.getMessage());
        }
    }

    private void deletarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um registro para deletar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este registro?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                long id = (long) table.getValueAt(selectedRow, 0);
                agendaService.deletar(id);
                atualizarTabela();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar registro: " + ex.getMessage());
            }
        }
    }
    private boolean verificarCamposVazios() {
        return dataField.getText().isEmpty() || horarioComboBox.getSelectedItem() == null ||
                agenteSaudeComboBox.getSelectedItem() == null || vacinaField.getText().isEmpty() ||
                nomeField.getText().isEmpty() || cpfField.getText().isEmpty() ||
                cepField.getText().isEmpty() || telefoneField.getText().isEmpty();
    }


    private void atualizarTabela() {
        try {
            List<Agenda> agendas = agendaService.listar();
            tableModel.setRowCount(0);
            for (Agenda agenda : agendas) {
                Object[] rowData = {
                        agenda.getId(),
                        agenda.getData(),
                        agenda.getHorario(),
                        agenda.getAgenteSaudeId(),
                        agenda.getVacina(),
                        agenda.getNome(),
                        formatarCPF(agenda.getCpf()),
                        formatarCEP(agenda.getCep()),
                        formatarTelefone(agenda.getTelefone())
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar agendas: " + ex.getMessage());
        }
    }

    private String formatarCPF(String cpf) {
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }

    private String formatarCEP(String cep) {
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }

    private String formatarTelefone(String telefone) {
        return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7);
    }

    private void exibirRegistros(List<Agenda> agendas) {
        tableModel.setRowCount(0);
        for (Agenda agenda : agendas) {
            Object[] rowData = {
                    agenda.getId(),
                    agenda.getData(),
                    agenda.getHorario(),
                    agenda.getAgenteSaudeId(),
                    agenda.getVacina(),
                    agenda.getNome(),
                    formatarCPF(agenda.getCpf()),
                    formatarCEP(agenda.getCep()),
                    formatarTelefone(agenda.getTelefone())
            };
            tableModel.addRow(rowData);
        }
    }

    private void limparCampos() {
        dataField.setText("");
        horarioComboBox.setSelectedIndex(0);
        agenteSaudeComboBox.setSelectedIndex(0);
        vacinaField.setText("");
        nomeField.setText("");
        cpfField.setText("");
        cepField.setText("");
        telefoneField.setText("");
    }

    private MaskFormatter createFormatter(String pattern) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(pattern);
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        return formatter;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgendaView());
    }
}
