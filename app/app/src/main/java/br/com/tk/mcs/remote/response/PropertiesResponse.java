package br.com.tk.mcs.remote.response;

/**
 * Created by revolution on 05/02/16.
 */

public class PropertiesResponse
{
	public final static String TAG             = PropertiesResponse.class.getName ( );
	private             String vehicleSubclass = "";
	
	public String get_vehicle_subclass ( )
	{
		return vehicleSubclass;
	}
	
	public void set_vehicle_subclass ( String vehicleSubclass )
	{
		this.vehicleSubclass = vehicleSubclass;
	}
}