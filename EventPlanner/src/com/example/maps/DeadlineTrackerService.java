package com.example.maps;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


//import com.example.maps.DeadlineTrackerServiceTask.ResultCallback;

public class DeadlineTrackerService extends Service {

    private static final String LOG_TAG = "MyService";
    
    ArrayOfEvents events;
    
    // Handle to notification manager.
    private NotificationManager notificationManager;
    private int ONGOING_NOTIFICATION_ID = 1; // This cannot be 0. So 1 is a good candidate.
    
    private Thread myThread;
    private DeadlineTrackerServiceTask myTask;
    private boolean myTaskStarted;
    
    // Binder given to clients
    private final IBinder myBinder = new MyBinder();
    
    // Binder class.
    public class MyBinder extends Binder {
    	DeadlineTrackerService getService() {
    		return DeadlineTrackerService.this;
    	}
    }

    @Override
    public void onCreate() {

		Log.i(LOG_TAG, "Service is being created");
		
        // Display a notification about us starting.  We put an icon in the status bar.
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		// Creates the thread running the camera service.
		myTask = new DeadlineTrackerServiceTask(getApplicationContext());
        myThread = new Thread(myTask);
		myThread.start();
		
		//Instantiating and registering the sensors and listeners
	}

    @Override
    public IBinder onBind(Intent intent) {
    	Log.i(LOG_TAG, "Service is being bound");
    	// Returns the binder to this service.
    	return myBinder;
    }
    
    public void stopProcessing()
    {
    	myTask.stopProcessing();
    }
    
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(LOG_TAG, "Service is being created");
		
        // Display a notification about us starting.  We put an icon in the status bar.
        //notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//showMyNotification();
		
		//assuming that this is the first time the service is started instantiate these, else just get the existing ones
		if(myTask == null)
		{
			
			myTask = new DeadlineTrackerServiceTask(getApplicationContext());
	        myThread = new Thread(myTask);
			myThread.start();
		}
        Log.i(LOG_TAG, "Received start id " + startId + ": " + intent);
        // We start the task thread.
    	if (!myThread.isAlive()) {
    		myThread.start();
    	}
    	myTask.startThis();
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
	}
	
	@SuppressWarnings("deprecation")
	private void showMyNotification() {

        // Creates a notification.
		Notification notification = new Notification(
        		R.drawable.ic_launcher, 
        		"Deadline Service running",
                System.currentTimeMillis());
        
    	Intent notificationIntent = new Intent(this, MainActivity.class);
    	PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    	notification.setLatestEventInfo(this, "Event Planner Notification",
    	        "Deadline Service Running", pendingIntent);
    	startForeground(ONGOING_NOTIFICATION_ID, notification);
    }
	
	public void setEvents(ArrayOfEvents myEvents){
		if(myTask != null){
			Log.i("LOG_TAG", "Giving events");
			myTask.setEvents(myEvents);
		}
	}
	
    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        notificationManager.cancel(ONGOING_NOTIFICATION_ID);
        Log.i(LOG_TAG, "Stopping.");
        // Stops the motion detector.
        myTask.stopProcessing();
        Log.i(LOG_TAG, "Stopped.");
    }
    
    /**
     * Show a notification while this service is running.
     */
   /* @SuppressWarnings("deprecation")
	private void showMyNotification() {

        // Creates a notification.
		/*Notification notification = new Notification(
        		R.drawable.ic_launcher, 
        		getString(R.string.my_service_started),
                System.currentTimeMillis());
        
    	Intent notificationIntent = new Intent(this, MainActivity.class);
    	PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
    	/*notification.setLatestEventInfo(this, getText(R.string.notification_title),
    	        getText(R.string.my_service_running), pendingIntent);
    	startForeground(ONGOING_NOTIFICATION_ID, notification);
    }*/
}
