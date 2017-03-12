package br.com.soulskyye.tecnonutri;

import android.app.Application;
import com.crashlytics.android.Crashlytics;

import br.com.soulskyye.tecnonutri.model.Item;
import io.fabric.sdk.android.Fabric;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class TecnonutriApplication extends Application {

    private static TecnonutriApplication sInstance;

//    SharedPreferences mPref;

    public static TecnonutriApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sInstance = this;

//        sInstance.initializeInstance();
//
//        onAppCreated();
    }

//    private void initializeInstance() {
//
//        // Do your application wise initialization task
//        screenConfiguration();
//
//        // set application wise preference
//        mPref = this.getApplicationContext().getSharedPreferences("pref_key", MODE_PRIVATE);
//    }
//
//    // particularly applicable in library projects
//    public abstract void onAppCreated();
//
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    public void screenConfiguration() {
//        Configuration config = getResources().getConfiguration();
//        boolean isTab = (config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
//
//
//        Point size = new Point();
//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//
//        int deviceScreenWidth;
//        int deviceScreenHeight;
//
//        try {
//            display.getSize(size);
//            deviceScreenWidth = size.x;
//            deviceScreenHeight = size.y;
//        } catch (NoSuchMethodError e) {
//            deviceScreenWidth = display.getWidth();
//            deviceScreenHeight = display.getHeight();
//        }
//    }
//
//    public boolean isFirstRun() {
//        // return true if the app is running for the first time
//        return mPref.getBoolean("is_first_run", true);
//    }
//
//    public void setRunned() {
//        // after a successful run, call this method to set first run false
//        SharedPreferences.Editor edit = mPref.edit();
//        edit.putBoolean("is_first_run", false);
//        edit.commit();
//    }
//
//    @Override
//    public void onTerminate() {
//        // Do your application wise Termination task
//        super.onTerminate();
//    }
}