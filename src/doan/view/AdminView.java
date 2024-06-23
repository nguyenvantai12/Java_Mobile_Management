package doan.view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import doan.tcp.Client;
import java.awt.Toolkit;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btn_sp;
	private JButton btn_quanly;
	private JButton btn_cart;
	private JButton btn_order;
 
	public AdminView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS\\OneDrive\\Pictures\\user (5).png"));
		setTitle("Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btn_sp = new JButton("Tìm kiếm sản phẩm");
		btn_sp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_sp.setBounds(178, 96, 200, 30);
		contentPane.add(btn_sp);
		
		btn_quanly = new JButton("Quản lý");
	 
		btn_quanly.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_quanly.setBounds(178, 254, 200, 30);
		contentPane.add(btn_quanly);
		
		btn_order = new JButton("Danh sách đặt hàng");
		btn_order.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_order.setBounds(178, 151, 200, 30);
		contentPane.add(btn_order);
		
		btn_cart = new JButton("Giỏ hàng");
		btn_cart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_cart.setBounds(178, 203, 200, 30);
		contentPane.add(btn_cart);
		
		JPanel panel = new JPanel();
		ImageIcon icon = new ImageIcon("C:\\Users\\ASUS\\eclipse-workspace\\DoAn1\\src");
		JLabel label = new JLabel(icon);
		panel.add(label);
		
		
		panel.setBounds(108, 80, 53, 66);
		contentPane.add(panel);
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
	public void addOrder(ActionListener action) {
		btn_order.addActionListener(action);
	}
	public void addDanhSach(ActionListener action) {
		btn_sp.addActionListener(action);
	}
	public void addQuanly(ActionListener action) {
		btn_quanly.addActionListener(action);
	}
	public void addCart(ActionListener action) {
		btn_cart.addActionListener(action);
	}
}