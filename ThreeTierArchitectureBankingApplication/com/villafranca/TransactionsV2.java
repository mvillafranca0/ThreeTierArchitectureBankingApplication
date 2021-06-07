/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

package com.villafranca;

import java.lang.*; //including Java packages used by this program
import java.sql.*;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.*;
import com.villafranca.*;

public class TransactionsV2
{   //Instance Variables
	private String TransactionNumber, TransactionType, TransactionTime, TransactionDate, FromAccount, ToAccount, CustomerID;
	private int T_Number = 1;
	private int max = 999999999, min = 100000000;
	private float TransactionAmount = -1;

	public TransactionsV2(String trans_type, String acc_num, String acc_num2, String Cust_ID, String Bal) { //Constructor One with three parameters
		T_Number = (int)(Math.random() * (max - min + 1) + min);
		TransactionNumber = String.valueOf(T_Number);
		TransactionAmount = Float.parseFloat(Bal);
		TransactionType = trans_type;
		LocalTime T_Time = LocalTime.now();
		TransactionTime = T_Time.toString();
		LocalDate T_Date = LocalDate.now();
		TransactionDate = T_Date.toString();
		FromAccount = acc_num;
		if (trans_type.equals("OpeningDeposit"))
		{
			ToAccount = acc_num;
		}
		else if (trans_type.equals("Transfer"))
		{
			ToAccount = acc_num2;
		}
		else
		{
			ToAccount = acc_num;
		}
		CustomerID = Cust_ID;
	}

	public TransactionsV2(String Cust_ID)
	{
		CustomerID = Cust_ID;
	}

    public void recordTransactions()
    {
				try
				{
				        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
				        Connection DBConn = ToDB.openConn();
				        Statement Stmt = DBConn.createStatement();
				        String SQL_Command = "INSERT INTO Transactions(TransactionNumber, TransactionAmount, TransactionType, TransactionTime, TransactionDate, FromAccount, ToAccount, CustomerID)"+
				        " VALUES ('"+TransactionNumber+"','"+TransactionAmount+"','"+TransactionType+"', '"+TransactionTime+"', '"+TransactionDate+"', '"+FromAccount+"', '"+ToAccount+"', '"+CustomerID+"')";
				        Stmt.executeUpdate(SQL_Command); //Inquire if the username exsits.

					    Stmt.close();
					    ToDB.closeConn();
				}
			    catch(java.sql.SQLException e)
			    {
						 System.out.println("SQLException: " + e);
						 while (e != null)
						 {   System.out.println("SQLState: " + e.getSQLState());
							 System.out.println("Message: " + e.getMessage());
							 System.out.println("Vendor: " + e.getErrorCode());
							 e = e.getNextException();
							 System.out.println("");
						 }
			    }
			    catch (java.lang.Exception e)
			    {
						 System.out.println("Exception: " + e);
						 e.printStackTrace ();
			    }
	}

	public Vector searchTransactions()
	{
		Vector TransFound = new Vector();
					try
					{
					        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
					        Connection DBConn = ToDB.openConn();
					        Statement Stmt = DBConn.createStatement();
					        String SQL_Command = "SELECT * FROM Transactions WHERE CustomerID = '"+CustomerID+"'";
					        ResultSet results = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
					        ResultSetMetaData rsmd=results.getMetaData();
					        //ResultSet results = stmt.executeQuery(command);
							while (results.next())
								{
								    TransFound.addElement(ToDB.getNextRow(results,rsmd));
								}
						    Stmt.close();
						    ToDB.closeConn();
						    //success = true;
					}
				    catch(java.sql.SQLException e)
				    {
							 System.out.println("SQLException: " + e);
							 while (e != null)
							 {   System.out.println("SQLState: " + e.getSQLState());
								 System.out.println("Message: " + e.getMessage());
								 System.out.println("Vendor: " + e.getErrorCode());
								 e = e.getNextException();
								 System.out.println("");
							 }
							 //success = false;
				    }
				    catch (java.lang.Exception e)
				    {
							 System.out.println("Exception: " + e);
							 e.printStackTrace ();
							 //success = false;
				    }
				    return TransFound;
	}
}