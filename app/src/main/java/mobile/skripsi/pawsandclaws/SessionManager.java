package mobile.skripsi.pawsandclaws;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Session Manager
 * Created by @lukmanadelt on 05/11/2017.
 */

public class SessionManager {
    // LogCat Tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences sharedPreferences;

    private Editor editor;
    private Context _context;

    // Shared preferences file name
    private static final String PREF_NAME= "PawsAndClaws";
    private static final String KEY_LOGIN = "isLoggedIn";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_ROLE_ID = "role_id";

    public SessionManager(Context context) {
        // Shared preferences mode
        int PRIVATE_MODE = 0;

        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLoginInfo(String user_id, String role_id) {
        // Set key value
        editor.putBoolean(KEY_LOGIN, true);
        editor.putString(KEY_USER_ID, user_id);
        editor.putString(KEY_ROLE_ID, role_id);

        // Commit changes
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGIN, false);
    }

    public HashMap<String, String> getLoginInfo() {
        HashMap<String, String> loginInfo = new HashMap<>();

        loginInfo.put(KEY_USER_ID, sharedPreferences.getString(KEY_USER_ID, null));
        loginInfo.put(KEY_ROLE_ID, sharedPreferences.getString(KEY_ROLE_ID, null));

        return loginInfo;
    }

    public void logout() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new flag to start new activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Activity
        _context.startActivity(i);
    }
}