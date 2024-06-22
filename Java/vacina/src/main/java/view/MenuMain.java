package view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MenuMain extends JFrame {

    public MenuMain() {
        setTitle("SISTEMA ADMINISTRATIVO ALFACARE");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setupLayout();
        setupListeners();
    }

    private JButton cadastroIdosoButton;
    private JButton cadastroAgenteButton;
    private JButton historicoButton;
    private JButton agendaButton;
    private JButton alertasLembretesButton;

    private void initComponents() {
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        Dimension buttonSize = new Dimension(300, 50);

        cadastroIdosoButton = new JButton("Cadastro de Idosos");
        cadastroIdosoButton.setFont(buttonFont);
        cadastroIdosoButton.setPreferredSize(buttonSize);

        cadastroAgenteButton = new JButton("Cadastro de Agentes");
        cadastroAgenteButton.setFont(buttonFont);
        cadastroAgenteButton.setPreferredSize(buttonSize);

        historicoButton = new JButton("Histórico de Saúde");
        historicoButton.setFont(buttonFont);
        historicoButton.setPreferredSize(buttonSize);

        agendaButton = new JButton("Agenda de Visitas");
        agendaButton.setFont(buttonFont);
        agendaButton.setPreferredSize(buttonSize);

        alertasLembretesButton = new JButton("Alertas e Lembretes");
        alertasLembretesButton.setFont(buttonFont);
        alertasLembretesButton.setPreferredSize(buttonSize);
    }

    private void setupLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(cadastroIdosoButton, gbc);

        gbc.gridy++;
        panel.add(cadastroAgenteButton, gbc);

        gbc.gridy++;
        panel.add(historicoButton, gbc);

        gbc.gridy++;
        panel.add(agendaButton, gbc);

        gbc.gridy++;
        panel.add(alertasLembretesButton, gbc);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        cadastroIdosoButton.addActionListener(e -> abrirCadastroIdoso());
        cadastroAgenteButton.addActionListener(e -> abrirCadastroAgente());
        historicoButton.addActionListener(e -> abrirHistorico());
        agendaButton.addActionListener(e -> abrirAgenda());
        alertasLembretesButton.addActionListener(e -> abrirAlertasLembretes());
    }

    private void abrirCadastroIdoso() {
        try {
            new CadastroIdoso().setVisible(true);
        } catch (SQLException ex) {
            handleSQLException(ex);
        }
    }

    private void abrirCadastroAgente() {
        try {
            new CadastroAgente().setVisible(true);
        } catch (SQLException ex) {
            handleSQLException(ex);
        }
    }

    private void abrirHistorico() {
        try {
            new HistoricoView().setVisible(true);
        } catch (SQLException ex) {
            handleSQLException(ex);
        }
    }

    private void abrirAgenda() {
        try {
            new AgendaView().setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir Agenda de Visitas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirAlertasLembretes() {
        try {
            new AlertasLembretesView().setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir Alertas e Lembretes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSQLException(SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao abrir tela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new MenuMain().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
