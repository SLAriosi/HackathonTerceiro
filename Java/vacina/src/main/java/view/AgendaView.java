package view;

import model.Agenda;
import service.AgendaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class AgendaView extends JFrame {

    private final AgendaService agendaService;
    private JComboBox<String> comboBoxAgenteSaude;
    private JComboBox<String> comboBoxVacina;
    private JFormattedTextField campoDataNascimento;
    private JSpinner spinnerHorario;
    private JTextField textFieldNome;
    private JFormattedTextField formattedTextFieldCPF;
    private JFormattedTextField formattedTextFieldCEP;
    private JFormattedTextField formattedTextFieldTelefone;
    private JTable tableAgendamentos;
    private DefaultTableModel tableModel;
    private long selectedAgendaId = 0;

    public AgendaView() throws SQLException {
        this.agendaService = new AgendaService();
        initComponents();
        loadAgendamentos(); // Carrega os agendamentos na inicialização da tela
    }

    private void initComponents() {
        setTitle("Agenda de Vacinação");
        setSize(1000, 600); // Aumentando o tamanho da janela para melhor acomodar os componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(10, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels e campos de entrada
        JLabel labelDataNascimento = new JLabel("Data de Nascimento:");
        campoDataNascimento = new JFormattedTextField(createFormatter("##/##/####"));
        campoDataNascimento.setPreferredSize(new Dimension(150, 30));

        JLabel labelHorario = new JLabel("Horário:");
        spinnerHorario = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHorario, "HH:mm");
        spinnerHorario.setEditor(timeEditor);

        JLabel labelNome = new JLabel("Nome:");
        textFieldNome = new JTextField();

        JLabel labelCPF = new JLabel("CPF:");
        formattedTextFieldCPF = new JFormattedTextField(createFormatter("###.###.###-##"));

        JLabel labelCEP = new JLabel("CEP:");
        formattedTextFieldCEP = new JFormattedTextField(createFormatter("#####-###"));

        JLabel labelTelefone = new JLabel("Telefone:");
        formattedTextFieldTelefone = new JFormattedTextField(createFormatter("(##) #####-####"));

        JLabel labelAgenteSaude = new JLabel("Agente de Saúde:");
        comboBoxAgenteSaude = new JComboBox<>();
        carregarAgentesDeSaude();

        JLabel labelVacina = new JLabel("Vacina:");
        comboBoxVacina = new JComboBox<>();
        carregarVacinas();

        // Adicionando os componentes ao painel
        panelForm.add(labelDataNascimento);
        panelForm.add(campoDataNascimento);

        panelForm.add(labelHorario);
        panelForm.add(spinnerHorario);

        panelForm.add(labelNome);
        panelForm.add(textFieldNome);

        panelForm.add(labelCPF);
        panelForm.add(formattedTextFieldCPF);

        panelForm.add(labelCEP);
        panelForm.add(formattedTextFieldCEP);

        panelForm.add(labelTelefone);
        panelForm.add(formattedTextFieldTelefone);

        panelForm.add(labelAgenteSaude);
        panelForm.add(comboBoxAgenteSaude);

        panelForm.add(labelVacina);
        panelForm.add(comboBoxVacina);

        // Botões de Ação
        JButton buttonSalvar = new JButton("Salvar");
        buttonSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    salvarAgenda();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao salvar agendamento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton buttonExcluir = new JButton("Excluir");
        buttonExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    excluirAgenda();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao excluir agendamento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Botão de Buscar
        JButton buttonBuscar = new JButton("Buscar");
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorCPF();
            }
        });

        // Botão de Listar Todos
        JButton buttonListarTodos = new JButton("Listar Todos");
        buttonListarTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAgendamentos();
            }
        });

        // Painel para botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelBotoes.add(buttonSalvar);
        panelBotoes.add(buttonExcluir);
        panelBotoes.add(buttonBuscar);
        panelBotoes.add(buttonListarTodos);

        // Tabela de Agendamentos
        tableModel = new DefaultTableModel(new Object[]{"ID", "Data", "Horário", "Agente de Saúde", "Vacina", "Nome", "CPF", "CEP", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Impede a edição das colunas de CPF e ID (colunas 6 e 0, respectivamente)
                return !(column == 0 || column == 6);
            }
        };
        tableAgendamentos = new JTable(tableModel);
        tableAgendamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAgendamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableAgendamentos.getSelectedRow() != -1) {
                carregarAgendaParaEdicao();
            }
        });

        // Configurações adicionais da tabela
        tableAgendamentos.getColumnModel().getColumn(0).setPreferredWidth(30); // Ajusta a largura da coluna ID
        tableAgendamentos.getColumnModel().getColumn(6).setPreferredWidth(100); // Ajusta a largura da coluna CPF

        // Adicionando componentes à janela
        add(new JScrollPane(tableAgendamentos), BorderLayout.CENTER);
        add(panelForm, BorderLayout.WEST);
        add(panelBotoes, BorderLayout.SOUTH);

        // Centralizar janela na tela
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarAgentesDeSaude() {
        try {
            List<String> agentes = agendaService.listarNomesAgentes();
            for (String agente : agentes) {
                comboBoxAgenteSaude.addItem(agente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar agentes de saúde: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Apenas para debug, você pode remover em produção
        }
    }

    private void carregarVacinas() {
        try {
            List<String> vacinas = agendaService.listarNomesVacinas();
            for (String vacina : vacinas) {
                comboBoxVacina.addItem(vacina);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vacinas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Apenas para debug, você pode remover em produção
        }
    }

    private void salvarAgenda() throws SQLException {
        try {
            // Formatação da data
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(campoDataNascimento.getText(), dateFormatter);

            LocalTime horario = ((SpinnerDateModel) spinnerHorario.getModel()).getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
            long agenteSaudeId = agendaService.getAgenteSaudeIdPorNome(comboBoxAgenteSaude.getSelectedItem().toString());
            String vacina = comboBoxVacina.getSelectedItem().toString();
            String nome = textFieldNome.getText();
            String cpf = formattedTextFieldCPF.getText().replaceAll("[^0-9]", "");
            String cep = formattedTextFieldCEP.getText().replaceAll("[^0-9]", "");
            String telefone = formattedTextFieldTelefone.getText().replaceAll("[^0-9]", "");
            LocalDateTime createdAt = LocalDateTime.now();
            LocalDateTime updatedAt = LocalDateTime.now();

            // Validar CPF, CEP e Telefone
            if (!validarCPF(cpf) || !validarCEP(cep) || !validarTelefone(telefone)) {
                JOptionPane.showMessageDialog(null, "Verifique os campos CPF, CEP ou Telefone.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Agenda agenda = new Agenda(selectedAgendaId, data, horario, agenteSaudeId, vacina, nome, cpf, cep, telefone, createdAt, updatedAt);
            agendaService.salvar(agenda);
            loadAgendamentos(); // Recarrega os agendamentos após salvar
            clearForm();
            JOptionPane.showMessageDialog(null, "Agendamento salvo com sucesso!");
        } catch (NumberFormatException | DateTimeException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar agendamento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCPF(String cpf) {
        return cpf.length() == 11;
    }

    private boolean validarCEP(String cep) {
        return cep.length() == 8;
    }

    private boolean validarTelefone(String telefone) {
        return telefone.length() == 11 || telefone.length() == 10;
    }

    private void excluirAgenda() throws SQLException {
        int selectedRow = tableAgendamentos.getSelectedRow();
        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o agendamento?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                long agendaId = (long) tableModel.getValueAt(selectedRow, 0);
                agendaService.excluir(agendaId);
                loadAgendamentos(); // Recarrega os agendamentos após excluir
                clearForm();
                JOptionPane.showMessageDialog(null, "Agendamento excluído com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um agendamento para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAgendamentos() {
        try {
            List<Agenda> agendas = agendaService.listarTodos();
            tableModel.setRowCount(0);
            for (Agenda agenda : agendas) {
                Object[] rowData = {
                        agenda.getId(),
                        agenda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        agenda.getHorario().format(DateTimeFormatter.ofPattern("HH:mm")),
                        agendaService.getNomeAgentePorId(agenda.getAgenteSaudeId()),
                        agenda.getVacina(),
                        agenda.getNome(),
                        agenda.getCpf(),
                        agenda.getCep(),
                        agenda.getTelefone()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar agendamentos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Apenas para debug, você pode remover em produção
        }
    }

    private void carregarAgendaParaEdicao() {
        int selectedRow = tableAgendamentos.getSelectedRow();
        if (selectedRow != -1) {
            selectedAgendaId = (long) tableModel.getValueAt(selectedRow, 0);
            try {
                campoDataNascimento.setText(tableModel.getValueAt(selectedRow, 1).toString());

                // Obter e converter o horário
                String horarioStr = tableModel.getValueAt(selectedRow, 2).toString();
                LocalTime horario = LocalTime.parse(horarioStr, DateTimeFormatter.ofPattern("HH:mm"));

                // Definir o horário no Spinner
                spinnerHorario.setValue(Date.from(horario.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));

                comboBoxAgenteSaude.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
                comboBoxVacina.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
                textFieldNome.setText(tableModel.getValueAt(selectedRow, 5).toString());
                formattedTextFieldCPF.setText(tableModel.getValueAt(selectedRow, 6).toString());
                formattedTextFieldCEP.setText(tableModel.getValueAt(selectedRow, 7).toString());
                formattedTextFieldTelefone.setText(tableModel.getValueAt(selectedRow, 8).toString());
            } catch (DateTimeException e) {
                e.printStackTrace();
            }
        }
    }


    private void clearForm() {
        selectedAgendaId = 0;
        campoDataNascimento.setText("");
        spinnerHorario.setValue(new java.util.Date()); // Reseta o spinner para o horário atual
        comboBoxAgenteSaude.setSelectedIndex(0);
        comboBoxVacina.setSelectedIndex(0);
        textFieldNome.setText("");
        formattedTextFieldCPF.setValue(null);
        formattedTextFieldCEP.setValue(null);
        formattedTextFieldTelefone.setValue(null);
    }

    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    private void buscarPorCPF() {
        String cpf = JOptionPane.showInputDialog(null, "Digite o CPF para buscar:");
        if (cpf != null && !cpf.isEmpty()) {
            try {
                List<Agenda> agendas = agendaService.buscarPorCPF(cpf);
                if (agendas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum agendamento encontrado para o CPF informado.", "Busca", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    tableModel.setRowCount(0);
                    for (Agenda agenda : agendas) {
                        Object[] rowData = {
                                agenda.getId(),
                                agenda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                agenda.getHorario().format(DateTimeFormatter.ofPattern("HH:mm")),
                                agendaService.getNomeAgentePorId(agenda.getAgenteSaudeId()),
                                agenda.getVacina(),
                                agenda.getNome(),
                                agenda.getCpf(),
                                agenda.getCep(),
                                agenda.getTelefone()
                        };
                        tableModel.addRow(rowData);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar agendamentos por CPF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); // Apenas para debug, você pode remover em produção
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AgendaView();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao iniciar aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
