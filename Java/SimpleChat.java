import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/*
 * Create a peer-to-peer chat. In this SimpleChat model, we assume one peer knows the (local) address of another
 * peer. The peer that knows should establish a connection. Use semaphores for preventing simultaneous read and write
 * by same process.
 * 
 * 
 */

public class SimpleChat implements Runnable {
    public static void main(String[] args) throws Exception {
        // do not need to use processes but threads to chat with different clients

        if (args[1].equals("Knows")) {
            
        } else {

        }

        /*
        while(true) {
            String broadc
            Thread.sleep(1);
        }
        */
    }

    public void run() {
        System.out.println("Child thread");
    }
}
