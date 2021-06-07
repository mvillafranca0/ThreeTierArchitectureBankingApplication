/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

//package com.villafranca;

import java.awt.*;     //including Java packages used by this program
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import com.villafranca.*;

class TransferPanel extends JPanel implements ActionListener
{
    private JButton TransferButton;
    private JTextField UsernameField, NameField, Destination, BalanceField;
    private JComboBox CheckingOrSavingsBox;
    private String TransactionType, UName, AccountNumber, Balance, Name, AccountType;
    private static String conArg1, conArg2;

    public TransferPanel(String UName, String CustomerName, String arg1, String arg2)
    {
		conArg1 = arg1;
		conArg2 = arg2;
        TransferButton = new JButton("Transfer"); //initializing two button references

        CheckingOrSavingsBox = new JComboBox();
        CheckingOrSavingsBox.addItem("Choose Account Type (Source)");
		CheckingOrSavingsBox.addItem("Checking");
		CheckingOrSavingsBox.addItem("Savings");

        UsernameField = new JTextField(15);
        UsernameField.setText(UName);
        NameField = new JTextField(CustomerName);
        BalanceField = new JTextField(15);
        BalanceField.setText("0.0");

        JLabel NameLabel = new JLabel("Customer Name:");
        JLabel UsernameLabel = new JLabel("Username: ");
        JLabel DestinationLabel = new JLabel("Destination: will be your other account");
        JLabel BalanceLabel = new JLabel("Transfer Amount:");

        JPanel TypePanel = new JPanel();
        JPanel ListPanel = new JPanel();
        JPanel ListPanel2 = new JPanel();
        JPanel UsernamePanel = new JPanel();
        JPanel NamePanel = new JPanel();
        JPanel DestinationPanel = new JPanel();
        JPanel BalancePanel = new JPanel();

        TypePanel.add(CheckingOrSavingsBox);
        UsernamePanel.add(UsernameLabel);
        UsernamePanel.add(UsernameField);
        NamePanel.add(NameLabel);
        NamePanel.add(NameField);
        DestinationPanel.add(DestinationLabel);
        BalancePanel.add(BalanceLabel);
        BalancePanel.add(BalanceField);

        TransferButton.addActionListener(this); //event listener registration

        JPanel TopPanel = new JPanel();
        TopPanel.add(TypePanel);
        TopPanel.add(DestinationPanel);

        JPanel CenterPanel = new JPanel();
        CenterPanel.add(UsernamePanel);
        CenterPanel.add(NamePanel);
        CenterPanel.add(BalancePanel);
        CenterPanel.add(TransferButton);
        setLayout(new BorderLayout());
        add(TopPanel, BorderLayout.NORTH);
        add(CenterPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent evt)  //event handling
    {
		Socket client;
		ObjectOutputStream sout;
		ObjectInputStream sin;
		String flag = "true";

		String arg = evt.getActionCommand();
        if (arg.equals("Transfer"))
			   	{ //determine which button is clicked
			   		UName = UsernameField.getText(); //take actions
			   		Name = NameField.getText();
			   		Balance = BalanceField.getText();
			   		AccountType = (String)CheckingOrSavingsBox.getSelectedItem();
			   		String s;
			   		if (AccountType.equals("Choose Account Type"))
			   			JOptionPane.showMessageDialog(null, "Please Choose an Account Type!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			   		else
			   		{
						try
						{
							client = new Socket(conArg1, Integer.parseInt(conArg2));
							OutputStream OS = client.getOutputStream();
							sout = new ObjectOutputStream(OS);

							System.out.println("connection to " + conArg1 + " established");

							s = "Transfer";
							sout.writeObject(s);
							sout.flush();

							s = UName;
							sout.writeObject(s);
							sout.flush();

							s = Name;
							sout.writeObject(s);
							sout.flush();

							s = Balance;
							sout.writeObject(s);
							sout.flush();

							s = AccountType;
							sout.writeObject(s);
							sout.flush();

							InputStream IS = client.getInputStream();
							sin = new ObjectInputStream(IS);

				        	String check = (String)sin.readObject();
							if (check.equals("transfer complete"))
							{
								JOptionPane.showMessageDialog(null, "Transfer successful", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Transfer failed...", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
							}
							System.out.println("Closing connection...");
							client.close();
						}
						catch (Exception e)
						{
							System.err.println("can't locate server: " + conArg1);
							return;
						}
			   		}
	   	}
    }
}

public class TransferBOV2 extends JFrame
{
    private TransferPanel Tran_Panel;

    public TransferBOV2(String UName, String CustomerName, String arg1, String arg2)
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
         Tran_Panel = new TransferPanel(UName, CustomerName, arg1, arg2);
         contentPane.add(Tran_Panel);
         show();
    }

    /*public static void main(String [] args)
    { JFrame frame = new SignUpBO(); //initialize a JFrame object
      frame.show(); //display the frame
    }*/
}

