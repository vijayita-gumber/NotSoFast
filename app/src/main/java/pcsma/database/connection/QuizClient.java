package pcsma.database.connection;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

public class QuizClient {

	QuizDetails qd ;
	ArrayList<QuizMarks> qm ;
	boolean isValid ;
	float average ;

	//private static final String LOCATION_SERVER = "http://192.168.55.2:80";

	private static final String LOCATION_SERVER = "http://54.209.192.114:80";
	private static QuizSvc quizControl = new RestAdapter.Builder()
			.setEndpoint(LOCATION_SERVER)
			.setLogLevel(LogLevel.FULL)
			.build()
			.create(QuizSvc.class);
	
	@Test
	public void testVideoAddAndList(String email , String option , String roll) throws Exception
	{
		qd = quizControl.greetingForm(email);
		if(qd==null)
		{
			// there are no active quize
			return;
		}
		System.out.println("quizsubmitTest: "+qd.getQuizId()+" : "+qd.getQuestion());
		
		Integer quizId = qd.getQuizId();
		String subject = qd.getSubject();
		String ques = qd.getQuestion() ;
		Log.v("ques", ques);

		QuizSubmission qs = new QuizSubmission();
		qs.setQuizId(quizId);
		qs.setSubject(subject);
		qs.setEmail(email);
		qs.setOption(option);
		qs.setRollNo(roll);
		
		System.out.println(quizControl.submitQuiz(qs));
		
	}

	public String getQuizQues(String email)
	{
		qd = quizControl.greetingForm(email);
		if(qd == null){ return "0" ;}
		else
		{
			String ques = qd.getQuestion();
			Log.v("ques", ques);
			return ques;
		}
	}

	public ArrayList<QuizMarks> getQuizMarks(String subject , String email)
	{
		qm = quizControl.getMarks(subject, email);
		ArrayList<QuizMarks> arrayList = qm ;
		return arrayList ;
	}

	public boolean getIsAlready(String email)
	{
		Log.v("emailll" , email);
		isValid = quizControl.isDone(email);
		Log.v("vallllll" , String.valueOf(isValid));
		return isValid ;
	}

	public float getAverage(String subject)
	{
		average = quizControl.getDone(subject);
		return average ;
	}
	
}