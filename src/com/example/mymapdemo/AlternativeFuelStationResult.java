package com.example.mymapdemo;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlternativeFuelStationResult implements Serializable
{
    private static final long serialVersionUID = -4657897852581700548L;
    
    //I think most of these data can be useful for user
    /*
     access_days_time": "MO: 24 hours; TU: 24 hours; WE: 24 hours; TH: 24 hours; FR: 24 hours; SA: 24 hours; SU: 24 hours",
      "cards_accepted": null,
      "date_last_confirmed": "2014-07-21",
      "expected_date": null,
      "fuel_type_code": "ELEC",
      "id": 58650,
      "groups_with_access_code": "Public - Card key at all times",
      "open_date": null,
      "owner_type_code": null,
      "status_code": "E",
      "station_name": "ACE Parking / 1776 Sacramento",
      "station_phone": "800-663-5633",
      "updated_at": "2014-07-21T07:31:54Z",
      "geocode_status": "GPS",
      "latitude": 37.7914615,
      "longitude": -122.4220511,
      "city": "San Francisco",
      "intersection_directions": null,
      "plus4": null,
      "state": "CA",
      "street_address": "1776 Sacramento Street",
      "zip": "94109",
      "bd_blends": null,
      "e85_blender_pump": null,
      "ev_connector_types": [
        "J1772"
      ],
      "ev_dc_fast_num": null,
      "ev_level1_evse_num": null,
      "ev_level2_evse_num": 1,
      "ev_network": "SemaCharge Network",
      "ev_network_web": "http://www.semacharge.com/",
      "ev_other_evse": null,
      "hy_status_link": null,
      "lpg_primary": null,
      "ng_fill_type_code": null,
      "ng_psi": null,
      "ng_vehicle_class": null,
      "ev_network_ids": {
        "station": [
          "594"
        ],
        "posts": [
          "395"
        ]
      },
      "distance": 0.39242
     */
    //variables for station info
    private String station_name;
    private String street_address;
    private String intersection_directions;	//street intersection
    private String city;
    private String state;
    private String zip;
    private String station_phone;
    private String latitude;
    private String longitude;
    private String distance;
    private String access_days_time;		//Hours of operation
    private String cards_accepted;			//what kind of card
										    //Value 	Description
										    //A 	American Express
										    //D 	Discover
										    //M 	MasterCard
										    //V 	VISA
										    //Cash 	
										    //Checks 	
										    //CFN 	
										    //CleanEnergy 	
										    //FuelMan 	
										    //GasCard 	
										    //PHH 	PHH Services
										    //Voyager 	
										    //Wright_Exp 	WEX
    private String expected_date;			//date, date the station is expected to open
    
    //variables for alternative fueling info
    private String bd_blends;				//biodiesel stations
    private String e85_blender_pump;		//boolean, providing mid-level ethanol blends
    private String lpg_primary;				//boolean, propane stations
    private String ng_fill_type_code;		//CNG stations, type of dispensing capability
    										//Value 	Description
    										//Q 	Quick fill
    										//T 	Timed fill
    										//B 	Both: quick fill and timed fill
    private String ng_psi;					//CNG stations PSI pressures available
    private String ng_vehicle_class;		//CNG and LNG stations, the maximum vehicle size that can physically access
										    //Value 	Description
										    //LD 	Station can only accommodate light-duty vehicles (Classes 1-2).
										    //MD 	Station can accommodate light- and medium-duty vehicles (Classes 1-5).
										    //HD 	Station can accommodate light-, medium-, and heavy-duty vehicles (Classes 1-8).
    private String ev_level1_evse_num;		//electric stations, the number of Level 1 EVSE (standard 110V outlet)
    private String ev_level2_evse_num;		//electric stations, the number of Level 2 EVSE (J1772 connector)
    private String ev_dc_fast_num;			//electric stations, the number of DC Fast Chargers
    private String ev_other_evse;			//electric stations, the number and type of additional EVSE
										    //SP Inductive - Small paddle inductive
										    //LP Inductive - Large paddle inductive
										    //Avcon Conductive

    public AlternativeFuelStationResult(JSONObject json)
    {
    	try
		{
    		this.station_name = json.getString("station_name");
    		this.street_address = json.getString("street_address");
    		this.intersection_directions = json.getString("intersection_directions");
    		this.city = json.getString("city");
    		this.state = json.getString("state");
    		this.zip = json.getString("zip");
    		this.station_phone = json.getString("station_phone");
    		this.latitude = json.getString("latitude");
    		this.longitude = json.getString("longitude");
    		this.distance = json.getString("distance");
    		this.access_days_time = json.getString("access_days_time");
    		this.cards_accepted = json.getString("cards_accepted");
    		this.expected_date = json.getString("expected_date");
    		
    		this.bd_blends = json.getString("bd_blends");
    		this.e85_blender_pump = json.getString("e85_blender_pump");
    		this.lpg_primary = json.getString("lpg_primary");
    		this.ng_fill_type_code = json.getString("ng_fill_type_code");
    		this.ng_psi = json.getString("ng_psi");
    		this.ng_vehicle_class = json.getString("ng_vehicle_class");
    		this.ev_level1_evse_num = json.getString("ev_level1_evse_num");
    		this.ev_level2_evse_num = json.getString("ev_level2_evse_num");
    		this.ev_dc_fast_num = json.getString("ev_dc_fast_num");
    		this.ev_other_evse = json.getString("ev_other_evse");
		}
		catch(JSONException e)
		{
			this.station_name = null;
    		this.street_address = null;
    		this.intersection_directions = null;
    		this.city = null;
    		this.state = null;
    		this.zip = null;
    		this.station_phone = null;
    		this.latitude = null;
    		this.longitude = null;
    		this.distance = null;
    		this.access_days_time = null;
    		this.cards_accepted = null;
    		this.expected_date = null;
    		
    		this.bd_blends = null;
    		this.e85_blender_pump = null;
    		this.lpg_primary = null;
    		this.ng_fill_type_code = null;
    		this.ng_psi = null;
    		this.ng_vehicle_class = null;
    		this.ev_level1_evse_num = null;
    		this.ev_level2_evse_num = null;
    		this.ev_dc_fast_num = null;
    		this.ev_other_evse = null;
		}
    }

    //getters for each data
    public String getStation_name()
	{
		return station_name;
	}

	public String getStreet_address()
	{
		return street_address;
	}

	public String getIntersection_directions()
	{
		return intersection_directions;
	}

	public String getCity()
	{
		return city;
	}

	public String getState()
	{
		return state;
	}

	public String getZip()
	{
		return zip;
	}

	public String getStation_phone()
	{
		return station_phone;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public String getDistance()
	{
		return distance;
	}

	public String getAccess_days_time()
	{
		return access_days_time;
	}

	public String getCards_accepted()
	{
		return cards_accepted;
	}

	public String getExpected_date()
	{
		return expected_date;
	}

	public String getBd_blends()
	{
		return bd_blends;
	}

	public String getE85_blender_pump()
	{
		return e85_blender_pump;
	}

	public String getLpg_primary()
	{
		return lpg_primary;
	}

	public String getNg_fill_type_code()
	{
		return ng_fill_type_code;
	}

	public String getNg_psi()
	{
		return ng_psi;
	}

	public String getNg_vehicle_class()
	{
		return ng_vehicle_class;
	}

	public String getEv_level1_evse_num()
	{
		return ev_level1_evse_num;
	}

	public String getEv_level2_evse_num()
	{
		return ev_level2_evse_num;
	}

	public String getEv_dc_fast_num()
	{
		return ev_dc_fast_num;
	}

	public String getEv_other_evse()
	{
		return ev_other_evse;
	}

	/**
	 * just use to print the object when location
	 */
	public String toString()
	{
		return this.latitude + "/" + this.longitude;
	}

	/**
	 * Parse JSONArray to ArrayList
	 * @param JSONArray
	 * @return ArrayList
	 */
	public static ArrayList<AlternativeFuelStationResult> fromJSONArray(JSONArray array)
    {
		ArrayList<AlternativeFuelStationResult> results = new ArrayList<AlternativeFuelStationResult>();
		for (int x = 0; x < array.length(); x++)
		{
			try
			{
				results.add(new AlternativeFuelStationResult(array.getJSONObject(x)));
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		return results;
    }
}
