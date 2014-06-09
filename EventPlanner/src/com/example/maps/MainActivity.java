package com.example.maps;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.maps.DeadlineTrackerService.MyBinder;

public class MainActivity extends ActionBarActivity {
	
    private boolean serviceBound;
    private DeadlineTrackerService myService;
    
    ArrayOfEvents events;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		serviceBound = false;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		events = ArrayOfEvents.getInstance(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
    public void createEvent(View V){
		// Go to MakeEventActivity
		Intent intent = new Intent(this, MakeEventActivity.class);
		startActivity(intent);
    }
    
    public void startStopService(View V)
    {
    	if(!serviceBound){
	    	DeadlineTrackerService tracker = new DeadlineTrackerService();
	    	tracker.setEvents(events);
	    	Intent intent = new Intent(this, DeadlineTrackerService.class);
	        startService(intent);        
	        bindMyService(intent);
	        Button sb = (Button) findViewById(R.id.serviceButton);
	        sb.setText("Don't Notify Me");
    	}
    	else
    	{
    		myService.stopProcessing();
    		stopService(new Intent(this, DeadlineTrackerService.class));
    		unbindService(serviceConnection);
    		serviceBound = false;
    		Button sb = (Button) findViewById(R.id.serviceButton);
	        sb.setText("Notify Me");
    	}
    }
    
    private void bindMyService(Intent intent) {
    	Log.i("LOG_TAG", "Starting the service");
    	Log.i("LOG_TAG", "Trying to bind");
    	bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    
    public void sendEvents()
    {
    	myService.setEvents(events);
    }
    
    // Service connection code.
    private ServiceConnection serviceConnection = new ServiceConnection() {
    	@Override
    	public void onServiceConnected(ComponentName className, IBinder serviceBinder) {
    		MyBinder binder = (MyBinder) serviceBinder;
    		myService = binder.getService();
    		serviceBound = true;
    		Log.i("MyService", "Bound succeeded, adding the Event List");
    		sendEvents();
    	}
    	
    	@Override
    	public void onServiceDisconnected(ComponentName arg0) {
    		serviceBound = false;
    	}
    };
}
