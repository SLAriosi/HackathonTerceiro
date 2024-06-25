package view;

import model.Idoso;
import service.IdosoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CadastroIdoso extends JFrame {
    private IdosoService idosoService;

    private JTextField campoId, campoNome, campoNumeroCasa, campoCondicoes, campoDataNascimento;
    private JFormattedTextField campoCPF, campoCEP, campoTelefone;
    private JButton botaoSalvar, botaoCancelar, botaoExcluir, botaoListarTodos, botaoBuscarCPF;
    private JTable tabelaIdosos;
    private DefaultTableModel modeloTabela;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public CadastroIdoso() throws SQLException {
        idosoService = new IdosoService();

        initComponents();
        setupListeners();

        setSize(1000, 600);
        setTitle("Cadastro de Idosos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarDados(); // Carrega os dados iniciais na tabela
    }

    private void initComponents() {
        JPanel painelFormulario = new JPanel(new GridLayout(9, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelFormulario.add(new JLabel("ID:"));
        campoId = new JTextField(10);
        campoId.setEnabled(false);
        campoId.setEditable(false); // Torna o campo não editável
        painelFormulario.add(campoId);

        painelFormulario.add(new JLabel("Nome Completo:"));
        campoNome = new JTextField();
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("CPF:"));
        campoCPF = new JFormattedTextField(createFormatter("###.###.###-##"));
        campoCPF.setPreferredSize(new Dimension(150, 30));
        painelFormulario.add(campoCPF);

        painelFormulario.add(new JLabel("CEP:"));
        campoCEP = new JFormattedTextField(createFormatter("#####-###"));
        campoCEP.setPreferredSize(new Dimension(150, 30));
        painelFormulario.add(campoCEP);

        painelFormulario.add(new JLabel("Telefone:"));
        campoTelefone = new JFormattedTextField(createFormatter("(##) #####-####"));
        campoTelefone.setPreferredSize(new Dimension(150, 30));
        painelFormulario.add(campoTelefone);

        painelFormulario.add(new JLabel("Número da Casa:"));
        campoNumeroCasa = new JTextField(15);
        painelFormulario.add(campoNumeroCasa);

        painelFormulario.add(new JLabel("Condições de Saúde:"));
        campoCondicoes = new JTextField();
        painelFormulario.add(campoCondicoes);

        painelFormulario.add(new JLabel("Data de Nascimento:"));
        campoDataNascimento = new JTextField();
        campoDataNascimento.setPreferredSize(new Dimension(150, 30));
        painelFormulario.add(campoDataNascimento);

        botaoBuscarCPF = new JButton("Buscar");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoCancelar = new JButton("Cancelar");
        botaoSalvar = new JButton("Salvar");
        botaoExcluir = new JButton("Excluir");
        botaoListarTodos = new JButton("Listar Todos");
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoListarTodos);
        painelBotoes.add(botaoBuscarCPF);

        modeloTabela = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome", "CPF", "CEP", "Telefone", "Número Casa", "Condições", "Data Nascimento"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Define que a coluna 0 (ID) não é editável
                return column != 0;
            }
        };
        tabelaIdosos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaIdosos);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painelFormulario, BorderLayout.WEST);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(painelBotoes, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        botaoCancelar.addActionListener(e -> limparCampos());
        botaoSalvar.addActionListener(e -> {
            if (validarCampos()) {
                salvarOuAtualizarIdoso();
            }
        });
        botaoExcluir.addActionListener(e -> deletarIdoso());
        botaoListarTodos.addActionListener(e -> carregarDados());
        botaoBuscarCPF.addActionListener(e -> buscarIdosoPorCPF());

        tabelaIdosos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tabelaIdosos.getSelectedRow();
                if (selectedRow != -1) {
                    mostrarDetalhesIdoso(selectedRow);
                }
            }
        });
    }

    private void carregarDados() {
        try {
            modeloTabela.setRowCount(0); // Limpa a tabela
            List<Idoso> idosos = idosoService.findAll();
            for (Idoso idoso : idosos) {
                Object[] linha = {
                        idoso.getId(),
                        idoso.getNome(),
                        idoso.getCpf(),
                        idoso.getCep(),
                        idoso.getTelefone(),
                        idoso.getNumeroCasa(),
                        idoso.getCondicoes(),
                        DATE_FORMAT.format(idoso.getDataNascimento())
                };
                modeloTabela.addRow(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados dos idosos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarIdosoPorCPF() {
        String cpf = campoCPF.getText().replaceAll("[^0-9]", ""); // Remove caracteres não numéricos
        if (!cpf.isEmpty()) {
            try {
                Idoso idoso = idosoService.buscarPorCPF(cpf);
                if (idoso != null) {
                    modeloTabela.setRowCount(0); // Limpa a tabela
                    Object[] linha = {
                            idoso.getId(),
                            idoso.getNome(),
                            idoso.getCpf(),
                            idoso.getCep(),
                            idoso.getTelefone(),
                            idoso.getNumeroCasa(),
                            idoso.getCondicoes(),
                            DATE_FORMAT.format(idoso.getDataNascimento())
                    };
                    modeloTabela.addRow(linha);
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhum idoso encontrado com o CPF informado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar idoso por CPF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, informe um CPF válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void salvarOuAtualizarIdoso() {
        try {
            Idoso idoso = construirIdoso();
            if (campoId.getText().isEmpty()) { // Novo idoso (inserir)
                idosoService.salvarIdoso(idoso);
            } else { // Atualizar idoso
                idoso.setId(Integer.parseInt(campoId.getText()));
                idosoService.atualizarIdoso(idoso);
            }
            limparCampos();
            carregarDados();
        } catch (SQLException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar ou atualizar idoso: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarIdoso() {
        try {
            int id = Integer.parseInt(campoId.getText());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este idoso?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                idosoService.deletar(id);
                limparCampos();
                carregarDados();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir idoso: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDetalhesIdoso(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tabelaIdosos.getRowCount()) {
            campoId.setText(modeloTabela.getValueAt(rowIndex, 0).toString());
            campoNome.setText(modeloTabela.getValueAt(rowIndex, 1).toString());
            campoCPF.setText(modeloTabela.getValueAt(rowIndex, 2).toString());
            campoCEP.setText(modeloTabela.getValueAt(rowIndex, 3).toString());
            campoTelefone.setText(modeloTabela.getValueAt(rowIndex, 4).toString());
            campoNumeroCasa.setText(modeloTabela.getValueAt(rowIndex, 5).toString());
            campoCondicoes.setText(modeloTabela.getValueAt(rowIndex, 6).toString());

            String dataString = modeloTabela.getValueAt(rowIndex, 7).toString();
            campoDataNascimento.setText(dataString); // Define a data no campo de texto
        }
    }

    private Idoso construirIdoso() throws ParseException {
        String nome = campoNome.getText();
        String cpf = campoCPF.getText().replaceAll("[^0-9]", "");
        String cep = campoCEP.getText().replaceAll("[^0-9]", "");
        String telefone = campoTelefone.getText().replaceAll("[^0-9]", "");
        String numeroCasa = campoNumeroCasa.getText();
        String condicoes = campoCondicoes.getText();
        java.util.Date dataNascimento = DATE_FORMAT.parse(campoDataNascimento.getText());

        return new Idoso(nome, cpf, cep, telefone, numeroCasa, condicoes, new java.sql.Date(dataNascimento.getTime()));
    }

    private MaskFormatter createFormatter(String pattern) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(pattern);
            formatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    private boolean validarCampos() {
        if (campoNome.getText().isEmpty() || campoCPF.getText().replaceAll("[^0-9]", "").isEmpty() ||
                campoCEP.getText().replaceAll("[^0-9]", "").isEmpty() || campoTelefone.getText().replaceAll("[^0-9]", "").isEmpty() ||
                campoNumeroCasa.getText().isEmpty() || campoCondicoes.getText().isEmpty() || campoDataNascimento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoCPF.setValue(null);
        campoCEP.setValue(null);
        campoTelefone.setValue(null);
        campoNumeroCasa.setText("");
        campoCondicoes.setText("");
        campoDataNascimento.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new CadastroIdoso().setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
