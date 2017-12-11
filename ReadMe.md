# RFC913 Assignment

Computer networks assignment which demonstrates development on the application layer.
Program opens a TCP connection to the local host (can be changed) at port 13014 and
provides the RFC913 file transfer protocol interface.

<br/>

 **COMPILATION:**
1. Compile all all code in src using command prompt with command "javac *.java".
If failure likely because Java compiler not installed or listed as environment
variable.

2. Run both the server and client using "java Client" and "java Server"
(Client first)

3. Place commands into client, some commands are listed in test cases.

<br/>

 **USE CASES:**
Sign in with existing username
	*USER awesomeo2*
	
	<br/>

Sign in with valid credentials
	*ACCT momo32*
	*PASS bloblo*
	
	<br/>

Sign in using guest (You have to restart the server to sign out and redo this)
	*USER guest*
	
	<br/>
	
Switch modes 
	*TYPE A*
	*TYPE B*
	*TYPE C*
	*TYPE Dog*		- Dog is an invalid paramter and returns incorrect
	
	<br/>
	
List current directory
	*LIST F*
	*LIST V*
	
	<br/>
	
Change the directory (need to choose a valid path for your computer)
	*CDIR C:\Users\Ethan\Desktop\Compsys725\Assignment\Workspace*
	*ACCT momo32*
	*PASS bloblo*
	
	<br/>
	
Delete a file (I've given an example of a text file ive deleted)
	*KILL C:\Users\Ethan\Desktop\Compsys725\Assignment\Workspace\Assignment1\src\Deleteme.txt*
			
			<br/>
			
Close the connection
	*DONE*
	
	
	
	