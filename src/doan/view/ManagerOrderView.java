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
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;

public class ManagerOrderView extends JPanel {

	private static final long serialVersionUID = 1L;

 	private JTextField txt_search;
	private JTable tb_data;
	private JButton btn_search;
	private JScrollPane bottomPanel;
	private JComboBox<String> combo;
	private JButton btn_xacnhan;

	public ManagerOrderView() {
		setSize(787, 604);
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(400, 80)); // 1/5 của 400

		// Tạo bottom panel
		bottomPanel = new JScrollPane();
		bottomPanel.setBackground(Color.BLUE); // Chỉ để nhìn rõ panel

		// Thêm topPanel và bottomPanel vào mainPanel
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Tìm sản phẩm");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(20, 10, 152, 30);
		topPanel.add(lblNewLabel);

		txt_search = new JTextField();
		txt_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_search.setBounds(20, 51, 152, 20);
		topPanel.add(txt_search);
		txt_search.setColumns(10);

		btn_search = new JButton("Tìm kiếm");
		btn_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_search.setBounds(193, 49, 139, 25);
		topPanel.add(btn_search);
		
		combo = new JComboBox<String>();
		combo.setModel(new DefaultComboBoxModel(new String[] {"Pending", "Accepted", "Rejected"}));
		combo.setBounds(353, 50, 177, 21);
		topPanel.add(combo);
		
		btn_xacnhan = new JButton("Xác nhận");
		btn_xacnhan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_xacnhan.setBounds(547, 49, 139, 25);
		topPanel.add(btn_xacnhan);
		add(bottomPanel, BorderLayout.CENTER);

		tb_data = new JTable();
		bottomPanel.setViewportView(tb_data);

	}

	public JTable getTable() {
		return tb_data;
	}
	public void addXacNhan(ActionListener action) {
		  btn_xacnhan.addActionListener(action);
	}
	public JComboBox< String> getCombo(){
		return combo;
	}
}
