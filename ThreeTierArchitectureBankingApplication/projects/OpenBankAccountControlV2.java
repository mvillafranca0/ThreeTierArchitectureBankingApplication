/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.lang.*; //including Java packages used by this program
import javax.swing.*;
import com.villafranca.*;

public class OpenBankAccountControlV2
{
    	public OpenBankAccountControlV2(String TransactionType, String AcountType, String  AcountNumber, String  Name, String  UName, String  Balance)
    	{
			//Use CheckingAccount object to invoke method openAcct()
			if (AcountType.equals("Checking"))
			{
				CheckingAccountV2 CA = new CheckingAccountV2(AcountNumber, Name, UName, Balance);
           		if (CA.getCheckingAccountNumberBool(UName))
            	{
					CA.openAcct();
            		//System.out.println("successful!");
                	JOptionPane.showMessageDialog(null, "Opening a Checking Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                	TransactionType = "OpeningDeposit";
                	TransactionsV2 T = new TransactionsV2(TransactionType, AcountNumber, "NULL", UName, Balance);
                	T.recordTransactions();
            	}
            	else
            	{
            		//System.out.println("fail!");
            		JOptionPane.showMessageDialog(null, "Opening a Checking Account failed or Account already exists.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (AcountType.equals("Savings"))
			{
				SavingsAccountV2 SA = new SavingsAccountV2(AcountNumber, Name, UName, Balance);
				if (SA.getSavingsAccountNumberBool(UName))
				{
					SA.openAcct();
					//System.out.println("successful!");
					JOptionPane.showMessageDialog(null, "Opening a Savings Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			  		TransactionType = "OpeningDeposit";
			  		TransactionsV2 T = new TransactionsV2(TransactionType, AcountNumber, "NULL", UName, Balance);
                	T.recordTransactions();
				}
				else
				{
					//System.out.println("fail!");
					JOptionPane.showMessageDialog(null, "Opening a Savings Account failed or Account already exists.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
}