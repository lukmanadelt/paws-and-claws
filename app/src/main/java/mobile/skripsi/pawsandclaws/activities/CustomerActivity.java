package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.UserAdapter;
import mobile.skripsi.pawsandclaws.model.User;
import mobile.skripsi.pawsandclaws.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Doctor Activity
 * Created by @lukmanadelt on 11/10/2017.
 */

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private ListView lvCustomer;
    private TextView tvEmpty;
    private FloatingActionButton fabInsert;
    private ArrayList<User> customers;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        lvCustomer = findViewById(R.id.lvCustomer);
        tvEmpty = findViewById(R.id.tvEmpty);
        fabInsert = findViewById(R.id.fabInsert);

        // Array List for binding data from JSON to this list
        customers = new ArrayList<>();

        // Set component to listen click event
        fabInsert.setOnClickListener(this);

        lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent updateCustomer = new Intent(getApplicationContext(), CustomerUpdateActivity.class);
//
//                updateCustomer.putExtra("customer_id", customers.get(position).getId());
//                startActivity(updateCustomer);
//                finish();
            }
        });

        // Getting all doctors
        getCustomers();
    }

    @Override
    public void onClick(View v) {
        if (v == fabInsert) {
            startActivity(new Intent(this, CustomerInsertActivity.class));
            finish();
        }
    }

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

        Call<Users> call = service.getCustomers();

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                progressDialog.dismiss();

                customers = response.body().getCustomers();

                if (customers.size() == 0) {
                    lvCustomer.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    lvCustomer.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);

                    adapter = new UserAdapter(CustomerActivity.this, customers);
                    lvCustomer.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressDialog.dismiss();
                lvCustomer.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
