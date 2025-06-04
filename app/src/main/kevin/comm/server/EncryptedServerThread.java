package app.src.main.kevin.comm.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptedServerThread implements Runnable {
    private Socket socket = null;
    private KeyPair serverKey = null;
    private PublicKey serverPublicKey = null;
    private PrivateKey serverPrivateKey = null;

    public EncryptedServerThread(Socket socket, KeyPair serverKey) {
        this.socket = socket;
        this.serverKey = serverKey;
        this.serverPublicKey = this.serverKey.getPublic();
        this.serverPrivateKey = this.serverKey.getPrivate();
    }

    public void run() {
        try {
            // Get input/output stream of Client Socket
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // Send the public key over to the client
            byte[] serverPublicKeyEncoded = serverPublicKey.getEncoded();
            out.writeInt(serverPublicKeyEncoded.length);
            out.write(serverPublicKeyEncoded);

            // Receive symmetric key back
            int length = in.readInt();
            byte[] encodedSymmetricKey = new byte[length];
            in.readFully(encodedSymmetricKey, 0, length);


            // At this point, both sides have symmetric key, so communication can proceed
        } catch (Exception e) {
            System.err.printf("Error in thread with port %d: Failed to get input and output\n", socket.getPort());
        }
    }
}
