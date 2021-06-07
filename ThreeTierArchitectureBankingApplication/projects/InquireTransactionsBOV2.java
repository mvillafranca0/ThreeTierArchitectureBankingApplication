/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;
import com.villafranca.*;

class InquireTransactionsPanel extends JPanel implements ActionListener
{
       private JTable table;
       private JButton InquireButton;
       private JTextField UsernameField;
       private String UName;
       private static String conArg1, conArg2;

       Vector columnNames = new Vector();
       Vector componentNames = new Vector();

   public InquireTransactionsPanel(String UName, String CustomerName, String arg1, String arg2)
   {
	  conArg1 = arg1;
	  conArg2 = arg2;
      InquireButton = new JButton("Inquire");
      UsernameField = new JTextField(15);
      UsernameField.setText(UName);

      //Create the scroll pane and add it to the table.
      JLabel UsernameLabel = new JLabel("Inquire Transactions?: ");
      JPanel UsernamePanel = new JPanel();

      //Add the scroll pane to this window.
      UsernamePanel.add(UsernameLabel);
      UsernamePanel.add(UsernameField);
      InquireButton.addActionListener(this);

      JPanel TopPanel = new JPanel();
      TopPanel.add(UsernamePanel);
      TopPanel.add(InquireButton);

      setLayout(new BorderLayout());
	  add(TopPanel, BorderLayout.NORTH);
   }

   public void actionPerformed(ActionEvent evt)
   {
	   Socket client;
	   	ObjectOutputStream sout;
	   	ObjectInputStream sin;
		String s;

	   String arg = evt.getActionCommand();
	   if (arg.equals("Inquire"))
	   {
		   UName = UsernameField.getText();
		   try
		   {
		   		client = new Socket(conArg1, Integer.parseInt(conArg2));
				OutputStream OS = client.getOutputStream();
				sout = new ObjectOutputStream(OS);

				System.out.println("connection to " + conArg1 + " established");

				s = "InquireTransactions";
				sout.writeObject(s);
				sout.flush();

				s = UName;
				sout.writeObject(s);
				sout.flush();

				InputStream IS = client.getInputStream();
				sin = new ObjectInputStream(IS);

				String check = (String)sin.readObject();
		   		if (check.equals("inquire transactions complete"))
		   		{
		   			sout.flush();
		   			componentNames = (Vector)sin.readObject();
		   			JOptionPane.showMessageDialog(null, "Inquire transactions successful", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		   		}
		   		else
		   		{
		   			JOptionPane.showMessageDialog(null, "Inquire transactions failed...", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
		   		}
		   		System.out.println("Closing connection...");
		   		client.close();
		   	}
		   	catch (Exception e)
		   	{
		   		System.err.println("can't locate server: " + conArg1);
		   		return;
			}

           columnNames.addElement("Transaction Number");
           columnNames.addElement("Transaction Amount");
           columnNames.addElement("Transaction Type");
           columnNames.addElement("Transaction Time");
           columnNames.addElement("Transaction Date");
           columnNames.addElement("From Account");
           columnNames.addElement("To Account");
           columnNames.addElement("Customer ID"); //prepare two arrays for a table

           table = new JTable(componentNames, columnNames); //initialize a Table object
      	   table.setPreferredScrollableViewportSize(new Dimension(1020, 460));

      	   JScrollPane scrollPane = new JScrollPane(table);

      	   JPanel CenterPanel = new JPanel();
      	   CenterPanel.add(scrollPane);
      	   add(CenterPanel, BorderLayout.CENTER);
	   }
   }
}

public class InquireTransactionsBOV2 extends JFrame
{
    private InquireTransactionsPanel IT_Panel;

    public InquireTransactionsBOV2(String UName, String CustomerName, String arg1, String arg2)
    {
        setTitle("Open a Bank Account");
        setSize(450, 200);

         //get screen size and set the location of the frame
         Toolkit tk = Toolkit.getDefaultToolkit();
         Dimension d = tk.getScreenSize();
         int screenHeight = d.height;
         int screenWidth = d.width;
         setLocation( screenWidth / 3, screenHeight / 4);

         addWindowListener (new WindowAdapter()  //handle window event
            {
		       public void windowClosing (WindowEvent e)
			                  { System.exit(0);
               }
            });

         Container contentPane = getContentPane(); //add a panel to a frame
         IT_Panel = new InquireTransactionsPanel(UName, CustomerName, arg1, arg2);
         contentPane.add(IT_Panel);
         show();
    }

    /*public static void main(String [] args)
    { JFrame frame = new SignUpBO(); //initialize a JFrame object
      frame.show(); //display the frame
    }*/
}

