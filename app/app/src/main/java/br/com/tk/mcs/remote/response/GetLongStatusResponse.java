package br.com.tk.mcs.remote.response;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by revolution on 03/02/16.
 */

public class GetLongStatusResponse
{
	public final static String              TAG             = GetLongStatusResponse.class.getName ( );
	private             String              laneName        = "";
	private             String              moment          = "";
	private             Integer             laneMode        = 0;
	private             Integer             laneState       = 0;
	private             Integer             levelAlarm      = 0;
	private             String              operatorCode    = "";
	private             String              operatorNameBin = "";
	private             DeviceResponse      device          = null;
	private             List<String>        traffics        = null;
	private             Vector<DetailArray> m_traffics      = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public GetLongStatusResponse ( int laneState,
	                               DeviceResponse device
	                             )
	{
		set_lane_state ( laneState );
		set_device ( device );
		this.set_detail_array ( null );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public GetLongStatusResponse ( )
	{
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@SuppressWarnings( "unchecked" )
	public static GetLongStatusResponse to_GetLongStatusResponse ( Map<String, Object> longStatus ) throws
	                                                                                               Exception
	{
		String                res;
		GetLongStatusResponse status      = new GetLongStatusResponse ( );
		DeviceResponse        device      = new DeviceResponse ( );
		List<String>          transaction = new ArrayList<> ( );
		
		try
		{
			if ( longStatus.containsKey ( "ShortStatus" ) )
			{
				Map<String, Object> shortStatus = ( HashMap ) longStatus.get ( "ShortStatus" );
				
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
				//if (device != null && shortStatus.containsKey("devices"))
				if ( shortStatus.containsKey ( "devices" ) )
				{
					Map<String, Object> devices = ( HashMap ) shortStatus.get ( "devices" );
					
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
			}
			
			if ( longStatus.containsKey ( "DetailArray" ) )
			{
				
				Object[]            detail = ( Object[] ) longStatus.get ( "DetailArray" );
				Vector<DetailArray> queue  = new Vector<> ( );
				
				for ( final Object obj : detail )
				{
					Map<String, String> current = ( HashMap ) obj;
					transaction.add ( current.get ( "transactionId" ) + " " + current.get ( "moment" ) + " " + current.get ( "paymentType" ) + " " + current.get ( "paymentMeans" ) + " " + current.get ( "panNumber" ) + " " + current.get ( "vehicleClass" ) );
					
					DetailArray p = new DetailArray ( );
					{
						p.TransactionId = current.get ( "transactionId" );
						p.Moment = current.get ( "moment" );
						p.PaymentType = current.get ( "paymentType" );
						p.PaymentMeans = current.get ( "paymentMeans" );
						p.PanNumber = current.get ( "panNumber" );
						p.VehicleClass = current.get ( "vehicleClass" );
						queue.add ( p );
					}
				}
				
				status.set_detail_array ( queue );
				
				if ( transaction.size ( ) > 0 )
				{
					Collections.reverse ( transaction );
				}
			}
			
			status.set_device ( device );
			status.set_traffics_list ( transaction );
		}
		catch ( Exception e )
		{
			Log.e ( GetLongStatusResponse.class.getName ( ),
			        e.getMessage ( )
			      );
		}
		return status;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_moment ( )
	{
		return moment;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_moment ( String moment )
	{
		this.moment = moment;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Integer get_lane_state ( )
	{
		return laneState;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_lane_state ( Integer laneState )
	{
		this.laneState = laneState;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_lane_name ( )
	{
		return laneName;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_lane_name ( String laneName )
	{
		this.laneName = laneName;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Integer get_level_alarm ( )
	{
		return levelAlarm;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_level_alarm ( Integer levelAlarm )
	{
		this.levelAlarm = levelAlarm;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Integer get_lane_mode ( )
	{
		return laneMode;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_lane_mode ( Integer laneMode )
	{
		this.laneMode = laneMode;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DeviceResponse get_device ( )
	{
		return device;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_device ( DeviceResponse device )
	{
		this.device = device;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_operator_code ( )
	{
		return operatorCode;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_operator_code ( String operatorCode )
	{
		this.operatorCode = operatorCode;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_operator_binary_name ( )
	{
		return operatorNameBin;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_operator_binary_name ( String operatorNameBin )
	{
		this.operatorNameBin = operatorNameBin;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public List<String> get_traffics_list ( )
	{
		return traffics;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_traffics_list ( List<String> traffics )
	{
		this.traffics = traffics;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Vector<DetailArray> get_detail_array ( )
	{
		return this.m_traffics;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_detail_array ( Vector<DetailArray> pItems )
	{
		if ( pItems.size ( ) > 0 )
		{
			this.m_traffics = pItems;
		}
		else
		{
			this.m_traffics = null;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static class DetailArray
	{
		public String TransactionId;
		public String Moment;
		public String PaymentType;
		public String PaymentMeans;
		public String PanNumber;
		public String VehicleClass;
	}
}
