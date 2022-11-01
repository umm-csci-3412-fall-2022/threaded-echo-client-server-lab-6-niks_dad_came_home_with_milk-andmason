package echoserver;
import java.net.*;
import java.io.*;
import java.util.*;

public class EchoServer {
	public static final int portNumber = 6013;

	public static void main(String[] args) {
    		try {
      			// Start listening on the specified port
      			ServerSocket sock = new ServerSocket(portNumber);

      			// Run forever, which is common for server style services
      			while (true) {
        			// Wait until someone connects, thereby requesting a date
        			Socket client = sock.accept(); // Waits until a client connects
        			System.out.println("Got a request!");
				
				boolean wait = true;
				while (wait){
					try {
					//if (client.getInputStream() = 0) {client.close(); wait = false;}	
						
					// Writes the contents of the client input stream to its output stream	
					client.getOutputStream().write((byte)client.getInputStream().read());
      					}
					
					catch (SocketException se){
						client.close();
						wait = false;
					}
				}
				}	
			

    		// *Very* minimal error handling.
    		} catch (IOException ioe) {
     	 		System.out.println("We caught an unexpected exception");
      			System.err.println(ioe);
    		}
  	}
}
