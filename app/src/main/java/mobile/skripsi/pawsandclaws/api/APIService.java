package mobile.skripsi.pawsandclaws.api;

import mobile.skripsi.pawsandclaws.model.ExaminationDetails;
import mobile.skripsi.pawsandclaws.model.Examinations;
import mobile.skripsi.pawsandclaws.model.Medicals;
import mobile.skripsi.pawsandclaws.model.Notifications;
import mobile.skripsi.pawsandclaws.model.Pets;
import mobile.skripsi.pawsandclaws.model.Users;
import mobile.skripsi.pawsandclaws.model.Result;
import mobile.skripsi.pawsandclaws.model.Vaccines;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * API Service
 * Created by @lukmanadelt on 06/11/2017.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("doctors")
    Call<Users> getDoctors();

    @GET("doctors/{id}")
    Call<Result> getDoctor(@Path("id") int id);

    @FormUrlEncoded
    @POST("doctors/update/{id}")
    Call<Result> updateDoctor(
            @Path("id") int id,
            @Field("username") String username,
            @Field("fullname") String fullname,
            @Field("status") int status
    );

    @FormUrlEncoded
    @POST("doctors/insert")
    Call<Result> insertDoctor(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fullname") String fullname
    );

    @GET("customers")
    Call<Users> getCustomers();

    @FormUrlEncoded
    @POST("customers/insert")
    Call<Result> insertCustomer(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("address") String address
    );

    @GET("customers/{id}")
    Call<Result> getCustomer(@Path("id") int id);

    @FormUrlEncoded
    @POST("customers/update/{id}")
    Call<Result> updateCustomer(
            @Path("id") int id,
            @Field("username") String username,
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("status") int status
    );

    @FormUrlEncoded
    @POST("profiles/update/{id}")
    Call<Result> updateProfile(
            @Path("id") int id,
            @Field("username") String username,
            @Field("fullname") String fullname,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("pets/insert")
    Call<Result> insertPet(
            @Field("pet_category_id") int pet_category_id,
            @Field("user_id") int user_id,
            @Field("name") String name,
            @Field("sex") String sex,
            @Field("dob") String dob,
            @Field("breed") String breed,
            @Field("color") String color,
            @Field("photo") String photo
    );

    @Multipart
    @POST("pets/upload")
    Call<Result> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @GET("customer/pets/{user_id}")
    Call<Pets> getPets(@Path("user_id") int user_id);

    @GET("pets/{id}")
    Call<Result> getPet(@Path("id") int id);

    @FormUrlEncoded
    @POST("pets/update/{id}")
    Call<Result> updatePet(
            @Path("id") int id,
            @Field("pet_category_id") int pet_category_id,
            @Field("name") String name,
            @Field("sex") String sex,
            @Field("dob") String dob,
            @Field("breed") String breed,
            @Field("color") String color,
            @Field("photo") String photo
    );

    @GET("pets/vaccine_recommendation/{user_id}")
    Call<Pets> getVaccineRecommendation(@Path("user_id") int user_id);

    @GET("customer/havePets")
    Call<Users> getCustomerHavePets();

    @GET("vaccines/{pet_id}")
    Call<Vaccines> getVaccines(@Path("pet_id") int pet_id);

    @GET("medicals")
    Call<Medicals> getMedicals();

    @GET("examinations/{pet_id}")
    Call<Examinations> getExaminations(@Path("pet_id") int pet_id);

    @GET("examinations/vaccine/{pet_id}/{period}")
    Call<Result> getVaccineExamination(@Path("pet_id") int pet_id, @Path("period") String period);

    @GET("examinations/medical/{pet_id}/{period}")
    Call<Result> getMedicalExamination(@Path("pet_id") int pet_id, @Path("period") String period);

    @GET("examination_details/vaccine/{pet_id}/{period}")
    Call<ExaminationDetails> getVaccineExaminationDetails(@Path("pet_id") int pet_id, @Path("period") String period);

    @GET("examination_details/medical/{pet_id}/{period}")
    Call<ExaminationDetails> getMedicalExaminationDetails(@Path("pet_id") int pet_id, @Path("period") String period);

    @GET("examinations/next/{pet_id}")
    Call<ExaminationDetails> getNextExaminations(@Path("pet_id") int pet_id);

    @GET("reports/vaccines/{period_start}/{period_end}")
    Call<Vaccines> getReportVaccines(@Path("period_start") String period_start, @Path("period_end") String period_end);

    @GET("reports/pets/{period_start}/{period_end}")
    Call<Pets> getReportPets(@Path("period_start") String period_start, @Path("period_end") String period_end);

    @GET("notifications/{user_id}")
    Call<Notifications> getNotifications(@Path("user_id") int user_id);

    @FormUrlEncoded
    @POST("examinations/vaccine/insert")
    Call<Result> insertVaccineExamination(
            @Field("id") int id,
            @Field("pet_id") int pet_id,
            @Field("weight") Double weight,
            @Field("temperature") Double temperature,
            @Field("due_date") String due_date,
            @Field("given_date") String given_date,
            @Field("details") String details
    );

    @FormUrlEncoded
    @POST("examinations/medical/insert")
    Call<Result> insertMedicalExamination(
            @Field("id") int id,
            @Field("pet_id") int pet_id,
            @Field("weight") Double weight,
            @Field("temperature") Double temperature,
            @Field("size") Double size,
            @Field("due_date") String due_date,
            @Field("given_date") String given_date,
            @Field("details") String details
    );

    @FormUrlEncoded
    @POST("pets/delete")
    Call<Result> deletePet(@Field("id") int id);
}
