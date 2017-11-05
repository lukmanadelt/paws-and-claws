package mobile.skripsi.pawsandclaws;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Login Activity
 * Created by @lukmanadelt on 05/11/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    private String user_id, role_id, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Splash Screen
        setTheme(R.style.AppTheme);

        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initial Component
        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button bLogin = findViewById(R.id.bLogin);

        // Initial Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        // Initial Session Manager
        sessionManager = new SessionManager(getApplicationContext());

        // Check logged in status
        if (sessionManager.isLoggedIn()) {
            // Conditions for logged in users
            HashMap<String, String> loginInfo = sessionManager.getLoginInfo();
            role_id = loginInfo.get(SessionManager.KEY_ROLE_ID);
            redirectActivity(role_id);
        }

        // Login button click event
        bLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                // Check EditText value
                if (!username.isEmpty() && !password.isEmpty()) {
                    verifyLogin(username, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Nama Pengguna dan Kata Sandi wajib diisi", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        });
    }

    /**
     * Method to verify login in database
     */
    private void verifyLogin(final String username, final String password) {
        // Tag used to cancel the request
        String request_to_login = "request_to_login";

        progressDialog.setMessage("Masuk ...");
        dialog();

        StringRequest request = new StringRequest(Request.Method.POST,
                APIManager.LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response : " + response);
                dialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean success = jObj.getBoolean("success");
                    String message = jObj.getString("message");

                    Toast.makeText(getApplicationContext(),
                            message, Toast.LENGTH_SHORT).show();

                    // Check login status
                    if (success) {
                        JSONObject users = jObj.getJSONObject("users");
                        user_id = users.getString("user_id");
                        role_id = users.getString("role_id");

                        sessionManager.setLoginInfo(user_id, role_id);
                        redirectActivity(role_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error : " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Anda gagal masuk", Toast.LENGTH_SHORT).show();
                dialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to API
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };

        // Adding request to request queue
        RequestManager.getInstance().addToRequestQueue(request, request_to_login);
    }

    /**
     * Method to redirect to another activity based on user role
     */
    private void redirectActivity(String role_id) {
        switch (role_id) {
            case "1":
                // Role as Administrator
//                Intent intent = new Intent(LoginActivity.this, AdministratorActivity.class);
                break;
            case "2":
                // Role as Customer
//                Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                break;
            case "3":
                // Role as Doctor
//                Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                break;
        }

//            startActivity(intent);
        finish();
    }

    /**
     * Method to show and dismiss progress dialog
     */
    private void dialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }
}
