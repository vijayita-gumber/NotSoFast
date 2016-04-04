package com.example.android;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
/**
 * Created by vijayitagumber on 09-03-2016.
 */
public interface Jason {
    @POST("/hellosave")
    public Student hellosave(@Body Student v);

    @GET("/hellosave")
    public List<Student> hellosave();
}
