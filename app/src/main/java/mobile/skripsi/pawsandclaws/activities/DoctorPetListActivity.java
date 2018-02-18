package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.PetDoctorAdapter;
import mobile.skripsi.pawsandclaws.model.Pets;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Doctor Pet List Activity
 * Created by @lukmanadelt on 12/7/2017.
 */

public class DoctorPetListActivity extends AppCompatActivity {
    private View parentView;
    private RecyclerView rvPet;
    private RecyclerView.Adapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_pet_list);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        rvPet = findViewById(R.id.rvPet);
        rvPet.setHasFixedSize(true);
        rvPet.setLayoutManager(new LinearLayoutManager(this));

        // Initial customer id from previous activity
        int id = getIntent().getIntExtra("customer_id", 0);

        getPets(id);
    }

    /**
     * Method to getting customer pets
     */
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

                rvPet.setVisibility(View.VISIBLE);
                rvAdapter = new PetDoctorAdapter(response.body().getPets(), getApplicationContext());
                rvPet.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

