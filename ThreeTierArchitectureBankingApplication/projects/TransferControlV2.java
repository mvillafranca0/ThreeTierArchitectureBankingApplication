/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.lang.*; //including Java packages used by this program
import javax.swing.*;
import com.villafranca.*;

public class TransferControlV2
{
    	public TransferControlV2(String TransactionType, String AcountType, String  Name, String  UName, String  Balance)
    	{
			//Use CheckingAccount object to invoke method openAcct()
			if (AcountType.equals("Checking"))
			{
				CheckingAccountV2 CA = new CheckingAccountV2(Name, UName, Balance);
           		if (CA.withdraw())
            	{
            		//System.out.println("successful!");
            		SavingsAccountV2 SA = new SavingsAccountV2(Name, UName, Balance);
            		if (SA.deposit())
            		{
                		JOptionPane.showMessageDialog(null, "Transfer is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                		TransactionType = "Transfer";
                		TransactionsV2 T = new TransactionsV2(TransactionType, CA.getCheckingAccountNumber(UName), SA.getSavingsAccountNumber(UName), UName, Balance);
                		T.recordTransactions();
					}
            	}
            	else
            	{
            		//System.out.println("fail!");
            		JOptionPane.showMessageDialog(null, "Transfer failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (AcountType.equals("Savings"))
			{
				SavingsAccountV2 SA = new SavingsAccountV2(Name, UName, Balance);
				if (SA.withdraw())
				{
					//System.out.println("successful!");
					CheckingAccountV2 CA = new CheckingAccountV2(Name, UName, Balance);
					if (CA.deposit())
					{
						JOptionPane.showMessageDialog(null, "Transfer is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
					  	TransactionType = "Transfer";
					   	TransactionsV2 T = new TransactionsV2(TransactionType, SA.getSavingsAccountNumber(UName), CA.getCheckingAccountNumber(UName), UName, Balance);
					   	T.recordTransactions();
					}
				}
				else
				{
					//System.out.println("fail!");
					JOptionPane.showMessageDialog(null, "Opening a Savings Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
}