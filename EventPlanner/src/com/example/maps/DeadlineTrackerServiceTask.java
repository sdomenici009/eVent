package com.example.maps;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeadlineTrackerServiceTask implements Runnable{

	private NotificationManager notificationManager;
	public static final String LOG_TAG = "MyService";
	private boolean running;
	private Context context;
	
	public ArrayOfEvents events;
	
	public void setEvents(ArrayOfEvents myEvents){
		events = myEvents;
	}
	
    public DeadlineTrackerServiceTask(Context _context) {
    	context = _context;    	
    	// Put here what to do at creation.
    }
    
    @Override
    public void run() {
    	notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        running = true;
        while (running) {
        	// Sleep a tiny bit, the check the array
			try {
				Thread.sleep(1000);
				Log.i("LOG_TAG", "Checking events");
				Calendar rightNow = Calendar.getInstance();
				for(int i = 0; i < events.eventsArray.size(); i++){
					Log.i("LOG_TAG", "loop " + i);
					if( events.eventsArray.get(i).notified == false && events.eventsArray.get(i).deadline.getTimeInMillis() < rightNow.getTimeInMillis() ){
						Notify(events.eventsArray.get(i).Title);
						events.eventsArray.get(i).notified = true;
					}
				}
			} catch (Exception e) {
				e.getLocalizedMessage();
			}
        }
    }
    
    //will create a notification when a deadline has been hit
    @SuppressWarnings("deprecation")
	public void Notify(String title)
    {
    	Log.i("LOG_TAG", "Notifying");
		Notification notification = new Notification(
        		R.drawable.ic_launcher, 
        		title + " has hit its deadline",
                System.currentTimeMillis());
		Intent notificationIntent = new Intent();
    	PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
    	notification.setLatestEventInfo(context, "Event Planner Notification",
    			title + " has hit its deadline", pendingIntent);
		notificationManager.notify(1, notification);
    }
  
    //restarts the service. Probably not necessary
    public void startThis()
    {
    	running = true;
    }

	public void stopProcessing() {
		running = false;	
	}

}
