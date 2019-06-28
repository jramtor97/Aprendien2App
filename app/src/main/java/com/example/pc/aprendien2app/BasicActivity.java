package com.example.pc.aprendien2app;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.pc.aprendien2app.Settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Locale;


public abstract class BasicActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private TextToSpeech tts;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final String STYLE = "style_preference";
    private String textToSpeech = "Hola";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getStyle());
        checkTextToSpeechPermission();
    }

    public int getStyle() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String style = preferences.getString("style_preference", "1");

        String size = preferences.getString("text_size_preference", "1");

        int c = 4;

        switch (style) {
            case "1":

                switch (size) {
                    case "1":
                        c = R.style.Deuteranopia_Small;
                        break;

                    case "2":
                        c = R.style.Deuteranopia_Medium;
                        break;

                    case "3":
                        c = R.style.Deuteranopia_Large;
                        break;
                }
                break;

            case "2":

                switch (size) {
                    case "1":
                        c = R.style.Tritanopia_Small;
                        break;

                    case "2":
                        c = R.style.Tritanopia_Medium;
                        break;

                    case "3":
                        c = R.style.Tritanopia_Large;
                        break;
                }
                break;

            case "3":
                switch (size) {
                    case "1":
                        c = R.style.Protanopia_Small;
                        break;

                    case "2":
                        c = R.style.Protanopia_Medium;
                        break;

                    case "3":
                        c = R.style.Protanopia_Large;
                        break;
                }

                break;

            case "4":
                switch (size) {
                    case "1":
                        c = R.style.UniversalStyle_Small;
                        break;

                    case "2":
                        c = R.style.UniversalStyle_Medium;
                        break;

                    case "3":
                        c = R.style.UniversalStyle_Large;
                        break;
                }
                break;
        }

        return c;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String lang = preferences.getString("language_preference", "English");

        switch (lang) {
            case "Spanish":
                lang = "es";
                break;

            case "English":
                lang = "en";
                break;

            case "French":
                lang = "fr";
                break;
        }

        super.attachBaseContext(ConfigurationWrapper.wrapLocale(newBase, new Locale(lang)));
    }


    /**
     * Showing google speech input dialog
     */
    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Voice recognition incompatible",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    commands(result.get(0));
                    Log.d(result.get(0), "VALUES");
                }
                break;
            }

        }
    }

    public void commands(String command) {

        if (command.toLowerCase().contains(getString(R.string.add).toLowerCase())) {
            Intent c = new Intent(this, AddAdvert.class);
            Log.d("", "Enter");
            startActivity(c);
        } else if (command.toLowerCase().contains(getString(R.string.goSettings).toLowerCase())) {
            Intent c = new Intent(this, SettingsActivity.class);
            startActivity(c);
            Log.d("", "Enter");
        }

        else if (command.toLowerCase().contains(getString(R.string.receive_item).toLowerCase())) {
            Intent c = new Intent(this, ReadQR.class);
            startActivity(c);
            Log.d("", "Enter");
        }
    }


    //Text to speech
    @Override
    public void onInit(int status) {

        if(status == TextToSpeech.SUCCESS) {
            int result =  tts.setLanguage(Locale.getDefault());

            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                Log.d("TTS","Lenguage no soportado");
            }
            else  {
                speakOut();
            }
        }
        else {
            Log.d("TTS","Fallo de inicialiacion");
        }
    }

    @TargetApi(21)
    private void speakOut() {
        tts.speak(textToSpeech,TextToSpeech.QUEUE_FLUSH,null);
    }

    public void initTTS() {
        tts = new TextToSpeech(this,this);
        tts.setSpeechRate(((float)0.75));
        speakOut();
    }

    public void stopTTS() {
        if(tts != null) {
            tts.stop();
        }
    }

    public void setTextToSpeech(String val) {
        textToSpeech = val;
        initTTS();
    }

    public abstract String getActivityText();

    public void checkTextToSpeechPermission() {
        SharedPreferences c = PreferenceManager.getDefaultSharedPreferences(this);
        boolean status = c.getBoolean("text_speech",false);

        if(status) {
            String x = getActivityText();
            setTextToSpeech(x);
        }
    }


}
