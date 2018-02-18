package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.NonScrollListView;
import mobile.skripsi.pawsandclaws.helper.PetCustomerAdapter;
import mobile.skripsi.pawsandclaws.model.Pet;
import mobile.skripsi.pawsandclaws.model.Pets;
import mobile.skripsi.pawsandclaws.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Customer Detail Activity
 * Created by @lukmanadelt on 11/12/2017.
 */

public class CustomerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private TextView tvUsername, tvFullname, tvPhone, tvAddress, tvStatus, tvEmptyPet;
    private NonScrollListView lvPet;
    private Button bUpdate;
    private int id;
    private ArrayList<Pet> pets;
    private PetCustomerAdapter adapter;

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
        tvEmptyPet = findViewById(R.id.tvEmptyPet);
        lvPet = findViewById(R.id.lvPet);
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
    private void getCustomer(final int id) {
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

                getPets(id);
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
            Intent updateCustomer = new Intent(getApplicationContext(), CustomerUpdateActivity.class);

            updateCustomer.putExtra("customer_id", id);
            startActivity(updateCustomer);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CustomerListActivity.class));
        finish();
    }

    private void getPets(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Pets> call = service.getPets(id);

        call.enqueue(new Callback<Pets>() {
            @Override
            public void onResponse(Call<Pets> call, Response<Pets> response) {
                progressDialog.dismiss();

                pets = response.body().getPets();

                if (pets.size() == 0) {
                    lvPet.setVisibility(View.GONE);
                    tvEmptyPet.setVisibility(View.VISIBLE);
                } else {
                    lvPet.setVisibility(View.VISIBLE);
                    tvEmptyPet.setVisibility(View.GONE);

                    adapter = new PetCustomerAdapter(CustomerDetailActivity.this, pets);
                    lvPet.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                progressDialog.dismiss();
                lvPet.setVisibility(View.GONE);
                tvEmptyPet.setVisibility(View.VISIBLE);
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
