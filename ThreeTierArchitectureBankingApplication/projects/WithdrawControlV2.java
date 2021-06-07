/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.lang.*; //including Java packages used by this program
import javax.swing.*;
import com.villafranca.*;

public class WithdrawControlV2
{
    	public WithdrawControlV2(String TransactionType, String AcountType, String  Name, String  UName, String  Balance)
    	{
			//Use CheckingAccount object to invoke method openAcct()
						if (AcountType.equals("Checking"))
						{
							CheckingAccountV2 CA = new CheckingAccountV2(Name, UName, Balance);
			           		if (CA.withdraw())
			            	{
			            		//System.out.println("successful!");
			                	JOptionPane.showMessageDialog(null, "Withdraw is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
			                	TransactionType = "Withdraw";
			                	String AcountNumber = CA.getCheckingAccountNumber(UName);
			                	TransactionsV2 T = new TransactionsV2(TransactionType, AcountNumber, "NULL", UName, Balance);
			                	T.recordTransactions();
			            	}
			            	else
			            	{
			            		//System.out.println("fail!");
			            		JOptionPane.showMessageDialog(null, "Withdraw failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
							}
						}
						if (AcountType.equals("Savings"))
						{
							SavingsAccountV2 SA = new SavingsAccountV2(Name, UName, Balance);
							if (SA.withdraw())
							{
								//System.out.println("successful!");
							   	JOptionPane.showMessageDialog(null, "Withdraw is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
							   	TransactionType = "Withdraw";
							   	String AcountNumber = SA.getSavingsAccountNumber(UName);
							   	TransactionsV2 T = new TransactionsV2(TransactionType, AcountNumber, "NULL", UName, Balance);
							   	T.recordTransactions();
							}
							else
							{
							   	//System.out.println("fail!");
							   	JOptionPane.showMessageDialog(null, "Withdraw failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
							}
			}
		}
}