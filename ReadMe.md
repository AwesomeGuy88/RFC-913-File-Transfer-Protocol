# RFC913 Assignment

Computer networks assignment which demonstrates development on the application layer.
Program opens a TCP connection to the local host (can be changed) at port 13014 and
provides a file transfer service - the RFC913 protocol.

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
Sign in with existing username <br/>
	*USER awesomeo2* <br/>
	
<br/>

Sign in with valid credentials <br/>
	*ACCT momo32* <br/>
	*PASS bloblo* <br/>

<br/>

Sign in using guest (You have to restart the server to sign out and redo this) <br/>
	*USER guest* <br/>
	
<br/>
	
Switch modes <br/>
	*TYPE A* <br/>
	*TYPE B* <br/>
	*TYPE C* <br/>
	*TYPE Dog*		- Dog is an invalid paramter and returns incorrect <br/>
	
<br/>
	
List current directory <br/>
	*LIST F* <br/>
	*LIST V* <br/>
	
<br/>
	
Change the directory (need to choose a valid path for your computer) <br/>
	*CDIR C:\Users\Ethan\Desktop\Compsys725\Assignment\Workspace* <br/>
	*ACCT momo32* <br/>
	*PASS bloblo* <br/>
	
<br/>
	
Delete a file (I've given an example of a text file ive deleted) <br/>
	*KILL C:\Users\Ethan\Desktop\Compsys725\Assignment\Workspace\Assignment1\src\Deleteme.txt* <br/>
			
<br/>
			
Close the connection <br/>
	*DONE* <br/>
	
	
	
	