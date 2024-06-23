package doan.view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import doan.tcp.Client;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btn_timSanpham;
	private JButton btn_danhsachOrder;
	private JButton btn_giohang;

 
	public HomeView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btn_timSanpham = new JButton("Tìm sản phẩm");
		btn_timSanpham.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_timSanpham.setBounds(192, 98, 200, 30);
		contentPane.add(btn_timSanpham);
		
		btn_danhsachOrder = new JButton("Danh sách đặt hàng");
		btn_danhsachOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_danhsachOrder.setBounds(192, 177, 200, 30);
		contentPane.add(btn_danhsachOrder);
		
		btn_giohang = new JButton("Giỏ Hàng");
		btn_giohang.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_giohang.setBounds(192, 255, 200, 30);
		contentPane.add(btn_giohang);
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
	public void addTimSp(ActionListener action) {
		btn_timSanpham.addActionListener(action);
	}
	public void addGioHang(ActionListener action) {
		btn_giohang.addActionListener(action);
	}
	public void addDanhSach(ActionListener action) {
		btn_danhsachOrder.addActionListener(action);
	}
}
