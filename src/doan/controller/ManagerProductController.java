package doan.controller;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Category;

import doan.models.Message;
import doan.models.Product;
import doan.models.Vendor;
import doan.tcp.Client;
import doan.view.ManagerProductView;

import doan.db.*;

public class ManagerProductController {
	public ManagerProductView product;
	public JTable table;
	public List<Product> listItem;
	public List<Category> listCategory;
	public List<Vendor> listVendor;
	public Product p;
	public Vendor vendor;
	public Category c;
	public FileInputStream img;
 
	public File anh=null;

	public ManagerProductController(ManagerProductView product) {
		super();
		this.product = product;
		this.product.addRemove(new addRemove());
		this.product.addSave(new addSave());
		this.product.addCreate(new addCreate());
		this.product.addChoose(new addChoose());
		this.table = this.product.getTable();
		listCategory = GetCategory("GETALL", null);
		listVendor = GetVendor("GETALL", null);
		for (Vendor vd : listVendor) {
			product.getCombo().addItem(vd.getName());
		}
		table = updateTable();

		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRowIndex = table.getSelectedRow();
				selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
				p = listItem.get(selectedRowIndex);
				product.getTxtName().setText(p.getName());
				product.getDescription().setText(p.getDescription());
				product.getPrice().setText(String.valueOf(p.getPrice()));
				try {
					String currentDirectory = System.getProperty("user.dir");
					currentDirectory=currentDirectory+"/static";
					img=new FileInputStream(currentDirectory+"/"+p.getImage());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (Product pro : listItem) {
					if (p.getVendorName().equals(pro.getVendorName())) {
						System.out.println(p.getVendorName());

						product.getCombo().setSelectedItem(p.getVendorName());
					}
				}

			}
		});

	}

	class addChoose implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter(new ImageFilter());
			fileChooser.setAcceptAllFileFilterUsed(false);

			int option = fileChooser.showOpenDialog(product);
			if (option == JFileChooser.APPROVE_OPTION) {
				anh = fileChooser.getSelectedFile();
				try {
					img = new FileInputStream(anh.getAbsolutePath());
					

				} catch (FileNotFoundException e1) {

					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(product, "File Choose" + anh.getName());
			} else {
				JOptionPane.showMessageDialog(product, "ERR ");
			}
		}

	}
	 private static File getUniqueFile(String directory, String baseName, String extension) {
	        File file;
	        int count = 0;
	        do {
	            String fileName = baseName + (count == 0 ? "" : "_" + count) + "." + extension;
	            file = new File(directory, fileName);
	            count++;
	        } while (file.exists());
	        return file;
	    }

	class addCreate implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = product.getTxtName().getText();
			String price = product.getPrice().getText();
			String vender = product.getCombo().getSelectedItem().toString();
			String des = product.getDescription().getText();
			try {

				if (img.read() == -1) {
					JOptionPane.showMessageDialog(product, "Không được để trống ảnh");
					return;
				} else {
 
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					for (int readNum; (readNum = img.read(buf)) != -1;) {
						bos.write(buf, 0, readNum);
					}
					String currentDirectory = System.getProperty("user.dir");
					currentDirectory=currentDirectory+"/static";
					Path directory = Paths.get(currentDirectory);
		            if (!Files.exists(directory)) {
		                Files.createDirectories(directory);
		            }
					File sourceFile = new File(anh.getAbsolutePath());
			        String baseFileName = "product";
			        String fileExtension = "png";
					File file = getUniqueFile(currentDirectory, baseFileName, fileExtension);
					BufferedImage bufferedImage = ImageIO.read(sourceFile);
				
					anh=file;
					if (ImageIO.write(bufferedImage, "png", file)) {
						System.out.println("Tạo và lưu tệp ảnh thành công: " + file.getAbsolutePath());
					} else {
						System.out.println("Không thể lưu tệp ảnh.");
					}
					 

				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(product, "Không được để trống ảnh");

				e1.printStackTrace();
			}

			for (Vendor vd : listVendor) {
				if (vd.getName().equals(vender)) {
					vendor = vd;
				}
			}

			Product pr = new Product(0, name, des, Double.parseDouble(price), vendor, null,anh.getName());
			ObjectMapper mapper = new ObjectMapper();

			String orderJson = null;
			try {
				orderJson = mapper.writeValueAsString(pr);
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
			Message<Product> msg = new Message<>("CREATE", Product.class, orderJson);
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
 

	class addSave implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = product.getTxtName().getText();
			String price = product.getPrice().getText();
			String des = product.getDescription().getText();
			String vender = product.getCombo().getSelectedItem().toString();
			
			try {

				if (img.read() == -1) {
					JOptionPane.showMessageDialog(product, "Không được để trống ảnh");
					return;
				} else {
 
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					for (int readNum; (readNum = img.read(buf)) != -1;) {
						bos.write(buf, 0, readNum);
					}
					String currentDirectory = System.getProperty("user.dir");
					currentDirectory=currentDirectory+"/static";
					Path directory = Paths.get(currentDirectory);
		            if (!Files.exists(directory)) {
		                Files.createDirectories(directory);
		            }
		            if(anh==null) {
		            	
		            }else {
		            	
		          
					File sourceFile = new File(anh.getAbsolutePath());
			        String baseFileName = "product";
			        String fileExtension = "png";
					File file = getUniqueFile(currentDirectory, baseFileName, fileExtension);
					BufferedImage bufferedImage = ImageIO.read(sourceFile);
				
					anh=file;
					if (ImageIO.write(bufferedImage, "png", file)) {
						System.out.println("Tạo và lưu tệp ảnh thành công: " + file.getAbsolutePath());
					} else {
						System.out.println("Không thể lưu tệp ảnh.");
					}
		            }
					 

				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(product, "Không được để trống ảnh");

				e1.printStackTrace();
			}
			
			for (Vendor vd : listVendor) {
				if (vd.getName().equals(vender)) {
					vendor = vd;
				}
			}
			p.setDescription(des);
			p.setName(name);
			p.setPrice(Double.parseDouble(price));
			p.setVendor(vendor);
			if( anh==null) {
				
			}else {
				p.setImage(anh.getName());
			}
			

			ObjectMapper mapper = new ObjectMapper();
			String orderJson = null;
			try {
				orderJson = mapper.writeValueAsString(p);
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
			System.out.println(p.toString());
			Message<Product> msg = new Message<>("UPDATE", Product.class, orderJson);
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

	class addRemove implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (p.getDescription().isEmpty()) {
				JOptionPane.showMessageDialog(product, "Vui lòng chọn 1 product");
			}
			ObjectMapper mapper = new ObjectMapper();
			String orderJson = null;
			try {
				orderJson = mapper.writeValueAsString(p);
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
			Message<Product> msg = new Message<>("DELETE", Product.class, orderJson);
			try {
				System.out.println(msg.getObject());
				Message<?> res = Client.getInstance().Send(msg);
				System.out.println(res.getObject());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			p=null;
			updateTable();
		}

	}

	private List<Category> GetCategory(String operation, Category category) {
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

	private List<Vendor> GetVendor(String operation, Vendor vendor) {
		ObjectMapper mapper = new ObjectMapper();
		String prod;
		List<Vendor> list = null;
		try {
			prod = mapper.writeValueAsString(vendor);
			Message<?> msg = new Message<>(operation, Vendor.class, prod);
			msg = Client.getInstance().Send(msg);
			list = mapper.readValue(msg.getObject(), new TypeReference<List<Vendor>>() {
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	public JTable updateTable() {
		listItem = GetData("GETALL", null);
		 
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
		int num = listItem.size();
		Product p = null;
		String currentDirectory = System.getProperty("user.dir");
		currentDirectory=currentDirectory+"/static";
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				p = listItem.get(i);
				System.out.println(p.getImage());
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
		return table;
	}
	public class ImageRender extends DefaultTableCellRenderer{
		@Override
		public Component getTableCellRendererComponent(JTable table,Object value,boolean  isSelected,boolean hasFocus,int row,int column) {
			String photoName=value.toString();
			ImageIcon imageIcon=new ImageIcon(new ImageIcon("static/"+photoName).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			return new JLabel(imageIcon);
		}
	}

}
