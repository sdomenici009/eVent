package com.example.eventplanner;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection; //projections are used to get points on screen
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//used to make custom marker images

public class MakeEventActivity extends FragmentActivity implements OnMarkerClickListener {

	private GoogleMap googleMap;
	Projection projection;
	DisplayMetrics metrics;
	
	static int screen_height;
	static int screen_width;
	
	private LocationManager locationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_event);
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkLocationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsLocationListener);
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// Define a listener that responds to location updates
	LocationListener networkLocationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	      // Calls your function that uses the location.
	    	if (googleMap != null){
		        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
		    	locationManager.removeUpdates(this);
	    	}
	    }

	    public void onStatusChanged(String provider, int status, Bundle extras) {}
	    public void onProviderEnabled(String provider) {}
	    public void onProviderDisabled(String provider) {}
	  };
	  
	// Define a listener that responds to location updates
	LocationListener gpsLocationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {}
	    public void onStatusChanged(String provider, int status, Bundle extras) {}
	    public void onProviderEnabled(String provider) {}
	    public void onProviderDisabled(String provider) {}
	  };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.make_event, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_make_event,
					container, false);
			return rootView;
		}
	}
	
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            else
            {
            	projection = googleMap.getProjection();
    			googleMap.setPadding(0, 0, 0, 100);
    			googleMap.setMyLocationEnabled(true);
    			getFragmentManager().findFragmentById(R.id.map).setRetainInstance(true);
            }
            
    		//googleMap.setOnMarkerClickListener(googleMap);
    		
    		metrics = new DisplayMetrics();
    		getWindowManager().getDefaultDisplay().getMetrics(metrics);
    		screen_width = metrics.widthPixels;
    		screen_height = metrics.heightPixels;
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }	
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	locationManager.removeUpdates(gpsLocationListener);
    }
    
    public void clickPlaceEvent(View v){
		projection = googleMap.getProjection();
		LatLng tempLoc = projection.fromScreenLocation(new Point((int) (screen_width / 2), (int) (screen_height/2)));
		/*SharedPreferences settings = getSharedPreferences(MYPREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
	    editor.putString(CreateEvent.PREF_LAT, Double.toString(tempLoc.latitude));
	    editor.putString(CreateEvent.PREF_LONG, Double.toString(tempLoc.longitude));
	    editor.commit();

		Intent intent = new Intent(this, CreateEvent.class);
		startActivity(intent);*/
		eventMarker temp = new eventMarker();
		temp.Loc = tempLoc;
		EditText tempText = (EditText)findViewById(R.id.editText1);
		temp.Title = tempText.getText().toString();
		Marker tempMarker = googleMap.addMarker(new MarkerOptions()
		.position(temp.Loc)
		.title(temp.Title)
		/*.snippet("Kiel is cool")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))*/);
		tempText.setText("New Event Title");
	}
    
	public void onClickTitleEditText(View v)
	{
		EditText tempText = (EditText)findViewById(R.id.editText1);
		tempText.getText().clear();
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		//go to new activity that allows viewing/rsvping/event editing
		return false;
	}
    
	/*@Override
	public boolean onMarkerClick(Marker arg0) {
		//go to new activity that allows viewing/rsvping/event editing
		return false;
	}*/

}
