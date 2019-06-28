package com.example.pc.aprendien2app.FingerPrint;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.pc.aprendien2app.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by Pc on 18/10/2017.
 */


@TargetApi(23)
public class FingerprintLogic {

    private static final String KEY_NAME = "example_key";
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private  Context context;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;


    public FingerprintLogic(Context context) {
        this.context = context;

            keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager)context.getSystemService(FINGERPRINT_SERVICE);

            if (!keyguardManager.isKeyguardSecure()) {

                Toast.makeText(context,
                        R.string.lock_screen,
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.USE_FINGERPRINT) !=
                    PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context,
                        R.string.finger_not_permission,
                        Toast.LENGTH_LONG).show();

                return;
            }

            if (!fingerprintManager.hasEnrolledFingerprints()) {

                // This happens when no fingerprints are registered.
                Toast.makeText(context,
                        R.string.include_fingerprint,
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.USE_FINGERPRINT) !=
                    PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context,
                        R.string.finger_auth_disable,
                        Toast.LENGTH_LONG).show();

                return;
            }

            generateKey();

            if (cipherInit()) {
                cryptoObject = new FingerprintManager.CryptoObject(cipher);
                FingerprintHandler helper = new FingerprintHandler(context);
                helper.startAuth(fingerprintManager, cryptoObject);
            }

    }

    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}
