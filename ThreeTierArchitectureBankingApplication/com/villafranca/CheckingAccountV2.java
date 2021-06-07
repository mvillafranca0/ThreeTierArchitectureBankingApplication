/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/
package com.villafranca;

import java.lang.*; //including Java packages used by this program
import java.sql.*;
import com.villafranca.*;

public class CheckingAccountV2
{   //Instance Variables
	private String CheckingAccountNumber = "", CustomerName, CustomerID, StringBalance;
	private float Balance = -1, DW_Input = 0;

	public CheckingAccountV2(String CA_Num, String Cust_Name, String Cust_ID, String Bal)
	{ //Constructor One with three parameters
		CheckingAccountNumber = CA_Num;
		CustomerName = Cust_Name;
		CustomerID = Cust_ID;
		Balance = Float.parseFloat(Bal);
		DW_Input = Balance;
	}

	public CheckingAccountV2(String Cust_Name, String Cust_ID, String Bal)
	{ //Constructor One with three parameters
			CustomerName = Cust_Name;
			CustomerID = Cust_ID;
			Balance = Float.parseFloat(Bal);
			DW_Input = Balance;
	}

	public CheckingAccountV2(String Cust_Name, String Cust_ID)
	{ //Constructor One with three parameters
				CustomerName = Cust_Name;
				CustomerID = Cust_ID;
	}

	public CheckingAccountV2(String CA_Num)
	{ //Constructor Two with one parameter
		CheckingAccountNumber = CA_Num;
	}
	public CheckingAccountV2()
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
				        String SQL_Command = "SELECT CheckingAccountNumber FROM CheckingAccount WHERE CheckingAccountNumber ='"+CheckingAccountNumber+"'"; //SQL query command
				        ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
				        done = !Rslt.next();
				        if (done)
				        {
						    SQL_Command = "INSERT INTO CheckingAccount(CheckingAccountNumber, CustomerName, Balance, CustomerID)"+
						                  " VALUES ('"+CheckingAccountNumber+"','"+CustomerName+"',"+Balance+", '"+CustomerID+"')"; //Save the username, password and Name
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
					        String SQL_Command = "SELECT CheckingAccountNumber FROM CheckingAccount WHERE CustomerID ='"+CustomerID+"'"; //SQL query command
					        ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
					        done = Rslt.next();
							CheckingAccountNumber = Rslt.getString(1);
					        if (done)
					        {
								SQL_Command = "SELECT Balance FROM CheckingAccount WHERE CheckingAccountNumber ='"+CheckingAccountNumber+"'";
								Rslt = Stmt.executeQuery(SQL_Command);
								Rslt.next();
								Balance = Rslt.getFloat(1);
								Balance = Balance + DW_Input;
							    SQL_Command = "UPDATE CheckingAccount SET Balance = "+Balance+" WHERE CheckingAccountNumber ='"+CheckingAccountNumber+"'";
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
							        String SQL_Command = "SELECT CheckingAccountNumber FROM CheckingAccount WHERE CustomerID ='"+CustomerID+"'"; //SQL query command
							        ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
							        done = Rslt.next();
									CheckingAccountNumber = Rslt.getString(1);
							        if (done)
							        {
										SQL_Command = "SELECT Balance FROM CheckingAccount WHERE CheckingAccountNumber ='"+CheckingAccountNumber+"'";
										Rslt = Stmt.executeQuery(SQL_Command);
										Rslt.next();
										Balance = Rslt.getFloat(1);
										Balance = Balance - DW_Input;
									    SQL_Command = "UPDATE CheckingAccount SET Balance = "+Balance+" WHERE CheckingAccountNumber ='"+CheckingAccountNumber+"'";
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
    public String getBalance(String CheckingAccountNumber)
    {  //Method to return a CheckingAccount balance
		try
		{
		        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
		        Connection DBConn = ToDB.openConn();
		        Statement Stmt = DBConn.createStatement();
		        String SQL_Command = "SELECT Balance FROM CheckingAccount WHERE CheckingAccountNumber ='"+CheckingAccountNumber+"'"; //SQL query command for Balance
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

	public String getCheckingAccountNumber (String UName)
	{  //Method to return a CheckingAccount balance
			try
			{
			        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
			        Connection DBConn = ToDB.openConn();
			        Statement Stmt = DBConn.createStatement();
			        String SQL_Command = "SELECT CheckingAccountNumber FROM CheckingAccount WHERE CustomerID ='"+UName+"'"; //SQL query command for Balance
			        ResultSet Rslt = Stmt.executeQuery(SQL_Command);

			        while (Rslt.next())
			        {
						CheckingAccountNumber = Rslt.getString(1);
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
		    System.out.println(CheckingAccountNumber);
		    return CheckingAccountNumber;
	}

	public boolean getCheckingAccountNumberBool (String UName)
	{  //Method to return a CheckingAccount balance
				boolean bool;
				try
				{
				        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
				        Connection DBConn = ToDB.openConn();
				        Statement Stmt = DBConn.createStatement();
				        String SQL_Command = "SELECT CheckingAccountNumber FROM CheckingAccount WHERE CustomerID ='"+UName+"'"; //SQL query command for Balance
				        ResultSet Rslt = Stmt.executeQuery(SQL_Command);

				        while (Rslt.next())
				        {
							CheckingAccountNumber = Rslt.getString(1);
					    }
					    Stmt.close();
					    ToDB.closeConn();
					    if (CheckingAccountNumber.equals(""))
					    	bool = true;
					    else
					    	bool = false;
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
						 bool = false;
			    }
			    catch (java.lang.Exception e)
			    {
						 System.out.println("Exception: " + e);
						 e.printStackTrace ();
						 bool = false;
			    }
			    return bool;
	}
}