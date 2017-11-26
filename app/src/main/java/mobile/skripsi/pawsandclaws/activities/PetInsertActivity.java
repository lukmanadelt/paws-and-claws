package mobile.skripsi.pawsandclaws.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.squareup.picasso.Picasso;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import mobile.skripsi.pawsandclaws.R;
import mobile.skripsi.pawsandclaws.api.APIService;
import mobile.skripsi.pawsandclaws.api.APIUrl;
import mobile.skripsi.pawsandclaws.helper.PermissionsActivity;
import mobile.skripsi.pawsandclaws.helper.PermissionsChecker;
import mobile.skripsi.pawsandclaws.helper.SharedPreferencesManager;
import mobile.skripsi.pawsandclaws.model.Result;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Pet Insert Activity
 * Created by @lukmanadelt on 11/26/2017.
 */

public class PetInsertActivity extends AppCompatActivity implements View.OnClickListener {
    private View parentView;
    private EditText etPetName, etDOB, etAge, etBreed, etColor;
    private RadioButton rbDog, rbCat, rbMale, rbFemale;
    private Button bInsert;
    private int id, pet_type, age;
    private String pet_name, sex, dob, breed, color, imagePath;
    private Calendar cDate;
    private int year, month, day;
    private DecimalFormat decimalFormat;
    private Date date_from, date_to;
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private PermissionsChecker permissionsChecker;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create Layout
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_pet_insert);

        // Permission Checker Initialized
        permissionsChecker = new PermissionsChecker(this);

        // Initial Component
        parentView = findViewById(R.id.parentLayout);
        etPetName = findViewById(R.id.etPetName);
        etDOB = findViewById(R.id.etDOB);
        etAge = findViewById(R.id.etAge);
        etBreed = findViewById(R.id.etBreed);
        etColor = findViewById(R.id.etColor);
        rbDog = findViewById(R.id.rbDog);
        rbCat = findViewById(R.id.rbCat);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        bInsert = findViewById(R.id.bInsert);

        // Set component to listen click event
        etDOB.setOnClickListener(this);
        bInsert.setOnClickListener(this);

        // Initial Customer ID
        id = SharedPreferencesManager.getInstance(getApplicationContext()).getUser().getId();

        // Initial Date Picker element
        cDate = Calendar.getInstance();
        year = cDate.get(Calendar.YEAR);
        month = cDate.get(Calendar.MONTH);
        day = cDate.get(Calendar.DAY_OF_MONTH);
        decimalFormat = new DecimalFormat("00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
    }

    /**
     * Method to inserting a pet
     */
    private void insertPet() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Menambah...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        Call<Result> call = service.insertPet(pet_type, id, pet_name, sex, dob, age, breed, color, body);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                if (response.body().getSuccess()) onBackPressed();

                imagePath = "";

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
        switch (v.getId()) {
            case R.id.etDOB:
                setDate(v, 999);
                break;
            case R.id.bInsert:
                pet_name = etPetName.getText().toString().trim();
                dob = etDOB.getText().toString().trim();
                age = Integer.parseInt(etAge.getText().toString().trim());
                breed = etBreed.getText().toString().trim();
                color = etColor.getText().toString().trim();

                if (pet_name.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_pet_name, Snackbar.LENGTH_SHORT).show();
                } else if (dob.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_dob, Snackbar.LENGTH_SHORT).show();
                } else if (etAge.getText().toString().trim().isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_age, Snackbar.LENGTH_SHORT).show();
                } else if (breed.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_breed, Snackbar.LENGTH_SHORT).show();
                } else if (color.isEmpty()) {
                    Snackbar.make(parentView, R.string.empty_color, Snackbar.LENGTH_SHORT).show();
                } else if (!rbDog.isChecked() && !rbCat.isChecked()) {
                    Snackbar.make(parentView, R.string.empty_pet_type, Snackbar.LENGTH_SHORT).show();
                } else if (!rbMale.isChecked() && !rbFemale.isChecked()) {
                    Snackbar.make(parentView, R.string.empty_sex, Snackbar.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(imagePath)) {
                    Snackbar.make(parentView, R.string.empty_photo, Snackbar.LENGTH_SHORT).show();
                } else {
                    if (rbDog.isChecked()) {
                        pet_type = 1;
                    } else if (rbCat.isChecked()) {
                        pet_type = 2;
                    }

                    if (rbMale.isChecked()) {
                        sex = "M";
                    } else if (rbFemale.isChecked()) {
                        sex = "F";
                    }

                    insertPet();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CustomerActivity.class));
        finish();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view, int id) {
        showDialog(id);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    dobListener, year, month, day);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener dobListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        etDOB.setText(new StringBuilder().append(year).append("-")
                .append(decimalFormat.format(Double.valueOf(month))).append("-").append(decimalFormat.format(Double.valueOf(day))));
        getAge(year, month, day);
    }

    private void getAge(int year, int month, int day){
        Calendar cDOB = Calendar.getInstance();
        Calendar cToday = Calendar.getInstance();

        cDOB.set(year, month, day);
        cToday.set(cToday.get(Calendar.YEAR), cToday.get(Calendar.MONTH) + 1, cToday.get(Calendar.DAY_OF_MONTH));

        DateTime dtDOB = new DateTime(cDOB.getTime());
        DateTime dtToday = new DateTime(cToday.getTime());

        Period period = new Period(dtDOB, dtToday);
        Integer age = new Integer(period.getWeeks());

        etAge.setText(age.toString());
    }

    public void showImagePopup(View view) {
        if (permissionsChecker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } else {
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            // Chooser of file system options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 1010);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                Snackbar.make(parentView, R.string.string_unable_to_pick_image, Snackbar.LENGTH_INDEFINITE).show();
                return;
            }

            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                Picasso.with(mContext).load(new File(imagePath))
                        .into(imageView);

                Snackbar.make(parentView, R.string.string_reselect, Snackbar.LENGTH_LONG).show();
                cursor.close();

                textView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                Snackbar.make(parentView, R.string.string_unable_to_load_image, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }
}
