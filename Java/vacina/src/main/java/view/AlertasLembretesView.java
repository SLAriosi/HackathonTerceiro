package view;

import dao.AgendaDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AlertasLembretesView extends JFrame {
    private JTextArea alertasLembretesArea;
    private JButton botaoFechar;
    private AgendaDAO agendaDAO;

    public AlertasLembretesView() {
        try {
            agendaDAO = new AgendaDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("Alertas e Lembretes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setupLayout();
        setupListeners();

        loadAlertasLembretes();
    }

    private void initComponents() {
        alertasLembretesArea = new JTextArea();
        alertasLembretesArea.setEditable(false);
        alertasLembretesArea.setFont(new Font("Arial", Font.PLAIN, 16));
        alertasLembretesArea.setMargin(new Insets(10, 10, 10, 10));

        botaoFechar = new JButton("Fechar");
    }

    private void setupLayout() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.BOTH;

        // Área de texto com rolagem
        JScrollPane scrollPane = new JScrollPane(alertasLembretesArea);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridwidth = 2;
        painelPrincipal.add(scrollPane, constraints);

        // Botão Fechar
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(botaoFechar, constraints);

        getContentPane().add(painelPrincipal, BorderLayout.CENTER);
    }

    private void setupListeners() {
        botaoFechar.addActionListener(e -> dispose());
    }

    private void loadAlertasLembretes() {
        try {
            List<String> alertasLembretes = agendaDAO.listarAlertasLembretes();
            StringBuilder sb = new StringBuilder();
            for (String alertaLembrete : alertasLembretes) {
                sb.append(alertaLembrete).append("\n");
            }
            alertasLembretesArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alertas e lembretes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new AlertasLembretesView().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
