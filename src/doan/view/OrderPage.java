package doan.view;

 

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
 
public class OrderPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel topPanel;
	private JButton btn_back;
	private JScrollPane scrollPane;
	private JTable table;
 
	public OrderPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 759, 599);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		setContentPane(contentPane);

		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(400, 50));
		topPanel.setLayout(null);

		contentPane.add(topPanel, BorderLayout.NORTH);

		btn_back = new JButton("Back");
		btn_back.setBounds(10, 10, 65, 21);
		topPanel.add(btn_back);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
	public JTable getTable() {
		return table;
	}
	public void addBack(ActionListener action) {
		  btn_back.addActionListener(action);
	}
}
