/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

package com.villafranca;

import java.lang.*; //including Java packages used by this program
import java.sql.*;
import java.time.LocalDate;
import com.villafranca.*;

public class AccountV2
{
	private String Username, Password, Password1, Name;
	private String LastLogin="";
	private int ThreeConsecutiveFailures;

	public AccountV2(String UN, String PassW, String PassW1, String NM)
	{
		Username = UN;
		Password = PassW;
		Password1 = PassW1;
		Name = NM;
	}

	public AccountV2(String UN, String PassW)
	{
		Username = UN;
		Password = PassW;
	}

	public AccountV2()
	{

	}

    public boolean signUp()
    {
		boolean done = !Username.equals("") && !Password.equals("") && !Password1.equals("") && Password.equals(Password1);
		try
		{
		    if (done)
		    {
		        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
		        Connection DBConn = ToDB.openConn();
		        Statement Stmt = DBConn.createStatement();
		        String SQL_Command = "SELECT Username FROM Account WHERE Username ='"+Username+"'"; //SQL query command
		        ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
		        done = done && !Rslt.next();
		        if (done)
		        {
				    SQL_Command = "INSERT INTO Account(Username, Password, Name) VALUES ('"+Username+ "','"+Password+"','"+Name+"')"; //Save the username, password and Name
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

	public String signIn()
	{
		try
		{
			DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
			Connection DBConn = ToDB.openConn();
			Statement Stmt = DBConn.createStatement();
			String SQL_Command = "select Username from Account where Username = '"+Username+"' and Password = '" +Password+ "'"; //SQL query command
			ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.

			while (Rslt.next())
			{
				Name = Rslt.getString(1);
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
	    return Name;
	}

	public boolean changePassword(String NewPassword) {
		boolean done = false;
		try
		{
		        DBConnectionV2 ToDB = new DBConnectionV2(); //Have a connection to the DB
		        Connection DBConn = ToDB.openConn();
		        Statement Stmt = DBConn.createStatement();
		        String SQL_Command = "SELECT * FROM Account WHERE Username ='"+Username+ "'AND Password ='"+Password+"'"; //SQL query command
		        ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
		        if (Rslt.next())
		        {
				    SQL_Command = "UPDATE Account SET Password='"+NewPassword+"' WHERE Username ='"+Username+"'"; //Save the username, password and Name
				    Stmt.executeUpdate(SQL_Command);
			        Stmt.close();
			        ToDB.closeConn();
                    done=true;
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
	    {         done = false;
				 System.out.println("Exception: " + e);
				 e.printStackTrace ();
	    }
	    return done;
	}
}