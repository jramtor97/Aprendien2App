package com.example.pc.aprendien2app.Settings;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

import com.example.pc.aprendien2app.R;

import java.util.Locale;

import static java.lang.Integer.parseInt;

/**
 * Created by Pc on 14/11/2017.
 */

@TargetApi(23)
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ContentResolver contentResolver;

    private boolean success = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);

        getPremissions();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Context context = getActivity().getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SettingsActivity myAct = (SettingsActivity) getActivity();


        switch (key) {
            case "language_preference":
                setLanguage();
                myAct.recreate();
                break;

            case "style_preference":
                myAct.finish();
                Intent DC  = new Intent(context,SettingsActivity.class);
                startActivity(DC);

                break;

            case "Brightness":
                int l = preferences.getInt("Brightness",0);
                setBrightness(l);
                break;

            case "text_size_preference":
                myAct.finish();
                Intent aux  = new Intent(context,SettingsActivity.class);
                startActivity(aux);

                break;
        }

    }

    public void setBrightness(int value) {

        contentResolver = getActivity().getApplication().getContentResolver();
        Settings.System.putInt(contentResolver,Settings.System.SCREEN_BRIGHTNESS,value);
    }

    public void getPremissions() {
        boolean value;

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            value = Settings.System.canWrite(this.getActivity().getApplicationContext());

            if(value) {
                success = true;
            }

            else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getActivity().getApplicationContext().getPackageName()));
                startActivityForResult(intent,1000);
            }

       }
    }


    @TargetApi(23)
    public void setLanguage(){

        Context context = getActivity().getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String lang = preferences.getString("language_preference","en");

        final Resources res = context.getResources();
        final Configuration config = res.getConfiguration();
        config.setLocale(new Locale(lang));
        context.createConfigurationContext(config);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000) {

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
                boolean value = Settings.System.canWrite(this.getActivity().getApplicationContext());

                if(value) {
                    success = true;
                }

                else {
                    Toast.makeText(getActivity(),"Permission not granted", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
}