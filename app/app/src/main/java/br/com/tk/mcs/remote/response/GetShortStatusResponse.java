package br.com.tk.mcs.remote.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by revolution on 03/02/16.
 */

public class GetShortStatusResponse
{
	public final static String         TAG             = GetShortStatusResponse.class.getName ( );
	private             String         laneName        = "";
	private             String         moment          = "";
	private             int            laneMode        = 0;
	private             int            laneState       = 0;
	private             int            levelAlarm      = 0;
	private             String         operatorCode    = "";
	private             String         operatorNameBin = "";
	private             DeviceResponse device          = null;
	
	public GetShortStatusResponse ( )
	{
	}
	
	public static GetShortStatusResponse to_GetShortStatusResponse ( Map<String, Object> shortStatus )
	{
		String                 res;
		GetShortStatusResponse status = new GetShortStatusResponse ( );
		DeviceResponse         device = new DeviceResponse ( );
		
		if ( shortStatus.containsKey ( "moment" ) )
		{
			status.set_moment ( shortStatus.get ( "moment" )
			                               .toString ( ) );
		}
		
		if ( shortStatus.containsKey ( "laneState" ) )
		{
			status.set_lane_state ( ( Integer ) shortStatus.get ( "laneState" ) );
		}
		
		if ( shortStatus.containsKey ( "laneName" ) )
		{
			status.set_lane_name ( shortStatus.get ( "laneName" )
			                                  .toString ( ) );
		}
		
		if ( shortStatus.containsKey ( "levelAlarm" ) )
		{
			status.set_level_alarm ( ( Integer ) shortStatus.get ( "levelAlarm" ) );
		}
		
		if ( shortStatus.containsKey ( "laneMode" ) )
		{
			status.set_lane_mode ( ( Integer ) shortStatus.get ( "laneMode" ) );
		}
		
		if ( shortStatus.containsKey ( "operatorCode" ) )
		{
			status.set_operator_code ( shortStatus.get ( "operatorCode" )
			                                      .toString ( ) );
		}
		
		if ( shortStatus.containsKey ( "operatorNameBin" ) )
		{
			status.set_operator_binary_name ( shortStatus.get ( "operatorNameBin" )
			                                             .toString ( ) );
		}
		
		try
		{
			//09-20-2016
			//if ((null != device) && shortStatus.containsKey("devices"))
			if ( shortStatus.containsKey ( "devices" ) )
			{
				Map<String, Object> devices = ( HashMap<String, Object> ) shortStatus.get ( "devices" );
				
				if ( devices.containsKey ( "lightStateCanopy" ) )
				{
					device.setLightStateCanopy ( devices.get ( "lightStateCanopy" )
					                                    .toString ( ) );
				}
				
				if ( devices.containsKey ( "lightStateEntry" ) )
				{
					res = devices.get ( "lightStateEntry" )
					             .toString ( );
					device.setLightStateEntry ( ! res.isEmpty ( ) ?
					                            Integer.parseInt ( res ) :
					                            0 );
				}
				
				if ( devices.containsKey ( "lightStateExit" ) )
				{
					res = devices.get ( "lightStateExit" )
					             .toString ( );
					device.setLightStateExit ( ! res.isEmpty ( ) ?
					                           Integer.parseInt ( res ) :
					                           0 );
				}
				
				if ( devices.containsKey ( "antennaOBU" ) )
				{
					device.setAntennaOBU ( devices.get ( "antennaOBU" )
					                              .toString ( ) );
				}
				
				if ( devices.containsKey ( "antennaOBU2" ) )
				{
					device.setAntennaOBU2 ( devices.get ( "antennaOBU2" )
					                               .toString ( ) );
				}
				
				if ( devices.containsKey ( "barrierExit" ) )
				{
					res = devices.get ( "barrierExit" )
					             .toString ( );
					device.setBarrierExit ( ! res.isEmpty ( ) ?
					                        Integer.parseInt ( res ) :
					                        0 );
				}
				
				if ( devices.containsKey ( "aspectCameraEntry" ) )
				{
					device.setAspectCameraEntry ( devices.get ( "aspectCameraEntry" )
					                                     .toString ( ) );
				}
				
				if ( devices.containsKey ( "aspectCameraEntry2" ) )
				{
					device.setAspectCameraEntry2 ( devices.get ( "aspectCameraEntry2" )
					                                      .toString ( ) );
				}
				
				if ( devices.containsKey ( "aspectCameraExit" ) )
				{
					device.setAspectCameraExit ( devices.get ( "aspectCameraExit" )
					                                    .toString ( ) );
				}
				
				if ( devices.containsKey ( "aspectCameraExit2" ) )
				{
					device.setAspectCameraExit2 ( devices.get ( "aspectCameraExit2" )
					                                     .toString ( ) );
				}
				
				if ( devices.containsKey ( "zoneEntry" ) )
				{
					device.setZoneEntry ( devices.get ( "zoneEntry" )
					                             .toString ( ) );
				}
				
				if ( devices.containsKey ( "zoneExit" ) )
				{
					device.setZoneExit ( devices.get ( "zoneExit" )
					                            .toString ( ) );
				}
				
				if ( devices.containsKey ( "lastPaymentType" ) )
				{
					device.setLastPaymentType ( devices.get ( "lastPaymentType" )
					                                   .toString ( ) );
				}
				
				if ( devices.containsKey ( "lastPaymentMeans" ) )
				{
					device.setLastPaymentMeans ( devices.get ( "lastPaymentMeans" )
					                                    .toString ( ) );
				}
				
				if ( devices.containsKey ( "currentOcr" ) )
				{
					device.setCurrentOcr ( devices.get ( "currentOcr" )
					                              .toString ( ) );
				}
				
				if ( devices.containsKey ( "totalVehicles" ) )
				{
					res = devices.get ( "totalVehicles" )
					             .toString ( );
					device.setTotalVehicles ( ! res.isEmpty ( ) ?
					                          Integer.parseInt ( res ) :
					                          0 );
				}
				
				if ( devices.containsKey ( "vehicleStopped" ) )
				{
					res = devices.get ( "vehicleStopped" )
					             .toString ( );
					device.setVehicleStopped ( ! res.isEmpty ( ) ?
					                           Integer.parseInt ( res ) :
					                           0 );
				}
			}
			status.device = device;
		}
		catch ( Exception e )
		{
			//none for while
		}
		return status;
	}
	
	public String get_moment ( )
	{
		return moment;
	}
	
	public void set_moment ( String moment )
	{
		this.moment = moment;
	}
	
	public int get_lane_state ( )
	{
		return laneState;
	}
	
	public void set_lane_state ( int laneState )
	{
		this.laneState = laneState;
	}
	
	public String get_lane_name ( )
	{
		return laneName;
	}
	
	public void set_lane_name ( String laneName )
	{
		this.laneName = laneName;
	}
	
	public int get_level_alarm ( )
	{
		return levelAlarm;
	}
	
	public void set_level_alarm ( int levelAlarm )
	{
		this.levelAlarm = levelAlarm;
	}
	
	public int get_lane_mode ( )
	{
		return laneMode;
	}
	
	public void set_lane_mode ( int laneMode )
	{
		this.laneMode = laneMode;
	}
	
	public DeviceResponse get_device ( )
	{
		return device;
	}
	
	public void set_device ( DeviceResponse device )
	{
		this.device = device;
	}
	
	public String get_operator_code ( )
	{
		return operatorCode;
	}
	
	public void set_operator_code ( String operatorCode )
	{
		this.operatorCode = operatorCode;
	}
	
	public String get_operator_binary_name ( )
	{
		return operatorNameBin;
	}
	
	public void set_operator_binary_name ( String operatorNameBin )
	{
		this.operatorNameBin = operatorNameBin;
	}
}
