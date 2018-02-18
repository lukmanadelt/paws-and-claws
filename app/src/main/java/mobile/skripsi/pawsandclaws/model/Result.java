package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.SerializedName;

/**
 * Result Model
 * Created by @lukmanadelt on 06/11/2017.
 */

public class Result {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("photo")
    private String photo;

    @SerializedName("user")
    private User user;

    @SerializedName("doctor")
    private User doctor;

    @SerializedName("customer")
    private User customer;

    @SerializedName("pet")
    private Pet pet;

    @SerializedName("examination")
    private Examination examination;

    public Result(Boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }
}
