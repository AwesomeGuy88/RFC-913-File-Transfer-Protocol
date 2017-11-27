import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//To Do:
// Write to username file
// - check if valid/logged/
// - Implement the 3 basic methods
// - Go through other 7 methods

public class Handler implements Runnable {
	private Socket socket;
	
	private String[] szUserNames = new String[10];
	private String[] szAccounts = new String[10];
	private String[] szPasswords = new String[10];
	private String[] szLoggedIn = new String[10];
	private int nCurrentId = -1;
	private boolean loggedIn = false;
	
	private String szMode = "Binary";
	private String szCurrentDirectory = "unknown";
	

	private String DirectoryCDIR;
	private boolean cdirFlag = false;
	private boolean cdirFlag1 = false;
	private boolean cdirFlag2 = false;
	
	private String szResponseCode;
	private String szResponse;

	//Constructor
	public Handler(Socket socket)
	{
		this.socket = socket;
	}
	
	public void run()
	{
		for(;;)
		{
			try 
			{
				//Retrieve message from client
				BufferedReader messagesFromClient =
						new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//Process message from client
				String szFromClient = messagesFromClient.readLine();
				String[] szCommand = szFromClient.split("\\s+");
			
				
				//Perform specified command
				switch(szCommand[0]) 
				{
				case "USER":	
					USER(szCommand[1]);
					break;
				case "ACCT":	
					ACCT(szCommand[1]);
					break;
				case "PASS":	
					PASS(szCommand[1]);
					break;
				case "TYPE":	
					TYPE(szCommand[1]);
					break;
				case "LIST":	
					LIST(szCommand[1]);
					break;
				case "CDIR":	
					CDIR(szCommand[1]);
					break;
				case "KILL":	
					KILL(szCommand[1]);
					break;
				case "DONE":	
					DONE();
					break;
				default: 		
					szResponseCode = "Command is invalid";
					break;
				}
				
				//Print Response
				System.out.println(szResponseCode + " " + szResponse);
			}
			catch (IOException e) { /*…*/ }
			
		}
	}
	
	public void USER(String userID)
	{
        String fileName = "src/user.txt";
        szUserNames = retrieveData(fileName);
        
        //If requested ID is guest then log in
        if(userID.equals("guest"))
		{
			szResponseCode = "!";
			szResponse = userID + " logged in";
			loggedIn = true;
			return;
		}
        
        //If user already exists then ask for account and password
        for(int j=0; j<szUserNames.length; j++)
		{
			if(userID.equals(szUserNames[j]))
			{
				szResponseCode = "+";
				szResponse = "User-id valid, send account and password";
				return;
			}
		}
        
        //Else userID is invalid
        szResponseCode = "-";
		szResponse = "Invalid User";
        
        
      //Debugging: Print Usernames database
		/*for(int j=0; j<szUserNames.length; j++)
		{
			if(szUserNames[j] != null) 
			{
			System.out.println(szUserNames[j]);
			}
		}*/
	}
	
	public void ACCT(String account)
	{
        String fileName = "src/account.txt";
        szAccounts = retrieveData(fileName);
        
        //Check if logged on already
		if(loggedIn)
		{
			szResponseCode = "!";
			szResponse = account + " logged in";
			return;
		}
		
        
        //Check if account exists in database
        for(int j=0; j<szAccounts.length; j++)
		{
			if(account.equals(szAccounts[j]))
			{
				szResponseCode = "+";
				szResponse = "Account valid, send password";
				this.nCurrentId = j;
				
				//CDIR related code
				if(cdirFlag) {
					if(cdirFlag1) {
						szResponseCode = "!";
						szResponse = "Changed working dir to " + DirectoryCDIR;
						System.setProperty("user.dir", DirectoryCDIR);
						cdirFlag = false;
						cdirFlag1 = false;
					} else {
						cdirFlag2 = true;
					}
				}
				return;
			}
		}
        
        //Else invalid
		szResponseCode = "-";
		szResponse = "Invalid account, try again";
	}
	
	public void PASS(String password)
	{
		String fileName = "src/password.txt";
		szPasswords = retrieveData(fileName);
        
        //Check account has been sent
		if(this.nCurrentId == -1)
		{
			szResponseCode = "+";
			szResponse = "Send account";
			return;
		}
        
        
        //Check if password matches in database
		if(password.equals(szPasswords[this.nCurrentId]))
		{
			szResponseCode = "!";
			szResponse = "Logged in";
			loggedIn = true;
			
			//CDIR related code
			if(cdirFlag) {
				if(cdirFlag2) {
					szResponseCode = "!";
					szResponse = "Changed working dir to " + DirectoryCDIR;
					System.setProperty("user.dir", DirectoryCDIR);
					cdirFlag = false;
					cdirFlag2 = false;
				} else {
					cdirFlag1 = true;
				}
			}
			return;
		}
		
		//Else password is invalid
		szResponseCode = "-";
		szResponse = "Wrong password, try again";
        
	}
	
	public void TYPE(String type)
	{
		 //Check if logged on already
		if(!loggedIn)
		{
			szResponseCode = "-";
			szResponse = "Not logged in";
			return;
		}
		
		//Change mode
		switch(type)
		{
		case "A":
			this.szMode = "Ascii";
			break;
		case "B":
			this.szMode = "Binary";
			break;
		case "C":
			this.szMode = "Continuous";
			break;
		default:
			//Deal with invalid input
			szResponseCode = "-";
			szResponse = "Type is not valid";
			return;
		}
		
		//Produce response
		szResponseCode = "+";
		szResponse = "Using " + this.szMode + " mode\n";
		
	}
	
	public void LIST(String format)
	{
		//Find current directory
		String currentDirectory = System.getProperty("user.dir");
	    File file = new File(currentDirectory);
	    
	    //Obtain the files of the current directory
	    File[] filesList = file.listFiles();
	    
	    //Display current directory
	    if(format.equals("V"))
	    { //Concatenate according to V format
			szResponseCode = "+";
			szResponse = currentDirectory + "\r\n";
	        for(File f : filesList){
	        	szResponse += f.getName() + ", Last Modified: " + f.lastModified() + "\r\n";
	        }
	    	
	    } else
	    { //Concatenate according to F format
			szResponseCode = "+";
			szResponse = currentDirectory + "\r\n";
	        for(File f : filesList){
	        	szResponse += f.getName() + "\r\n";
	        }
			szResponse += '\0';
	    }
	}
	
	public void CDIR(String newDirectory)
	{
		//Check that directory is valid
	    File file = new File(newDirectory);
	    if (!(file.exists() && file.isDirectory())) {
	    	//Return if invalid
			szResponseCode = "-";
			szResponse = "Can't connect to directory because it is invalid";
			return;
	    }
	    
	    //Check if logged on already
		if(loggedIn)
		{
			szResponseCode = "!";
			szResponse = "Changed working dir to " + newDirectory;
			System.setProperty("user.dir", newDirectory);
			return;
		}

		szResponseCode = "+";
		szResponse = "Directory ok, send account/password";
		cdirFlag = true;
		DirectoryCDIR = newDirectory;
      
	}
	
	public void KILL(String fileSpec)
	{
		//Find current directory
		String currentDirectory = System.getProperty("user.dir");
	    File file = new File(currentDirectory);
		
	    try
	    {
	        File f=new File(fileSpec);
	        boolean deleted = f.delete();
	    } catch(Exception e) {
			szResponseCode = "-";
			szResponse = "Not deleted ";
			return;
	    }
	    

		szResponseCode = "+";
		szResponse = fileSpec + " deleted";
	}
	
	public void DONE() throws IOException
	{
		szResponseCode = "+";
		szResponse = "(the message may be charge/accounting info";
		
		this.socket.close();
		return;
	}
	
	
	
	
	private String[] retrieveData(String fileName)
	{
		String[] szBuffer = new String[10];
		String line = null;
        int i = 0;
        

        try {
            BufferedReader bufferedReader = 
                new BufferedReader(new FileReader(fileName));

            while((line = bufferedReader.readLine()) != null) {
            	szBuffer[i] = line;
                i++;
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
        	szResponse += "Unable to open file. ";            
        }
        catch(IOException ex) {
        	szResponse += "Error reading file. ";   
        }
        
        return szBuffer;
	}
	
	private void storeData(String fileName, String data)
	{
		try (FileWriter fw = new FileWriter(fileName, true)) {
        	fw.append(data + '\n');
        }
        catch(IOException e) {}
	}
}
