package com.example.android;

import org.junit.Test;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

public class Retrofit {
	private static final String LOCATION_SERVER = "52.35.159.233:80";
	private static GmobileSvc gmobileControl = new RestAdapter.Builder()
			.setEndpoint(LOCATION_SERVER)
			.setLogLevel(LogLevel.FULL)
			.build()
			.create(GmobileSvc.class);

	@Test
	public void testVideoAddAndList() throws Exception {
		Gmobile mobile = new Gmobile();
		mobile.setXaxis("1");
		mobile.setYaxis("3");
		mobile.setZaxis("4");

		mobile.setLattitude("23");
		mobile.setLongitude("23");

		System.out.println(gmobileControl.submitGmobile(mobile));

	}
	//static  Gmobile gmobile = new Gmobile();
	public GmobileSvc senddata(String xaxis,String yaxis,String zaxis,String lat,String lon)
	{
		Gmobile gmobile = new Gmobile();
		gmobile.setXaxis(xaxis);
		gmobile.setYaxis(yaxis);
		gmobile.setZaxis(zaxis);
		gmobile.setLattitude(lat);
		gmobile.setLongitude(lon);
       return gmobileControl.submitGmobile(gmobile);


		//return gmobile;
	}


}