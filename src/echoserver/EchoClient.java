package echoserver;
import java.net.*;
import java.io.*;
import java.util.*;

public class EchoClient {
	public static final int portNumber = 6013;

	public static void main(String[] args) throws IOException {
		String hostname;
		if(args.length == 0) {
			hostname = "127.0.0.1";
		} else {
			hostname = args[0];
		}
		Socket socker = new Socket(hostname, portNumber);
		try {Thread outThread = new Thread(new Runnable() {
                                 @Override
                                 public void run(){
                                    try{
                                         InputStream serverInput = socker.getInputStream();
                                         int character;
                                         while((character = serverInput.read()) != -1){
                                                 System.out.write((char)character);
                                         }
 
                                    } catch (IOException ce){
                                         System.out.println("Exception: " + ce);
                                    }
                                 }
                         });
			// Create a one way connection to the server
			// Read the standard input stream
                        // What the user is inputting
                        // Reads a single char and returns the ASCII table value of that char
                        // Returns -1 if no character has been read(end i think)
			outThread.start();
			Thread inThread = new Thread(new Runnable() {
                                @Override
                                 public void run(){
                                 try {
                                         int character;
                                         OutputStream output = socker.getOutputStream();
                                         while((character = System.in.read()) != -1){
                                                 output.write((byte)character);
                                                 output.flush();
                                         }
                                         
                                     } catch (IOException ce) {
                                         System.out.println("We were unable to connect to " + hostname);
                                         System.out.println("You should make sure the server is running.");
                                     }
                                 }
 
                         });
			inThread.start();
			inThread.join();
			socker.shutdownOutput();
			outThread.join();
			System.out.flush();
			// Close the socket when we're done reading from it
			socker.shutdownInput();
			socker.close();

			// Provide some minimal error handling.
    		} catch (ConnectException ioe) {
      			System.out.println("We caught an unexpected exception");
      			System.err.println(ioe);
    		} catch (InterruptedException ie) {
			System.out.println("Thread was interrupted");
		}
				
	}
}
