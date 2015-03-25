package com.example.mymapdemo;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Parse JSONArray to ArrayList
 * @author Liu
 */
public class GasStationResult implements Serializable
{
    private static final long serialVersionUID = -4430398376963733386L;
    
    private String reg_price;		//regular
    private String mid_price;		//midgrade
    private String pre_price;		//premium
    private String diesel_price;
	private String address;
	private String lat;
	private String lng;
	private String station;			//brand name
	private String region;			//state
	private String city;
	private String distance;
	
	public GasStationResult(JSONObject json)
	{
		try
		{
			this.reg_price = json.getString("reg_price");
			this.mid_price = json.getString("mid_price");
			this.pre_price = json.getString("pre_price");
			this.diesel_price = json.getString("diesel_price");
			this.address = json.getString("address");
			this.lat = json.getString("lat");
			this.lng = json.getString("lng");
			this.station = json.getString("station");
			this.region = json.getString("region");
			this.city = json.getString("city");
			this.distance = json.getString("distance");
		}
		catch(JSONException e)
		{
			this.reg_price = null;
			this.mid_price = null;
			this.pre_price = null;
			this.diesel_price = null;
			this.address = null;
			this.lat = null;
			this.lng = null;
			this.station = null;
			this.region = null;
			this.city = null;
			this.distance = null;
		}
	}
	
	//getters for each data
	public String getReg_price()
	{
		return reg_price;
	}

	public String getMid_price()
	{
		return mid_price;
	}

	public String getPre_price()
	{
		return pre_price;
	}

	public String getDiesel_price()
	{
		return diesel_price;
	}

	public String getAddress()
	{
		return address;
	}

	public String getLat()
	{
		return lat;
	}

	public String getLng()
	{
		return lng;
	}

	public String getStation()
	{
		return station;
	}

	public String getRegion()
	{
		return region;
	}

	public String getCity()
	{
		return city;
	}

	public String getDistance()
	{
		return distance;
	}

	/**
	 * just use to print the object when location
	 */
	public String toString()
	{
		return this.lat + "/" + this.lng;
	}
	
	/**
	 * Parse JSONArray to ArrayList
	 * @param JSONArray
	 * @return ArrayList
	 */
	public static ArrayList<GasStationResult> fromJSONArray(JSONArray array)
    {
		ArrayList<GasStationResult> results = new ArrayList<GasStationResult>();
		for (int x = 0; x < array.length(); x++)
		{
			try
			{
				results.add(new GasStationResult(array.getJSONObject(x)));
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		return results;
    }
}
