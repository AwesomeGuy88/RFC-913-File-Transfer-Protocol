
RFC913 ASSIGNMENT

DETAILS:
- For the sake of testing, both the client and server open on port 13014
manually.
- username, account and password text files contain account information
-I have a connection handler class which does most of the server side
computation
- Some methods i have not had time to implement

COMPILATION:
1. Compile all all code in src using command prompt with command "javac *.java".
If failure likely because Java compiler not installed or listed as environment
variable.

2. Run both the server and client using "java Client" and "java Server"
(Client first)

3. Place commands into client, some commands are listed in test cases.


USE CASES:
Sign in with existing username
	USER awesomeo2

Sign in with valid credentials
	ACCT momo32
	PASS bloblo

Sign in using guest (You have to restart the server to sign out and redo this)
	USER guest
	
Switch modes 
	TYPE A
	TYPE B
	TYPE C
	TYPE Dog		- Dog is an invalid paramter and returns incorrect
	
List current directory
	LIST F
	LIST V
	
Change the directory (I've given an example but you need to choose a valid path for your computer)
	CDIR C:\Users\Ethan\Desktop\Compsys725\Assignment\Workspace
	ACCT momo32
	PASS bloblo
	
Delete a file (I've given an example of a text file ive deleted)
	KILL C:\Users\Ethan\Desktop\Compsys725\Assignment\Workspace\Assignment1\src\Deleteme.txt
			
Close the connection
	DONE
	
	
	
	