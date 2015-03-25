package com.example.mymapdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AlternativeFuelStationSearchOptionActivity extends Activity
{
	//distance
	EditText etAFSDistance;
	String strAFSDistance;
	String strDefaultDistance = "1";
	
	//limit max number of result
	EditText etMaxNumOfResults;
	String strMaxNumOfResults;
	String strDefaultNumOfResults = "10";
	
	//find station is in service, out of service, or include both
	Spinner spServiceStatus;
	ArrayAdapter<CharSequence> aAdapterServiceStatus;
	String strServiceStatus;
	
	//find station access by public, private, or include both
	Spinner spAccessBy;
	ArrayAdapter<CharSequence> aAdapterAccessBy;
	String strAccessBy;
	
	//find station with specific charging level
	Spinner spEVChargingLevel;
	ArrayAdapter<CharSequence> aAdapterEVChargingLevel;
	String strEVChargingLevel;
	
	//find station with specific alternative fuel type
	Spinner spAFSFuelType;
	ArrayAdapter<CharSequence> aAdapterAFSFuelType;
	String strAFSFuelType;
	
	//save each search options by SharedPreferences
	private SharedPreferences prefAltFuelSearchOption;
	public static final String PREFS_NAME = "MyPrefsAlternativeFuelStationSearchOptionFile";
	public static final String SAVED_SEARCH_DISTANCE = "keyAFSDistance";
	public static final String SAVED_SEARCH_MAX_RESULTS = "keyMaxNumOfResults";
	public static final String SAVED_SEARCH_SERVICE_STATUS = "keyServiceStatus"; 
	public static final String SAVED_SEARCH_ACCESS_BY = "keyAccessBy";
	public static final String SAVED_SEARCH_EV_CHARGING_LEVEL = "keyEVChargingLevel";
	public static final String SAVED_SEARCH_AFS_Fuel_Type = "keyAFSFuelType";
	public static final String DEFAULT_DISTANCE = "1";
	public static final String DEFAULT_MAX_RESULTS = "10";
	public static final String DEFAULT_SERVICE_STATUS = "all"; 
	public static final String DEFAULT_ACCESS_BY = "all";
	public static final String DEFAULT_EV_CHARGING_LEVEL = "all";
	public static final String DEFAULT_AFS_Fuel_Type = "all";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alternative_fuel_station_search_option);
		
		//access SharedPreferences
		//default way
//		prefAltFuelSearchOption = PreferenceManager.getDefaultSharedPreferences(this);
		
		//way access across activities by give a name, 
		//other activities use getSharedPreferences() to assign to their SharedPreferences object
		prefAltFuelSearchOption = getSharedPreferences(PREFS_NAME, 0);
		
		setupView();
		onLoadAltFuelSearchOption();
	}
	
	private void setupView()
	{
		Log.d("DEBUG", "inside setupView()");
		//
		etAFSDistance = (EditText) findViewById(R.id.etAFSDistance);
		strAFSDistance = etAFSDistance.getText().toString();
		
		//
		etMaxNumOfResults = (EditText) findViewById(R.id.etMaxNumOfResults);
		strMaxNumOfResults = etMaxNumOfResults.getText().toString();
		
		//
		spServiceStatus = (Spinner) findViewById(R.id.spServiceStatus);
		aAdapterServiceStatus = ArrayAdapter.createFromResource(this,
		        R.array.afs_spServiceStatus_arrays, android.R.layout.simple_spinner_item);
		aAdapterServiceStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spServiceStatus.setAdapter(aAdapterServiceStatus);
		spServiceStatus.setOnItemSelectedListener(new MyOnItemSelectedListener());
		strServiceStatus = String.valueOf(spServiceStatus.getSelectedItem());
		
		//
		spAccessBy = (Spinner) findViewById(R.id.spAccessBy);
		aAdapterAccessBy = ArrayAdapter.createFromResource(this,
		        R.array.afs_spAccessBy_arrays, android.R.layout.simple_spinner_item);
		aAdapterAccessBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spAccessBy.setAdapter(aAdapterAccessBy);
		spAccessBy.setOnItemSelectedListener(new MyOnItemSelectedListener());
		strAccessBy = String.valueOf(spAccessBy.getSelectedItem());
		
		//
		spEVChargingLevel = (Spinner) findViewById(R.id.spEVChargingLevel);
		aAdapterEVChargingLevel = ArrayAdapter.createFromResource(this,
		        R.array.afs_spEVChargingLevel_arrays, android.R.layout.simple_spinner_item);
		aAdapterEVChargingLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spEVChargingLevel.setAdapter(aAdapterEVChargingLevel);
		spEVChargingLevel.setOnItemSelectedListener(new MyOnItemSelectedListener());
		strEVChargingLevel = String.valueOf(spEVChargingLevel.getSelectedItem());
		
		//
		spAFSFuelType = (Spinner) findViewById(R.id.spAFSFuelType);
		aAdapterAFSFuelType = ArrayAdapter.createFromResource(this,
		        R.array.afs_spFuelType_arrays, android.R.layout.simple_spinner_item);
		aAdapterAFSFuelType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spAFSFuelType.setAdapter(aAdapterAFSFuelType);
		spAFSFuelType.setOnItemSelectedListener(new MyOnItemSelectedListener());
		strAFSFuelType = String.valueOf(spAFSFuelType.getSelectedItem());
	}
	
	//
	public void onSaveAltFuelSearchOption(View v)
	{
		//
		strAFSDistance = etAFSDistance.getText().toString();
		if(strAFSDistance.isEmpty() || Double.parseDouble(strAFSDistance) <= 0)
		{
			strAFSDistance = strDefaultDistance;
		}
		//
		strMaxNumOfResults = etMaxNumOfResults.getText().toString();
		if(strMaxNumOfResults.isEmpty() || Integer.parseInt(strMaxNumOfResults) < 1)
		{
			strMaxNumOfResults = strDefaultNumOfResults;
		}
		
		//
		strServiceStatus = String.valueOf(spServiceStatus.getSelectedItem());
		
		//
		strAccessBy = String.valueOf(spAccessBy.getSelectedItem());
		
		//
		strEVChargingLevel = String.valueOf(spEVChargingLevel.getSelectedItem());

		//
		strAFSFuelType = String.valueOf(spAFSFuelType.getSelectedItem());
		
		//save changes in key/value use Editor on SharedPreferences
		Editor editPrefAltFuelSearchOption;
		editPrefAltFuelSearchOption = prefAltFuelSearchOption.edit();
		editPrefAltFuelSearchOption.putString(SAVED_SEARCH_DISTANCE, strAFSDistance);
		editPrefAltFuelSearchOption.putString(SAVED_SEARCH_MAX_RESULTS, strMaxNumOfResults);
		editPrefAltFuelSearchOption.putString(SAVED_SEARCH_SERVICE_STATUS, strServiceStatus);
		editPrefAltFuelSearchOption.putString(SAVED_SEARCH_ACCESS_BY, strAccessBy);
		editPrefAltFuelSearchOption.putString(SAVED_SEARCH_EV_CHARGING_LEVEL, strEVChargingLevel);
		editPrefAltFuelSearchOption.putString(SAVED_SEARCH_AFS_Fuel_Type, strAFSFuelType);
		editPrefAltFuelSearchOption.apply();

		//
		setResult(RESULT_OK);
		finish();
	}
	
	//retrieve the values and put them back
	public void onLoadAltFuelSearchOption()
	{
		strAFSDistance = prefAltFuelSearchOption.getString(SAVED_SEARCH_DISTANCE, DEFAULT_DISTANCE);
		etAFSDistance.setText(strAFSDistance);
		strMaxNumOfResults = prefAltFuelSearchOption.getString(SAVED_SEARCH_MAX_RESULTS, DEFAULT_MAX_RESULTS);
		etMaxNumOfResults.setText(strMaxNumOfResults);
		strServiceStatus = prefAltFuelSearchOption.getString(SAVED_SEARCH_SERVICE_STATUS, DEFAULT_SERVICE_STATUS);
		spServiceStatus.setSelection(getIndex(spServiceStatus, strServiceStatus));
		strAccessBy = prefAltFuelSearchOption.getString(SAVED_SEARCH_ACCESS_BY, DEFAULT_ACCESS_BY);
		spAccessBy.setSelection(getIndex(spAccessBy, strAccessBy));
		strEVChargingLevel = prefAltFuelSearchOption.getString(SAVED_SEARCH_EV_CHARGING_LEVEL, DEFAULT_EV_CHARGING_LEVEL);
		spEVChargingLevel.setSelection(getIndex(spEVChargingLevel, strEVChargingLevel));
		strAFSFuelType = prefAltFuelSearchOption.getString(SAVED_SEARCH_AFS_Fuel_Type, DEFAULT_AFS_Fuel_Type);
		spAFSFuelType.setSelection(getIndex(spAFSFuelType, strAFSFuelType));
	}
	
	//
	private int getIndex(Spinner spinner, String myString)
	{
		int index = 0;
		for (int i = 0; i < spinner.getCount(); i++)
		{
			if (spinner.getItemAtPosition(i).equals(myString))
			{
				index = i;
			}
		}
		return index;
	}
}
