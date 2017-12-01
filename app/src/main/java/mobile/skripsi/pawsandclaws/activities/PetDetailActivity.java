package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.IOException;
import java.util.Calendar;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.model.Result;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Pet Detail Activity
 * Created by Sleekr on 11/29/2017.
 */

public class PetDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private ImageView ivPhoto;
    private TextView tvPetType, tvPetName, tvSex, tvDOB, tvAge, tvBreed, tvColor;
    private Button bUpdate, bDelete;
    private int id;
    private String photo;

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
        bUpdate = findViewById(R.id.bUpdate);
        bDelete = findViewById(R.id.bDelete);

        // Set component to listen click event
        bUpdate.setOnClickListener(this);
        bDelete.setOnClickListener(this);

        // Initial doctor id from previous activity
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

                tvPetName.setText(response.body().getPet().getName());
                tvDOB.setText(response.body().getPet().getDOB());
                tvBreed.setText(response.body().getPet().getBreed());
                tvColor.setText(response.body().getPet().getColor());

                photo = response.body().getPet().getPhoto();

                String[] dob = response.body().getPet().getDOB().split("-");
                getAge(Integer.parseInt(dob[0]), Integer.parseInt(dob[1]), Integer.parseInt(dob[2]));

                if (!photo.isEmpty()) {
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
        finish();
    }

    private void getAge(int year, int month, int day) {
        Calendar cDOB = Calendar.getInstance();
        Calendar cToday = Calendar.getInstance();

        cDOB.set(year, month, day);
        cToday.set(cToday.get(Calendar.YEAR), cToday.get(Calendar.MONTH) + 1, cToday.get(Calendar.DAY_OF_MONTH));

        DateTime dtDOB = new DateTime(cDOB.getTime());
        DateTime dtToday = new DateTime(cToday.getTime());

        Period period = new Period(dtDOB, dtToday);
        Integer age = new Integer(period.getWeeks());

        tvAge.setText(age.toString());
    }
}
