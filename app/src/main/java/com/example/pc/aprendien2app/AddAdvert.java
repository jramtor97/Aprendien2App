package com.example.pc.aprendien2app;

import android.content.Intent;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddAdvert extends BasicActivity {

    ImageButton upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advert);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar);

        upload = (ImageButton)findViewById(R.id.uploadAdvert);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(v.getContext(),MainActivity.class);

                Toast.makeText(v.getContext().getApplicationContext(), R.string.success_upload,Toast.LENGTH_SHORT).show();

                ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(400);

                startActivity(c);
                finish();
            }
        });
    }

    @Override
    public String getActivityText() {
        return getString(R.string.title) + "\n"  + getString(R.string.autor) + "\n" + getString(R.string.editorial) + "\n" + getString(R.string.a_o) + "\n" +
                getString(R.string.cancel) + "\n" + "upload";
    }
}
