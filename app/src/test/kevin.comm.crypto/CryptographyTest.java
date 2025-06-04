package kevin.comm.crypto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.*;

class CryptographyTest {

    @Test
    public void testRSAEncryptionAndDecryption() throws Exception {
        KeyPair localKeyPair = Cryptography.generateKeyPair();
        PublicKey localPublicKey = localKeyPair.getPublic();
        PrivateKey localPrivateKey = localKeyPair.getPrivate();

        // First convert public key to bytes
        byte[] localPublicKeyAsBytes = localPublicKey.getEncoded();

        // Then convert the bytes back into a public key
        PublicKey reconstructedPublicKey = Cryptography.reconstructRSAPublicKey(localPublicKeyAsBytes);

        // Check if the private key is reconstructed properly
        assertEquals(localPublicKey, reconstructedPublicKey);

        String originalMessage = "Hello World";
        byte[] encryptedMessage = Cryptography.encryptWithRSA(originalMessage.getBytes(), reconstructedPublicKey);
        byte[] decryptedMessage = Cryptography.decryptWithRSA(encryptedMessage, localPrivateKey);
        String reconstructedMessage = new String(decryptedMessage);

        assertEquals(originalMessage, reconstructedMessage);
    }

    @Test
    public void testAESEncryptionAndDecryption() {

    }
}