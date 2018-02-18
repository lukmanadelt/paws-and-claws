package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

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
 * Pet Detail Activity
 * Created by @lukmanadelt on 11/29/2017.
 */

public class PetDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private ImageView ivPhoto;
    private TextView tvPetType, tvPetName, tvSex, tvDOB, tvAge, tvBreed, tvColor;
    private int id;
    private String pet_name, photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        ivPhoto = findViewById(R.id.ivPhoto);
        tvPetType = findViewById(R.id.tvPetType);
        tvPetName = findViewById(R.id.tvPetName);
        tvSex = findViewById(R.id.tvSex);
        tvDOB = findViewById(R.id.tvDOB);
        tvAge = findViewById(R.id.tvAge);
        tvBreed = findViewById(R.id.tvBreed);
        tvColor = findViewById(R.id.tvColor);
        Button bUpdate = findViewById(R.id.bUpdate);
        Button bDelete = findViewById(R.id.bDelete);

        // Set component to listen click event
        bUpdate.setOnClickListener(this);
        bDelete.setOnClickListener(this);

        // Initial pet id from previous activity
        id = getIntent().getIntExtra("pet_id", 0);

        // Getting a pet
        getPet(id);
    }

    /**
     * Method to getting a pet
     */
    private void getPet(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.getPet(id);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                switch (response.body().getPet().getPetCategoryId()) {
                    case 1:
                        tvPetType.setText(R.string.dog);
                        break;
                    case 2:
                        tvPetType.setText(R.string.cat);
                        break;
                }

                switch (response.body().getPet().getSex()) {
                    case "M":
                        tvSex.setText(R.string.male);
                        break;
                    case "F":
                        tvSex.setText(R.string.female);
                        break;
                }

                pet_name = response.body().getPet().getName();

                tvPetName.setText(pet_name);
                tvDOB.setText(response.body().getPet().getDOB());
                tvBreed.setText(response.body().getPet().getBreed());
                tvColor.setText(response.body().getPet().getColor());

                photo = response.body().getPet().getPhoto();

                String[] dob = response.body().getPet().getDOB().split("-");
                getAge(Integer.parseInt(dob[0]), Integer.parseInt(dob[1]) - 1, Integer.parseInt(dob[2]));

                if (photo != null) {
                    ivPhoto.setVisibility(View.VISIBLE);
                    Picasso.with(getApplicationContext()).load(APIUrl.BASE_URL + "uploads/" + photo).into(ivPhoto);
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
                Intent updatePet = new Intent(getApplicationContext(), PetUpdateActivity.class);

                updatePet.putExtra("pet_id", id);
                startActivity(updatePet);
                finish();
                break;
            case R.id.bDelete:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.delete)
                        .setMessage(getString(R.string.message_delete, pet_name))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deletePet();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(Calendar.YEAR, year);
        dob.set(Calendar.MONTH, month);
        dob.set(Calendar.DATE, day);
        dob.setFirstDayOfWeek(dob.get(Calendar.DAY_OF_WEEK));
        today.setFirstDayOfWeek(dob.get(Calendar.DAY_OF_WEEK));

        int age = today.get(Calendar.WEEK_OF_YEAR) - dob.get(Calendar.WEEK_OF_YEAR);

        tvAge.setText(String.valueOf(age));
    }

    /**
     * Method to deleting a pet
     */
    private void deletePet() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.deletePet(id);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess()) {
                    startActivity(new Intent(PetDetailActivity.this, CustomerActivity.class));
                    onBackPressed();
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
}
