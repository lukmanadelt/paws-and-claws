package mobile.skripsi.pawsandclaws.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
 * Created by @lukmanadelt on 11/7/2017.
 */

public class DoctorActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lvDoctor;
    private TextView tvEmpty;
    private FloatingActionButton fabAdd;
    private ArrayList<User> userList;
    private UserAdapter adapter;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        lvDoctor = findViewById(R.id.lvDoctor);
        tvEmpty = findViewById(R.id.tvEmpty);
        fabAdd = findViewById(R.id.fabAdd);

        // Array List for binding data from JSON to this list
        userList = new ArrayList<>();

        // Set component to listen click event
        fabAdd.setOnClickListener(this);

        lvDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent updateDoctor = new Intent(getApplicationContext(), DoctorUpdateActivity.class);

                updateDoctor.putExtra("doctor_id", userList.get(position).getId());
                startActivity(updateDoctor);
                finish();
            }
        });

        getDoctors();
    }

    @Override
    public void onClick(View v) {
//        if (v == fabAdd) {
//            Intent addDoctorActivity = new Intent(getApplicationContext(), AddDoctorActivity.class);
//            startActivity(addDoctorActivity);
//        }
    }

    private void getDoctors() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Users> call = service.getUsers();

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                userList = response.body().getUsers();
                adapter = new UserAdapter(DoctorActivity.this, userList);
                lvDoctor.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
