package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Message;
import doan.models.Order;
import doan.tcp.Client;
import doan.view.AdminView;
import doan.view.HomeView;
import doan.view.OrderPage;

public class OrderController {
	public OrderPage order;
	public JTable table;
	public List<Order> listItem;
	public Order o;

	public OrderController(OrderPage order) {
		super();
		this.order = order;
		this.table = order.getTable();
		this.order.addBack(new addBack());
		updateTable();
	}
	class addBack implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (Client.getInstance().getCustomer().getUser().getRole().getName().toUpperCase().equals("ADMIN")) {
					AdminView home = new AdminView();
					new AdminController(home);
					home.setVisible(true);
					order.setVisible(false);
				} else {
					HomeView home = new HomeView();
					new HomeController(home);
					home.setVisible(true);
					order.setVisible(false);
				}
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			
		}
		
	}
	private List<Order> GetData(Order order) {
		ObjectMapper mapper = new ObjectMapper();
		List<Order> list = null;
		try {
			String orderJson = mapper.writeValueAsString(order);
			Message<?> msg = new Message<>("GETCUSTOMERSORDERS", Order.class, orderJson);
			msg = Client.getInstance().Send(msg);
			list = mapper.readValue(msg.getObject(), new TypeReference<List<Order>>() {
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public JTable updateTable() {
		try {
			listItem = GetData(new Order(0, null, 0, Client.getInstance().getCustomer(), null, null));
		} catch (IOException e) {

			e.printStackTrace();
		}
		String[] listColumn = new String[] { "ID", "Name", "Status", "Total price", "Date" };
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
		Order p = null;
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				p = listItem.get(i);
				obj = new Object[columns];
				obj[0] = p.getId();
				obj[1] = p.getCustomerName();
				obj[2] = p.getTotalPrice();
				obj[3] = p.getDatetime();
				obj[4] = p.getStatus();
				dtm.addRow(obj);
			}
		}
		table.setModel(dtm);
		return table;
	}
}
