package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Customer;
import doan.models.Message;
import doan.models.Order;
import doan.models.User;
import doan.tcp.Client;
import doan.view.ManagerOrderView;

public class ManagerOrderController {
	public ManagerOrderView view;
	public JTable table;
	public List<Order> listItem;
	public Order o;

	public ManagerOrderController(ManagerOrderView view) {
		super();
		this.view = view;

		table = this.view.getTable();
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRowIndex = table.getSelectedRow();
				selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
				o = listItem.get(selectedRowIndex);
				for (Order or : listItem) {
					if (o.getStatus().equals(or.getStatus())) {
						view.getCombo().setSelectedItem(o.getStatus());
					}
				}
			}
		});
		updateTable();
		this.view.addXacNhan(new updateOrder());
	}

	class updateOrder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ObjectMapper mapper = new ObjectMapper();
			o.setStatus(view.getCombo().getSelectedItem().toString());
			System.out.println(o.toString());
			try {
				String json = mapper.writeValueAsString(o);
				Client.getInstance().Send(new Message<>("UPDATE", Order.class, json));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			updateTable();
		}

	}

	public JTable updateTable() {
		try {
			listItem = GetData(new Order(0, null, 0, Client.getInstance().getCustomer(), null, null));
		} catch (IOException e) {

			e.printStackTrace();
		}
		String[] listColumn = new String[] { "ID", "Name", "Total price", "Date", "Status" };
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

	private List<Order> GetData(Order order) {
		ObjectMapper mapper = new ObjectMapper();
		List<Order> list = null;
		try {
			String orderJson = mapper.writeValueAsString(order);
			Message<?> msg = new Message<>("GETALL", Order.class, orderJson);
			msg = Client.getInstance().Send(msg);
			list = mapper.readValue(msg.getObject(), new TypeReference<List<Order>>() {
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}
}
