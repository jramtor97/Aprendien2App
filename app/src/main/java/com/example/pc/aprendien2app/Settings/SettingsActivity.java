package com.example.pc.aprendien2app.Settings;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;

import com.example.pc.aprendien2app.BasicActivity;
import com.example.pc.aprendien2app.MainActivity;
import com.example.pc.aprendien2app.R;


public class SettingsActivity extends BasicActivity{

    private ContentResolver contentResolver;
    SpeechRecognizer speech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

    @Override
    public String getActivityText() {
        String x = getString(R.string.general) + "\n" +  getString(R.string.Textsize) + "\n" + getString(R.string.testSizeDescription) + "\n" + getString(R.string.Style) + "\n" +
                getString(R.string.language) + "\n" + "Brightness" + "\n" + getString(R.string.voice_sound) + "\n" + getString(R.string.voice_recognition) +
                "\n" + getString(R.string.vibration) + "\n" + getString(R.string.text_speech);

        return x;
    }

    @Override
    public void onBackPressed() {
        Intent v = new Intent(this, MainActivity.class);
        startActivity(v);
        finish();
    }


}
