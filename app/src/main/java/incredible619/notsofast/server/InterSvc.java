package incredible619.notsofast.server;

import java.util.List;

import retrofit.http.GET;

public interface InterSvc {

	@GET("/getpothole2")
	public  List<Pothole2> getpothole();
	
}