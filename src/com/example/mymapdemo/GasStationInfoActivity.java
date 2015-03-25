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
/**
 * display the selected gas station[Marker] info and prices.
 * @author Liu
 */
public class GasStationInfoActivity extends Activity
{
	GasStationResult gasStationResult;
	ArrayList<GasStationResult> gasStationResults = new ArrayList<GasStationResult>();
	
	TextView tvGasStationName;
	TextView tvGasStationAddress;
	TextView tvGasStationDistance;
	TextView tvRegularPrice;
	TextView tvMidgradePrice;
	TextView tvPremiumPrice;
	TextView tvDieselPrice;
	Button btnBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gas_station_info);
		
		setupViews();
	}

	/**
	 * Get the Intent data.
	 * Put them in each TextView.
	 */
	private void setupViews()
    {
		gasStationResult = (GasStationResult) getIntent().getSerializableExtra("resultKey");
		gasStationResults = (ArrayList<GasStationResult>) getIntent().getSerializableExtra("resultKeys");
		
		tvGasStationName = (TextView) findViewById(R.id.tvGasStationName);
		tvGasStationAddress = (TextView) findViewById(R.id.tvGasStationAddress);
		tvGasStationDistance = (TextView) findViewById(R.id.tvGasStationDistance);
		tvRegularPrice = (TextView) findViewById(R.id.tvRegularPrice);
		tvMidgradePrice = (TextView) findViewById(R.id.tvMidgradePrice);
		tvPremiumPrice = (TextView) findViewById(R.id.tvPremiumPrice);
		tvDieselPrice = (TextView) findViewById(R.id.tvDieselPrice);
		btnBack = (Button) findViewById(R.id.btnBack);
		
		tvGasStationName.setText(gasStationResult.getStation());
		tvGasStationAddress.setText(
				gasStationResult.getAddress() + ", " + 
				gasStationResult.getCity() + ", " + 
				gasStationResult.getRegion());
		tvGasStationDistance.setText(gasStationResult.getDistance());
		tvRegularPrice.setText(gasStationResult.getReg_price());
		tvMidgradePrice.setText(gasStationResult.getMid_price());
		tvPremiumPrice.setText(gasStationResult.getPre_price());
		tvDieselPrice.setText(gasStationResult.getDiesel_price());
    }
	
	/**
	 * Go back to the MainActivity.
	 * follow up note:
	 * to retain the map data
	 * http://wptrafficanalyzer.in/blog/retain-markers-on-screen-rotation-in-google-maps-android-api-v2-using-parcelable-latlng-points/
	 * @param v
	 */
	public void onBack(View v)
	{
		Log.d("DEBUG", "inside onBack()");
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		
		i.putExtra("resultKeys", gasStationResults);
		setResult(RESULT_OK, i);
		finish();
		
//		startActivity(i);
	}
}
