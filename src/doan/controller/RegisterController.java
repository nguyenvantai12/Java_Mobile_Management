package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Customer;
import doan.models.Message;
import doan.models.Role;
import doan.models.User;
import doan.tcp.Client;
import doan.view.LoginView;
import doan.view.RegisterView;

 
 
 
 

public class RegisterController {
	private RegisterView rg;
	private Pattern emailPattern = Pattern.compile("^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$");
	private Pattern phonePattern = Pattern.compile("\\d{9}|(?:\\d{3}-){2}\\d{4}|\\(\\d{2}\\)\\d{3}-?\\d{4}$");
	private ObjectMapper mapper = new ObjectMapper();

	public RegisterController(RegisterView rg) {
		super();
		this.rg = rg;
		this.rg.addRegisterButton(new RegisterListener());
		this.rg.addNextLoginListener(new NextLoginListener());
	}

	class RegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = rg.getTxtUserName();
			String password = rg.getTxtPass();
			String phone = rg.getPhone();
			String email = rg.getEmail();
			String name = rg.getName();
			if (username.equals("") || password.equals("") || phone.equals("") || email.equals("") || name.equals("")) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
				return;
			}
			if (Validate(emailPattern, email)) {
				JOptionPane.showMessageDialog(null, "Email không hợp lệ");
				return;
			}
			if (Validate(phonePattern, phone)) {
				JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
				return;
			}
			Customer customer;
			try {
				String encodedPass = Base64.getEncoder().encodeToString(password.getBytes());
				customer = new Customer(0, name, "+" + phone,
						new User(0, username, encodedPass, email, new Role(2, "user")));
			} catch (Exception e1) {
				e1.printStackTrace();
				return;
			}
			String userJson;
			Message<?> msg;
			try {
				userJson = mapper.writeValueAsString(customer);
				msg = new Message<>("REGISTER", Customer.class, userJson);
				msg = Client.getInstance().Send(msg);
			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}

			if (msg.getHead().equals("SUCCESS")) {

				JOptionPane.showMessageDialog(null, "Đăng ký thành cấp nhật");

				LoginView re = new LoginView();
				new LoginController(re);
				re.setVisible(true);
				rg.dispose();
			}

			System.out.println(username + " " + password);
		}
	}

	class NextLoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LoginView re = new LoginView();
			new LoginController(re);
			re.setVisible(true);
			rg.dispose();
		}
	}

	private boolean Validate(Pattern pattern, String str) {
		Matcher matcher = pattern.matcher(str);
		return !matcher.find();
	}

}
