package it.lo.exp.weander;

import android.app.Application;
import android.preference.PreferenceManager;

import org.osmdroid.config.Configuration;

import java.io.File;

public class WeanderApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        File cacheDir = getExternalCacheDir();
        if (cacheDir != null) {
            Configuration.getInstance().setOsmdroidTileCache(new File(cacheDir, "osmdroid"));
        }
    }
}
