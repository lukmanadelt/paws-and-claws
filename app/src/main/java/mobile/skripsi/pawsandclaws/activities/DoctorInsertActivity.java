package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
 * Doctor Insert Activity
 * Created by @lukmanadelt on 11/9/2017.
 */

public class DoctorInsertActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private EditText etUsername, etPassword, etFullname;
    private Button bInsert;
    private String username, password, fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_insert);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFullname = findViewById(R.id.etFullname);
        bInsert = findViewById(R.id.bInsert);

        // Set component to listen click event
        bInsert.setOnClickListener(this);
    }

    /**
     * Method to inserting a doctor
     */
    private void insertDoctor() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Menambah...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.insertDoctor(username, password, fullname);

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
        if (v == bInsert) {
            username = etUsername.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            fullname = etFullname.getText().toString().trim();

            if (username.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_username, Snackbar.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_password, Snackbar.LENGTH_SHORT).show();
            } else if (fullname.isEmpty()) {
                Snackbar.make(parentView, R.string.empty_fullname, Snackbar.LENGTH_SHORT).show();
            } else {
                insertDoctor();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, DoctorListActivity.class));
        finish();
    }
}
