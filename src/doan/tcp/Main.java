package doan.tcp;

 

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import doan.controller.LoginController;
import doan.view.LoginView;
 

public class Main  
{
   
    public static void main(String[] args)
    {
         LoginView login= new LoginView();
          new LoginController(login);
         login.setVisible(true);
         login.addWindowListener(new WindowAdapter() {
             @Override
             public void windowClosing(WindowEvent e) {
                 int confirm = JOptionPane.showOptionDialog(
                		 login,
                     "Are you sure you want to close this window?",
                     "Exit Confirmation",
                     JOptionPane.YES_NO_OPTION,
                     JOptionPane.QUESTION_MESSAGE,
                     null, null, null);
                 if (confirm == JOptionPane.NO_OPTION) {
                	
                 }else if (confirm==JOptionPane.YES_OPTION) {
                	 try {
 						Client.getInstance().Close();
 						
 					} catch (IOException e1) {
 						 
 						e1.printStackTrace();
 						System.out.println(e1.getMessage());
 					}finally {
 						login.dispose();
					}
                 	 
                 }
             }
         });
    }
   
}
