package com.example.pc.aprendien2app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * Created by Pc on 20/11/2017.
 */
@TargetApi(17)
public class ConfigurationWrapper {

    //Creates a Context with updated Configuration.
    public static Context wrapConfiguration(@NonNull final Context context,
                                            @NonNull final Configuration config) {
        return context.createConfigurationContext(config);
    }

    // Creates a Context with updated Locale.
    public static Context wrapLocale(@NonNull final Context context,
                                     @NonNull final Locale locale) {
        final Resources res = context.getResources();
        final Configuration config = res.getConfiguration();
        config.setLocale(locale);
        return wrapConfiguration(context, config);
    }
}
