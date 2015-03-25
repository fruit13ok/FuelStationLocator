package com.example.mymapdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlternativeFuelStationInfoActivity extends Activity
{
	AlternativeFuelStationResult alternativeFuelStationResult;
	ArrayList<AlternativeFuelStationResult> alternativeFuelStationResults = 
			new ArrayList<AlternativeFuelStationResult>();
	
	TextView tvAlternativeFuelStationName;
	TextView tvAlternativeFuelStationAddress;
	TextView tvAlternativeFuelStationDistance;
	TextView tvAlternativeFuelStationPhone;
	TextView tvAlternativeFuelStationAccessDaysTime;
	TextView tvAlternativeFuelStationCardsAccepted;
	TextView tvAlternativeFuelStationExpectedDate;		//date the station is expected to open
	TextView tvAlternativeFuelStationBDBlends;			//has biodiesel blends
	TextView tvAlternativeFuelStationE85BlenderPump;	//has ethanol blends
	TextView tvAlternativeFuelStationLPGPrimary;		//has propane
	TextView tvAlternativeFuelStationNGFillTypeCode;	//type of dispensing capability
	TextView tvAlternativeFuelStationNGPsi;				//has PSI pressures
	TextView tvAlternativeFuelStationNGVehicleClass;	//maximum vehicle size
	TextView tvAlternativeFuelStationEVLevel1EvseNum;	//number of Level 1 EVSE (standard 110V outlet)
	TextView tvAlternativeFuelStationEVLevel2EvseNum;	//number of Level 1 EVSE (J1772 connector)
	TextView tvAlternativeFuelStationEVDCFastNum;		//number of DC Fast Chargers
	TextView tvAlternativeFuelStationEVOtherEvse;		//number of other EVSE
	
	Button btnBack2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alternative_fuel_station_info);
		
		setupViews();
	}
	
	/**
	 * Get the Intent data.
	 * Put them in each TextView.
	 */
	private void setupViews()
    {
		alternativeFuelStationResult = 
				(AlternativeFuelStationResult) getIntent().getSerializableExtra("resultKey2");
		alternativeFuelStationResults = 
				(ArrayList<AlternativeFuelStationResult>) getIntent().getSerializableExtra("resultKeys2");
		
		tvAlternativeFuelStationName = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationName);
		tvAlternativeFuelStationAddress = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationAddress);
		tvAlternativeFuelStationDistance = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationDistance);
		tvAlternativeFuelStationPhone = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationPhone);
		tvAlternativeFuelStationAccessDaysTime = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationAccessDaysTime);
		tvAlternativeFuelStationCardsAccepted = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationCardsAccepted);
		tvAlternativeFuelStationExpectedDate = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationExpectedDate);
		tvAlternativeFuelStationBDBlends = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationBDBlends);
		tvAlternativeFuelStationE85BlenderPump = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationE85BlenderPump);
		tvAlternativeFuelStationLPGPrimary = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationLPGPrimary);
		tvAlternativeFuelStationNGFillTypeCode = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationNGFillTypeCode);
		tvAlternativeFuelStationNGPsi = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationNGPsi);
		tvAlternativeFuelStationNGVehicleClass = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationNGVehicleClass);
		tvAlternativeFuelStationEVLevel1EvseNum = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationEVLevel1EvseNum);
		tvAlternativeFuelStationEVLevel2EvseNum = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationEVLevel2EvseNum);
		tvAlternativeFuelStationEVDCFastNum = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationEVDCFastNum);
		tvAlternativeFuelStationEVOtherEvse = 
				(TextView) findViewById(R.id.tvAlternativeFuelStationEVOtherEvse);
		
		btnBack2 = (Button) findViewById(R.id.btnBack2);
		
		tvAlternativeFuelStationName.setText(
				alternativeFuelStationResult.getStation_name());
		tvAlternativeFuelStationAddress.setText(
				"> " + alternativeFuelStationResult.getStreet_address() + ", " +
				alternativeFuelStationResult.getCity() + ", " +
				alternativeFuelStationResult.getState() + ", " +
				alternativeFuelStationResult.getZip());
		tvAlternativeFuelStationDistance.setText(
				"> " + alternativeFuelStationResult.getDistance() + " mile(s)");
		tvAlternativeFuelStationPhone.setText(
				"> " + alternativeFuelStationResult.getStation_phone());
		tvAlternativeFuelStationAccessDaysTime.setText(
				"> Hours of operation: " + 
				alternativeFuelStationResult.getAccess_days_time());
		tvAlternativeFuelStationCardsAccepted.setText(
				"> Type of card(s) accepted: " + 
				alternativeFuelStationResult.getCards_accepted());
		tvAlternativeFuelStationExpectedDate.setText(
				"> Station expected to open: " + 
				alternativeFuelStationResult.getExpected_date());
		tvAlternativeFuelStationBDBlends.setText(
				"> Biodiesel availability: " + 
				alternativeFuelStationResult.getBd_blends());
		tvAlternativeFuelStationE85BlenderPump.setText(
				"> Mid-level ethanol E85 availability: " + 
				alternativeFuelStationResult.getE85_blender_pump());
		tvAlternativeFuelStationLPGPrimary.setText(
				"> Propane availability: " + 
				alternativeFuelStationResult.getLpg_primary());
		tvAlternativeFuelStationNGFillTypeCode.setText(
				"> Type of dispensing capability: " + 
				alternativeFuelStationResult.getNg_fill_type_code());
		tvAlternativeFuelStationNGPsi.setText(
				"> PSI pressures availability: " + 
				alternativeFuelStationResult.getNg_psi());
		tvAlternativeFuelStationNGVehicleClass.setText(
				"> Maximum vehicle size: " + 
				alternativeFuelStationResult.getNg_vehicle_class());
		tvAlternativeFuelStationEVLevel1EvseNum.setText(
				"> Number of Level 1 EVSE (standard 110V outlet): " + 
				alternativeFuelStationResult.getEv_level1_evse_num());
		tvAlternativeFuelStationEVLevel2EvseNum.setText(
				"> Number of Level 1 EVSE (J1772 connector): " + 
				alternativeFuelStationResult.getEv_level2_evse_num());
		tvAlternativeFuelStationEVDCFastNum.setText(
				"> Number of DC Fast Chargers: " + 
				alternativeFuelStationResult.getEv_dc_fast_num());
		tvAlternativeFuelStationEVOtherEvse.setText(
				"> Number of other EVSE: " + 
				alternativeFuelStationResult.getEv_other_evse());
    }
	
	/**
	 * Go back to the MainActivity.
	 * follow up note:
	 * to retain the map data
	 * http://wptrafficanalyzer.in/blog/retain-markers-on-screen-rotation-in-google-maps-android-api-v2-using-parcelable-latlng-points/
	 * @param v
	 */
	public void onBack2(View v)
	{
		Log.d("DEBUG", "inside onBack2()");
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		
		i.putExtra("resultKeys2", alternativeFuelStationResults);
		setResult(RESULT_OK, i);
		finish();
	}
}
