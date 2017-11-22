package mobile.skripsi.pawsandclaws.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
 * Administrator Activity
 * Created by @lukmanadelt on 06/11/2017.
 */

public class AdministratorActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvFullname, tvRole;
    private Button bDoctor, bCustomer, bReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        // If user not logged in open the Login Activity
        if (!SharedPreferencesManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        // Initial Component
        tvFullname = findViewById(R.id.tvFullname);
        tvRole = findViewById(R.id.tvRole);
        bDoctor = findViewById(R.id.bDoctor);
        bCustomer = findViewById(R.id.bCustomer);
        bReport = findViewById(R.id.bReport);

        // Set component to listen click event
        bDoctor.setOnClickListener(this);
        bCustomer.setOnClickListener(this);
        bReport.setOnClickListener(this);

        // Append fullname and role
        String fullname = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getFullname();
        tvFullname.append(" " + fullname);
        tvRole.append(" Administrator");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bDoctor:
                startActivity(new Intent(this, DoctorListActivity.class));
                break;
            case R.id.bCustomer:
                startActivity(new Intent(this, CustomerListActivity.class));
                break;
            case R.id.bReport:
                startActivity(new Intent(this, ReportActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_administrator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
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
