package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Profile Activity
 * Created by @lukmanadelt on 11/17/2017.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private EditText etPassword, etPasswordConfirmation;
    private Button bUpdatePassword;
    private int id;
    private String password, password_confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirmation = findViewById(R.id.etPasswordConfirmation);
        bUpdatePassword = findViewById(R.id.bUpdatePassword);

        // Set component to listen click event
        bUpdatePassword.setOnClickListener(this);

        // Initial user id from previous activity
        id = getIntent().getIntExtra("user_id", 0);
    }

    /**
     * Method to updating user profile
     */
    private void updateProfile(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengubah...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.updateProfile(id, password_confirmation);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess()) {
                    onBackPressed();
                    finish();
                }

                Snackbar.make(parentView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == bUpdatePassword) {
            password = etPassword.getText().toString().trim();
            password_confirmation = etPasswordConfirmation.getText().toString().trim();

            if (password.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_password, Snackbar.LENGTH_SHORT).show();
            } else if (password_confirmation.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_password_confirmation, Snackbar.LENGTH_SHORT).show();
            } else if (!password_confirmation.equals(password)) {
                Snackbar.make(parentView, R.string.comparison_password, Snackbar.LENGTH_SHORT).show();
            } else {
                updateProfile(id);
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdministratorActivity.class));
        finish();
    }
}

