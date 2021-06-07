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

// LoginBOV2 localhost 2020
public class LoginBOV2 extends JFrame implements ActionListener // Implementing ActionListener is for event handling.
{
    private JButton SignUpButton, LoginButton;  //Instance variables
    private JTextField UsernameField;
    private JPasswordField PasswordField;
	private static String arg1, arg2;

    public LoginBOV2()
    {
        setTitle("Login");
        setSize(300, 200);

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

         SignUpButton = new JButton("Sign Up"); //initializing two button references
         LoginButton = new JButton("Login");

         UsernameField = new JTextField(15);
         PasswordField = new JPasswordField(15);
         PasswordField.setActionCommand("Login");

         JLabel FirstTimeUserLabel = new JLabel("First time user? Click Sign Up to register!");
         JLabel UsernameLabel = new JLabel("Username: ");
         JLabel PasswordLabel = new JLabel("Password: ");

         JPanel UsernamePanel = new JPanel();
         JPanel PasswordPanel = new JPanel();

         UsernamePanel.add(UsernameLabel);
         UsernamePanel.add(UsernameField);
         PasswordPanel.add(PasswordLabel);
         PasswordPanel.add(PasswordField);

         JPanel LoginPanel = new JPanel();
         LoginPanel.add(UsernamePanel);
         LoginPanel.add(PasswordPanel);

         LoginPanel.add(LoginButton);  //add the two buttons on to this panel
         LoginPanel.add(FirstTimeUserLabel);
         LoginPanel.add(SignUpButton);

         SignUpButton.addActionListener(this);  //event listener registration
         LoginButton.addActionListener(this);
         PasswordField.addActionListener(this);

         Container contentPane = getContentPane(); //add a panel to a frame
         contentPane.add(LoginPanel);

	}

    public void actionPerformed(ActionEvent evt)  //event handling
    {
		Socket client;
		ObjectOutputStream sout;
        ObjectInputStream sin;
        String s;
        String CustomerName = "";
        String arg = evt.getActionCommand();

        if (arg.equals("Sign Up"))
        { //determine which button is clicked
            SignUpControlV2 SUC = new SignUpControlV2(arg1, arg2); //take actions
		}

		if (arg.equals("Login"))
		{
			String Username = UsernameField.getText();
			String Password = PasswordField.getText();

			try
			      {
				  		client = new Socket(arg1, Integer.parseInt(arg2));
				  		System.out.println("connection to " + arg1 + " established");

				        OutputStream OS = client.getOutputStream();
				  	    sout = new ObjectOutputStream(OS);

						s = "Login";
						sout.writeObject(s);
						sout.flush();

						s = Username;
						sout.writeObject(s);
				  	    sout.flush();

						s = Password;
						sout.writeObject(s);
						sout.flush();

				        InputStream IS = client.getInputStream();
				        sin = new ObjectInputStream(IS);

				  	    System.out.flush();

						CustomerName = (String)sin.readObject();
						if (!CustomerName.equals(""))
						{
							tabFrameV2 tabs = new tabFrameV2(Username, CustomerName, arg1, arg2);
						}
				  	    System.out.println("Received: " + CustomerName );
						System.out.println("Closing connection...");
				        client.close();
				  }
				  catch (Exception e)
				  {
				  		System.err.println("can't locate server: " + arg1);
				  	  	return;
      			  }
			}
  	}

    public static void main(String [] args) throws IOException
    {
	  JFrame frame = new LoginBOV2(); //initialize a JFrame object
      frame.show(); //display the frame

      if (args.length != 2)
      {
	  	  System.err.println("Usage: java SocketClient server port");
	  	  return;
      }
      else
      {
		  arg1 = args[0];
	  	  arg2 = args[1];
	  }
   }
}

