package mobile.skripsi.pawsandclaws.helper;

import android.content.Context;
import android.content.SharedPreferences;

import mobile.skripsi.pawsandclaws.model.User;

/**
 * Shared Preferences Manager
 * Created by @lukmanadelt on 05/11/2017.
 */

public class SharedPreferencesManager {
    private static SharedPreferencesManager mInstance;
    private static Context mContext;

    private static final String SHARED_PREFERENCE_NAME = "paws_and_claws";

    private static final String KEY_ID = "key_id";
    private static final String KEY_ROLE_ID = "key_role_id";
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_FULLNAME = "key_fullname";

    private SharedPreferencesManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPreferencesManager(context);

        return mInstance;
    }

    public boolean login(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putInt(KEY_ROLE_ID, user.getRoleId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_FULLNAME, user.getFullname());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_USERNAME, null) != null)
            return true;

        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, 0),
                sharedPreferences.getInt(KEY_ROLE_ID, 0),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_FULLNAME, null)
        );
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}