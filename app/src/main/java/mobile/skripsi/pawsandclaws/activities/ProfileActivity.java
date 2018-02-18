package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Profile Activity
 * Created by @lukmanadelt on 11/17/2017.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private EditText etUsername, etFullname, etPhone, etAddress, etPassword, etPasswordConfirmation;
    private Button bUpdate;
    private int id, role_id;
    private String username, fullname, phone, address, password_confirmation;
    private Call<Result> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvFullname = findViewById(R.id.tvFullname);
        TextView tvPhone = findViewById(R.id.tvPhone);
        TextView tvAddress = findViewById(R.id.tvAddress);
        etUsername = findViewById(R.id.etUsername);
        etFullname = findViewById(R.id.etFullname);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirmation = findViewById(R.id.etPasswordConfirmation);
        bUpdate = findViewById(R.id.bUpdate);

        // Set component to listen click event
        bUpdate.setOnClickListener(this);

        // Initial user id and role id
        id = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getId();
        role_id = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getRoleId();

        switch (role_id) {
            case 2:
                tvUsername.setVisibility(View.VISIBLE);
                tvFullname.setVisibility(View.VISIBLE);
                tvPhone.setVisibility(View.VISIBLE);
                tvAddress.setVisibility(View.VISIBLE);
                etUsername.setVisibility(View.VISIBLE);
                etFullname.setVisibility(View.VISIBLE);
                etPhone.setVisibility(View.VISIBLE);
                etAddress.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvUsername.setVisibility(View.VISIBLE);
                tvFullname.setVisibility(View.VISIBLE);
                etUsername.setVisibility(View.VISIBLE);
                etFullname.setVisibility(View.VISIBLE);
                break;
        }

        // Getting a user
        if ((role_id == 2) || role_id == 3) getUser(id);
    }

    /**
     * Method to getting a user
     */
    private void getUser(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        switch (role_id) {
            case 2:
                call = service.getCustomer(id);
                break;
            case 3:
                call = service.getDoctor(id);
                break;
        }

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                switch (role_id) {
                    case 2:
                        etUsername.setText(response.body().getCustomer().getUsername());
                        etFullname.setText(response.body().getCustomer().getFullname());
                        etPhone.setText(response.body().getCustomer().getPhone());
                        etAddress.setText(response.body().getCustomer().getAddress());
                        break;
                    case 3:
                        etUsername.setText(response.body().getDoctor().getUsername());
                        etFullname.setText(response.body().getDoctor().getFullname());
                        break;
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
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

        Call<Result> call = service.updateProfile(id, username, fullname, phone, address, password_confirmation);

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
        if (v == bUpdate) {
            username = etUsername.getText().toString().trim();
            fullname = etFullname.getText().toString().trim();
            phone = etPhone.getText().toString().trim();
            address = etAddress.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            password_confirmation = etPasswordConfirmation.getText().toString().trim();

            if (role_id == 2) {
                if (username.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_username, Snackbar.LENGTH_SHORT).show();
                } else if (fullname.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_fullname, Snackbar.LENGTH_SHORT).show();
                } else if (phone.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_phone, Snackbar.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_address, Snackbar.LENGTH_SHORT).show();
                }
            } else if (role_id == 3) {
                if (username.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_username, Snackbar.LENGTH_SHORT).show();
                } else if (fullname.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_fullname, Snackbar.LENGTH_SHORT).show();
                }
            }

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
        switch (role_id) {
            case 1:
                startActivity(new Intent(this, AdministratorActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, CustomerActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, DoctorActivity.class));
                break;
        }

        finish();
    }
}

