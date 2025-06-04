package app.src.main.kevin.comm.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.*;
import java.security.KeyPair;


public class EncryptedServer {
    public static void main(String[] args) throws Exception {
        // Generate Server Public and Private Keys
        System.out.println("--- Server Key Generation ---");
        KeyPair serverKey = generateKeyPair();
        PublicKey serverPublicKey = serverKey.getPublic();
        PrivateKey serverPrivateKey = serverKey.getPrivate();

        // Wait for Clients to Connect
        // After 10 connections are made, disconnect
        int portNumber = 5001;
        while (portNumber < 5010) {
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                EncryptedServerThread est = new EncryptedServerThread(serverSocket.accept(), serverKey);
                Thread newClientConnection = new Thread(est);
                newClientConnection.start();
                portNumber++;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Generate an RSA public and private key pair.
     * @return A KeyPair object
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        return kpg.generateKeyPair();
    }

}
