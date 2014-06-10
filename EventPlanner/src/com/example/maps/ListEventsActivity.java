package com.example.maps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;


public class ListEventsActivity extends ActionBarActivity{
	
	eventMarker temp;
	ArrayOfEvents events;
	
	private class ListElement {
		ListElement() {};
		
		public eventMarker thisEvent;
		
		public String titleLabel;
		public String timeLabel;
	}
	
	private ArrayList<ListElement> aList;
	
	private class MyAdapter extends ArrayAdapter<ListElement>{

		int resource;
		Context context;
		
		public MyAdapter(Context _context, int _resource, List<ListElement> items) {
			super(_context, _resource, items);
			resource = _resource;
			context = _context;
			this.context = _context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout newView;
			
			ListElement w = getItem(position);
			
			// Inflate a new view if necessary.
			if (convertView == null) {
				newView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
				vi.inflate(resource,  newView, true);
			} else {
				newView = (LinearLayout) convertView;
			}
			
			// Fills in the view.
			TextView tv = (TextView) newView.findViewById(R.id.listTitle);
			TextView timeText = (TextView) newView.findViewById(R.id.listTime);
			Button b = (Button) newView.findViewById(R.id.listButton);
			tv.setText(w.titleLabel);
			timeText.setText(w.timeLabel);

			b.setTag(Integer.toString(position));
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String s = (String) v.getTag();
					int pos = Integer.parseInt(s);
					events.eventsArray.remove(pos);
					Gson gson = new Gson();					
					SharedPreferences settings = getSharedPreferences(MainActivity.MYPREFS, 0);
					Editor editor = settings.edit();
					editor.putString(MainActivity.PREF_STRING_1, gson.toJson(events, ArrayOfEvents.class));
					editor.commit();
					
					aList.remove(pos);
					aa.notifyDataSetChanged();
				}
			});

			return newView;
		}		
	}
	
	private MyAdapter aa;
	
	public void makeNewElement(eventMarker marker)
	{
		Log.d("LOG_TAG", "New element being created");
		ListElement el = new ListElement();
		Log.d("LOG_TAG", "New element created");
		el.thisEvent = marker;
		Log.d("LOG_TAG", "Marker Assigned");
		el.titleLabel = marker.Title;
		Log.d("LOG_TAG", "Title Assigned");
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		el.timeLabel = sdf.format(marker.deadline.getTime());
		Log.d("LOG_TAG", "Marker Assigned");
		aList.add(el);
		Log.d("LOG_TAG", "The length of the list now is " + aList.size());
		aa.notifyDataSetChanged();
	}
	
	public void makeList()
	{
		events.bubbleSort();
		for(int i = 0; i < events.eventsArray.size(); i++)
			makeNewElement(events.eventsArray.get(i));
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listed_events);
		aList = new ArrayList<ListElement>();
		aa = new MyAdapter(this, R.layout.list_element, aList);
		ListView myListView = (ListView) findViewById(R.id.listView1);
		myListView.setAdapter(aa);
		aa.notifyDataSetChanged();
	}

	@Override
	protected void onResume(){
		super.onResume();
		temp = eventMarker.getInstance(this);
		events = ArrayOfEvents.getInstance(this);
		makeList();
	}

}
