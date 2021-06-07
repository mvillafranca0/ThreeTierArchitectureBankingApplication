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

class SignUpPanel extends JPanel implements ActionListener
{
    private JButton RegisterButton;
    private JTextField UsernameField, NameField;
    private JPasswordField PasswordField, PasswordField1;
    private String UName, PsWord, PsWord1, Name;
    private static String conArg1, conArg2;

    public SignUpPanel(String arg1, String arg2)
    {
		conArg1 = arg1;
		conArg2 = arg2;
        RegisterButton = new JButton("Register"); //initializing two button references

        UsernameField = new JTextField(15);
        PasswordField = new JPasswordField(15);
        PasswordField1 = new JPasswordField(15);
        NameField = new JTextField(15);

        JLabel UsernameLabel = new JLabel("Username: ");
        JLabel PasswordLabel = new JLabel("Password: ");
        JLabel PasswordLabel1 = new JLabel("Re-enter Password");
        JLabel NameLabel = new JLabel("Name");

        JPanel UsernamePanel = new JPanel();
        JPanel PasswordPanel = new JPanel();
        JPanel PasswordPanel1 = new JPanel();
        JPanel NamePanel = new JPanel();

        UsernamePanel.add(UsernameLabel);
        UsernamePanel.add(UsernameField);
        PasswordPanel.add(PasswordLabel);
        PasswordPanel.add(PasswordField);
        PasswordPanel1.add(PasswordLabel1);
        PasswordPanel1.add(PasswordField1);
        NamePanel.add(NameLabel);
        NamePanel.add(NameField);

        add(UsernamePanel);
        add(PasswordPanel);
        add(PasswordPanel1);
        add(NamePanel);

        add(RegisterButton);  //add the two buttons on to this panel
        RegisterButton.addActionListener(this); //event listener registration
    }

    public void actionPerformed(ActionEvent evt)  //event handling
    {
		Socket client;
		ObjectOutputStream sout;
		ObjectInputStream sin;
		String s;
        String arg = evt.getActionCommand();
        if (arg.equals("Register"))
        { //determine which button is clicked
            UName = UsernameField.getText(); //take actions
            PsWord =PasswordField.getText();
            PsWord1 = PasswordField1.getText();
            Name = NameField.getText();
            try
			{
				client = new Socket(conArg1, Integer.parseInt(conArg2));
				System.out.println("connection to " + conArg1 + " established");
				OutputStream OS = client.getOutputStream();
				sout = new ObjectOutputStream(OS);

		  	    s = "SignUp";
				sout.writeObject(s);
				sout.flush();

				s = UName;
				sout.writeObject(s);
				sout.flush();

				s = PsWord;
				sout.writeObject(s);
				sout.flush();

				s = PsWord1;
				sout.writeObject(s);
				sout.flush();

				s = Name;
				sout.writeObject(s);
				sout.flush();

				InputStream IS = client.getInputStream();
				sin = new ObjectInputStream(IS);
				System.out.flush();

				String check = (String)sin.readObject();
				if (check.equals("sign up complete"))
				{
					JOptionPane.showMessageDialog(null, "Account has been created!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please try again...", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
				System.out.println("Closing connection...");
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

public class SignUpBOV2 extends JFrame
{
    private SignUpPanel SU_Panel;

    public SignUpBOV2(String arg1, String arg2)
    {
        setTitle("Sign Up");
        setSize(340, 210);

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
         SU_Panel = new SignUpPanel(arg1, arg2);
         contentPane.add(SU_Panel);
         show();
    }

    /*public static void main(String [] args)
    { JFrame frame = new SignUpBO(); //initialize a JFrame object
      frame.show(); //display the frame
    }*/
}

