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

        // Check if the public key is reconstructed properly
        assertEquals(localPublicKey, reconstructedPublicKey);

        String originalMessage = "Hello World";
        byte[] encryptedMessage = Cryptography.encryptWithRSA(originalMessage.getBytes(), localPublicKey);
        byte[] decryptedMessage = Cryptography.decryptWithRSA(encryptedMessage, localPrivateKey);
        String reconstructedMessage = new String(decryptedMessage);

        assertEquals(originalMessage, reconstructedMessage);
    }

    @Test
    public void testAESEncryptionAndDecryption() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKey symmetricKey = Cryptography.generateSymmetricKey();

        // First convert symmetric key to bytes
        byte[] symmetricKeyAsBytes = symmetricKey.getEncoded();

        // Then convert it back into a symmetric key
        SecretKey reconstructedKey = Cryptography.reconstructSymmetricKey(symmetricKeyAsBytes);

        // Check if the secret key is reconstructed properly
        assertEquals(symmetricKey, reconstructedKey);

        // Check if encrypting and decrypting a string will return the same string
        String originalMessage = "Hello World";
        byte[] encryptedMessage = Cryptography.encryptWithAES(originalMessage.getBytes(), symmetricKey);
        byte[] decryptedMessage = Cryptography.decryptWithAES(encryptedMessage, symmetricKey);
        String reconstructedMessage = new String(decryptedMessage);

        assertEquals(originalMessage, reconstructedMessage);
    }
}