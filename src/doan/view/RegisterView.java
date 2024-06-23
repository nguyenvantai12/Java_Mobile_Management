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

public class RegisterView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_reigster_username;
	private JPasswordField txt_register_pass;
	private JButton btn_register;
	private JButton btn_nextLogin;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JLabel lb_dk_pass_2;
	private JTextField txtName;

	 
	public RegisterView() {
		setTitle("Đăng ký");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 450, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btn_register = new JButton("Đăng ký");
		btn_register.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_register.setBounds(182, 276, 100, 30);
		contentPane.add(btn_register);
		
		txt_reigster_username = new JTextField();
		txt_reigster_username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_reigster_username.setBounds(229, 36, 160, 30);
		contentPane.add(txt_reigster_username);
		txt_reigster_username.setColumns(10);
		
		JLabel lb_dk_username = new JLabel("Tên tài khoản");
		lb_dk_username.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_dk_username.setBounds(86, 39, 120, 30);
		contentPane.add(lb_dk_username);
		
		JLabel lb_dk_pass = new JLabel("Mật khẩu");
		lb_dk_pass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_dk_pass.setBounds(86, 79, 120, 30);
		contentPane.add(lb_dk_pass);
		
		txt_register_pass = new JPasswordField();
		txt_register_pass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txt_register_pass.setBounds(229, 80, 160, 30);
		contentPane.add(txt_register_pass);
		txt_register_pass.setColumns(10);
		
		btn_nextLogin = new JButton("Bạn đã có tài khoản. Đăng nhập ngay");
		btn_nextLogin.setBounds(78, 316, 311, 21);
		btn_nextLogin.setBackground(new Color(255, 255, 255));
		btn_nextLogin.setForeground(new Color(0, 0, 0));
		contentPane.add(btn_nextLogin);
		
		JLabel lb_dk_pass_1 = new JLabel("Phone");
		lb_dk_pass_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_dk_pass_1.setBounds(86, 228, 120, 30);
		contentPane.add(lb_dk_pass_1);
		
		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPhone.setColumns(10);
		txtPhone.setBounds(229, 228, 160, 30);
		contentPane.add(txtPhone);
		
		JLabel lb_dk_pass_1_1 = new JLabel("Email");
		lb_dk_pass_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_dk_pass_1_1.setBounds(87, 184, 120, 30);
		contentPane.add(lb_dk_pass_1_1);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtEmail.setColumns(10);
		txtEmail.setBounds(230, 184, 160, 30);
		contentPane.add(txtEmail);
		
		lb_dk_pass_2 = new JLabel("Name");
		lb_dk_pass_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_dk_pass_2.setBounds(86, 133, 120, 30);
		contentPane.add(lb_dk_pass_2);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtName.setColumns(10);
		txtName.setBounds(229, 134, 160, 30);
		contentPane.add(txtName);
	}
	public void addRegisterButton(ActionListener listener) {
		btn_register.addActionListener(listener);
	}
	public void addNextLoginListener(ActionListener listener) {
		btn_nextLogin.addActionListener(listener);
	}
	public String getTxtUserName() {
		return txt_reigster_username.getText();
	}

	public String getTxtPass() {
		String pwd = new String(txt_register_pass.getPassword());

		return pwd;
	}
	public String getName() {
		return txtName.getText();
	}
	public String getPhone() {
		return txtPhone.getText();
	}
	public String getEmail() {
		return txtEmail.getText();
	}
}
