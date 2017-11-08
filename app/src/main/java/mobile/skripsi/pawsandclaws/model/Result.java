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

    @SerializedName("user")
    private User user;

    public Result(Boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public Result(User user) {
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() { return user; }
}
