package com.example.android;

//import com.example.quiz.repository.QuizSubmission;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;

public interface GmobileSvc {
	@POST("/submitgmobile")
	public GmobileSvc submitGmobile(@Body Gmobile mobile);

	@POST("/submitgmobilelist")
	public String submitGmobileList (@Body List<Gmobile> mobile);
	
}