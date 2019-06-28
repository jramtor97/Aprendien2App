package com.example.pc.aprendien2app;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.aprendien2app.GpsImplementation.GPSAdapter;
import com.example.pc.aprendien2app.GpsImplementation.UsabilityGPS;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A login screen that offers login via email/password.
 */
public class SignUp extends BasicActivity implements UsabilityGPS {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordView2;

    //GPS
    GPSAdapter myGPS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.signupEmail);
        mPasswordView = (EditText) findViewById(R.id.signupPass);
        mPasswordView2 = (EditText) findViewById(R.id.signupPassAgain);

        myGPS = new GPSAdapter(this,this);

        if(myGPS.getStatus()) {
            Toast.makeText(this, "Turn on GPS", Toast.LENGTH_LONG).show();
        }


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!attemptLogin()) {
                    Intent x = new Intent(view.getContext(), SuccessSignUp.class);
                    finish();
                    startActivity(x);
                }
            }
        });



    }

    @Override
    public String getActivityText() {
        return R.string.start + " "  + R.string.new_exchanges + " " + R.string.news + " " + R.string.Events;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptLogin() {

        boolean status = false;

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            status = true;
        }

        if (!password.equals(password2)) {
            mPasswordView2.setError(getString(R.string.passDifferent));
            status = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            status = true;

        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            status = true;
        }

        return status;

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    @Override
    public void useGPS(Location loc) {
        getLocation(loc);
    }

    public void getLocation(Location loc) {
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
        }
        catch (IOException e) {
            Log.v("","Error on getLocation");
        }

        if (addresses.size() > 0) {
            String city = addresses.get(0).getAddressLine(0);
            Log.v("Location",addresses.get(0).toString());
            EditText c = (EditText)findViewById(R.id.gpsLat);
            c.setText(city + "");
        }

    }
}

