package mobile.skripsi.pawsandclaws.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Pet Model
 * Created by @lukmanadelt on 11/28/2017.
 */

public class Pet {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("pet_category_id")
    @Expose
    private int pet_category_id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("breed")
    @Expose
    private String breed;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("photo")
    @Expose
    private String photo;

    /* Used for vaccine recommendations */

    @SerializedName("vaccine")
    @Expose
    private String vaccine;

    @SerializedName("period")
    @Expose
    private int period;

    @SerializedName("completed")
    @Expose
    private int completed;

    @SerializedName("description")
    @Expose
    private String description;

    /* End used for vaccine recommendations */

    /* Used for reports */

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("pet_category")
    @Expose
    private String pet_category;

    @SerializedName("amount")
    @Expose
    private String amount;

    /* End used for reports */

    public Pet(int id, int pet_category_id, int user_id, String name, String sex, String dob, String breed, String color, String photo) {
        this.id = id;
        this.pet_category_id = pet_category_id;
        this.user_id = user_id;
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.breed = breed;
        this.color = color;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetCategoryId() {
        return pet_category_id;
    }

    public void setPetCategoryId(int pet_category_id) {
        this.pet_category_id = pet_category_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDOB() {
        return dob;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /* Used for vaccine recommendations */

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* End used for vaccine recommendations */

    /* Used for reports */

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPetCategory() {
        return pet_category;
    }

    public void setPetCategory(String pet_category) {
        this.pet_category = pet_category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /* End used for reports */

}
