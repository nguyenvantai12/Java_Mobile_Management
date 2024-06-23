package doan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ManagerCategoryView extends JPanel {

	private static final long serialVersionUID = 1L;

 	private JTable tb_data;
	private JButton btn_save;
	private JScrollPane bottomPanel;
	private JTextField txt_name;
	private JScrollPane scrollPane;
	private JTextArea txt_des;
	private JButton btn_edit;
	private JButton btn_remove;

	public ManagerCategoryView() {
		setSize(1312, 729);
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(400, 80)); // 1/5 của 400

		// Tạo bottom panel
		bottomPanel = new JScrollPane();
		bottomPanel.setBackground(Color.BLUE); // Chỉ để nhìn rõ panel

		// Thêm topPanel và bottomPanel vào mainPanel
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(20, 10, 152, 30);
		topPanel.add(lblNewLabel);

		btn_save = new JButton("Lưu");
		btn_save.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_save.setBounds(549, 30, 139, 25);
		topPanel.add(btn_save);
		
		txt_name = new JTextField();
		txt_name.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_name.setColumns(10);
		txt_name.setBounds(20, 50, 152, 20);
		topPanel.add(txt_name);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(205, 10, 290, 60);
		topPanel.add(scrollPane);
		
		txt_des = new JTextArea();
		scrollPane.setViewportView(txt_des);
		
		btn_edit = new JButton("Sửa");
		btn_edit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_edit.setBounds(717, 30, 139, 25);
		topPanel.add(btn_edit);
		
		btn_remove = new JButton("Xóa");
		btn_remove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_remove.setBounds(898, 30, 139, 25);
		topPanel.add(btn_remove);
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
	public JTextField getTxtName() {
		return txt_name;
	}
	 
	public JTextArea getDescription() {
		return txt_des;
	}
 
	public JTable getTable() {
		return tb_data;
	}
}
