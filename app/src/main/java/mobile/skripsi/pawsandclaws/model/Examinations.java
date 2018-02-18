package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Examinations Model
 * Created by @lukmanadelt on 12/8/2017.
 */

public class Examinations {
    @SerializedName("examinations")
    @Expose
    private ArrayList<Examination> examinations = new ArrayList<>();

    public ArrayList<Examination> getExaminations() {
        return examinations;
    }

    public void setExaminations(ArrayList<Examination> examinations) {
        this.examinations = examinations;
    }
}
