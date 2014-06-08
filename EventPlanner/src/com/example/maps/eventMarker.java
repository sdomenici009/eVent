package com.example.maps;
import java.util.Calendar;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;


public class eventMarker {

	private static eventMarker instance = null;

	protected eventMarker() {
		// Exists only to defeat instantiation.
	}	
	
	// Here are some values we want to keep global.
	public LatLng Loc;
	public String Title;
	public String Description;
	public Calendar deadline;
	public static eventMarker getInstance(Context context) {
		if(instance == null) {
			instance = new eventMarker();
			instance.Title = "";
			instance.Description = "";
			instance.Loc = new LatLng(0,0);
			instance.deadline = Calendar.getInstance();
		}
		return instance;
	}	
	
	public eventMarker copy(){
		eventMarker t = new eventMarker();
		t.Title = this.Title;
		t.Description = this.Description;
		t.Loc = this.Loc;
		t.deadline = this.deadline;
		return t;
	}
	
}
