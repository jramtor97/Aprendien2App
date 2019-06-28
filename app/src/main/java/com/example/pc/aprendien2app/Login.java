package com.example.pc.aprendien2app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.aprendien2app.FingerPrint.FingerprintLogic;

public class Login extends BasicActivity {

    Intent mainActivity;
    Intent signActivity;
    SharedPreferences mPreferences;
    SharedPreferences.Editor editor;
    FingerprintLogic fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPreferences();

        if(isFirstTime() == false && isKeepLogin() == true) {
            stopTTS();
            mainActivity = new Intent(this,MainActivity.class);
            startActivity(mainActivity);
            finish();
        }

        else {

            setContentView(R.layout.activity_login);
            setFirstTime(false);

            ImageView img = (ImageView)findViewById(R.id.icon_finger_print);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(v.getContext(),LoginFinger.class);
                    startActivity(in);
                    finish();
                }
            });
            Button login = (Button) findViewById(R.id.login);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopTTS();
                    mainActivity = new Intent(v.getContext(), MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
            });

            CheckBox remember = (CheckBox) findViewById(R.id.rememberLogin);
            remember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean val = mPreferences.getBoolean("KeeepLogin", false);
                    setKeepLogin(!val);

                }
            });

            final TextView signup = (TextView)findViewById(R.id.signup);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signActivity = new Intent(v.getContext(),SignUp.class);
                    startActivity(signActivity);
                    finish();
                }
            });
        }

    }

    @Override
    public String getActivityText() {
        return getString(R.string.welcome) + "\n"  + getString(R.string.prompt_user) + "\n" + getString(R.string.prompt_password) + "\n" +
                getString(R.string.login) + "\n" + getString(R.string.remember) + "\n" + getString(R.string.sign_up_now);
    }

    public void initPreferences() {
        mPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        editor = mPreferences.edit();
    }

    public boolean isFirstTime() {
        return mPreferences.getBoolean("FirstExec", true);
    }

    public void setFirstTime(boolean val) {
        editor.putBoolean("FirstExec", val);
        editor.commit();
    }

    public boolean isKeepLogin() {
        return mPreferences.getBoolean("KeepLogin", false);
    }

    public void setKeepLogin(boolean val) {
        editor.putBoolean("KeepLogin", val);
        editor.commit();
    }

}
