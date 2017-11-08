package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.SharedPreferencesManager;
import mobile.skripsi.pawsandclaws.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Login Activity
 * Created by @lukmanadelt on 05/11/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername, etPassword;
    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set Splash Screen
        setTheme(R.style.AppTheme);

        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // If user is already logged in open the activity based on user role
        if (SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            redirectActivity(SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getRoleId());
        }

        // Initial Component
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);

        // Set component to listen click event
        bLogin.setOnClickListener(this);
    }

    /**
     * Method to verify login in database
     */
    private void userLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Nama pengguna dan kata sandi wajib diisi", Toast.LENGTH_SHORT)
                    .show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Masuk...");
            progressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

            Call<Result> call = service.userLogin(username, password);

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    progressDialog.dismiss();

                    if (response.body().getSuccess()) {
                        finish();
                        SharedPreferencesManager.getInstance(getApplicationContext()).login(response.body().getUser());
                        redirectActivity(response.body().getUser().getRoleId());
                    }

                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Method to redirect to another activity based on user role
     */
    private void redirectActivity(int role_id) {
        switch (role_id) {
            case 1:
                // Role as Administrator
                Intent home = new Intent(getApplicationContext(), AdministratorActivity.class);
                startActivity(home);
                break;
            case 2:
                // Role as Customer
//                Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                break;
            case 3:
                // Role as Doctor
//                Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == bLogin) {
            userLogin();
        }
    }
}
