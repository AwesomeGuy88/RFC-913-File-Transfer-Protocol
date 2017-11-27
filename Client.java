import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	
	public static void main(String[] args) throws Exception
	{
		String IP = "localhost";
		int Port = 13014;
		
		//Open active socket
		Socket clientSocket = new Socket(IP, Port);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Client started and sending to " + IP + "with port " + Port);
		
		//Get user input
		while(true)
		{
			System.out.println("Send Command: ");
			String userInput = inFromUser.readLine();
			
			//Hardcoded inputs
			//String userInput = "USER param <NULL>";
			//String userInput = "ACCT momo32 <NULL>";
			//String userInput = "PASS bloblo <NULL>";
			//String userInput = "TYPE B <NULL>";
			//LIST F, LIST V
			
			//CDIR C:\Users\Ethan\Desktop\Compsys725\Assignment\Workspace
			//ACCT momo32
			//PASS bloblo
			
			//KILL C:\Users\Ethan\Desktop\Compsys725\Assignment\Workspace\Assignment1\src\Deleteme.txt
			
			//Send user input and close socket
			outToServer.writeBytes(userInput + '\n');
			if(userInput.equals("DONE"))
			{
				break;
			}
		}
		clientSocket.close();
	}
}
