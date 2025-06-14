package kevin.comm.client;

import kevin.comm.crypto.Cryptography;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptedChatClient {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) System.exit(-1);
        System.out.println("--- Client: Connecting to Server ---");
        String address = args[0];
        int portNumber = Integer.parseInt(args[1]);
        Socket connect = new Socket(address, portNumber);
        System.out.println("--- Client: Connected to Server ---");

        DataOutputStream writeToSocket = new DataOutputStream(connect.getOutputStream());
        DataInputStream readFromSocket = new DataInputStream(connect.getInputStream());
        BufferedReader readFromStdInput = new BufferedReader(new InputStreamReader(System.in));

        // Once connected to server, read the encoded response from the server
        System.out.println("--- Read Server Public Key ---");
        int lengthOfPublicKey = readFromSocket.readInt();
        byte[] publicKeyEncoded = new byte[lengthOfPublicKey];
        readFromSocket.readFully(publicKeyEncoded, 0, lengthOfPublicKey);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyEncoded));

        // Generate a symmetric key and encrypt it using the server public key
        System.out.println("--- Generate Symmetric Key ---");
        SecretKey symmetricKey = Cryptography.generateSymmetricKey();
        byte[] encodedSymmetricKey = Cryptography.encryptWithRSA(symmetricKey.getEncoded(), publicKey);
        writeToSocket.writeInt(encodedSymmetricKey.length);
        writeToSocket.write(encodedSymmetricKey);

        // For now, send a simple Hello World
        byte[] message = "Hello World".getBytes();
        byte[] encryptedMessage = Cryptography.encryptWithAES(message, symmetricKey);
        writeToSocket.writeInt(encryptedMessage.length);
        writeToSocket.write(encryptedMessage);

        writeToSocket.close();
        readFromSocket.close();
        readFromStdInput.close();
        connect.close();
    }
}
