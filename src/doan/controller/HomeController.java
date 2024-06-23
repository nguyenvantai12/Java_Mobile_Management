package doan.controller;

 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import doan.view.CartPage;
import doan.view.HomeView;
import doan.view.OrderPage;
import doan.view.ProductPage;
 
public class HomeController {
	public HomeView home;

	public HomeController(HomeView home) {
		super();
		this.home = home;
		this.home.addTimSp(new TimSpListener());
		this.home.addGioHang(new GiohangListener());
		this.home.addDanhSach(new OrderListener());
	}
	class TimSpListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
 			 ProductPage p=new ProductPage();
			 new ProductController(p);
			 p.setVisible(true);
			 home.setVisible(false);
		}
	}
	class GiohangListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			 
			 CartPage c=new CartPage();
			 new CartController(c);
			 c.setVisible(true);
			 home.setVisible(false);
			
		}
		
	}
	class OrderListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		 OrderPage o=new OrderPage();
		 new OrderController(o);
		 o.setVisible(true);
		 home.setVisible(false);
			
		}
		
	}

}
