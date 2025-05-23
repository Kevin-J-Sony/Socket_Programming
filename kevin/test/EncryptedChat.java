package kevin.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EncryptedChat {
	public static void main(String[] args) throws Exception { 
		Socket peer = null;
		DataInputStream inputFromPeerSocket = null;
		DataOutputStream outputToSocket = null;
		DataInputStream inputFromTerminal = null;
		String name = "Anonymous";

		byte[] buffer = new byte[2048];

		/*
		 * In the case where there is an argument is supplied, there should be only two arguments supplied: the address of the other chat and the port to connect to
		 * If no arguments are supplied, listen for incoming requests. For now, hard-code the port to listen to at 5001
		 * 
		 */
		if (args.length == 0) {
			ServerSocket serverSocket = new ServerSocket(5001);
			peer = serverSocket.accept();
			serverSocket.close();
			
			inputFromPeerSocket = new DataInputStream(peer.getInputStream());
			outputToSocket = new DataOutputStream(peer.getOutputStream());
			inputFromTerminal = new DataInputStream(System.in);
			
		} else if (args.length == 2) {
			peer = new Socket(args[0], Integer.parseInt(args[1]));

			inputFromPeerSocket = new DataInputStream(peer.getInputStream());
			outputToSocket = new DataOutputStream(peer.getOutputStream());
			inputFromTerminal = new DataInputStream(System.in);

			outputToSocket.writeUTF("Connecting...\n");

		} else if (args.length == 3) {
			peer = new Socket(args[0], Integer.parseInt(args[1]));

			inputFromPeerSocket = new DataInputStream(peer.getInputStream());
			outputToSocket = new DataOutputStream(peer.getOutputStream());
			inputFromTerminal = new DataInputStream(System.in);

			outputToSocket.writeUTF("Connecting...\n");
			
		} else {
			System.out.println("Invalid number of arguments");
			System.exit(0);
		}


		String socketInput = "";
		String userInput = "";
		
		do {
			// read from the peer
			StringBuilder builder = new StringBuilder();
			char character = 0;
			while ((character = inputFromPeerSocket.readChar()) != -1) {
				if (character == '\n') break;
				builder.append(character);
			}
			socketInput = builder.toString();
			System.out.printf("(%s) > %s\n", peer.getInetAddress().toString(), socketInput);

			// read from the terminal and output to peer socket
			builder = new StringBuilder();
			while ((character = inputFromPeerSocket.readChar()) != -1) {
				if (character == '\n') break;
				builder.append(character);
			}
			userInput = builder.toString();
			outputToSocket.writeUTF(userInput);

		} while (!userInput.toLowerCase().equals("bye") && !socketInput.toLowerCase().equals("bye")); 


		inputFromPeerSocket.close();
		inputFromTerminal.close();
		outputToSocket.close();
		peer.close();
	}
	
}
