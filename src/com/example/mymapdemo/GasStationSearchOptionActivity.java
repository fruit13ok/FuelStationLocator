package com.example.mymapdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GasStationSearchOptionActivity extends Activity implements OnItemSelectedListener {

	
	public static final String SAVED_SEARCH_FUEL_TYPE = "savedSearchFuelType";
	public static final String SAVED_SEARCH_SORT_BY = "savedSearchSortBy";
	public static final String SAVED_SEARCH_DISTANCE = "savedSearchDistance"; 
	public static final String PREFS_NAME = "MyPrefsGasStationSearchOptionFile";
	private static final String DISTANCE_DEFAULT_MILES = "5";
	private static final String FUEL_TYPE_DEFAULT_VALUE = "reg";
	private static final String SORT_BY_DEFAULT_VALUE = "distance";
	
	Button btnSave;
	Spinner spinnerFuelType;
	Spinner spinnerSortBy;
	EditText etDistance;
	TextView tvSearchOption;
	SharedPreferences prefs;
	String mLastSortBySelection;
	String mLastFuelTypeSelection;
	String mLastDistanceEnteredValue;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gas_station_search_option);
		setupView();
		addListenerOnButtonSave();
		//set listener on spinner field
		spinnerFuelType.setOnItemSelectedListener(this);
		spinnerSortBy.setOnItemSelectedListener(this);
		
	}
    
	private void setupView() {
		spinnerFuelType = (Spinner) findViewById(R.id.spinnerFuelType);
		spinnerSortBy = (Spinner) findViewById(R.id.spinnerSortBy);
		tvSearchOption = (TextView) findViewById(R.id.tvSearchOption); 
		
		retrieveSearchOptionsFromPreference();
		setSpinnerFuelType();
		setSpinnerSortBy();
		
		tvSearchOption.setText(Html.fromHtml("<b>" + 
		           getString(R.string.gas_station_search_options_label) 	+ "</b>"));
		
		etDistance = (EditText) findViewById(R.id.etDistance);	
		etDistance.setText(mLastDistanceEnteredValue);
		
	}

	public void setSpinnerFuelType() {
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//		        R.array.fuel_type_array, android.R.layout.simple_spinner_item);
				R.array.fuel_type_array, R.layout.my_simple_spinner_item);
		// Specify the layout to use when the list of choices appears
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerFuelType.setAdapter(adapter);
		spinnerFuelType.setSelection(getIndex(spinnerFuelType, mLastFuelTypeSelection));
		
	}
	
	public void setSpinnerSortBy() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	        	R.array.sort_by_array, R.layout.my_simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerSortBy.setAdapter(adapter);
		spinnerSortBy.setSelection(getIndex(spinnerSortBy, mLastSortBySelection));
		
	}
	
	private int getIndex(Spinner spinner, String myString) {
		int index = 0;
		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).equals(myString)) {
				index = i;
			}
		}
		return index;
	}
	 
	 private void retrieveSearchOptionsFromPreference() {
		 
	        // Restore preferences
	        SharedPreferences prefs = getSharedPreferences(GasStationSearchOptionActivity.PREFS_NAME, 0);
	        mLastFuelTypeSelection =  prefs.getString(
	        		GasStationSearchOptionActivity.SAVED_SEARCH_FUEL_TYPE, FUEL_TYPE_DEFAULT_VALUE);
	        mLastSortBySelection =  prefs.getString(
	        		GasStationSearchOptionActivity.SAVED_SEARCH_SORT_BY, SORT_BY_DEFAULT_VALUE);
	        mLastDistanceEnteredValue =  prefs.getString(
	        		GasStationSearchOptionActivity.SAVED_SEARCH_DISTANCE, DISTANCE_DEFAULT_MILES);
	        
//	        Toast.makeText(this, "Search option: fuel type, sort by, distance " + mLastFuelTypeSelection
//	        		+ mLastSortBySelection + mLastDistanceEnteredValue,
//	        		Toast.LENGTH_SHORT).show(); 
		}
	//*********************addListenerOnButtonSave******************************
	//handle Save button clicks
	public void addListenerOnButtonSave() {
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveEnteredDistance(etDistance.getText().toString());
				//return to GasStationSearchActivity, 
				setResult(RESULT_OK); // set result code and bundle data for response
				finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gas_station_search_option, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}

 
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d("DEBUG", "onItemSelected");
		Spinner spinner = (Spinner) parent;

		switch (spinner.getId()) {

			case (R.id.spinnerFuelType):
				spinnerFuelType = (Spinner) findViewById(R.id.spinnerFuelType);
				saveFuelTypeItemSelected(spinnerFuelType);
				break;
	
			case (R.id.spinnerSortBy):
				spinnerSortBy = (Spinner) findViewById(R.id.spinnerSortBy);
				saveSortByItemSelected(spinnerSortBy);
				break;
		}
	}

	private void saveFuelTypeItemSelected(Spinner spinnerFuelType) {
		prefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putString(SAVED_SEARCH_FUEL_TYPE, spinnerFuelType.getSelectedItem().toString());
        prefEditor.commit();
//        Toast.makeText(getApplicationContext(), 
//                "On Item Select for fuel type : \n" +  spinnerFuelType.getSelectedItem().toString() ,
//                Toast.LENGTH_SHORT).show();
	}

	private void saveSortByItemSelected(Spinner spinnerSortBy) {
		 
		prefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putString(SAVED_SEARCH_SORT_BY, spinnerSortBy.getSelectedItem().toString());
        prefEditor.commit();
        Toast.makeText(getApplicationContext(), 
                "On Item Select for image color : \n" +  spinnerSortBy.getSelectedItem().toString() ,
                Toast.LENGTH_SHORT).show();
	}

	 
	private void saveEnteredDistance(String distance) {
		
		if (distance.length() == 0) distance = DISTANCE_DEFAULT_MILES;
		 
		prefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putString(SAVED_SEARCH_DISTANCE, distance);
        prefEditor.commit();
//        Toast.makeText(getApplicationContext(), 
//                "Enter distance is : \n" +   distance ,
//                Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
		Log.d("DEBUG", "onNothingSelected");
		 Toast.makeText(getApplicationContext(), 
	                "onNothingSelected : \n"  ,
	                Toast.LENGTH_SHORT).show();
		
	}

}