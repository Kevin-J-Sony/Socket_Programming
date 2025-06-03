package app.src.main.kevin.test;

public class BroadcastChat implements Runnable {
	
	public static void main(String[] args) throws Exception {
		// do not need to use processes but threads to chat with different clients
		/*
		Chat listener = new Chat();
		listener.run();
		System.out.println("Parent thread");

		DatagramSocket socket = new DatagramSocket();
		socket.setBroadcast(true);

		String message = "Hello World!\n";
		byte[] buffer = message.getBytes();
		*/

		/*
		while(true) {
			String broadc
			Thread.sleep(1);
		}
		*/

		/*
		 * USE PIPEDINPUTSTREAM / PIPEDOUTPUTSTREAM FOR COMMUNICATION BETWEEN THREADS
		 */
	}

	public void run() {
		System.out.println("Child thread");
	}
}
