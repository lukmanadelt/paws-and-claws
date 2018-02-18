package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Examination Details Model
 * Created by @lukmanadelt on 12/8/2017.
 */

public class ExaminationDetails {
    @SerializedName("examination_details")
    @Expose
    private ArrayList<ExaminationDetail> examination_details = new ArrayList<>();

    public ArrayList<ExaminationDetail> getExaminationDetails() {
        return examination_details;
    }

    public void setExaminationDetails(ArrayList<ExaminationDetail> examination_details) {
        this.examination_details = examination_details;
    }
}
