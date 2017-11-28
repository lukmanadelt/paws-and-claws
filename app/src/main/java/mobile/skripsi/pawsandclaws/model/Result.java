package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.SerializedName;

/**
 * Result Model
 * Created by @lumanadelt on 06/11/2017.
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

    public Result(Boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoto() {
        return photo;
    }

    public User getUser() {
        return user;
    }

    public User getDoctor() {
        return doctor;
    }

    public User getCustomer() {
        return customer;
    }
}
