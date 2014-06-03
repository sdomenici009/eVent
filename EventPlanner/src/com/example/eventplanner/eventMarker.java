package com.example.eventplanner;
import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;


public class eventMarker {

	private static eventMarker instance = null;

	protected eventMarker() {
		// Exists only to defeat instantiation.
	}	
	
	// Here are some values we want to keep global.
	public LatLng Loc;
	public String Title;
	public Date EventTime;
	public String Description;
	public int numRSVPs;
	
	public static eventMarker getInstance(Context context) {
		if(instance == null) {
			instance = new eventMarker();
			instance.Title = "";
			instance.Description = "";
			instance.Loc = new LatLng(0,0);
			instance.EventTime = new Date();
			instance.numRSVPs = 0;
		}
		return instance;
	}	
	
}
