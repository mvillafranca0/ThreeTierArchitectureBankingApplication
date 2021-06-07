/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import com.villafranca.*;

class tabPanel extends JPanel
{
   public tabPanel(String UName, String CustomerName, String arg1, String arg2)
   {
      tabbedPane = new JTabbedPane(); //initialize a JTabbedPane object

	  tabPanel_1 = new AccountOverviewPanel(UName, CustomerName, arg1, arg2);
      tabPanel_2 = new OpenBankAccountPanel(UName, CustomerName, arg1, arg2);
      tabPanel_3 = new DepositPanel(UName, CustomerName, arg1, arg2);
      tabPanel_4 = new WithdrawPanel(UName, CustomerName, arg1, arg2);
      tabPanel_5 = new TransferPanel(UName, CustomerName, arg1, arg2);
      tabPanel_6 = new InquireTransactionsPanel(UName, CustomerName, arg1, arg2);

      tabbedPane.addTab("Account Overview", tabPanel_1); //add GUI components to Tabbed Pane
      tabbedPane.addTab("Open Account", tabPanel_2);
      tabbedPane.addTab("Deposit", tabPanel_3);
      tabbedPane.addTab("Withdraw", tabPanel_4);
      tabbedPane.addTab("Transfer", tabPanel_5);
      tabbedPane.addTab("Inquire Transactions", tabPanel_6);
      tabbedPane.setSelectedIndex(2);

      add(tabbedPane);
   }

   private JTabbedPane tabbedPane;
   private JPanel tabPanel_1 , tabPanel_2, tabPanel_3, tabPanel_4, tabPanel_5, tabPanel_6;
}

public class tabFrameV2 extends JFrame
{
	private static String conArg1, conArg2;
   public tabFrameV2(String UName, String CustomerName, String arg1, String arg2)
   {
      setTitle("Test Tab");
      setSize(1800, 600);

      //get screen size and set the location of the frame
      Toolkit tk = Toolkit.getDefaultToolkit();
      Dimension d = tk.getScreenSize();
      int screenHeight = d.height;
      int screenWidth = d.width;
      setLocation( screenWidth / 2 - 900, screenHeight / 2 - 400);

      addWindowListener (new WindowAdapter()  //handle window closing event
         {
			 public void windowClosing (WindowEvent e)
            {
				System.exit(0);
            }
         });

      JPanel tabbedPanel = new tabPanel(UName, CustomerName, arg1, arg2);
      Container contentPane = getContentPane(); //add a panel to a frame
      contentPane.add(tabbedPanel);
      show();
   }
}
/*
public class Tabs  //main class of this program
{  public static void main(String [] args)
   { JFrame frame = new tabFrame(); //initialize a JFrame object
     frame.show(); //display the frame
   }
}*/