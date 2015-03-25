package com.example.mymapdemo;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

//import android.app.ActionBar;
//import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/*
 * this app included appcompat_v7
 * 
 * use MyMapDemoDefaultGoogleMap as a default project
 * 
 * 1).
 * To use the google map API
 * 
 * create a GoogleMap object either by MapFragment or MapView
 * set and enable MyLocation
 * set other locations with Java
 * 
 * 
 * 
 * follow up note:
 * to retain the map data
 * http://wptrafficanalyzer.in/blog/retain-markers-on-screen-rotation-in-google-maps-android-api-v2-using-parcelable-latlng-points/
 */
public class MainActivity extends FragmentActivity
{
	/**
     * Note that this may be null if the Google Play services APK is not available.
     */
	GoogleMap map;
	Marker marker;
	ArrayList<Marker> markers = new ArrayList<Marker>();
	ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
	//latitude and longitude, I found from genymotion map
	LatLng sanFrancisco = new LatLng(37.7749, -122.419);
	LatLng oakland = new LatLng(37.8043637, -122.2711137);
	//convert address to LatLng use Geocoder
	LatLng myLatLng = null;
	
	EditText etAddress;
	
	ArrayList<GasStationResult> gasStationResults = new ArrayList<GasStationResult>();
	//just to save not using yet
	//this gasStationResults2 is a save clone of gasStationResults, 
	//pass to and back from GasStationInfoActivity.
	ArrayList<GasStationResult> gasStationResults2 = new ArrayList<GasStationResult>();
	
	ArrayList<AlternativeFuelStationResult> alternativeFuelStationResults = 
			new ArrayList<AlternativeFuelStationResult>();
	ArrayList<AlternativeFuelStationResult> alternativeFuelStationResults2 = 
			new ArrayList<AlternativeFuelStationResult>();
	
	private static final int GAS_MARKER_REQUEST_CODE = 1;
	private static final int ALT_FUEL_MARKER_REQUEST_CODE = 2;
	
	//create SharedPreferences
	private SharedPreferences prefETAddress;
	
	private static final int OPTION_REQUEST_CODE = 100;
	private static final int OTHER_ENERGY_OPTION_REQUEST_CODE = 102;
	
	public static final String SAVED_SEARCH_FUEL_TYPE = "savedSearchFuelType";
	public static final String SAVED_SEARCH_SORT_BY = "savedSearchSortBy";
	public static final String SAVED_SEARCH_DISTANCE = "savedSearchDistance"; 
//	public static final String PREFS_NAME = "MyPrefsGasStationSearchOptionFile";
	private static final String DISTANCE_DEFAULT_MILES = "5";
	private static final String FUEL_TYPE_DEFAULT_VALUE = "reg";
	private static final String SORT_BY_DEFAULT_VALUE = "distance";
	private static final String API_URL_PART_00 = "http://api.mygasfeed.com/stations/radius/";
	
	private String searchOptionFuelType = null;
	private String searchOptionSortBy = null;
	private String searchOptionDistance = null;
	
	boolean isGas = false;
	boolean isAlternativeFuel = false;
	
	private String searchOptionAFSDistance = null;
	private String searchOptionMaxNumOfResults = null;
	private String searchOptionServiceStatus = null;
	private String searchOptionAccessBy = null;
	private String searchOptionEVChargingLevel = null;
	private String searchOptionAFSFuelType = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//access SharedPreferences
		prefETAddress = PreferenceManager.getDefaultSharedPreferences(this);

		Log.d("DEBUG", "inside onCreate()");
		
		setupViews();
		setUpMapIfNeeded();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		
		Log.d("DEBUG", "inside onResume()");
		
		setUpMapIfNeeded();
	}

	public void setupViews()
	{
		etAddress = (EditText) findViewById(R.id.etAddress);
		onLoadETAddress();
	}
	
	/**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
	private void setUpMapIfNeeded()
	{
		// Do a null check to confirm that we have not already instantiated the map.
		if (map == null)
		{
			//to define a map use (MapFragment)getFragmentManager()
			//getFragmentManager() require minSdkVersion 11, change in AndroidManifest.xml to 11
//			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			
//			FragmentManager fragmentManager = getSupportFragmentManager();
//		    SupportMapFragment mapFragment =  (SupportMapFragment)
//		        fragmentManager.findFragmentById(R.id.map);
//		    map = mapFragment.getMap();
		    
			// Check if we were successful in obtaining the map.
			if (map != null)
			{
				// The Map is verified. It is now safe to manipulate the map.
				setupMarkerListener();
			}
		}
	}
	
	/**
	 * When click on Marker fire up GasStationInfoActivity.
	 * The selected Marker of the GasStationResult will pass through Intent.
	 */
	public void setupMarkerListener()
	{
		map.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(Marker marker)
			{
				//click on user Marker should not go any where
				if(isGas)
				{
					if(!marker.getTitle().equalsIgnoreCase(etAddress.getText().toString()))
					{
						Intent i = new Intent(getApplicationContext(), GasStationInfoActivity.class);
						for (int x = 0; x < gasStationResults.size(); x++)
						{
							if(marker.getTitle().equalsIgnoreCase(gasStationResults.get(x).getStation()))
							{
								i.putExtra("resultKey", gasStationResults.get(x));
							}
						}
						//try to store ArrayList by pass it to GasStationInfoActivity and pass it back
						i.putExtra("resultKeys", gasStationResults);
						startActivityForResult(i, GAS_MARKER_REQUEST_CODE);
					}
				}
				if(isAlternativeFuel)
				{
					if(!marker.getTitle().equalsIgnoreCase(etAddress.getText().toString()))
					{
						Intent i = new Intent(getApplicationContext(), 
								AlternativeFuelStationInfoActivity.class);
						for (int x = 0; x < alternativeFuelStationResults.size(); x++)
						{
							if(marker.getTitle().equalsIgnoreCase(alternativeFuelStationResults.get(x)
									.getStation_name()))
							{
								i.putExtra("resultKey2", alternativeFuelStationResults.get(x));
							}
						}
						//try to store ArrayList by pass it to 
						//AlternativeFuelStationInfoActivity and pass it back
						i.putExtra("resultKeys2", alternativeFuelStationResults);
						startActivityForResult(i, ALT_FUEL_MARKER_REQUEST_CODE);
					}
				}
				return false;
			}
		});
	}
	
	/**
	 * convert input address to LatLng
	 * @return LatLng
	 */
	public LatLng addressToLatLng()
	{
		LatLng myLocation = null;
		
		Geocoder coder = new Geocoder(this);
		List<Address> address;

		try {
		    //address = coder.getFromLocationName("1335 pacific ave, San Francisco, CA",5);
		    address = coder.getFromLocationName(etAddress.getText().toString(),5);
		    if (address == null) {
		        return null;
		    }
		    Address location = address.get(0);
		    location.getLatitude();
		    location.getLongitude();

		    myLocation = new LatLng(location.getLatitude(), location.getLongitude());
		    return myLocation;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return myLocation;
	}
	
	/**
	 * Go to user input location and set user Marker and result Markers.
	 * Remove all Marker on new search.
	 * @param gasStationResults
	 */
	public void putMarkerResult(ArrayList<GasStationResult> gasStationResults)
	{
		LatLng gasStationLatLng;
		//remove Markers and latLngs on new search
		if(!markers.isEmpty())
		{
			for (int x = 0; x < markers.size(); x++)
			{
				markers.get(x).remove();
			}
			markers.clear();
			latLngs.clear();
		}
		
		//go to user location and add user Marker
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 13));
		marker = map.addMarker(new MarkerOptions().position(myLatLng).title(etAddress.getText().toString()));
		markers.add(marker);
		latLngs.add(myLatLng);

		//add result Markers depends on the Json result
		for (int i = 0; i < gasStationResults.size(); i++)
		{
			gasStationLatLng = new LatLng(Double.parseDouble(gasStationResults.get(i).getLat()), 
					Double.parseDouble(gasStationResults.get(i).getLng()));
			marker = map.addMarker(new MarkerOptions()
				.position(gasStationLatLng)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.gas_station_icon))
				.title(gasStationResults.get(i).getStation())
				.snippet(gasStationResults.get(i).getAddress()));
			markers.add(marker);
			latLngs.add(gasStationLatLng);
		}
	}
	
	/**
	 * When click on search button.
	 * Call addressToLatLng() to convert input address to LatLng.
	 * Use AsyncHttpClient to do Json request.
	 * http://api.mygasfeed.com/stations/radius/37.795589/-122.417451/1/reg/price/f4xi4og4c8%20.json
	 * Request Url example: /stations/radius/(Latitude)/(Longitude)/(distance)/(fuel type)/(sort by)/apikey.json?callback=?
	 * Method: GET
	 * Distance - A number (miles) of the radius distance of stations according to the user's geo location
	 * Fuel Type - Argument types: reg,mid,pre or diesel. Which type of gas prices that will be returned.
	 * Sort By (Distance or Price) - Type arguments: price or distance. Gas stations will be sorted according to the argument.
	 * apikey - my Api key, get it by register from mygasfeed.com 
	 * Callback - Only needed for JSONP requests 
	 * @param v
	 */
	public void onGSSearch(View v)
	{
		//toggle isGas and isAlternativeFuel
		isGas = true;
		isAlternativeFuel = false;
		
		//Editor is set of changes
		Editor editPrefETAddress = prefETAddress.edit();
		//set editor to key / value format
		//"keyETAddress" key will store any string type into etAddress, after commit or apply
		editPrefETAddress.putString("keyETAddress", etAddress.getText().toString());
		//apply()		write to disk asynchronously in background
		editPrefETAddress.apply();
				
		myLatLng = addressToLatLng();
		double lat = myLatLng.latitude;
		double lng = myLatLng.longitude;
		
		AsyncHttpClient client = new AsyncHttpClient();
		
		retrieveSearchOptionsFromPreference(); 
		String mLatAndLng = String.valueOf(lat) + "/" + String.valueOf(lng);
		String mFullUrl = API_URL_PART_00 + mLatAndLng + "/" + searchOptionDistance
				+ "/" + searchOptionFuelType + "/" + searchOptionSortBy 
				+ "/" + "f4xi4og4c8%20.json";
//		Log.d("debug", "url=" + mFullUrl);
		client.get(mFullUrl, new JsonHttpResponseHandler()
		{
			
//		client.get("http://api.mygasfeed.com/stations/radius/" + 
//				String.valueOf(lat) + "/" + String.valueOf(lng) + 
//				"/1/reg/price/f4xi4og4c8%20.json", new JsonHttpResponseHandler()
//		{
			@Override
			public void onSuccess(JSONObject response)
			{
				//JSONArray holds real Json result
				JSONArray gasStationJsonResults = null;
				//parse the response from JSONArray
				try
				{
					gasStationJsonResults = response.getJSONArray("stations");
					//each new search will clear the last search result
					gasStationResults.clear();
					//call GasStationResult.fromJSONArray() to do the parsing
					gasStationResults.addAll(GasStationResult.fromJSONArray(gasStationJsonResults));
					//show GasStationResult in logcat
					Log.d("DEBUG", gasStationResults.toString());
					putMarkerResult(gasStationResults);
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	protected void onPause()
	{
	    super.onPause();
	    Log.d("DEBUG", "inside onPause()");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{  
	    if (resultCode == RESULT_OK)
	    {
	    	checkActivityRequestCode(requestCode, data);
	    }
	    else
	    {
	    	Toast.makeText(this, "calling activity is not returning ok!!", Toast.LENGTH_SHORT).show();
	    } 
	}
	
	//
	private void checkActivityRequestCode(int requestCode, Intent data)
	{
		if (requestCode == OPTION_REQUEST_CODE)
		{
			// Extract name value from result extras
	    	//String name = data.getExtras().getString("name");
	    	// Used preference to save the data from ImageSearchOptionActivity.java, not bundle
	    	// retrieve from preference, set the to variables, so that the api can use
//			retrieveSearchOptionsFromPreference();
			Toast.makeText(this, "back from search option", Toast.LENGTH_SHORT).show();
		} 
		else if (requestCode == OTHER_ENERGY_OPTION_REQUEST_CODE)
		{
			// do whatever we need to
			Toast.makeText(this, "back from other energy search option", Toast.LENGTH_SHORT).show();
		}
		else if (requestCode == GAS_MARKER_REQUEST_CODE)
		{
//			gasStationResults2.addAll(
//	    			(Collection<? extends GasStationResult>) data.getSerializableExtra("resultKeys"));
			Toast.makeText(this, "back from selected gas station info", Toast.LENGTH_SHORT).show();
		}
		else if (requestCode == ALT_FUEL_MARKER_REQUEST_CODE)
		{
//			alternativeFuelStationResults2.addAll(
//	    			(Collection<? extends AlternativeFuelStationResult>) 
//	    			data.getSerializableExtra("resultKeys2"));
			Toast.makeText(this, "back from selected alternative fuel station info", 
					Toast.LENGTH_SHORT).show();
		}
	}
	
	//retrieve the value and put it into the EditText
	public void onLoadETAddress()
	{
		//load the save data with key "text", if fail load ""
		String inputAddress = prefETAddress.getString("keyETAddress", "");
		etAddress.setText(inputAddress);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// use the onOptionsItemSelected(MenuItem item) below
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId())
		{
			case R.id.gasStationSearchOptions:
				//Toast.makeText(this, "Search option ", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(this, GasStationSearchOptionActivity.class);
				startActivityForResult(i, OPTION_REQUEST_CODE);
				break;
			case R.id.OtherEnergyStationSearchOptions:
				//Toast.makeText(this, "Search option ", Toast.LENGTH_SHORT).show();
				Log.d("DEBUG", "inside onOptionsItemSelected() enter OtherEnergyStationSearchOptions");
				Intent iOtherEnergy = new Intent(this, AlternativeFuelStationSearchOptionActivity.class);
				//mt: I let you create this new activity in your pc. Feel free to change the name if you have a better name. 
				startActivityForResult(iOtherEnergy, OTHER_ENERGY_OPTION_REQUEST_CODE);
				break;
			default:
				break;
		}
		return true;
	}
	
	private void retrieveSearchOptionsFromPreference()
	{
		if(isGas)
		{
			// Restore preferences from GasStationSearchOptionActivity
	        SharedPreferences prefs = getSharedPreferences(GasStationSearchOptionActivity.PREFS_NAME, 0);
	        searchOptionFuelType =  prefs.getString(
	        		GasStationSearchOptionActivity.SAVED_SEARCH_FUEL_TYPE, FUEL_TYPE_DEFAULT_VALUE);
	        searchOptionSortBy =  prefs.getString(
	        		GasStationSearchOptionActivity.SAVED_SEARCH_SORT_BY, SORT_BY_DEFAULT_VALUE);
	        searchOptionDistance =  prefs.getString(
	        		GasStationSearchOptionActivity.SAVED_SEARCH_DISTANCE, DISTANCE_DEFAULT_MILES);
	        
	        Toast.makeText(this, "Search option: fuel type, sort by, distance " + searchOptionFuelType
	        		+ searchOptionSortBy + searchOptionDistance,
	        		Toast.LENGTH_SHORT).show(); 
		}
		if(isAlternativeFuel)
		{
			// Restore preferences from AlternativeFuelStationSearchOptionActivity
	        SharedPreferences prefs = getSharedPreferences(
	        		AlternativeFuelStationSearchOptionActivity.PREFS_NAME, 0);
	        searchOptionAFSDistance = prefs.getString(
	        		AlternativeFuelStationSearchOptionActivity.SAVED_SEARCH_DISTANCE, 
	        		AlternativeFuelStationSearchOptionActivity.DEFAULT_DISTANCE);
	        searchOptionMaxNumOfResults = prefs.getString(
	        		AlternativeFuelStationSearchOptionActivity.SAVED_SEARCH_MAX_RESULTS, 
	        		AlternativeFuelStationSearchOptionActivity.DEFAULT_MAX_RESULTS);
	        searchOptionServiceStatus = prefs.getString(
	        		AlternativeFuelStationSearchOptionActivity.SAVED_SEARCH_SERVICE_STATUS, 
	        		AlternativeFuelStationSearchOptionActivity.DEFAULT_SERVICE_STATUS);
	        if(searchOptionServiceStatus.equalsIgnoreCase("in service"))
			{
	        	searchOptionServiceStatus = "E";
			}
			else if(searchOptionServiceStatus.equalsIgnoreCase("out of service"))
			{
				searchOptionServiceStatus = "P";
			}
			else
			{
				searchOptionServiceStatus = "all";
			}
	        searchOptionAccessBy = prefs.getString(
	        		AlternativeFuelStationSearchOptionActivity.SAVED_SEARCH_ACCESS_BY, 
	        		AlternativeFuelStationSearchOptionActivity.DEFAULT_ACCESS_BY);
	        searchOptionEVChargingLevel = prefs.getString(
	        		AlternativeFuelStationSearchOptionActivity.SAVED_SEARCH_EV_CHARGING_LEVEL, 
	        		AlternativeFuelStationSearchOptionActivity.DEFAULT_EV_CHARGING_LEVEL);
	        searchOptionAFSFuelType = prefs.getString(
	        		AlternativeFuelStationSearchOptionActivity.SAVED_SEARCH_AFS_Fuel_Type, 
	        		AlternativeFuelStationSearchOptionActivity.DEFAULT_AFS_Fuel_Type);
	        
	        Toast.makeText(this, "Search option: fuel type, sort by, distance " + searchOptionFuelType
	        		+ searchOptionSortBy + searchOptionDistance,
	        		Toast.LENGTH_SHORT).show(); 
		}
	}

	//
	public void onAFSSearch(View v)
	{
		//toggle isGas and isAlternativeFuel
		isGas = false;
		isAlternativeFuel = true;
				
		Editor editPrefETAddress = prefETAddress.edit();
		editPrefETAddress.putString("keyETAddress", etAddress.getText().toString());
		editPrefETAddress.apply();

		myLatLng = addressToLatLng();
		double lat = myLatLng.latitude;
		double lng = myLatLng.longitude;

		AsyncHttpClient client = new AsyncHttpClient();
		
		retrieveSearchOptionsFromPreference();

		client.get("http://developer.nrel.gov/api/alt-fuel-stations/v1/nearest.json?" + 
				"api_key=CZS4QIQLqPVgCZceUyat60w7icj1AbI3nItWnbXR" + 
				"&latitude=" + String.valueOf(lat) + 
				"&longitude=" + String.valueOf(lng) + 
				"&radius=" + searchOptionAFSDistance +
				"&limit=" + searchOptionMaxNumOfResults +
				"&status=" + searchOptionServiceStatus + 
				"&access=" + searchOptionAccessBy + 
				"&ev_charging_level=" + searchOptionEVChargingLevel +
				"&fuel_type=" + searchOptionAFSFuelType
				, new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONObject response)
			{
				JSONArray alternativeFuelStationJsonResults = null;
				try
				{
					alternativeFuelStationJsonResults = response.getJSONArray("fuel_stations");
					alternativeFuelStationResults.clear();
					alternativeFuelStationResults.addAll(
							AlternativeFuelStationResult.fromJSONArray(alternativeFuelStationJsonResults));
					Log.d("DEBUG", alternativeFuelStationResults.toString());
					putMarkerResult2(alternativeFuelStationResults);
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Go to user input location and set user Marker and result Markers.
	 * Remove all Marker on new search.
	 * @param alternativeFuelStationResults
	 */
	public void putMarkerResult2(ArrayList<AlternativeFuelStationResult> alternativeFuelStationResults)
	{
		LatLng alternativeFuelStationLatLng;
		//remove Markers and latLngs on new search
		if(!markers.isEmpty())
		{
			for (int x = 0; x < markers.size(); x++)
			{
				markers.get(x).remove();
			}
			markers.clear();
			latLngs.clear();
		}
		
		//go to user location and add user Marker
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 13));
		marker = map.addMarker(new MarkerOptions().position(myLatLng).title(etAddress.getText().toString()));
		markers.add(marker);
		latLngs.add(myLatLng);

		//add result Markers depends on the Json result
		for (int i = 0; i < alternativeFuelStationResults.size(); i++)
		{
			alternativeFuelStationLatLng = new LatLng(
					Double.parseDouble(alternativeFuelStationResults.get(i).getLatitude()), 
					Double.parseDouble(alternativeFuelStationResults.get(i).getLongitude()));
			marker = map.addMarker(new MarkerOptions()
				.position(alternativeFuelStationLatLng)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.alternative_fuel_station_icon))
				.title(alternativeFuelStationResults.get(i).getStation_name())
				.snippet(alternativeFuelStationResults.get(i).getStreet_address()));
			markers.add(marker);
			latLngs.add(alternativeFuelStationLatLng);
		}
	}
}
