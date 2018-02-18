package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.CustomerAdapter;
import mobile.skripsi.pawsandclaws.helper.SharedPreferencesManager;
import mobile.skripsi.pawsandclaws.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Doctor Activity
 * Created by @lukmanadelt on 12/3/2017.
 */

public class DoctorActivity extends AppCompatActivity {
    private View parentView;
    private TextView tvEmptyCustomer;
    private RecyclerView rvCustomer;
    private RecyclerView.Adapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        // If user not logged in open the Login Activity
        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        TextView tvFullname = findViewById(R.id.tvFullname);
        TextView tvRole = findViewById(R.id.tvRole);
        tvEmptyCustomer = findViewById(R.id.tvEmptyCustomer);
        rvCustomer = findViewById(R.id.rvCustomer);
        rvCustomer.setHasFixedSize(true);
        rvCustomer.setLayoutManager(new LinearLayoutManager(this));

        // Append fullname and role
        String fullname = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getFullname();
        tvFullname.append(" " + fullname);
        tvRole.append(" Dokter");

        getCustomers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent profile = new Intent(this, ProfileActivity.class);

                profile.putExtra("user_id", SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getId());
                startActivity(profile);
                finish();
                return true;
            case R.id.logout:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.logout)
                        .setMessage(R.string.message_logout)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout() {
        SharedPreferencesManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * Method to getting customers
     */
    private void getCustomers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Users> call = service.getCustomerHavePets();

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                progressDialog.dismiss();

                if (response.body().getCustomers().size() > 0) {
                    rvCustomer.setVisibility(View.VISIBLE);
                    tvEmptyCustomer.setVisibility(View.GONE);
                    rvAdapter = new CustomerAdapter(response.body().getCustomers(), getApplicationContext());
                    rvCustomer.setAdapter(rvAdapter);
                } else {
                    rvCustomer.setVisibility(View.GONE);
                    tvEmptyCustomer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
