package com.example.pc.aprendien2app.FingerPrint;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.pc.aprendien2app.LoginFinger;
import com.example.pc.aprendien2app.R;

/**
 * Created by Pc on 18/10/2017.
 */
@TargetApi(23)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context appContext;
    private Fingerprint sending;

    public FingerprintHandler(Context context) {
        appContext = context;
    }

    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        Toast.makeText(appContext,
                appContext.getString(R.string.auth_error) + errString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(appContext,
                "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext,
                R.string.auth_failed,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(appContext,
                R.string.auth_success,
                Toast.LENGTH_LONG).show();
        sending = new LoginFinger();
        sending.verifySignUp();
    }

}
