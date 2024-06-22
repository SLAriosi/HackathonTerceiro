package view;

import model.Idoso;
import service.IdosoService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CadastroIdoso extends JFrame {
    private IdosoService service;
    private JLabel labelId, labelNome, labelCPF, labelCEP, labelTelefone, labelNumeroCasa, labelCondicoes, labelDataNascimento;
    private JTextField campoId, campoNome, campoCPF, campoCEP, campoNumeroCasa, campoCondicoes;
    private JFormattedTextField campoTelefone, campoDataNascimento;
    private JButton botaoSalvar, botaoCancelar, botaoExcluir;
    private JTable tabelaIdoso;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public CadastroIdoso() throws SQLException {
        service = new IdosoService();

        initComponents();
        setupLayout();
        setupListeners();

        setSize(900, 600);
        setTitle("Cadastro de Idosos");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarDados();
    }

    private void initComponents() {
        labelId = new JLabel("ID:");
        campoId = new JTextField(10);
        campoId.setEnabled(false);

        labelNome = new JLabel("Nome Completo:");
        campoNome = new JTextField(30);

        labelCPF = new JLabel("CPF:");
        campoCPF = new JFormattedTextField(createFormatter("###.###.###-##"));

        labelCEP = new JLabel("CEP:");
        campoCEP = new JFormattedTextField(createFormatter("#####-###"));

        labelTelefone = new JLabel("Telefone:");
        campoTelefone = new JFormattedTextField(createFormatter("(##) ####-####"));

        labelNumeroCasa = new JLabel("Número da Casa:");
        campoNumeroCasa = new JTextField(15);

        labelCondicoes = new JLabel("Condições de Saúde:");
        campoCondicoes = new JTextField(30);

        labelDataNascimento = new JLabel("Data de Nascimento:");
        campoDataNascimento = new JFormattedTextField(createFormatter("##/##/####"));

        botaoCancelar = new JButton("Cancelar");
        botaoSalvar = new JButton("Salvar");
        botaoExcluir = new JButton("Excluir");

        tabelaIdoso = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome", "CPF", "CEP", "Telefone", "Número da Casa", "Condições de Saúde", "Data de Nascimento"}
        ));
    }

    private void setupLayout() {
        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        // Primeira coluna
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(labelId, constraints);

        constraints.gridy = 1;
        painelEntrada.add(labelNome, constraints);

        constraints.gridy = 2;
        painelEntrada.add(labelCPF, constraints);

        constraints.gridy = 3;
        painelEntrada.add(labelCEP, constraints);

        constraints.gridy = 4;
        painelEntrada.add(labelTelefone, constraints);

        constraints.gridy = 5;
        painelEntrada.add(labelNumeroCasa, constraints);

        constraints.gridy = 6;
        painelEntrada.add(labelCondicoes, constraints);

        constraints.gridy = 7;
        painelEntrada.add(labelDataNascimento, constraints);

        // Segunda coluna
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoId, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 2;
        painelEntrada.add(campoNome, constraints);

        constraints.gridy = 2;
        painelEntrada.add(campoCPF, constraints);

        constraints.gridy = 3;
        painelEntrada.add(campoCEP, constraints);

        constraints.gridy = 4;
        painelEntrada.add(campoTelefone, constraints);

        constraints.gridy = 5;
        constraints.gridwidth = 1;
        painelEntrada.add(campoNumeroCasa, constraints);

        constraints.gridy = 6;
        constraints.gridwidth = 2;
        painelEntrada.add(campoCondicoes, constraints);

        constraints.gridy = 7;
        constraints.gridwidth = 1;
        painelEntrada.add(campoDataNascimento, constraints);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoExcluir);

        JPanel painelSaida = new JPanel(new BorderLayout());
        painelSaida.add(new JScrollPane(tabelaIdoso), BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painelEntrada, BorderLayout.NORTH);
        getContentPane().add(painelBotoes, BorderLayout.SOUTH);
        getContentPane().add(painelSaida, BorderLayout.CENTER);
    }

    private void setupListeners() {
        botaoCancelar.addActionListener(e -> limparCampos());
        botaoSalvar.addActionListener(e -> {
            if (validarCampos()) {
                salvarOuAtualizarIdoso();
            }
        });
        botaoExcluir.addActionListener(e -> deletarIdoso());

        tabelaIdoso.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selecionarIdoso(e);
            }
        });
    }

    private void selecionarIdoso(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabelaIdoso.getSelectedRow();
            if (selectedRow != -1) {
                campoId.setText(tabelaIdoso.getValueAt(selectedRow, 0).toString());
                campoNome.setText((String) tabelaIdoso.getValueAt(selectedRow, 1));
                campoCPF.setText((String) tabelaIdoso.getValueAt(selectedRow, 2));
                campoCEP.setText((String) tabelaIdoso.getValueAt(selectedRow, 3));
                campoTelefone.setText((String) tabelaIdoso.getValueAt(selectedRow, 4));
                campoNumeroCasa.setText((String) tabelaIdoso.getValueAt(selectedRow, 5));
                campoCondicoes.setText((String) tabelaIdoso.getValueAt(selectedRow, 6));
                campoDataNascimento.setText((String) tabelaIdoso.getValueAt(selectedRow, 7));

                // Habilita o botão de excluir ao selecionar um idoso na tabela
                botaoExcluir.setEnabled(true);
            }
        }
    }

    private void carregarDados() {
        try {
            List<Idoso> idosos = service.findAll();
            DefaultTableModel model = (DefaultTableModel) tabelaIdoso.getModel();
            model.setRowCount(0);
            for (Idoso idoso : idosos) {
                model.addRow(new Object[]{
                        idoso.getId(),
                        idoso.getNome(),
                        idoso.getCpf(),
                        idoso.getCep(),
                        idoso.getTelefone(),
                        idoso.getNumero_casa(),
                        idoso.getCondicoes(),
                        DATE_FORMAT.format(idoso.getDataNascimento())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarOuAtualizarIdoso() {
        try {
            Idoso idoso = construirIdoso();
            if (campoId.getText().isEmpty()) {
                service.salvarIdoso(idoso);
            } else {
                idoso.setId(Integer.parseInt(campoId.getText()));
                service.atualizarIdoso(idoso);
            }
            limparCampos();
            carregarDados();
        } catch (SQLException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar ou atualizar Idoso: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarIdoso() {
        try {
            int id = Integer.parseInt(campoId.getText());
            service.deletar(id);
            limparCampos();
            carregarDados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar Idoso: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Selecione um idoso para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (campoNome.getText().isEmpty() ||
                campoCPF.getText().isEmpty() ||
                campoCEP.getText().isEmpty() ||
                campoTelefone.getText().isEmpty() ||
                campoNumeroCasa.getText().isEmpty() ||
                campoCondicoes.getText().isEmpty() ||
                campoDataNascimento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            DATE_FORMAT.parse(campoDataNascimento.getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data de Nascimento deve estar no formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoCPF.setText("");
        campoCEP.setText("");
        campoTelefone.setValue(null); // Limpa o valor formatado do campo de telefone
        campoNumeroCasa.setText("");
        campoCondicoes.setText("");
        campoDataNascimento.setValue(null);
    }

    private Idoso construirIdoso() throws ParseException {
        return campoId.getText().isEmpty()
                ? new Idoso(
                campoNome.getText(),
                campoTelefone.getText(),
                campoCEP.getText(),
                campoCPF.getText(),
                campoNumeroCasa.getText(),
                campoCondicoes.getText(),
                DATE_FORMAT.parse(campoDataNascimento.getText())
        )
                : new Idoso(
                Integer.parseInt(campoId.getText()),
                campoNome.getText(),
                campoTelefone.getText(),
                campoCEP.getText(),
                campoCPF.getText(),
                campoNumeroCasa.getText(),
                campoCondicoes.getText(),
                DATE_FORMAT.parse(campoDataNascimento.getText())
        );
    }

    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholderCharacter('_');
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new CadastroIdoso().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
