package gui;

import com.formdev.flatlaf.FlatLightLaf;
import bus.TaiKhoanBUS;
import dto.TaiKhoanDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class LoginGUI extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblTitle, lblUsername, lblPassword, lblError;
    private TaiKhoanBUS taiKhoanBUS;

    public LoginGUI() {
        // Thiết lập giao diện FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        taiKhoanBUS = new TaiKhoanBUS();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Đăng Nhập Hệ Thống");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(66, 165, 245), 0, getHeight(), new Color(33, 147, 176));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Roboto", Font.PLAIN, 16));
        lblUsername.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(lblUsername, gbc);

        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Roboto", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(250, 35));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(txtUsername, gbc);

        lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Roboto", Font.PLAIN, 16));
        lblPassword.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        contentPanel.add(lblPassword, gbc);

        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Roboto", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(250, 35));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(txtPassword, gbc);

        lblError = new JLabel("");
        lblError.setFont(new Font("Roboto", Font.ITALIC, 12));
        lblError.setForeground(new Color(255, 82, 82));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        contentPanel.add(lblError, gbc);

        btnLogin = new JButton("Đăng Nhập");
        btnLogin.setFont(new Font("Roboto", Font.BOLD, 16));
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setPreferredSize(new Dimension(150, 40));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(39, 174, 96));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(46, 204, 113));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        contentPanel.add(btnLogin, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> handleLogin());
        txtPassword.addActionListener(e -> handleLogin());

        setContentPane(mainPanel);
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (taiKhoanBUS.checkLogin(username, password)) {
            TaiKhoanDTO taiKhoan = taiKhoanBUS.getByUsername(username);
            lblError.setText("");
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            try {
                Main mainFrame = new Main(taiKhoan);
                mainFrame.setVisible(true);
                dispose();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        } else {
            lblError.setText("Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI login = new LoginGUI();
            login.setVisible(true);
        });
    }

}
