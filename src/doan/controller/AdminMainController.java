package doan.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import doan.view.AdminView;
import doan.view.MangerView;

public class AdminMainController {
	public MangerView main;

	public AdminMainController(MangerView main) {
		super();
		this.main = main;
		this.main.addBack(new addBack());
	}
	class addBack implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		 AdminView a= new AdminView();
		 new AdminController(a);
		 a.setVisible(true);
		 main.dispose();
			
		}
		
	}

}
