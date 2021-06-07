/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.lang.*; //including Java packages used by this program
import javax.swing.*;
import java.util.*;
import com.villafranca.*;

public class InquireTransactionsControlV2
{
    	public InquireTransactionsControlV2(String  UName)
    	{
			//Use CheckingAccount object to invoke method openAcct()
				TransactionsV2 TS = new TransactionsV2(UName);
				Vector TransFound = new Vector();
				Vector TransFound2 = new Vector();
				TransFound = TS.searchTransactions();
				TransFound2 = TS.searchTransactions();
           		if (TransFound == TransFound2)
            	{
            		//System.out.println("successful!");
                	JOptionPane.showMessageDialog(null, "Searching Transactions is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            	}
            	else
            	{
            		//System.out.println("fail!");
            		JOptionPane.showMessageDialog(null, "Opening a Checking Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
		}
}