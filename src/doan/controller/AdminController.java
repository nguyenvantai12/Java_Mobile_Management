package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import doan.tcp.Client;
import doan.view.AdminView;
import doan.view.CartPage;
import doan.view.LoginView;
import doan.view.MangerView;
import doan.view.OrderPage;
import doan.view.ProductPage;

public class AdminController {
	public AdminView admin;

	public AdminController(AdminView admin) {
		super();
		this.admin = admin;
		this.admin.addQuanly(new QuanlyListener());
	 
		this.admin.addCart(new addCart());
		this.admin.addDanhSach(new DanhSach());
		this.admin.addOrder(new addOrder());
	}

	class addOrder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			OrderPage p = new OrderPage();
			new OrderController(p);
			p.setVisible(true);
			admin.setVisible(false);
		}

	}

	class Logout implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			LoginView l = new LoginView();
			new LoginController(l);
			l.setVisible(true);
			admin.dispose();

		}
	}

	class DanhSach implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ProductPage p = new ProductPage();
			new ProductController(p);
			p.setVisible(true);
			admin.dispose();

		}

	}

	class addCart implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CartPage c = new CartPage();
			new CartController(c);
			c.setVisible(true);
			admin.setVisible(false);

		}

	}

	class QuanlyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MangerView m = new MangerView();
			
			new AdminMainController(m);
			m.setVisible(true);
			m.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					int confirm = JOptionPane.showOptionDialog(m, "Are you sure you want to close this window?",
							"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
							null);
					if (confirm == JOptionPane.NO_OPTION) {

					} else if (confirm == JOptionPane.YES_OPTION) {
						try {
							Client.getInstance().Close();

						} catch (IOException e1) {

							e1.printStackTrace();
							System.out.println(e1.getMessage());
						} finally {
							m.dispose();
						}

					}
				}
			});
			admin.dispose();
		}

	}

}
