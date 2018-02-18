package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.model.ExaminationDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Examination Next Activity
 * Created by @lukmanadelt on 12/14/2017.
 */

public class ExaminationNextActivity extends AppCompatActivity {
    private View parentView;
    private TableLayout tlExamination;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_next);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        tlExamination = findViewById(R.id.tlExamination);
        tvEmpty = findViewById(R.id.tvEmpty);

        // Initial pet id from previous activity
        int pet_id = getIntent().getIntExtra("pet_id", 0);

        // Getting all examinations
        getNextExaminations(pet_id);
    }

    private void getNextExaminations(final int pet_id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<ExaminationDetails> call = service.getNextExaminations(pet_id);

        call.enqueue(new Callback<ExaminationDetails>() {
            @Override
            public void onResponse(Call<ExaminationDetails> call, final Response<ExaminationDetails> response) {
                progressDialog.dismiss();

                if (response.body().getExaminationDetails().size() == 0) {
                    tlExamination.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    tlExamination.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);

                    tlExamination.removeAllViews();

                    TableRow rowHeader = new TableRow(ExaminationNextActivity.this);
                    rowHeader.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    rowHeader.setGravity(Gravity.CENTER);
                    String[] headerText = {"TANGGAL", "VAKSIN"};

                    int column_header = 0;

                    for (String c : headerText) {
                        TextView tv = new TextView(ExaminationNextActivity.this);
                        tv.setLayoutParams(new TableRow.LayoutParams(column_header));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setTextColor(Color.WHITE);
                        tv.setPadding(20, 5, 20, 5);
                        tv.setText(c);

                        rowHeader.addView(tv);

                        column_header = column_header + 1;
                    }

                    tlExamination.addView(rowHeader);

                    String[][] size = new String[response.body().getExaminationDetails().size()][];

                    for (int i = 0; i < response.body().getExaminationDetails().size(); i++) {
                        size[i] = new String[]{response.body().getExaminationDetails().get(i).getNextDate(), response.body().getExaminationDetails().get(i).getName()};
                    }

                    for (int i = 0; i < response.body().getExaminationDetails().size(); i++) {
                        TableRow row = new TableRow(ExaminationNextActivity.this);

                        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        row.setGravity(Gravity.CENTER);

                        int column_detail = 0;

                        for (String text : size[i]) {
                            final TextView tv = new TextView(ExaminationNextActivity.this);

                            tv.setLayoutParams(new TableRow.LayoutParams(column_detail));
                            tv.setGravity(Gravity.CENTER);
                            tv.setTextSize(16);
                            tv.setPadding(20, 5, 20, 5);
                            tv.setText(text);
                            tv.setTextColor(getResources().getColor(R.color.colorPrimary));

                            row.addView(tv);

                            column_detail = column_detail + 1;
                        }

                        tlExamination.addView(row);
                    }
                }
            }

            @Override
            public void onFailure(Call<ExaminationDetails> call, Throwable t) {
                progressDialog.dismiss();
                tlExamination.setVisibility(View.GONE);
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
