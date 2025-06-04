package kevin.comm.server;

import kevin.comm.crypto.Cryptography;

import javax.crypto.SecretKey;
import java.io.*;
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
            DataOutputStream writeToSocket = new DataOutputStream(socket.getOutputStream());
            DataInputStream readFromSocket = new DataInputStream(socket.getInputStream());
            BufferedReader readFromStdInput = new BufferedReader(new InputStreamReader(System.in));

            // Send the public key over to the client
            byte[] serverPublicKeyEncoded = serverPublicKey.getEncoded();
            writeToSocket.writeInt(serverPublicKeyEncoded.length);
            writeToSocket.write(serverPublicKeyEncoded);

            // Receive symmetric key back
            int symmetricKeyLength = readFromSocket.readInt();
            byte[] encodedSymmetricKey = new byte[symmetricKeyLength];
            readFromSocket.readFully(encodedSymmetricKey, 0, symmetricKeyLength);

            // Decrypt symmetric key
            byte[] symmetricKeyAsBytes = Cryptography.decryptWithRSA(encodedSymmetricKey, serverPrivateKey);
            SecretKey symmetricKey = Cryptography.reconstructSymmetricKey(symmetricKeyAsBytes);

            // For now, read input from Client
            int inputLength = readFromSocket.readInt();
            byte[] encodedMessage = new byte[inputLength];
            readFromSocket.readFully(encodedMessage, 0, inputLength);
            String message = new String(Cryptography.decryptWithAES(encodedMessage, symmetricKey));
            System.out.println(message);

            // Close all input streams
            writeToSocket.close();
            readFromSocket.close();
            readFromStdInput.close();
        } catch (Exception e) {
            System.err.printf("Error in thread with port %d: Failed to get input and output\n", socket.getPort());
        }
    }
}
