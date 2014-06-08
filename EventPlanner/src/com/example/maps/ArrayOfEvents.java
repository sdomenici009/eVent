package com.example.maps;

import java.util.ArrayList;

import android.content.Context;

public class ArrayOfEvents {

	private static ArrayOfEvents instance = null;
	
	protected ArrayOfEvents(){
		//exists only to defeat instantiation
	}
	
	public ArrayList<eventMarker> eventsArray;
	public static ArrayOfEvents getInstance(Context context) {
		if(instance == null){
			instance = new ArrayOfEvents();
			instance.eventsArray = new ArrayList<eventMarker>();
		}
		return instance;
	}
	
	@Override
	public String toString(){
		String rep = "";
		for(int i = 0; i < this.eventsArray.size(); i++){
			rep += eventsArray.get(i).Title + '\n';
		}
		return rep;
	}

}
