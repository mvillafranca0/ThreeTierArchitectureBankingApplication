/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.awt.*;     //including Java packages used by this program
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;
import com.villafranca.*;

class AccountOverviewPanel extends JPanel implements ActionListener
{
    private JButton GetButton;
    private JTextField UsernameField, NameField, AccountNumberField, BalanceField;
    private JComboBox CheckingOrSavingsBox;
    private String TransactionType, UName, AccountNumber, Balance, Name, AccountType;
    private static String conArg1, conArg2;

    public AccountOverviewPanel(String UName, String CustomerName, String arg1, String arg2)
    {
		conArg1 = arg1;
		conArg2 = arg2;
        GetButton = new JButton("Get Balance"); //initializing two button references

        CheckingOrSavingsBox = new JComboBox();
        CheckingOrSavingsBox.addItem("Choose Account Type");
		CheckingOrSavingsBox.addItem("Checking");
		CheckingOrSavingsBox.addItem("Savings");

        UsernameField = new JTextField(15);
        UsernameField.setText(UName);
        NameField = new JTextField(CustomerName);

        JLabel WelcomeLabel = new JLabel("Welcome!");
        JLabel NameLabel = new JLabel("Customer Name:");
        JLabel UsernameLabel = new JLabel("Username: ");
        JLabel NumberLabel = new JLabel("Account Number:");

        JPanel TypePanel = new JPanel();
        JPanel WelcomePanel = new JPanel();
        JPanel UsernamePanel = new JPanel();
        JPanel NamePanel = new JPanel();

        TypePanel.add(CheckingOrSavingsBox);
        WelcomePanel.add(WelcomeLabel);
        UsernamePanel.add(UsernameLabel);
        UsernamePanel.add(UsernameField);
        NamePanel.add(NameLabel);
        NamePanel.add(NameField);

        GetButton.addActionListener(this); //event listener registration

        JPanel TopPanel = new JPanel();
        TopPanel.add(WelcomePanel);
        TopPanel.add(NamePanel);
        TopPanel.add(UsernamePanel);

        JPanel CenterPanel = new JPanel();
        CenterPanel.add(TypePanel);
        CenterPanel.add(GetButton);

        setLayout(new BorderLayout());
        add(TopPanel, BorderLayout.NORTH);
        add(CenterPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent evt)  //event handling
    {
		Socket client;
		ObjectOutputStream sout;
		ObjectInputStream sin;
		String s;

        String arg = evt.getActionCommand();

        if (arg.equals("Get Balance"))
        {
			UName = UsernameField.getText(); //take actions
        	Name = NameField.getText();
			AccountType = (String)CheckingOrSavingsBox.getSelectedItem();
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

					s = "AccountOverview";
					sout.writeObject(s);
					sout.flush();

					s = UName;
					sout.writeObject(s);
					sout.flush();

					s = Name;
					sout.writeObject(s);
					sout.flush();

					s = AccountType;
					sout.writeObject(s);
					sout.flush();

					InputStream IS = client.getInputStream();
					sin = new ObjectInputStream(IS);

					String Balance = (String)sin.readObject();
					if (!Balance.equals(""))
					{
						JOptionPane.showMessageDialog(null, AccountType + " Account Balance: " + Balance, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
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

public class AccountOverviewBOV2 extends JFrame
{
    private AccountOverviewPanel AO_Panel;

    public AccountOverviewBOV2(String UName, String CustomerName, String arg1, String arg2)
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
         AO_Panel = new AccountOverviewPanel(UName, CustomerName, arg1, arg2);
         contentPane.add(AO_Panel);
         show();
    }

    /*public static void main(String [] args)
    { JFrame frame = new SignUpBO(); //initialize a JFrame object
      frame.show(); //display the frame
    }*/
}

