/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.lang.*; //including Java packages used by this program
import javax.swing.*;
import com.villafranca.*;

// this was taken from an older version from the project but was not needed for this version of the project

public class AccountOverviewControlV2
{
	/*	public OpenBankAccountControl(String AcountType, String  AcountNumber, String  Name, String  UName, String  Balance, String IntR)
				{
					if (AcountType.equals("Savings"))
					{
						SavingsAccount SA = new SavingsAccount(AcountNumber, Name, UName, Balance, IntR = ".50");
				        if (SA.openAcct())
				        {
				            //System.out.println("successful!");
				            JOptionPane.showMessageDialog(null, "Opening a Savings Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				        }
				        else
				        {
				            //System.out.println("fail!");
				            JOptionPane.showMessageDialog(null, "Opening a Savings Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				        }
					}
		}

    	public AccountOverviewControl(String TransactionType, String AcountType, String  AcountNumber, String  Name, String  UName, String  Balance)
    	{
			//Use CheckingAccount object to invoke method openAcct()
			if (AcountType.equals("Checking"))
			{
				CheckingAccount CA = new CheckingAccount(AcountNumber, Name, UName, Balance);
           		if (CA.openAcct())
            	{
            		//System.out.println("successful!");
                	JOptionPane.showMessageDialog(null, "Opening a Checking Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                	TransactionType = "OpeningDeposit";
                	Transactions T = new Transactions(TransactionType, AcountNumber, UName, Balance);
                	T.recordTransactions();
            	}
            	else
            	{
            		//System.out.println("fail!");
            		JOptionPane.showMessageDialog(null, "Opening a Checking Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (AcountType.equals("Savings"))
			{
				SavingsAccount SA = new SavingsAccount(AcountNumber, Name, UName, Balance);
				if (SA.openAcct())
				{
					//System.out.println("successful!");
					JOptionPane.showMessageDialog(null, "Opening a Savings Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			  		TransactionType = "OpeningDeposit";
			  		Transactions T = new Transactions(TransactionType, AcountNumber, UName, Balance);
                	T.recordTransactions();
				}
				else
				{
					//System.out.println("fail!");
					JOptionPane.showMessageDialog(null, "Opening a Savings Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		*/
}