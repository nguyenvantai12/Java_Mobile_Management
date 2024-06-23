package doan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ManagerProductView extends JPanel {

	private static final long serialVersionUID = 1L;

 	private JTextField txt_name;
	private JTable tb_data;
	private JButton btn_remove;
	private JScrollPane bottomPanel;
	private JTextField txt_price;
	private JLabel lblPrice;
	private JLabel lblNewLabel_3;
	private JComboBox<String> txt_combo;
	private JTextArea txt_des;
	private JButton btn_save;
	private JButton btn_edit;
	private JButton btn_choose;
	public ManagerProductView() {
		
		setSize(1408, 759);
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(400, 80)); // 1/5 của 400

		// Tạo bottom panel
		bottomPanel = new JScrollPane();
		bottomPanel.setBackground(new Color(0, 0, 0)); // Chỉ để nhìn rõ panel

		// Thêm topPanel và bottomPanel vào mainPanel
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setToolTipText("Name product");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(20, 10, 152, 30);
		topPanel.add(lblNewLabel);

		txt_name = new JTextField();
		txt_name.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_name.setBounds(20, 51, 152, 20);
		topPanel.add(txt_name);
		txt_name.setColumns(10);

		btn_remove = new JButton("Xóa");
		 
		btn_remove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_remove.setBounds(1298, 33, 100, 25);
		topPanel.add(btn_remove);
		
		txt_price = new JTextField();
		txt_price.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_price.setColumns(10);
		txt_price.setBounds(188, 51, 139, 20);
		topPanel.add(txt_price);
		
		lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPrice.setBounds(187, 10, 140, 30);
		topPanel.add(lblPrice);
		
		lblNewLabel_3 = new JLabel("Nhà cung cấp");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(371, 10, 140, 30);
		topPanel.add(lblNewLabel_3);
		
		txt_combo = new JComboBox<String>();
		txt_combo.setBounds(355, 50, 158, 21);
		topPanel.add(txt_combo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(553, 10, 305, 60);
		topPanel.add(scrollPane);
		
		txt_des = new JTextArea();
		scrollPane.setViewportView(txt_des);
		txt_des.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		btn_save = new JButton("Lưu");
		btn_save.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_save.setBounds(1053, 33, 116, 25);
		topPanel.add(btn_save);
		
		btn_edit = new JButton("Sửa");
	 
		btn_edit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_edit.setBounds(1179, 33, 109, 25);
		topPanel.add(btn_edit);
		
		btn_choose = new JButton("Chọn ảnh ");
		btn_choose.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_choose.setBounds(916, 33, 116, 25);
		topPanel.add(btn_choose);
		add(bottomPanel, BorderLayout.CENTER);

		tb_data = new JTable();
		bottomPanel.setViewportView(tb_data);
	}
	public void addRemove(ActionListener action) {
		btn_remove.addActionListener(action);
	}
	public void addCreate(ActionListener action) {
		btn_save.addActionListener(action);
	}
	public void addSave(ActionListener action) {
		btn_edit.addActionListener(action);
	}
	public void addChoose(ActionListener action) {
		btn_choose.addActionListener(action);
	}
	
	public JTextField getTxtName() {
		return txt_name;
	}
 
	public JTextField getPrice() {
		return txt_price;
	}
	
	public JTextArea getDescription() {
		return txt_des;
	}
	public JComboBox<String> getCombo(){
		return txt_combo;
	}
	public JTable getTable() {
		return tb_data;
	}
}
