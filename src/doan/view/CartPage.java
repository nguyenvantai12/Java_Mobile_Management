package doan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import doan.tcp.Client;

public class CartPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane bottomPanel;

	private JTextField txt_quantity;
	private JTable tb_data;
	private JPanel topPanel;
	private JButton btn_back;
	private JButton btn_luu;
	private JButton btn_xoa;
	private JButton btn_xacnhan;
	private JButton btn_load;
 
 
	public CartPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 912, 560);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
	 

		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("New button");
		contentPane.add(btnNewButton);
		
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(400, 80));
		topPanel.setLayout(null);
		bottomPanel = new JScrollPane();
		bottomPanel.setBackground(new Color(0, 0, 0)); 
		
		JLabel lblNewLabel = new JLabel("Số lượng");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(115, 11, 152, 30);
		topPanel.add(lblNewLabel);
		
		txt_quantity = new JTextField();
		txt_quantity.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_quantity.setBounds(115, 51, 95, 20);
		topPanel.add(txt_quantity);
		txt_quantity.setColumns(10);
		tb_data = new JTable();
		bottomPanel.setViewportView(tb_data);
		
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		btn_back = new JButton("Back");
		btn_back.setBounds(20, 18, 65, 21);
		topPanel.add(btn_back);
		
		btn_luu = new JButton("Lưu");
		btn_luu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_luu.setBounds(286, 45, 139, 25);
		topPanel.add(btn_luu);
		
		btn_xoa = new JButton("Xóa");
		btn_xoa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_xoa.setBounds(450, 45, 139, 25);
		topPanel.add(btn_xoa);
		
		btn_xacnhan = new JButton("Xác nhận");
		btn_xacnhan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_xacnhan.setBounds(620, 45, 139, 25);
		topPanel.add(btn_xacnhan);
		
		btn_load = new JButton("Load");
		btn_load.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_load.setBounds(286, 11, 139, 25);
		topPanel.add(btn_load);
		contentPane.add(bottomPanel, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
               		 null,
                    "Are you sure you want to close this window?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
               	 try {
						Client.getInstance().Close();
					} catch (IOException e1) {
						 
						e1.printStackTrace();
						System.out.println(e1.getMessage());
					}
               	 dispose();
                }
            }
        });
	}
	public void addBack(ActionListener action) {
		btn_back.addActionListener(action);
	}
	public void addLoad(ActionListener action) {
		btn_load.addActionListener(action);
	}
	public void addRemove(ActionListener action) {
		btn_xoa.addActionListener(action);
	}
	public void addSave(ActionListener action) {
		btn_luu.addActionListener(action);
	}
	public void addXacNhan(ActionListener action) {
		btn_xacnhan.addActionListener(action);
	}
	public JTable table() {
		return tb_data;
	}
	public JTextField getText() {
		return txt_quantity;
	}

}
