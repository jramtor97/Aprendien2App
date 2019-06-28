package com.example.pc.aprendien2app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pc.aprendien2app.FingerPrint.Fingerprint;
import com.example.pc.aprendien2app.FingerPrint.FingerprintLogic;

public class LoginFinger extends BasicActivity implements Fingerprint{

    private FingerprintLogic fp;
    private boolean enable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_finger);
        initFingerprint();
        initToolbar();

        final Button btnOk = (Button)findViewById(R.id.right_fingerprint);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(enable) {
                    Intent n = new Intent(v.getContext(),MainActivity.class);
                    startActivity(n);
                    finish();
                }
            }
        });

        final Button btnCancel = (Button)findViewById(R.id.cancel_fingerprint);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(v.getContext(),Login.class);
                startActivity(n);
                finish();
            }
        });
    }

    @Override
    public String getActivityText() {
        return getString(R.string.cancel) + "\n" + getString(R.string.ok);
    }

    public void initFingerprint() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            fp = new FingerprintLogic(this);

        }
        else {
            Toast.makeText(this,
                    R.string.not_compatible_finger,
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void verifySignUp() {
       enable=true;
    }


    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_finger);
        setSupportActionBar(toolbar);
    }
}
