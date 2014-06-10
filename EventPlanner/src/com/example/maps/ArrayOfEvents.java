package com.example.maps;

import java.util.ArrayList;

import android.content.Context;

//singleton class for sharing all events between activities
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
	
	public void insertByDate(eventMarker newest)
	{
		for(int i = 0; i < this.eventsArray.size(); i++){
			if(this.eventsArray.get(i).deadline.getTimeInMillis() > newest.deadline.getTimeInMillis())
			{
				this.eventsArray.add(i, newest);
				return;
			}
		}
		this.eventsArray.add(newest);
	}
	
	//sorts events by deadline
	public void bubbleSort(){
		for (int c = 0; c < ( this.eventsArray.size() - 1 ); c++) {
			for (int d = 0; d < this.eventsArray.size() - c - 1; d++) {
				if (this.eventsArray.get(d).deadline.getTimeInMillis() > this.eventsArray.get(d+1).deadline.getTimeInMillis()) 
				{
					eventMarker tempMarker = this.eventsArray.get(d);
					this.eventsArray.set(d, this.eventsArray.get(d+1));
					this.eventsArray.set(d+1,tempMarker);
				}
		    }
  		} 
	}

}
