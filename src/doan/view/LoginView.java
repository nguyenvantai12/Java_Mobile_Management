package doan.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btn_login;
	private JTextField txt_username;
	private JPasswordField txt_pass;
	private JButton btnNewButton;

	public LoginView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS\\OneDrive\\Pictures\\user (5).png"));
		setTitle("Đăng nhập");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(500, 200, 483, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btn_login = new JButton("Đăng nhập");
		btn_login.setForeground(new Color(0, 0, 0));
		btn_login.setBackground(new Color(0, 255, 255));

		btn_login.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_login.setBounds(155, 175, 134, 30);
		contentPane.add(btn_login);

		txt_username = new JTextField();
		txt_username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_username.setBounds(251, 42, 140, 30);
		contentPane.add(txt_username);
		txt_username.setColumns(10);

		txt_pass = new JPasswordField();
		txt_pass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_pass.setBounds(251, 101, 140, 30);
		contentPane.add(txt_pass);
		txt_pass.setColumns(10);

		JLabel lb_pass = new JLabel("Mật khẩu");
		lb_pass.setIcon(new ImageIcon("C:\\Users\\ASUS\\OneDrive\\Pictures\\key (1).png"));
		lb_pass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_pass.setBounds(63, 98, 100, 30);
		contentPane.add(lb_pass);

		JLabel lb_user = new JLabel("Tên người dùng");
		lb_user.setIcon(new ImageIcon("C:\\Users\\ASUS\\OneDrive\\Pictures\\user (2).png"));
		lb_user.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_user.setBounds(63, 42, 165, 30);
		contentPane.add(lb_user);

		btnNewButton = new JButton("Bạn chưa có tài khoản, Đăng ký ngay");
		btnNewButton.setBackground(new Color(0, 255, 64));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBounds(99, 232, 260, 21);
		contentPane.add(btnNewButton);
	}

	public JButton getBtn_login() {
		return btn_login;
	}
	
	public String getTxtUserName() {
		return txt_username.getText();
	}

	public String getTxtPass() {
		String pwd = new String(txt_pass.getPassword());

		return pwd;
	}

	public void addLoginListener(ActionListener listener) {
		btn_login.addActionListener(listener);
	}
	public void addNextRegisterListener(ActionListener listener) {
		btnNewButton.addActionListener(listener);
	}
}
