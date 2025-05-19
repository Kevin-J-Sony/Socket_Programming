package kevin.test;
import java.net.*;
import java.io.*;

/*
 * Create a peer-to-peer chat. In this SimpleChat model, we assume one peer knows the (local) address of another
 * peer. The peer that knows should establish a connection. We assume only one communication channel between different chats.
 */

public class SimpleChat {
	
	public static void main(String[] args) throws Exception { 
		Socket peer;
		BufferedReader inputFromPeerSocket;
		PrintWriter outputToSocket;
		BufferedReader inputFromTerminal;

		/*
		 * In the case where there is an argument is supplied, there should be only two arguments supplied: the address of the other chat and the port to connect to
		 * If no arguments are supplied, listen for incoming requests. For now, hard-code the port to listen to at 5001
		 * 
		 */
		if (args.length == 0) {
			ServerSocket serverSocket = new ServerSocket(5001);
			peer = serverSocket.accept();
			serverSocket.close();
			
			inputFromPeerSocket = new BufferedReader(new InputStreamReader(peer.getInputStream()));
			outputToSocket = new PrintWriter(peer.getOutputStream(), true);
			inputFromTerminal = new BufferedReader(new InputStreamReader(System.in));
			
		} else {
			peer = new Socket(args[0], Integer.parseInt(args[1]));

			inputFromPeerSocket = new BufferedReader(new InputStreamReader(peer.getInputStream()));
			outputToSocket = new PrintWriter(peer.getOutputStream(), true);
			inputFromTerminal = new BufferedReader(new InputStreamReader(System.in));

			outputToSocket.println("Connecting...");
		}


		String socketInput = "";
		String userInput = "";
		
		do {
			// read from the peer
			socketInput = inputFromPeerSocket.readLine();
			System.out.printf("(%s) > %s\n", peer.getInetAddress().toString(), socketInput);

			// read from the terminal and output to peer socket
			userInput = inputFromTerminal.readLine();
			outputToSocket.println(userInput);

		} while (!userInput.toLowerCase().equals("bye") && !socketInput.toLowerCase().equals("bye")); 


		peer.close();

	}
}
