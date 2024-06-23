package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Customer;
import doan.models.Message;
import doan.models.User;
import doan.tcp.Client;
import doan.view.AdminView;
import doan.view.HomeView;
import doan.view.LoginView;
import doan.view.RegisterView;

public class LoginController {
	private LoginView loginview;

	public LoginController(LoginView login) {
		super();
		this.loginview = login;
		this.loginview.addLoginListener(new LoginListener());
		this.loginview.addNextRegisterListener(new NextRegisterListener());
	}

	class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = loginview.getTxtUserName();
			String password = loginview.getTxtPass();
			System.out.println(password);
 
			ObjectMapper mapper = new ObjectMapper();
			User user;
			try {
				String encodedPass = Base64.getEncoder().encodeToString(password.getBytes());
				user = new User(0, username, encodedPass, "", null);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				ex.printStackTrace();

				return;
			}
			Message<?> message;
			Message<?> responseMessage;
			try {
				message = new Message<>("LOGIN", User.class, mapper.writeValueAsString(user));
				responseMessage = Client.getInstance().Send(message);
				System.out.println(responseMessage);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				ex.printStackTrace();
				return;
			}

			if (responseMessage.getHead().equals("ERROR") || responseMessage.getHead().equals("FAILURE"))
			{
				JOptionPane.showMessageDialog(null, responseMessage.getObject());
 			}
			else {
 				try {
					Customer customer = mapper.readValue(responseMessage.getObject(), Customer.class);
					Client.getInstance().setCustomer(customer);
 					if (customer.getRole().equals("blocked")) {
						JOptionPane.showMessageDialog(null, "Tài khoản của bạn đã bị khóa");
						return;
					}
					if (Client.getInstance().getCustomer().getUser().getRole().getName().toUpperCase().equals("ADMIN")) {
						AdminView ad =new AdminView();
						new AdminController(ad);
						ad.setVisible(true);
						loginview.dispose();
					}else {
						HomeView v=new HomeView();
						new HomeController(v);
						v.setVisible(true);
						loginview.dispose();
					}
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
				
			}
		}
	}

	class NextRegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			RegisterView re = new RegisterView();
			new RegisterController(re);
			re.setVisible(true);
			loginview.dispose();
		}
	}

}
