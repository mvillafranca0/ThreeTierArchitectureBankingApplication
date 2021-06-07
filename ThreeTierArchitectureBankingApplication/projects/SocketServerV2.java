/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
import com.villafranca.*;

// SocketServerV2 2020
public class SocketServerV2
{

   public static void main(String[] args) throws IOException
   {
   		Socket echoSocket;
   		ObjectInputStream sin = null;
		String clientMsg = "";
		String serverMsg = "";
   		String request = "";
	  	if (args.length != 1)
	  	{
	  		System.err.println("Usage: java SocketServer port");
	  		return;
	  	}
	  	try
	  	{
	  		ServerSocket s = new ServerSocket(Integer.parseInt(args[0]));
	  		for(;;)
	  		{
	  			echoSocket = s.accept();
	  			System.out.println("Connection from: " + echoSocket.getInetAddress());
				sin = new ObjectInputStream(echoSocket.getInputStream());
				clientMsg = (String) sin.readObject();
				System.out.println("Request is: " + clientMsg);
				request = clientMsg;
				serverMsg = clientMsg;
				serverMsg = serverMsg + " request";

				if (request.equals("Login"))
				{
					LoginRequestThread NewReq = new LoginRequestThread(echoSocket, sin);
					NewReq.start();
				}
				else if (request.equals("SignUp"))
				{
					SignUpRequestThread NewReq = new SignUpRequestThread(echoSocket, sin);
					NewReq.start();
				}
				else if (request.equals("AccountOverview"))
				{
					AccountOverviewRequestThread NewReq = new AccountOverviewRequestThread(echoSocket, sin);
					NewReq.start();
				}
				else if (request.equals("OpenAccount"))
				{
					OpenAccountRequestThread NewReq = new OpenAccountRequestThread(echoSocket, sin);
					NewReq.start();
				}
				else if (request.equals("Withdraw"))
				{
					WithdrawRequestThread NewReq = new WithdrawRequestThread(echoSocket, sin);
					NewReq.start();
				}
				else if (request.equals("Deposit"))
				{
					DepositRequestThread NewReq = new DepositRequestThread(echoSocket, sin);
					NewReq.start();
				}
				else if (request.equals("Transfer"))
				{
					TransferRequestThread NewReq = new TransferRequestThread(echoSocket, sin);
					NewReq.start();
				}
				else if (request.equals("InquireTransactions"))
				{
					InquireTransactionsRequestThread NewReq = new InquireTransactionsRequestThread(echoSocket, sin);
					NewReq.start();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No Valid Request Error...", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
				}
	  		}
	  	}
	  	catch (Exception e)
	  	{
	  		System.err.println(e);
	  		return;
	    }
	}
}

class LoginRequestThread extends Thread
{
	Socket echoSocket;
	ObjectInputStream sin = null;
	ObjectOutputStream sout  = null;
	String userName;
	String passWord;

	public LoginRequestThread(Socket S, ObjectInputStream s_in)
	{
		try
		{
			echoSocket=S;
       	 	sin = s_in;
			sout = new ObjectOutputStream(echoSocket.getOutputStream());
		}
		catch (Exception e)
		{
			 System.err.println(e);
        }
	}

	public void run()
	{
		try
		{
			 	String clientMsg = (String) sin.readObject();
			 	System.out.println("Username is: " + clientMsg);
			 	userName = clientMsg;

			 	clientMsg = (String) sin.readObject();
			 	System.out.println("Password is: " + clientMsg);
				passWord = clientMsg;

				AccountV2 Acct = new AccountV2(userName, passWord);
				String CustomerName = "";
				CustomerName = Acct.signIn();
        		if (!CustomerName.equals(""))
        		{
					sout.writeObject(CustomerName);
					sout.flush();
				}
				else
            		JOptionPane.showMessageDialog(null, "Login failed because of invalid username or password.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

		 }
		 catch (Exception e)
		 {
			 System.err.println(e);
			 return;
         }
	}
}

class SignUpRequestThread extends Thread
{
	Socket echoSocket;
	ObjectInputStream sin = null;
	ObjectOutputStream sout  = null;
	String userName;
	String passWord;
	String passWord1;
	String customerName;

	public SignUpRequestThread(Socket S, ObjectInputStream s_in)
		{
			try
			{
				echoSocket=S;
	       	 	sin = s_in;
				sout = new ObjectOutputStream(echoSocket.getOutputStream());
			}
			catch (Exception e)
			{
				 System.err.println(e);
	        }
		}

		public void run()
	{
				try
				{
					 	String clientMsg = (String) sin.readObject();
						System.out.println("Username is: " + clientMsg);
						userName = clientMsg;

						clientMsg = (String) sin.readObject();
						System.out.println("Password is: " + clientMsg);
						passWord = clientMsg;

					 	clientMsg = (String) sin.readObject();
					 	System.out.println("Confirmation Password is: " + clientMsg);
						passWord1 = clientMsg;

					 	clientMsg = (String) sin.readObject();
						System.out.println("Customer Name is: " + clientMsg);
						customerName = clientMsg;

						AccountV2 Acct = new AccountV2(userName, passWord, passWord1, customerName);

		        		if (Acct.signUp())
		        		{
							sout.writeObject("sign up complete");
						}
						else
		            		JOptionPane.showMessageDialog(null, "Sign up Failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
					 	//sout.writeObject(serverMsg);
				 }
				 catch (Exception e)
				 {
					 System.err.println(e);
					 return;
         		 }
	}
}

class OpenAccountRequestThread extends Thread
{
	Socket echoSocket;
	ObjectInputStream sin = null;
	ObjectOutputStream sout  = null;
	String userName;
	String accountNumber;
	String accountType;
	String bal;
	String customerName;
	String transType;

	public OpenAccountRequestThread(Socket S, ObjectInputStream s_in)
		{
			try
			{
				echoSocket=S;
	       	 	sin = s_in;
				sout = new ObjectOutputStream(echoSocket.getOutputStream());
			}
			catch (Exception e)
			{
				 System.err.println(e);
	        }
		}

		public void run()
		{
			try
							{
									String clientMsg = (String) sin.readObject();
									System.out.println("Username is: " + clientMsg);
									userName = clientMsg;

									clientMsg = (String) sin.readObject();
									System.out.println("Customer Name is: " + clientMsg);
									customerName = clientMsg;

									clientMsg = (String) sin.readObject();
									System.out.println("Account Number is: " + clientMsg);
									accountNumber = clientMsg;

									clientMsg = (String) sin.readObject();
									System.out.println("Balance is: " + clientMsg);
									bal = clientMsg;

									clientMsg = (String) sin.readObject();
									System.out.println("Account Type is: " + clientMsg);
									accountType = clientMsg;


									if (accountType.equals("Checking"))
												{
													CheckingAccountV2 CA = new CheckingAccountV2(accountNumber, customerName, userName, bal);
														CA.openAcct();
									                	transType = "OpeningDeposit";
									                	TransactionsV2 T = new TransactionsV2(transType, accountNumber, "NULL", userName, bal);
									                	T.recordTransactions();
														sout.writeObject("open account complete");
												}
												if (accountType.equals("Savings"))
												{
													SavingsAccountV2 SA = new SavingsAccountV2(accountNumber, customerName, userName, bal);
														SA.openAcct();
												  		transType = "OpeningDeposit";
												  		TransactionsV2 T = new TransactionsV2(transType, accountNumber, "NULL", userName, bal);
									                	T.recordTransactions();
														sout.writeObject("open account complete");
								 }
							 }
							 catch (Exception e)
							 {
								 System.err.println(e);
								 return;
         					 }
		}
}

class AccountOverviewRequestThread extends Thread
{
	Socket echoSocket;
	ObjectInputStream sin = null;
	ObjectOutputStream sout  = null;
	String userName = "", name = "", accountType = "";

	public AccountOverviewRequestThread(Socket S, ObjectInputStream s_in)
		{
			try
			{
				echoSocket=S;
	       	 	sin = s_in;
				sout = new ObjectOutputStream(echoSocket.getOutputStream());
			}
			catch (Exception e)
			{
				 System.err.println(e);
	        }
		}

		public void run()
	{
		try
					{
						String clientMsg = (String) sin.readObject();
						System.out.println("Username is: " + clientMsg);
						userName = clientMsg;

						clientMsg = (String) sin.readObject();
						System.out.println("Customer Name is: " + clientMsg);
						name = clientMsg;

						clientMsg = (String) sin.readObject();
						System.out.println("Account Type is: " + clientMsg);
						accountType = clientMsg;

						if (accountType.equals("Checking"))
						{
							CheckingAccountV2 CA = new CheckingAccountV2(name, userName);
							String AccountNumber = CA.getCheckingAccountNumber(userName);
							String Balance = CA.getBalance(AccountNumber);
							if (!Balance.equals(""))
							{
								sout.writeObject(Balance);
							}
							//BalanceField.setText(Balance);
							//JOptionPane.showMessageDialog(null, "Checking Account Balance: " + Balance, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
						}
						else if (accountType.equals("Savings"))
						{
							SavingsAccountV2 SA = new SavingsAccountV2(name, userName);
							String AccountNumber = SA.getSavingsAccountNumber(userName);
							String Balance = SA.getBalance(AccountNumber);
							if (!Balance.equals(""))
							{
								sout.writeObject(Balance);
							}
							//BalanceField.setText(Balance);
							//JOptionPane.showMessageDialog(null, "Savings Account Balance: " + Balance, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Please Choose an Account Type!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					catch (Exception e)
					{
						System.err.println(e);
			}
	}
}

class WithdrawRequestThread extends Thread
{
	Socket echoSocket;
	ObjectInputStream sin = null;
	ObjectOutputStream sout  = null;
	String TransactionType, UName, AccountNumber, Balance, Name, AccountType;

	public WithdrawRequestThread(Socket S, ObjectInputStream s_in)
		{
			try
			{
				echoSocket=S;
	       	 	sin = s_in;
				sout = new ObjectOutputStream(echoSocket.getOutputStream());
			}
			catch (Exception e)
			{
				 System.err.println(e);
	        }
		}

		public void run()
	{
		try
							{
										String clientMsg = (String) sin.readObject();
									 	System.out.println("Username is: " + clientMsg);
									 	UName = clientMsg;
									 	//String serverMsg = clientMsg;
									 	//serverMsg = "*" + serverMsg + "*";
									 	//sout.writeObject(serverMsg);

										clientMsg = (String) sin.readObject();
									 	System.out.println("Customer Name is: " + clientMsg);
										Name = clientMsg;
										//serverMsg = clientMsg;
										//serverMsg = "*" + serverMsg + "*";
									 	//sout.writeObject(serverMsg);

										clientMsg = (String) sin.readObject();
										System.out.println("Balance is: " + clientMsg);
										Balance = clientMsg;

									 	clientMsg = (String) sin.readObject();
									 	System.out.println("Account Type is: " + clientMsg);
										AccountType = clientMsg;
										//serverMsg = clientMsg;
										//serverMsg = "*" + serverMsg + "*";
									 	//sout.writeObject(serverMsg);

								if (AccountType.equals("Checking"))
								{
									CheckingAccountV2 CA = new CheckingAccountV2(Name, UName, Balance);
												           		if (CA.withdraw())
												            	{
												            		//System.out.println("successful!");
												                	//JOptionPane.showMessageDialog(null, "Withdraw is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
												                	TransactionType = "Withdraw";
												                	String AcountNumber = CA.getCheckingAccountNumber(UName);
												                	TransactionsV2 T = new TransactionsV2(TransactionType, AcountNumber, "NULL", UName, Balance);
												                	T.recordTransactions();
																	sout.writeObject("withdraw complete");
			            										}
								}
								if (AccountType.equals("Savings"))
								{
									SavingsAccountV2 SA = new SavingsAccountV2(Name, UName, Balance);
																if (SA.withdraw())
																{
																	//System.out.println("successful!");
																   	//JOptionPane.showMessageDialog(null, "Withdraw is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
																   	TransactionType = "Withdraw";
																   	String AcountNumber = SA.getSavingsAccountNumber(UName);
																   	TransactionsV2 T = new TransactionsV2(TransactionType, AcountNumber, "NULL", UName, Balance);
																   	T.recordTransactions();
																   	sout.writeObject("withdraw complete");
																}
								}
							}
							catch (Exception e)
							{
								System.err.println(e);
							}
	}
}

class DepositRequestThread extends Thread
{
	Socket echoSocket;
	ObjectInputStream sin = null;
	ObjectOutputStream sout  = null;
	String TransactionType, UName, AccountNumber, Balance, Name, AccountType;

	public DepositRequestThread(Socket S, ObjectInputStream s_in)
		{
			try
			{
				echoSocket=S;
	       	 	sin = s_in;
				sout = new ObjectOutputStream(echoSocket.getOutputStream());
			}
			catch (Exception e)
			{
				 System.err.println(e);
	        }
		}

		public void run()
	{
		try
									{
												String clientMsg = (String) sin.readObject();
												System.out.println("Username is: " + clientMsg);
												UName = clientMsg;
												//String serverMsg = clientMsg;
												//serverMsg = "*" + serverMsg + "*";
												//sout.writeObject(serverMsg);

												clientMsg = (String) sin.readObject();
												System.out.println("Customer Name is: " + clientMsg);
												Name = clientMsg;
												//serverMsg = clientMsg;
												//serverMsg = "*" + serverMsg + "*";
												//sout.writeObject(serverMsg);

												clientMsg = (String) sin.readObject();
												System.out.println("Balance is: " + clientMsg);
												Balance = clientMsg;

												clientMsg = (String) sin.readObject();
												System.out.println("Account Type is: " + clientMsg);
												AccountType = clientMsg;
												//serverMsg = clientMsg;
												//serverMsg = "*" + serverMsg + "*";
												//sout.writeObject(serverMsg);


										if (AccountType.equals("Checking"))
										{
											CheckingAccountV2 CA = new CheckingAccountV2(Name, UName, Balance);
														           		if (CA.deposit())
																		{
																		   	//System.out.println("successful!");
																		   	//JOptionPane.showMessageDialog(null, "Deposit is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
																		   	TransactionType = "Deposit";
																		   	String AcountNumber = CA.getCheckingAccountNumber(UName);
																		   	TransactionsV2 T = new TransactionsV2(TransactionType, AcountNumber, "NULL", UName, Balance);
                															T.recordTransactions();
																			sout.writeObject("deposit complete");
					            										}
										}
										if (AccountType.equals("Savings"))
										{
											SavingsAccountV2 SA = new SavingsAccountV2(Name, UName, Balance);
																		if (SA.deposit())
																		{
																			//System.out.println("successful!");
																			//JOptionPane.showMessageDialog(null, "Deposit is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
																			TransactionType = "Deposit";
																			String AcountNumber = SA.getSavingsAccountNumber(UName);
																			TransactionsV2 T = new TransactionsV2(TransactionType, AcountNumber, "NULL", UName, Balance);
				   															T.recordTransactions();
																		   	sout.writeObject("deposit complete");
																		}
										}
									}
									catch (Exception e)
									{
										System.err.println(e);
							}
	}
}

class TransferRequestThread extends Thread
{
	Socket echoSocket;
	ObjectInputStream sin = null;
	ObjectOutputStream sout  = null;
	String TransactionType, UName, AccountNumber, Balance, Name, AccountType;

	public TransferRequestThread(Socket S, ObjectInputStream s_in)
		{
			try
			{
				echoSocket=S;
	       	 	sin = s_in;
				sout = new ObjectOutputStream(echoSocket.getOutputStream());
			}
			catch (Exception e)
			{
				 System.err.println(e);
	        }
		}

		public void run()
	{
		try
											{
															String clientMsg = (String) sin.readObject();
															System.out.println("Username is: " + clientMsg);
															UName = clientMsg;
															//String serverMsg = clientMsg;
															//serverMsg = "*" + serverMsg + "*";
															//sout.writeObject(serverMsg);

															clientMsg = (String) sin.readObject();
															System.out.println("Customer Name is: " + clientMsg);
															Name = clientMsg;
															//serverMsg = clientMsg;
															//serverMsg = "*" + serverMsg + "*";
															//sout.writeObject(serverMsg);

															clientMsg = (String) sin.readObject();
															System.out.println("Balance is: " + clientMsg);
															Balance = clientMsg;

															clientMsg = (String) sin.readObject();
															System.out.println("Account Type is: " + clientMsg);
															AccountType = clientMsg;

															if (AccountType.equals("Checking"))
															{
																CheckingAccountV2 CA = new CheckingAccountV2(Name, UName, Balance);
												           		if (CA.withdraw())
												            	{
												            		//System.out.println("successful!");
												            		SavingsAccountV2 SA = new SavingsAccountV2(Name, UName, Balance);
												            		if (SA.deposit())
												            		{
												                		//JOptionPane.showMessageDialog(null, "Transfer is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
												                		TransactionType = "Transfer";
												                		TransactionsV2 T = new TransactionsV2(TransactionType, CA.getCheckingAccountNumber(UName), SA.getSavingsAccountNumber(UName), UName, Balance);
												                		T.recordTransactions();
																		sout.writeObject("transfer complete");
																	}
												            	}
															}
															if (AccountType.equals("Savings"))
															{
																SavingsAccountV2 SA = new SavingsAccountV2(Name, UName, Balance);
																if (SA.withdraw())
																{
																	//System.out.println("successful!");
																	CheckingAccountV2 CA = new CheckingAccountV2(Name, UName, Balance);
																	if (CA.deposit())
																	{
																		//JOptionPane.showMessageDialog(null, "Transfer is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
																	  	TransactionType = "Transfer";
																	   	TransactionsV2 T = new TransactionsV2(TransactionType, SA.getSavingsAccountNumber(UName), CA.getCheckingAccountNumber(UName), UName, Balance);
																	   	T.recordTransactions();
																		sout.writeObject("transfer complete");
																	}
																}
															}
											}
											catch (Exception e)
											{
												System.err.println(e);
											}
	}
}

class InquireTransactionsRequestThread extends Thread
{
	Socket echoSocket;
	ObjectInputStream sin = null;
	ObjectOutputStream sout  = null;
	String UName;
	Vector serverMsgVector = new Vector();

	public InquireTransactionsRequestThread(Socket S, ObjectInputStream s_in)
		{
			try
			{
				echoSocket=S;
	       	 	sin = s_in;
				sout = new ObjectOutputStream(echoSocket.getOutputStream());
			}
			catch (Exception e)
			{
				 System.err.println(e);
	        }
		}

		public void run()
	{
		try
													{
																String clientMsg = (String) sin.readObject();
															 	System.out.println("Username is: " + clientMsg);
															 	UName = clientMsg;
															 	//String serverMsg = clientMsg;
															 	//serverMsg = "*" + serverMsg + "*";
															 	//sout.writeObject(serverMsg);

																TransactionsV2 TS = new TransactionsV2(UName);
																serverMsgVector = TS.searchTransactions();
																sout.writeObject("inquire transactions complete");
																sout.writeObject(serverMsgVector);
													}
													catch (Exception e)
													{
														System.err.println(e);
													}
	}
}

/*
class HandleRequestThread extends Thread
{     Socket echoSocket;
      InputStream sin = null;
      OutputStream sout  = null;
      byte[] b = new byte[1024];
      int i;
	public HandleRequestThread(Socket S) {
		try {
			echoSocket=S;
			sin = echoSocket.getInputStream();
       	 	sout= echoSocket.getOutputStream();
		}
		catch (Exception e) {
			 System.err.println(e);
         }
	}

	public void run() {
		try {

			 while ((i = sin.read(b)) != -1) {
		            byte[] temp = new byte[i+2];
		            temp[0] = (byte) '*';
		            for (int k=1; k<=i; k++)
		               temp[k] = b[k-1];
		            temp[i+1] = (byte)'*';
			    sout.write(temp, 0, i+2);
			    sout.flush();
			 }
		 }
		 catch (Exception e) {
			 System.err.println(e);
			 return;
         }
	}
}
*/