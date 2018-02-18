package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import mobile.skripsi.pawsandclaws.helper.ExaminationAdapter;
import mobile.skripsi.pawsandclaws.model.Examination;
import mobile.skripsi.pawsandclaws.model.Examinations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Examination History Activity
 * Created by @lukmanadelt on 12/8/2017.
 */

public class ExaminationHistoryActivity extends AppCompatActivity {
    private View parentView;
    private ListView lvExamination;
    private TextView tvEmpty;
    private ArrayList<Examination> examinations;
    private ExaminationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_history);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        lvExamination = findViewById(R.id.lvExamination);
        tvEmpty = findViewById(R.id.tvEmpty);

        // Array List for binding data from JSON to this list
        examinations = new ArrayList<>();

        // Initial pet id from previous activity
        int pet_id = getIntent().getIntExtra("pet_id", 0);

        // Getting all examinations
        getExaminations(pet_id);
    }

    private void getExaminations(final int pet_id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Examinations> call = service.getExaminations(pet_id);

        call.enqueue(new Callback<Examinations>() {
            @Override
            public void onResponse(Call<Examinations> call, final Response<Examinations> response) {
                progressDialog.dismiss();

                examinations = response.body().getExaminations();

                if (examinations.size() == 0) {
                    lvExamination.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    lvExamination.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);

                    adapter = new ExaminationAdapter(ExaminationHistoryActivity.this, examinations);
                    lvExamination.setAdapter(adapter);

                    lvExamination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent detailExaminationHistory = new Intent(getApplicationContext(), ExaminationHistoryDetailActivity.class);

                            detailExaminationHistory.putExtra("pet_id", pet_id);
                            detailExaminationHistory.putExtra("period", response.body().getExaminations().get(position).getGivenDate());
                            startActivity(detailExaminationHistory);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Examinations> call, Throwable t) {
                progressDialog.dismiss();
                lvExamination.setVisibility(View.GONE);
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
