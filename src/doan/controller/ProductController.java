package doan.controller;

import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Message;
import doan.models.Product;
import doan.models.ProductAmount;
import doan.tcp.Client;
import doan.view.AdminView;
import doan.view.HomeView;
import doan.view.ProductPage;

public class ProductController {
	public ProductPage productView;
	public Product p;
	public JTable table;
	public List<Product> listitem;

	public ProductController(ProductPage productView) {
		super();
		this.productView = productView;
		this.productView.addBack(new addBack());
		this.productView.addLoad(new addLoad());
		this.table = productView.table();
		this.productView.addSearch(new addSearch());
		this.productView.table().addMouseListener(new MouseAdp());

	}

	class MouseAdp extends MouseAdapter {
		public void mousePressed(MouseEvent mouseEvent) {

			JTable table = (JTable) mouseEvent.getSource();
			Point point = mouseEvent.getPoint();
			int row = table.rowAtPoint(point);
			if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {

				var amount = JOptionPane.showInputDialog("Thêm vào giỏ hàng! Nhập số lượng");
				if (Integer.parseInt(amount) > 0) {
					try {
						ArrayList<ProductAmount> pro = Client.getInstance().getCart().getProducts();
//								.add(new ProductAmount(listitem.get(row), Integer.parseInt(amount)));
						int count = 0;
						for (ProductAmount p : pro) {
							System.out.println(p.getName());
							System.out.println(listitem.get(row).getName());
							if (p.getName().equals(listitem.get(row).getName())) {
								count = 3;
								p.setAmount(p.getAmount() + Integer.parseInt(amount));
							}
						}
						if (count != 0) {
							Client.getInstance().getCart().setProducts(pro);
						} else {
							Client.getInstance().getCart().getProducts()
									.add(new ProductAmount(listitem.get(row), Integer.parseInt(amount)));
						}
						System.out.println(count);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

	class addLoad implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			listitem = GetData("GETALL", null);
			String[] listColumn = new String[] { "ID", "Name", "Price", "Description", "Vendor", "Image" };
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
			int num = listitem.size();
			Product p = null;
			String currentDirectory = System.getProperty("user.dir");
			currentDirectory = currentDirectory + "/static";
			if (num > 0) {
				for (int i = 0; i < num; i++) {
					p = listitem.get(i);
					obj = new Object[columns];
					obj[0] = p.getId();

					obj[1] = p.getName();
					obj[2] = p.getPrice();
					obj[3] = p.getDescription();
					obj[4] = p.getVendorName();
					obj[5] = p.getImage();
					dtm.addRow(obj);
				}
			}
			table.setModel(dtm);
			table.setRowHeight(70);
			table.getColumnModel().getColumn(5).setCellRenderer(new ImageRender());

		}

	}

	class addSearch implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String txt = productView.getSearch();
			Product prod = new Product(0, txt, txt, 0, null, null);
			listitem = GetData("FIND", prod);
			String[] listColumn = new String[] { "ID", "Name", "Price", "Description", "Vendor","Image" };
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
			int num = listitem.size();
			Product p = null;
			if (num > 0) {
				for (int i = 0; i < num; i++) {
					p = listitem.get(i);
					obj = new Object[columns];
					obj[0] = p.getId();
					obj[1] = p.getName();
					obj[2] = p.getPrice();
					obj[3] = p.getDescription();
					obj[4] = p.getVendorName();
					obj[5]=p.getImage();
					dtm.addRow(obj);
				}
			}
			table.setModel(dtm);
			table.setRowHeight(70);
			table.getColumnModel().getColumn(5).setCellRenderer(new ImageRender());

		}

	}

	public class ImageRender extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if( value=="" || value ==null) {
				return new JLabel("");
			}
			String photoName = value.toString();
			
			ImageIcon imageIcon = new ImageIcon(
					new ImageIcon("static/" + photoName).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			return new JLabel(imageIcon);
		}
	}

	private List<Product> GetData(String operation, Product product) {
		ObjectMapper mapper = new ObjectMapper();
		String prod;
		List<Product> list = null;
		try {
			prod = mapper.writeValueAsString(product);
			Message<?> msg = new Message<>(operation, Product.class, prod);
			msg = Client.getInstance().Send(msg);
		 
			list = mapper.readValue(msg.getObject(), new TypeReference<List<Product>>() {
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	class addBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (Client.getInstance().getCustomer().getUser().getRole().getName().toUpperCase().equals("ADMIN")) {
					AdminView a = new AdminView();
					new AdminController(a);
					a.setVisible(true);
					productView.dispose();
				} else {
					HomeView home = new HomeView();
					new HomeController(home);
					home.setVisible(true);
					productView.setVisible(false);

				}
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

	}

}
