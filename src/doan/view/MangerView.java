package doan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import doan.controller.ManagerCategoryController;
import doan.controller.ManagerOrderController;
import doan.controller.ManagerProductController;
import doan.controller.ManagerVendorController;
 

public class MangerView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel bottomPanel;
 	private JPanel topPanel;
	private JButton btn_back;

	public MangerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 759, 599);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		setContentPane(contentPane);

		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(400, 50));
		topPanel.setLayout(null);
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout(0, 0));

		contentPane.add(topPanel, BorderLayout.NORTH);

		btn_back = new JButton("Back");
		btn_back.setBounds(10, 10, 65, 21);
		topPanel.add(btn_back);
		contentPane.add(bottomPanel);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		ManagerProductView m1 = new ManagerProductView();
		new ManagerProductController(m1);
		ManagerCategoryView m3 = new ManagerCategoryView();
		new ManagerCategoryController(m3);
		ManagerOrderView m2 = new ManagerOrderView();
		new ManagerOrderController(m2);
		ManagerVenderView m4 = new ManagerVenderView();
		new ManagerVendorController(m4);

		tabbedPane.add("Product", m1);
		tabbedPane.add("Order", m2);
		tabbedPane.add("Category", m3);
		tabbedPane.add("Vendor", m4);
		bottomPanel.add(tabbedPane);

	}

	public void addBack(ActionListener action) {
		btn_back.addActionListener(action);
	}

}
