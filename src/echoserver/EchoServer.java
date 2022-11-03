package echoserver;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat; 
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer{
	public static final int portNumber = 6013;

	public static void main(String[] args) throws SocketException, IOException, InterruptedException {
    		try {
      			// Start listening on the specified port
      			ServerSocket sock = new ServerSocket(portNumber);
			int THREAD_MAX = 10;
			ExecutorService pool = Executors.newFixedThreadPool(THREAD_MAX);
			Thread[] listThreads = new Thread[10];

      			// Run forever, which is common for server style services
      			while (true) {
        			// Wait until someone connects
	        		Socket clientAccepted = sock.accept(); // Waits until a client connects
        			System.out.println("Got a request!");	
					
				// Writes the contents of the client input stream to its output stream	
				pool.execute(new client(clientAccepted));
      			

			
				
			}
		} catch(Exception e) {
			System.err.println("Exception: " + e);
		} 
	}

	public static class client implements Runnable {
		Socket clientSock;
		OutputStream output;
		InputStream input;
		int num;

		public client(Socket inClient) {
			try{
				this.clientSock = inClient;
				output = clientSock.getOutputStream();
				input = clientSock.getInputStream();
			} catch (Exception e) {
				System.err.println("Exception: " + e);
			}
		}

		@Override
		public void run () {
			try {
				while((num = input.read()) != -1) {
					output.write((byte)num);
					output.flush();
				}
				clientSock.close();
				System.out.println("Client Disconnected");
			} catch (SocketException e) {
				System.err.println("Client dc'd: " + e);
			}
			catch (Exception ae){
				System.err.println("Exception: " + ae);
			}	
		}
	}
}
