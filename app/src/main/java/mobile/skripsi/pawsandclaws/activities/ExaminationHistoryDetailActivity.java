package mobile.skripsi.pawsandclaws.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.DetailInfo;
import mobile.skripsi.pawsandclaws.helper.HeaderInfo;
import mobile.skripsi.pawsandclaws.helper.MyListMedicalAdapter;
import mobile.skripsi.pawsandclaws.helper.MyListVaccineAdapter;
import mobile.skripsi.pawsandclaws.helper.NonScrollExpandableListView;
import mobile.skripsi.pawsandclaws.model.ExaminationDetails;
import mobile.skripsi.pawsandclaws.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Examination History Detail Activity
 * Created by @lukmanadelt on 12/8/2017.
 */

public class ExaminationHistoryDetailActivity extends AppCompatActivity {
    private View parentView;
    private TextView tvWeight, tvTemperature, tvDueDate, tvGivenDate, tvWeightMedical, tvTemperatureMedical, tvSizeMedical, tvGivenDateMedical;
    private LinkedHashMap<String, HeaderInfo> mySection = new LinkedHashMap<>();
    private LinkedHashMap<String, HeaderInfo> mySectionMedical = new LinkedHashMap<>();
    private ArrayList<HeaderInfo> SectionList = new ArrayList<>();
    private ArrayList<HeaderInfo> SectionListMedical = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_history_detail);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        tvWeight = findViewById(R.id.tvWeight);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvGivenDate = findViewById(R.id.tvGivenDate);
        tvWeightMedical = findViewById(R.id.tvWeightMedical);
        tvTemperatureMedical = findViewById(R.id.tvTemperatureMedical);
        tvSizeMedical = findViewById(R.id.tvSizeMedical);
        tvGivenDateMedical = findViewById(R.id.tvGivenDateMedical);

        // Initial pet id and period
        int pet_id = getIntent().getIntExtra("pet_id", 0);
        String period = getIntent().getStringExtra("period");

        // Getting a examination and examination details
        getVaccineExamination(pet_id, period);
        AddProduct(pet_id, period);

        NonScrollExpandableListView expandableListViewVaccine = findViewById(R.id.myListVaccine);
        // Create the adapter by passing your ArrayList data
        MyListVaccineAdapter vaccineListAdapter = new MyListVaccineAdapter(ExaminationHistoryDetailActivity.this, SectionList);
        // Attach the adapter to the list
        expandableListViewVaccine.setAdapter(vaccineListAdapter);

        // Getting a examination and examination details
        getMedicalExamination(pet_id, period);
        AddProductMedical(pet_id, period);

        NonScrollExpandableListView expandableListViewMedical = findViewById(R.id.myListMedical);
        // Create the adapter by passing your ArrayList data
        MyListMedicalAdapter medicalListAdapter = new MyListMedicalAdapter(ExaminationHistoryDetailActivity.this, SectionListMedical);
        // Attach the adapter to the list
        expandableListViewMedical.setAdapter(medicalListAdapter);
    }

    /**
     * Method to getting a vaccine examination
     */
    private void getVaccineExamination(final int pet_id, final String period) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.getVaccineExamination(pet_id, period);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                tvWeight.setText(getString(R.string.weight_value, String.valueOf(response.body().getExamination().getWeight())));
                tvTemperature.setText(getString(R.string.temperature_value, String.valueOf(response.body().getExamination().getTemperature())));
                tvDueDate.setText(response.body().getExamination().getDueDate());
                tvGivenDate.setText(response.body().getExamination().getGivenDate());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    //load some initial data into out list
    private void AddProduct(int pet_id, String period) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<ExaminationDetails> call = service.getVaccineExaminationDetails(pet_id, period);

        call.enqueue(new Callback<ExaminationDetails>() {
            @Override
            public void onResponse(Call<ExaminationDetails> call, Response<ExaminationDetails> response) {
                progressDialog.dismiss();

                for (int i = 0; i < response.body().getExaminationDetails().size(); i++) {
                    addProduct(response.body().getExaminationDetails().get(i).getName(), response.body().getExaminationDetails().get(i).getRemark());
                }
            }

            @Override
            public void onFailure(Call<ExaminationDetails> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //here we maintain our products in various departments
    private void addProduct(String department, String remark) {
        //check the hash map if the group already exists
        HeaderInfo headerInfo = mySection.get(department);

        //add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = new HeaderInfo();
            headerInfo.setName(department);
            mySection.put(department, headerInfo);
            SectionList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<DetailInfo> productList = headerInfo.getProductList();

        //create a new child and add that to the group
        DetailInfo detailInfo = new DetailInfo();
        detailInfo.setRemark(remark);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);
    }

    /**
     * Method to getting a medical examination
     */
    private void getMedicalExamination(final int pet_id, final String period) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.getMedicalExamination(pet_id, period);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                tvWeightMedical.setText(getString(R.string.weight_value, String.valueOf(response.body().getExamination().getWeight())));
                tvTemperatureMedical.setText(getString(R.string.temperature_value, String.valueOf(response.body().getExamination().getTemperature())));
                tvSizeMedical.setText(String.valueOf(response.body().getExamination().getSize()));
                tvGivenDateMedical.setText(response.body().getExamination().getGivenDate());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(parentView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    //load some initial data into out list
    private void AddProductMedical(int pet_id, String period) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<ExaminationDetails> call = service.getMedicalExaminationDetails(pet_id, period);

        call.enqueue(new Callback<ExaminationDetails>() {
            @Override
            public void onResponse(Call<ExaminationDetails> call, Response<ExaminationDetails> response) {
                progressDialog.dismiss();

                for (int i = 0; i < response.body().getExaminationDetails().size(); i++) {
                    addProductMedical(response.body().getExaminationDetails().get(i).getName(), response.body().getExaminationDetails().get(i).getRemark(), response.body().getExaminationDetails().get(i).getMedicine());
                }
            }

            @Override
            public void onFailure(Call<ExaminationDetails> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //here we maintain our products in various departments
    private void addProductMedical(String department, String remark, String medicine) {
        //check the hash map if the group already exists
        HeaderInfo headerInfo = mySectionMedical.get(department);

        //add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = new HeaderInfo();
            headerInfo.setName(department);
            mySectionMedical.put(department, headerInfo);
            SectionListMedical.add(headerInfo);
        }

        //get the children for the group
        ArrayList<DetailInfo> productList = headerInfo.getProductList();

        //create a new child and add that to the group
        DetailInfo detailInfo = new DetailInfo();
        detailInfo.setRemark(remark);
        detailInfo.setMedicine(medicine);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);
    }
}
