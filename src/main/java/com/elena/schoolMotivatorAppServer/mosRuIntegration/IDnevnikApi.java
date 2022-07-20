package com.elena.schoolMotivatorAppServer.mosRuIntegration;

import com.elena.schoolMotivatorAppServer.mosRuIntegration.models.DUser;
import com.elena.schoolMotivatorAppServer.mosRuIntegration.models.Schedule;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.time.LocalDate;
import java.util.Date;

public interface IDnevnikApi {

    @Headers("content-type: application/json;charset=UTF-8")
    @GET("mobile/api/profile")
    Call<DUser> getUserInfoByToken(@Header("auth-token") String token);

    @Headers("content-type: application/json;charset=UTF-8")
    @GET("mobile/api/schedule")
    Call<Schedule> getСhildrenGrades(@Header("auth-token") String token, @Query("student_id") Long student_id, @Query("date") LocalDate date);
}
