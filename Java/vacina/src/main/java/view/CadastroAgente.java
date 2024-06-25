package view;

import model.AgenteSaude;
import service.AgenteSaudeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroAgente extends JFrame {
    private JTextField nomeField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton salvarAgenteButton;
    private AgenteSaudeService agenteSaudeService = new AgenteSaudeService();

    public CadastroAgente() throws SQLException {
        setTitle("Cadastro de Agentes de Saúde");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        nomeField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        salvarAgenteButton = new JButton("Salvar Agente de Saúde");
        salvarAgenteButton.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    private void setupLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Cadastro de Agentes de Saúde");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        addLabelAndField(panel, gbc, "Nome:", nomeField, 1);
        addLabelAndField(panel, gbc, "Username:", usernameField, 2);
        addLabelAndField(panel, gbc, "Password:", passwordField, 3);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(salvarAgenteButton, gbc);

        add(panel);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int yPos) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        panel.add(field, gbc);
    }

    private void setupListeners() {
        salvarAgenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeField.getText().trim();
                    String username = usernameField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();

                    if (nome.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    AgenteSaude agente = new AgenteSaude(null, nome, username, password);
                    agenteSaudeService.save(agente);

                    JOptionPane.showMessageDialog(null, "Agente de Saúde salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar Agente de Saúde: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    private void clearFields() {
        nomeField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new CadastroAgente().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
