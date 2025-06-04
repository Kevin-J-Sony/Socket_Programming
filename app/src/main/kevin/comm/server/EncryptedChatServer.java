package kevin.comm.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.*;
import java.security.KeyPair;

import kevin.comm.crypto.Cryptography;
import kevin.comm.server.EncryptedServerThread;

public class EncryptedChatServer {
    public static void main(String[] args) throws Exception {
        // Generate Server Public and Private Keys
        System.out.println("--- Server Main Thread: Key Generation ---");
        KeyPair serverKey = Cryptography.generateKeyPair();
        PublicKey serverPublicKey = serverKey.getPublic();
        PrivateKey serverPrivateKey = serverKey.getPrivate();

        // Wait for Clients to Connect
        // Once 10 connections are made, end the server
        int portNumber = 5000;
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
}
