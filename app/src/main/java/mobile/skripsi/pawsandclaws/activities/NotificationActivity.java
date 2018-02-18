package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.NotificationAdapter;
import mobile.skripsi.pawsandclaws.helper.SharedPreferencesManager;
import mobile.skripsi.pawsandclaws.model.Notification;
import mobile.skripsi.pawsandclaws.model.Notifications;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Notification Activity
 * Created by @lukmanadelt on 12/12/2017.
 */

public class NotificationActivity extends AppCompatActivity {
    private View parentView;
    private ListView lvNotification;
    private TextView tvEmpty;
    private ArrayList<Notification> notifications;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        lvNotification = findViewById(R.id.lvNotification);
        tvEmpty = findViewById(R.id.tvEmpty);

        // Array List for binding data from JSON to this list
        notifications = new ArrayList<>();

        int user_id = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getId();

        // Getting all notifications
        getNotifications(user_id);
    }

    private void getNotifications(int user_id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Notifications> call = service.getNotifications(user_id);

        call.enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                progressDialog.dismiss();

                notifications = response.body().getNotifications();

                if (notifications.size() == 0) {
                    lvNotification.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    lvNotification.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);

                    adapter = new NotificationAdapter(NotificationActivity.this, notifications);
                    lvNotification.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {
                progressDialog.dismiss();
                lvNotification.setVisibility(View.GONE);
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
