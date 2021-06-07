/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.awt.*;     //including Java packages used by this program
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import com.villafranca.*;

class OpenBankAccountPanel extends JPanel implements ActionListener
{
    private JButton OpenButton;
    private JTextField UsernameField, NameField, AccountNumberField, BalanceField;
    private JComboBox CheckingOrSavingsBox;
    private String TransactionType, UName, AccountNumber, Balance, Name, AccountType;
    private static String conArg1, conArg2;

    public OpenBankAccountPanel(String UName, String CustomerName, String arg1, String arg2)
    {
		conArg1 = arg1;
		conArg2 = arg2;
        OpenButton = new JButton("Open"); //initializing two button references

        CheckingOrSavingsBox = new JComboBox();
        CheckingOrSavingsBox.addItem("Choose Account Type");
		CheckingOrSavingsBox.addItem("Checking");
		CheckingOrSavingsBox.addItem("Savings");

        UsernameField = new JTextField(15);
        UsernameField.setText(UName);
        NameField = new JTextField(CustomerName);
        AccountNumberField = new JTextField(15);
        BalanceField = new JTextField(15);
        BalanceField.setText("0.0");

        //JLabel TypeLabel = new JLabel("Choose Account Type: ");
        JLabel NameLabel = new JLabel("Customer Name:");
        JLabel UsernameLabel = new JLabel("Username: ");
        JLabel NumberLabel = new JLabel("Account Number:");
        JLabel BalanceLabel = new JLabel("Opening Deposit:");

        JPanel TypePanel = new JPanel();
        JPanel UsernamePanel = new JPanel();
        JPanel NamePanel = new JPanel();
        JPanel NumberPanel = new JPanel();
        JPanel BalancePanel = new JPanel();

        TypePanel.add(CheckingOrSavingsBox);
        UsernamePanel.add(UsernameLabel);
        UsernamePanel.add(UsernameField);
        NamePanel.add(NameLabel);
        NamePanel.add(NameField);
        NumberPanel.add(NumberLabel);
        NumberPanel.add(AccountNumberField);
        BalancePanel.add(BalanceLabel);
        BalancePanel.add(BalanceField);

        OpenButton.addActionListener(this); //event listener registration

        JPanel TopPanel = new JPanel();
        TopPanel.add(TypePanel);
        TopPanel.add(UsernamePanel);

        JPanel CenterPanel = new JPanel();
        CenterPanel.add(NamePanel);
        CenterPanel.add(NumberPanel);
        CenterPanel.add(BalancePanel);
        CenterPanel.add(OpenButton);
        setLayout(new BorderLayout());
        add(TopPanel, BorderLayout.NORTH);
        add(CenterPanel, BorderLayout.CENTER);
        //add(OpenButton, BorderLayout.SOUTH);//add the one button on to this panel
    }

    public void actionPerformed(ActionEvent evt)  //event handling
    {
		Socket client;
		ObjectOutputStream sout;
		ObjectInputStream sin;
		String s;
        String arg = evt.getActionCommand();

        if (arg.equals("Open")) { //determine which button is clicked
            UName = UsernameField.getText(); //take actions
            Name = NameField.getText();
            AccountNumber = AccountNumberField.getText();
            Balance = BalanceField.getText();
            AccountType = (String)CheckingOrSavingsBox.getSelectedItem();
            if (AccountType.equals("Choose Account Type"))
                JOptionPane.showMessageDialog(null, "Please Choose an Account Type!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (AccountNumber.length() != 8 )
                     JOptionPane.showMessageDialog(null, "Please Enter an Account Number with Exactly 8 Characters!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                 else
                 {
					 try
					 {
					 	client = new Socket(conArg1, Integer.parseInt(conArg2));
					 	OutputStream OS = client.getOutputStream();
					 	sout = new ObjectOutputStream(OS);

					 	System.out.println("connection to " + conArg1 + " established");

						s = "OpenAccount";
						sout.writeObject(s);
						sout.flush();

						s = UName;
						sout.writeObject(s);
						sout.flush();

						s = Name;
						sout.writeObject(s);
						sout.flush();

						s = AccountNumber;
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
					 	if (check.equals("open account complete"))
					 	{
					 		JOptionPane.showMessageDialog(null, "Account has been created!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
					 	}
					 	else
					 	{
							JOptionPane.showMessageDialog(null, "Please try again...", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
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

public class OpenBankAccountBOV2 extends JFrame
{
    private OpenBankAccountPanel OBA_Panel;

    public OpenBankAccountBOV2(String UName, String CustomerName, String arg1, String arg2)
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
         OBA_Panel = new OpenBankAccountPanel(UName, CustomerName, arg1, arg2);
         contentPane.add(OBA_Panel);
         show();
    }

    /*public static void main(String [] args)
    { JFrame frame = new SignUpBO(); //initialize a JFrame object
      frame.show(); //display the frame
    }*/
}

