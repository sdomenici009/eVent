package com.example.maps;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


public class EditEvent extends ActionBarActivity {

	private static final String LOG_TAG = "Edit Event Activity";

	eventMarker temp;
	ArrayOfEvents events;
	Spinner mspin;
	int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Removes TitleBar from Activity
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_event);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		temp = eventMarker.getInstance(this);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		events = ArrayOfEvents.getInstance(this);
		EditText tv = (EditText) findViewById(R.id.editText1);
		tv.setText(temp.Title);
		EditText etd = (EditText) findViewById(R.id.editText2);
		etd.setText(temp.Description);
		DatePicker dp = (DatePicker) findViewById(R.id.datePicker1);
		dp.setMinDate(System.currentTimeMillis() - 1000);
		for(int i = 0; i < events.eventsArray.size(); i++){
			if(events.eventsArray.get(i).Loc.equals(temp.Loc) && events.eventsArray.get(i).Title.equals(temp.Title)){
				index = i;
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_details, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_event_details,
					container, false);
			return rootView;
		}
	}
	
	public void saveEvent(View V){
		EditText et1 = (EditText) findViewById(R.id.editText1);
		temp.Title = et1.getText().toString();
		EditText et2 = (EditText) findViewById(R.id.editText2);
		temp.Description = et2.getText().toString();
		DatePicker dp = (DatePicker) findViewById(R.id.datePicker1);
		Log.i(LOG_TAG, Integer.toString(dp.getYear()));
		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
		temp.deadline.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), tp.getCurrentHour(), tp.getCurrentMinute());
		Log.i(LOG_TAG, temp.deadline.toString());	
		events.eventsArray.set(index, temp);
		//events.eventsArray.add(temp.copy());
		
		Gson gson = new Gson();
		
		SharedPreferences settings = getSharedPreferences(MainActivity.MYPREFS, 0);
		Editor editor = settings.edit();
		editor.putString(MainActivity.PREF_STRING_1, gson.toJson(events, ArrayOfEvents.class));
		editor.commit();

		onBackPressed();
	}
	
	public void cancel(View V){
		onBackPressed();
	}

}
