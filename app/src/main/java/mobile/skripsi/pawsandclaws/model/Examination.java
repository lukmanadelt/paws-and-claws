package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Examination Model
 * Created by @lukmanadelt on 12/8/2017.
 */

public class Examination {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("weight")
    @Expose
    private Double weight;

    @SerializedName("temperature")
    @Expose
    private Double temperature;

    @SerializedName("size")
    @Expose
    private Double size;

    @SerializedName("due_date")
    @Expose
    private String due_date;

    @SerializedName("given_date")
    @Expose
    private String given_date;

    @SerializedName("examination_details")
    @Expose
    private List<ExaminationDetail> examination_details = null;

    public Examination(int id, Double weight, Double temperature, Double size, String due_date, String given_date) {
        this.id = id;
        this.weight = weight;
        this.temperature = temperature;
        this.size = size;
        this.due_date = due_date;
        this.given_date = given_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getDueDate() {
        return due_date;
    }

    public void setDueDate(String due_date) {
        this.due_date = due_date;
    }

    public String getGivenDate() {
        return given_date;
    }

    public void setGivenDate(String given_date) {
        this.given_date = given_date;
    }

    public List<ExaminationDetail> getExaminationDetails() {
        return examination_details;
    }

    public void setExaminationDetails(List<ExaminationDetail> examination_details) {
        this.examination_details = examination_details;
    }
}
