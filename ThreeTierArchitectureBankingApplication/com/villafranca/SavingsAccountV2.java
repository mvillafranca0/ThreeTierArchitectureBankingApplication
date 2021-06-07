/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/
package com.villafranca;

import java.lang.*; //including Java packages used by this program
import java.sql.*;
import com.villafranca.*;

public class SavingsAccountV2
{   //Instance Variables
	private String SavingsAccountNumber = "", CustomerName, CustomerID, StringBalance;
	private float Balance = -1, DW_Input = 0, calcInterest = 0;
	private double InterestRate = .5;

	public SavingsAccountV2(String SA_Num, String Cust_Name, String Cust_ID, String Bal)
	{ //Constructor One with five parameters
		SavingsAccountNumber = SA_Num;
		CustomerName = Cust_Name;
		CustomerID = Cust_ID;
		Balance = Float.parseFloat(Bal);
		DW_Input = Balance;
	}

	public SavingsAccountV2(String Cust_Name, String Cust_ID, String Bal)
	{ //Constructor One with three parameters
				CustomerName = Cust_Name;
				CustomerID = Cust_ID;
				Balance = Float.parseFloat(Bal);
				DW_Input = Balance;
	}

	public SavingsAccountV2(String Cust_Name, String Cust_ID)
	{ //Constructor One with three parameters
					CustomerName = Cust_Name;
					CustomerID = Cust_ID;
	}

	public SavingsAccountV2(String SA_Num)
	{ //Constructor Two with one parameter
		SavingsAccountNumber = SA_Num;
	}
	public SavingsAccountV2()
	{

	}

    public boolean openAcct()
    {
	     boolean done = false;
				try
				{
				    if (!done)
				    {
				        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
				        Connection DBConn = ToDB.openConn();
				        Statement Stmt = DBConn.createStatement();
				        String SQL_Command = "SELECT SavingsAccountNumber FROM SavingsAccount WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'"; //SQL query command
				        ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
				        done = !Rslt.next();
				        if (done)
				        {
						    SQL_Command = "INSERT INTO SavingsAccount(SavingsAccountNumber, CustomerName, Balance, InterestRate, CustomerID)"+
							" VALUES ('"+SavingsAccountNumber+"','"+CustomerName+"',"+Balance+", "+InterestRate+", '"+CustomerID+"')"; //Save the username, password and Name
						    Stmt.executeUpdate(SQL_Command);
					    }
					    Stmt.close();
					    ToDB.closeConn();
					}
				}
			    catch(java.sql.SQLException e)
			    {         done = false;
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
			    {         done = false;
						 System.out.println("Exception: " + e);
						 e.printStackTrace ();
			    }
	    return done;
	}

	public boolean deposit()
	{
			     boolean done = false;
				 					try
				 					{
				 					    if (!done)
				 					    {
				 					        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
				 					        Connection DBConn = ToDB.openConn();
				 					        Statement Stmt = DBConn.createStatement();
				 					        String SQL_Command = "SELECT SavingsAccountNumber FROM SavingsAccount WHERE CustomerID ='"+CustomerID+"'"; //SQL query command
				 					        ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
				 					        done = Rslt.next();
				 							SavingsAccountNumber = Rslt.getString(1);
				 					        if (done)
				 					        {
				 								SQL_Command = "SELECT Balance FROM SavingsAccount WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'";
				 								Rslt = Stmt.executeQuery(SQL_Command);
				 								Rslt.next();
				 								Balance = Rslt.getFloat(1);
				 								Balance = Balance + DW_Input;
				 							    SQL_Command = "UPDATE SavingsAccount SET Balance = "+Balance+" WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'";
				 							    Stmt.executeUpdate(SQL_Command);
				 						    }
				 						    Stmt.close();
				 						    ToDB.closeConn();
				 						}
				 					}
				 				    catch(java.sql.SQLException e)
				 				    {         done = false;
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
				 				    {         done = false;
				 							 System.out.println("Exception: " + e);
				 							 e.printStackTrace ();
				 				    }
		    return done;
		}

		public boolean withdraw()
		{
			boolean done = false;
			try
			{
			    if (!done)
			    {
				        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
				        Connection DBConn = ToDB.openConn();
				        Statement Stmt = DBConn.createStatement();
				        String SQL_Command = "SELECT SavingsAccountNumber FROM SavingsAccount WHERE CustomerID ='"+CustomerID+"'"; //SQL query command
				        ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
				        done = Rslt.next();
						SavingsAccountNumber = Rslt.getString(1);
				        if (done)
				        {
							SQL_Command = "SELECT Balance FROM SavingsAccount WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'";
							Rslt = Stmt.executeQuery(SQL_Command);
							Rslt.next();
							Balance = Rslt.getFloat(1);
							Balance = Balance - DW_Input;
							SQL_Command = "UPDATE SavingsAccount SET Balance = "+Balance+" WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'";
							Stmt.executeUpdate(SQL_Command);
						}
						Stmt.close();
						ToDB.closeConn();
				 }
			}
			catch(java.sql.SQLException e)
			{
					 done = false;
					 System.out.println("SQLException: " + e);
					 while (e != null)
					 {
						 System.out.println("SQLState: " + e.getSQLState());
						 System.out.println("Message: " + e.getMessage());
						 System.out.println("Vendor: " + e.getErrorCode());
						 e = e.getNextException();
						 System.out.println("");
					 }
			}
			catch (java.lang.Exception e)
			{
				done = false;
			    System.out.println("Exception: " + e);
   			    e.printStackTrace ();
			}
		    return done;
		}

	public String getSavingsAccountNumber (String UName)
	{  //Method to return a CheckingAccount balance
				try
				{
				        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
				        Connection DBConn = ToDB.openConn();
				        Statement Stmt = DBConn.createStatement();
				        String SQL_Command = "SELECT SavingsAccountNumber FROM SavingsAccount WHERE CustomerID ='"+UName+"'"; //SQL query command for Balance
				        ResultSet Rslt = Stmt.executeQuery(SQL_Command);

				        while (Rslt.next())
				        {
							SavingsAccountNumber = Rslt.getString(1);
					    }
					    Stmt.close();
					    ToDB.closeConn();
				}
			    catch(java.sql.SQLException e)
			    {
						 System.out.println("SQLException: " + e);
						 while (e != null)
						 {
							 System.out.println("SQLState: " + e.getSQLState());
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
			    return SavingsAccountNumber;
	}

	public boolean getSavingsAccountNumberBool (String UName)
	{  //Method to return a CheckingAccount balance
					boolean bool;
					try
					{
					        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
					        Connection DBConn = ToDB.openConn();
					        Statement Stmt = DBConn.createStatement();
					        String SQL_Command = "SELECT SavingsAccountNumber FROM SavingsAccount WHERE CustomerID ='"+UName+"'"; //SQL query command for Balance
					        ResultSet Rslt = Stmt.executeQuery(SQL_Command);

					        while (Rslt.next())
					        {
								SavingsAccountNumber = Rslt.getString(1);
						    }
						    Stmt.close();
						    ToDB.closeConn();
						    if (SavingsAccountNumber.equals(""))
								bool = true;
							else
					    		bool = false;
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
							 bool = false;
				    }
				    catch (java.lang.Exception e)
				    {
							 System.out.println("Exception: " + e);
							 e.printStackTrace ();
							 bool = false;
				    }
				    //bool = true;
				    return bool;
	}

	public float calculateInterest()
	{
				boolean done = false;
				try
				{
					if (!done)
					{
						DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
						Connection DBConn = ToDB.openConn();
						Statement Stmt = DBConn.createStatement();
						String SQL_Command = "SELECT SavingsAccountNumber FROM SavingsAccount WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'"; //SQL query command
						ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
						done = !Rslt.next();
						if (done)
						{
							SQL_Command = "SELECT Balance FROM SavingsAccount WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'";
							Rslt = Stmt.executeQuery(SQL_Command);
							Balance = Rslt.getFloat(1);
							float FIR = (float)InterestRate;
							calcInterest = Balance * FIR;
						}
						Stmt.close();
						ToDB.closeConn();
					}
				}
				catch(java.sql.SQLException e)
				{       done = false;
						System.out.println("SQLException: " + e);
						while (e != null)
						{
								System.out.println("SQLState: " + e.getSQLState());
								System.out.println("Message: " + e.getMessage());
								System.out.println("Vendor: " + e.getErrorCode());
								e = e.getNextException();
								System.out.println("");
						}
				}
				catch (java.lang.Exception e)
				{
					done = false;
					System.out.println("Exception: " + e);
					e.printStackTrace ();
				}
				return calcInterest;
		}

    public float getBalance()
    {  //Method to return a SsavingsAccount balance
		try
		{
		        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
		        Connection DBConn = ToDB.openConn();
		        Statement Stmt = DBConn.createStatement();
		        String SQL_Command = "SELECT Balance FROM SavingsAccount WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'"; //SQL query command for Balance
		        ResultSet Rslt = Stmt.executeQuery(SQL_Command);

		        while (Rslt.next())
		        {
					Balance = Rslt.getFloat(1);
			    }
			    Stmt.close();
			    ToDB.closeConn();
		}
	    catch(java.sql.SQLException e)
	    {
				 System.out.println("SQLException: " + e);
				 while (e != null)
				 {
					 System.out.println("SQLState: " + e.getSQLState());
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
	    return Balance;
	}

    public String getBalance(String SavingsAccountNumber)
    {  //Method to return a CheckingAccount balance
		try
		{
		        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
		        Connection DBConn = ToDB.openConn();
		        Statement Stmt = DBConn.createStatement();
		        String SQL_Command = "SELECT Balance FROM SavingsAccount WHERE SavingsAccountNumber ='"+SavingsAccountNumber+"'"; //SQL query command for Balance
		        ResultSet Rslt = Stmt.executeQuery(SQL_Command);
				//Rslt.next();

		        while (Rslt.next())
		        {
					Balance = Rslt.getFloat(1);
					StringBalance = String.valueOf(Balance);
			    }
			    Stmt.close();
			    ToDB.closeConn();
		}
	    catch(java.sql.SQLException e)
	    {
				 System.out.println("SQLException: " + e);
				 while (e != null)
				 {
					 System.out.println("SQLState: " + e.getSQLState());
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
	    return StringBalance;
	}
}