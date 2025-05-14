package kevin.test;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/*
 * Create a peer-to-peer chat. In this SimpleChat model, we assume one peer knows the (local) address of another
 * peer. The peer that knows should establish a connection. Use semaphores for preventing simultaneous read and write
 * by same process. We assume only one communication channel between different chats.
 * 
 */

public class SimpleChat implements Runnable {

	private int mode;

	/*
	 * If mode is 0 (meaning no arguments passed), then this instance will not be making the connection.
	 * Otherwise, it will be making the connection.
	 *
	 */	
	public SimpleChat(int mode) {
		this.mode = mode;
	}
	

	public static void main(String[] args) throws Exception {
		SimpleChat chat = new SimpleChat(args.length);
		
		if (args.length == 0) {
			chat.run();
		} else {
			semaphore 
		}

		/*
		while(true) {
			String broadc
			Thread.sleep(1);
		}
		*/

	}

	public void run() {
		/*
		 * This listens for the connection
		 *
		 */
		System.out.println("Main Child Thread");
		

		Socket s = new Socket();

	}
}
