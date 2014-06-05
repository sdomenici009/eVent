package com.example.eventplanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EventDetails extends ActionBarActivity {

	eventMarker temp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		temp = eventMarker.getInstance(this);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(temp.Title);
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
	
	public void makeEvent(View V){
		EditText et = (EditText) findViewById(R.id.editText1);
		temp.Description = et.getText().toString();
		et = (EditText) findViewById(R.id.editText2);
		temp.neededRSVPs = Integer.parseInt(et.getText().toString());
		et = (EditText) findViewById(R.id.editText3);
		temp.date = et.getText().toString();
		et = (EditText) findViewById(R.id.editText4);
		temp.time = et.getText().toString();
		
		//Now we need to send temp to server
	}
	
	public void cancel(View V){
		temp.Title = "cancel";
		onBackPressed();
	}

}
