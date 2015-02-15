package multiplayergame;

import java.net.*;
import java.io.*;

public class Server {
	public static void main(String args[]) throws IOException {
		// declaration section:
		// declare a server socket and a client socket for the server
		// declare an input and an output stream
		        ServerSocket Server = null;
		        String line;
		        DataInputStream is;
		        PrintStream os;
		        Socket clientSocket = null;
		// Try to open a server socket on port 9999
		// Note that we can't choose a port less than 1023 if we are not
		// privileged users (root)
		        try {
		           Server = new ServerSocket(8888);
		        }
		        catch (IOException e) {
		           System.out.println(e);
		        }   
		// Create a socket object from the ServerSocket to listen and accept 
		// connections.
		// Open input and output streams
		    try {
		           clientSocket = Server.accept();
		           is = new DataInputStream(clientSocket.getInputStream());
		           os = new PrintStream(clientSocket.getOutputStream());
		// As long as we receive data, echo that data back to the client.
		           while (true) {
		             line = is.readLine();
		             os.println(line); 
		           }
		        }   
		    catch (IOException e) {
		           System.out.println(e);
		        }
		    }
}