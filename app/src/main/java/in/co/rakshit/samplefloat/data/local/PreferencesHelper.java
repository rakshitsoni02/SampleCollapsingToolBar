package in.co.rakshit.samplefloat.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.co.rakshit.samplefloat.data.model.Base;
import in.co.rakshit.samplefloat.injection.ApplicationContext;

@Singleton
public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "android_boilerplate_pref_file";
    public static final String DATA = "data";
    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public Base getBussiness() {
        String json = mPref.getString(DATA, null);
        Type type = new TypeToken<Base>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    public void setBussiness(Base list) {
        mPref.edit().putString(DATA, new Gson().toJson(list)).apply();
    }
}
