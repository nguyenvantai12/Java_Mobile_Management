package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Cart;
import doan.models.Customer;
import doan.models.Message;
import doan.models.Order;
import doan.models.Order_Product;
import doan.models.ProductAmount;
import doan.tcp.Client;
import doan.view.AdminView;
import doan.view.CartPage;
import doan.view.HomeView;

public class CartController {
	public CartPage cart;
	public Cart c;
	public ArrayList<ProductAmount> listItem;
	public JTable table;
	public ProductAmount p;
	public JTextField txt;
	private Customer customer;

	public CartController(CartPage cart) {
		super();
		this.cart = cart;
		this.cart.addBack(new addBack());
		this.cart.addLoad(new addLoad());
		this.cart.addRemove(new addRemove());
		this.cart.addSave(new addEdit());
		this.table = cart.table();

		this.txt = cart.getText();
		this.cart.addXacNhan(new addXacNhan());
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRowIndex = table.getSelectedRow();
				selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
				p = listItem.get(selectedRowIndex);
				cart.getText().setText(String.valueOf(p.getAmount()));
			}
		});
		try {
			c = Client.getInstance().getCart();
			customer = Client.getInstance().getCustomer();
			table = updateTable();

		} catch (IOException e1) {

			e1.printStackTrace();
		}
		table=updateTable();
	}

	class addXacNhan implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (listItem.isEmpty()) {
				JOptionPane.showMessageDialog(cart, "Khong co san pham nao ca");
				return;
			}
			ObjectMapper mapper = new ObjectMapper();
			ArrayList<Order_Product> order_products = new ArrayList<>();
			for (ProductAmount pa : c.getProducts()) {
				order_products
						.add(new Order_Product(0, null, pa.getProduct().getId(), pa.getProduct(), pa.getAmount()));
			}
			Order order = new Order(0, null, c.getTotalPrice(), customer, order_products, null);
			String orderJson = null;
			try {
				orderJson = mapper.writeValueAsString(order);
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
			Message<Order> msg = new Message<>("CREATE", Order.class, orderJson);
			try {
				System.out.println(msg.getObject());
				Message<?> res = Client.getInstance().Send(msg);
				System.out.println(res.getObject());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			c.getProducts().clear();
			table = updateTable();

		}

	}

	class addRemove implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			listItem.remove(p);
			table = updateTable();

		}

	}

	class addEdit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (ProductAmount product : listItem) {
				if (product.getName().equals(p.getName())) {
					product.setAmount(Integer.parseInt(txt.getText()));
				}
			}
			try {
				Client.getInstance().getCart().setProducts(listItem);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			table = updateTable();

		}

	}

	class addLoad implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

   
			try {
				c = Client.getInstance().getCart();
				customer = Client.getInstance().getCustomer();
				table = updateTable();

			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}
	}

	public JTable updateTable() {
		listItem = c.getProducts();
		String[] listColumn = new String[] { "Name", "Amount", "Price", };
		int columns = listColumn.length;
		DefaultTableModel dtm = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndenx, int colIdenx) {
				return false;
			}
		};
		dtm.setColumnIdentifiers(listColumn);
		Object[] obj = null;
		int num = listItem.size();
		ProductAmount p = null;
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				p = listItem.get(i);
				obj = new Object[columns];
				obj[0] = p.getName();
				obj[1] = p.getAmount();
				obj[2] = p.getPrice();

				dtm.addRow(obj);
			}
		}
		table.setModel(dtm);
		return table;
	}

	class addBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (Client.getInstance().getCustomer().getUser().getRole().getName().toUpperCase().equals("ADMIN")) {
					AdminView home = new AdminView();
					new AdminController(home);
					home.setVisible(true);
					cart.setVisible(false);
				} else {
					HomeView home = new HomeView();
					new HomeController(home);
					home.setVisible(true);
					cart.setVisible(false);
				}
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		}

	}

}
