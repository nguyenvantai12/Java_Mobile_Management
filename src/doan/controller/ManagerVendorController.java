package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Category;
import doan.models.Message;
import doan.models.Vendor;
import doan.tcp.Client;
import doan.view.ManagerVenderView;

public class ManagerVendorController {
	public ManagerVenderView view;
	public JTable table;
	public List<Vendor> listItem;
	public Vendor v;
	public ManagerVendorController(ManagerVenderView vendor) {
		super();
		this.view = vendor;

		table=vendor.getTable();
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRowIndex = table.getSelectedRow();
				selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
				 v=listItem.get(selectedRowIndex);
				 view.getTxtName().setText(v.getName());
				 view.getEmail().setText(v.getEmail());
				 view.getPhone().setText(v.getPhoneNumber());
				 view.getContry().setText(v.getCountry());
			}
		});
		updateTable();
		this.view.addRemove(new addRemove());
		this.view.addSave(new addSave());
		this.view.addCreate(new addCreate());
	}

	class addSave implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name=view.getTxtName().getText();
			String phone=view.getPhone().getText();
			String contry=view.getContry().getText();
			String email=view.getEmail().getText();
			v.setCountry(contry);
			v.setEmail(email);
			v.setPhoneNumber(phone);
			v.setName(name);
			if ( name.isEmpty() || phone.isEmpty()||contry.isEmpty() || email.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Vui lòng nhập 1 trường");
			}else {
				ObjectMapper mapper = new ObjectMapper();
				 System.out.println(v.toString());
				String orderJson = null;
				try {
					orderJson = mapper.writeValueAsString(v);
				} catch (JsonProcessingException ex) {
					ex.printStackTrace();
				}
				Message<Vendor> msg = new Message<>("UPDATE", Vendor.class, orderJson);
				try {
					System.out.println(msg.getObject());
					Message<?> res = Client.getInstance().Send(msg);
					System.out.println(res.getObject());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
			updateTable();
		}

	}

	class addCreate implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name=view.getTxtName().getText();
			String phone=view.getPhone().getText();
			String contry=view.getContry().getText();
			String email=view.getEmail().getText();
			if ( name.isEmpty() || phone.isEmpty()||contry.isEmpty() || email.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Vui lòng nhập 1 trường");
			}else {
				ObjectMapper mapper = new ObjectMapper();
				Vendor cate = new Vendor( 0,name,email,phone,contry);
				String orderJson = null;
				try {
					orderJson = mapper.writeValueAsString(cate);
				} catch (JsonProcessingException ex) {
					ex.printStackTrace();
				}
			 
				Message<Vendor> msg = new Message<>("CREATE", Vendor.class, orderJson);
				try {
					System.out.println(msg.getObject());
					Message<?> res = Client.getInstance().Send(msg);
					System.out.println(res.getObject());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			updateTable();
		}

	}

	class addRemove implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(view.getEmail().getText().isEmpty()) {
				JOptionPane.showMessageDialog(view, "Vui lòng chọn 1 vendor");
			}
			ObjectMapper mapper = new ObjectMapper();
			String orderJson = null;
			try {
				orderJson = mapper.writeValueAsString(v);
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
			Message<Vendor> msg = new Message<>("DELETE", Vendor.class, orderJson);
			try {
				System.out.println(msg.getObject());
				Message<?> res = Client.getInstance().Send(msg);
				System.out.println(res.getObject());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			updateTable();
		}

	}

	private List<Vendor> GetData(String operation, Vendor product) {
		ObjectMapper mapper = new ObjectMapper();
		String prod;
		List<Vendor> list = null;
		try {
			prod = mapper.writeValueAsString(product);
			Message<?> msg = new Message<>(operation, Vendor.class, prod);
			msg = Client.getInstance().Send(msg);
			list = mapper.readValue(msg.getObject(), new TypeReference<List<Vendor>>() {
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public void updateTable() {
		listItem = GetData("GETALL", null);
		String[] listColumn = new String[] { "ID", "Name", "Email", "Phone", "Country" };
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
		Vendor p = null;
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				p = listItem.get(i);
				obj = new Object[columns];
				obj[0] = p.getId();
				obj[1] = p.getName();
				obj[2]=p.getEmail();
				obj[3]=p.getPhoneNumber();
				obj[4]=p.getCountry();
 
				dtm.addRow(obj);
			}
		}
		table.setModel(dtm);
	}

	 
}
