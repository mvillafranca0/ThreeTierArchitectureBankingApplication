/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.lang.*; //including Java packages used by this program
import javax.swing.*;
import com.villafranca.*;

public class LoginControlV2
{
    private AccountV2 Acct;

    public LoginControlV2(String UName, String PWord)
    {
		Acct = new AccountV2(UName, PWord);
		String CustomerName = "";
		CustomerName = Acct.signIn();
        if (!CustomerName.equals(""))
        {
            tabFrameV2 tabs = new tabFrameV2(UName, CustomerName);
        } else
            //System.out.println("fail!");
            JOptionPane.showMessageDialog(null, "Login failed because of invalid username or password.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
	}
}