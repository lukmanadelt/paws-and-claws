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
 * Update Customer Activity
 * Created by @lukmanadelt on 11/12/2017.
 */

public class CustomerUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private EditText etUsername, etFullname, etPhone, etAddress;
    private RadioButton rbActive, rbNotActive;
    private Button bUpdate;
    private int id, status;
    private String username, fullname, phone, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        etUsername = findViewById(R.id.etUsername);
        etFullname = findViewById(R.id.etFullname);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        rbActive = findViewById(R.id.rbActive);
        rbNotActive = findViewById(R.id.rbNotActive);
        bUpdate = findViewById(R.id.bUpdate);

        // Set component to listen click event
        bUpdate.setOnClickListener(this);

        // Initial doctor id from previous activity
        id = getIntent().getIntExtra("customer_id", 0);

        // Getting a customer
        getCustomer(id);
    }

    /**
     * Method to getting a customer
     */
    private void getCustomer(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.getCustomer(id);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                etUsername.setText(response.body().getCustomer().getUsername());
                etFullname.setText(response.body().getCustomer().getFullname());
                etPhone.setText(response.body().getCustomer().getPhone());
                etAddress.setText(response.body().getCustomer().getAddress());;

                switch (response.body().getCustomer().getStatus()) {
                    case 0:
                        rbNotActive.setChecked(true);
                        break;
                    case 1:
                        rbActive.setChecked(true);
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
     * Method to updating a customer
     */
    private void updateCustomer(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mengubah...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.updateCustomer(id, username, fullname, phone, address, status);

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

            if (username.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_username, Snackbar.LENGTH_SHORT).show();
            } else if (fullname.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_fullname, Snackbar.LENGTH_SHORT).show();
            } else if (phone.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_phone, Snackbar.LENGTH_SHORT).show();
            } else if (address.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_address, Snackbar.LENGTH_SHORT).show();
            } else if (!rbActive.isChecked() && !rbNotActive.isChecked()) {
                Snackbar.make(parentView, R.string.empty_status, Snackbar.LENGTH_SHORT).show();
            } else {
                if (rbActive.isChecked()) {
                    status = 1;
                } else if (rbNotActive.isChecked()) {
                    status = 0;
                }

                updateCustomer(id);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent detailCustomer = new Intent(getApplicationContext(), CustomerDetailActivity.class);

        detailCustomer.putExtra("customer_id", id);
        startActivity(detailCustomer);
        finish();
    }
}
