package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import doan.view.ManagerCategoryView;

public class ManagerCategoryController {
	public ManagerCategoryView view;
	public JTable table;
	public List<Category> listItem;
	public Category c;

	public ManagerCategoryController(ManagerCategoryView view) {
		super();
		this.view = view;
		
		this.table=view.getTable();
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRowIndex = table.getSelectedRow();
				selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
				 c=listItem.get(selectedRowIndex);
				 view.getTxtName().setText(c.getName());
				 view.getDescription().setText(c.getDescription());
			}
		});
		this.view.addSave(new addSave());
		this.view.addCreate(new addCreate());
		this.view.addRemove(new addRemove());
		updateTable();
	
	}

	class addSave implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name=view.getTxtName().getText();
			String des=view.getDescription().getText();
			c.setDescription(des);
			c.setName(name);
			if( name.isEmpty()|| des.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Vui lòng nhập 1 trường");

			}else {
				ObjectMapper mapper = new ObjectMapper();
 				String orderJson = null;
				try {
					orderJson = mapper.writeValueAsString(c);
				} catch (JsonProcessingException ex) {
					ex.printStackTrace();
				}
				Message<Category> msg = new Message<>("UPDATE", Category.class, orderJson);
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
			String name=view.getTxtName().getText() ;
			String des=view.getDescription().getText();
			if(! name.isEmpty() || !des.isEmpty())
			{
				ObjectMapper mapper = new ObjectMapper();
				Category cate = new Category( 0,name,des);
				String orderJson = null;
				try {
					orderJson = mapper.writeValueAsString(cate);
				} catch (JsonProcessingException ex) {
					ex.printStackTrace();
				}
				Message<Category> msg = new Message<>("CREATE", Category.class, orderJson);
				try {
					System.out.println(msg.getObject());
					Message<?> res = Client.getInstance().Send(msg);
					System.out.println(res.getObject());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			view.getTxtName().setText("");
			view.getDescription().setText("");
			updateTable();
		}

	}

	class addRemove implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(c.getDescription().isEmpty()) {
				JOptionPane.showMessageDialog(view, "Vui lòng chọn 1 category");
			}
			ObjectMapper mapper = new ObjectMapper();
			String orderJson = null;
			try {
				orderJson = mapper.writeValueAsString(c);
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
			Message<Category> msg = new Message<>("DELETE", Category.class, orderJson);
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

	private List<Category> GetData(String operation,Category category) {
		ObjectMapper mapper = new ObjectMapper();
		List<Category> list = null;
		try {
			String categoryJson = mapper.writeValueAsString(category);
			
			Message<?> msg = new Message<>(operation, Category.class, categoryJson);
			msg = Client.getInstance().Send(msg);
			System.out.println(msg);
			list = mapper.readValue(msg.getObject(), new TypeReference<List<Category>>() {
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public JTable updateTable() {
		listItem = GetData( "GETALL",null);
		String[] listColumn = new String[] { "ID", "Name", "Description" };
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
		Category p = null;
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				p = listItem.get(i);
				obj = new Object[columns];
				obj[0] = p.getId();
				obj[1] = p.getName();
				obj[2] = p.getDescription();

				dtm.addRow(obj);
			}
		}
		table.setModel(dtm);
		return table;
	}
}
