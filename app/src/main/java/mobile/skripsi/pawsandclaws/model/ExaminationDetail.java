package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Examination Detail Model
 * Created by @lukmanadelt on 12/8/2017.
 */

public class ExaminationDetail {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("vaccination_medical_id")
    @Expose
    private int vaccination_medical_id;

    @SerializedName("next_date")
    @Expose
    private String next_date;

    @SerializedName("remark")
    @Expose
    private String remark;

    @SerializedName("medicine")
    @Expose
    private String medicine;

    public ExaminationDetail(int vaccination_medical_id, String next_date, String remark, String medicine) {
        this.vaccination_medical_id = vaccination_medical_id;
        this.next_date = next_date;
        this.remark = remark;
        this.medicine = medicine;
    }

    public ExaminationDetail(int vaccination_medical_id, String remark, String medicine) {
        this.vaccination_medical_id = vaccination_medical_id;
        this.remark = remark;
        this.medicine = medicine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVaccinationMedicalId() {
        return vaccination_medical_id;
    }

    public void setVaccinationMedicalId(int vaccination_medical_id) {
        this.vaccination_medical_id = vaccination_medical_id;
    }

    public String getNextDate() {
        return next_date;
    }

    public void setNextDate(String next_date) {
        this.next_date = next_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
