package mobile.skripsi.pawsandclaws.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.helper.SharedPreferencesManager;

/**
 * Customer Activity
 * Created by Sleekr on 11/22/2017.
 */

public class CustomerActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvFullname, tvRole;
    private FloatingActionButton fabInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        // If user not logged in open the Login Activity
        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        // Initial Component
        tvFullname = findViewById(R.id.tvFullname);
        tvRole = findViewById(R.id.tvRole);
        fabInsert = findViewById(R.id.fabInsert);

        // Set component to listen click event
        fabInsert.setOnClickListener(this);

        // Append fullname and role
        String fullname = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getFullname();
        tvFullname.append(" " + fullname);
        tvRole.append(" Pelanggan");
    }

    @Override
    public void onClick(View v) {
        if (v == fabInsert) {
//            startActivity(new Intent(this, PetInsertActivity.class));
//            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_customer_doctor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                return true;
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
}
