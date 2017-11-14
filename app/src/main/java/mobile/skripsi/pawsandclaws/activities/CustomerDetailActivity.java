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
import android.widget.TextView;

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
 * Detail Customer Activity
 * Created by @lukmanadelt on 11/12/2017.
 */

public class CustomerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private TextView tvUsername, tvFullname, tvPhone, tvAddress, tvStatus;
    private Button bUpdate, bDelete;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        tvUsername = findViewById(R.id.tvUsername);
        tvFullname = findViewById(R.id.tvFullname);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvStatus = findViewById(R.id.tvStatus);
        bUpdate = findViewById(R.id.bUpdate);
        bDelete = findViewById(R.id.bDelete);

        // Set component to listen click event
        bUpdate.setOnClickListener(this);
        bDelete.setOnClickListener(this);

        // Initial doctor id from previous activity
        id = getIntent().getIntExtra("customer_id", 0);

        // Getting a doctor
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

                tvUsername.setText(response.body().getCustomer().getUsername());
                tvFullname.setText(response.body().getCustomer().getFullname());
                tvPhone.setText(response.body().getCustomer().getPhone());
                tvAddress.setText(response.body().getCustomer().getAddress());

                switch (response.body().getCustomer().getStatus()) {
                    case 0:
                        tvStatus.setText(R.string.not_active);
                        break;
                    case 1:
                        tvStatus.setText(R.string.active);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bUpdate:
                Intent updateCustomer = new Intent(getApplicationContext(), CustomerUpdateActivity.class);

                updateCustomer.putExtra("customer_id", id);
                startActivity(updateCustomer);
                finish();
                break;
            case R.id.bDelete:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CustomerActivity.class));
        finish();
    }
}
