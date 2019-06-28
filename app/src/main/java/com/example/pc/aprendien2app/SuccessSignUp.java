package com.example.pc.aprendien2app;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessSignUp extends AppCompatActivity {

    private ViewPager viewPager;
    private  Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_sign_up);

        Button config = (Button)findViewById(R.id.buttonBasicSetting);
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(),ConfigAssistant1.class);
                startActivity(intent);
            }
        });

    }
}
