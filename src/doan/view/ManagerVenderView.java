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
import javax.swing.JTextField;

public class ManagerVenderView extends JPanel {

	private static final long serialVersionUID = 1L;

 	private JTextField txt_name;
	private JTable tb_data;
	private JButton btn_edit;
	private JScrollPane bottomPanel;
	private JTextField txt_email;
	private JTextField txt_phone;
	private JTextField txt_contry;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lb_ctr;
	private JButton btn_save;
	private JButton btn_remove;

	public ManagerVenderView() {
		setSize(1305, 721);
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

		txt_name = new JTextField();
		txt_name.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_name.setBounds(20, 51, 152, 20);
		topPanel.add(txt_name);
		txt_name.setColumns(10);

		btn_edit = new JButton("Sửa");
		btn_edit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_edit.setBounds(949, 25, 139, 25);
		topPanel.add(btn_edit);

		txt_email = new JTextField();
		txt_email.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_email.setColumns(10);
		txt_email.setBounds(200, 51, 152, 20);
		topPanel.add(txt_email);

		txt_phone = new JTextField();
		txt_phone.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_phone.setColumns(10);
		txt_phone.setBounds(378, 51, 152, 20);
		topPanel.add(txt_phone);

		txt_contry = new JTextField();
		txt_contry.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_contry.setColumns(10);
		txt_contry.setBounds(559, 49, 152, 20);
		topPanel.add(txt_contry);

		lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(200, 10, 152, 30);
		topPanel.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Phone");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(378, 10, 152, 30);
		topPanel.add(lblNewLabel_2);

		lb_ctr = new JLabel("Country");
		lb_ctr.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lb_ctr.setBounds(559, 10, 152, 30);
		topPanel.add(lb_ctr);

		btn_save = new JButton("Lưu");
		btn_save.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_save.setBounds(783, 25, 139, 25);
		topPanel.add(btn_save);

		btn_remove = new JButton("Xóa");
		btn_remove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_remove.setBounds(1127, 25, 139, 25);
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

	public JTextField getContry() {
		return txt_contry;
	}

	public JTextField getEmail() {
		return txt_email;
	}

	public JTextField getPhone() {
		return txt_phone;
	}

	public JTable getTable() {
		return tb_data;
	}

}
