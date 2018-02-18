package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * User Model
 * Created by @lukmanadelt on 06/11/2017.
 */

public class User {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("role_id")
    @Expose
    private int role_id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("count_pets")
    @Expose
    private int count_pets;

    public User(int id, int role_id, String username, String password, String fullname, String phone, String address, int status) {
        this.id = id;
        this.role_id = role_id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public User(int id, int role_id, String username, String fullname, int count_pets) {
        this.id = id;
        this.role_id = role_id;
        this.username = username;
        this.fullname = fullname;
        this.count_pets = count_pets;
    }

    public int getId() {
        return id;
    }

    public int getRoleId() {
        return role_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() { return fullname; }

    public String getPhone() { return phone; }

    public String getAddress() { return address; }

    public int getStatus() { return status; }

    public int getCountPets() { return count_pets; }
}
