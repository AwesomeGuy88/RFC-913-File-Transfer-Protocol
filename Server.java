import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	public static void main(String[] args) throws Exception 
	{
		//Open Passive Socket
		int Port = 13014;
		ServerSocket ourFirstSocket = new ServerSocket(Port);
		System.out.println("Server started and listening at port " + Port + "\n");
		while(true) {
			
			//Create new active socket and thread per connection request
			Socket connectionSocket = ourFirstSocket.accept();
			new Thread(new Handler(connectionSocket)).start();
		}
	}

}
