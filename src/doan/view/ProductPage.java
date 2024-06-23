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
import javax.swing.table.DefaultTableModel;

import doan.tcp.Client;

public class ProductPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane bottomPanel;

	private JTextField txt_search;
	private JTable tb_data;
	private JButton btn_search;
	private JPanel topPanel;
	private JButton btn_back;
	private JButton btn_load;
	public ProductPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 759, 599);
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
		
		JLabel lblNewLabel = new JLabel("Tìm sản phẩm");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(115, 11, 152, 30);
		topPanel.add(lblNewLabel);
		
		txt_search = new JTextField();
		txt_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txt_search.setBounds(115, 51, 178, 20);
		topPanel.add(txt_search);
		txt_search.setColumns(10);
		
		btn_search = new JButton("Tìm kiếm");
		btn_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_search.setBounds(303, 49, 139, 25);
		topPanel.add(btn_search);
		tb_data = new JTable();
		tb_data.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Price", "Description", "Vendor"
			}
		));
		bottomPanel.setViewportView(tb_data);
		
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		btn_back = new JButton("Back");
		btn_back.setBounds(20, 18, 65, 21);
		topPanel.add(btn_back);
		
		btn_load = new JButton("Load");
		btn_load.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_load.setBounds(303, 14, 139, 25);
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
	public String getSearch() {
		return txt_search.getText();
	}
	
	public void addBack(ActionListener action) {
		btn_back.addActionListener(action);
	}
	public void addLoad(ActionListener action) {
		btn_load.addActionListener(action);
	}
	public void addSearch(ActionListener action) {
		btn_search.addActionListener(action);
	}
	public JTable table() {
		return tb_data;
	}

}
