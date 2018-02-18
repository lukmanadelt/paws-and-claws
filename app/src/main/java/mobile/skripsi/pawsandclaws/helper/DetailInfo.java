package mobile.skripsi.pawsandclaws.helper;

/**
 * Detail Info
 * Created by @lukmanadelt on 12/2/2017.
 */

public class DetailInfo {
    private String vaccine = "";
    private int period = 0;
    private int completed = 0;
    private String description = "";
    private String name = "";
    private String remark = "";
    private String medicine = "";

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    private int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    private int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    private String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
