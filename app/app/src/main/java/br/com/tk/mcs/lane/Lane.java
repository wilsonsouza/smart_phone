/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.lane;

import java.io.Serializable;

import br.com.tk.mcs.lane.State.BarreraState;
import br.com.tk.mcs.lane.State.LaneState;
import br.com.tk.mcs.lane.State.TrafficLightState;

/**
 * Created by revolution on 30/01/16.
 */

public class Lane implements Serializable
{
	public final static String            TAG               = Lane.class.getName ( );
	private             int               m_nID             = 0;
	private             String            m_szLaneName      = "";
	private             LaneState         m_LaneState       = LaneState.Starting;
	private             BarreraState      m_BarreraState    = BarreraState.SensorUnknown;
	private             TrafficLightState m_PassageState    = TrafficLightState.LightUnkown;
	private             TrafficLightState m_MarquiseState   = TrafficLightState.LightUnkown;
	private             int               m_nVehicleStopped = 0;
	private             int               m_nTotalVehicles  = 0;
	private             Operations        m_operations      = null;
	
	public Lane ( int id,
	              String name,
	              String szOperator,
	              final Operations operations
	            )
	{
		m_nID = id;
		m_szLaneName = name;
		m_operations = operations;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public int get_total_vehicles ( ) //GetTotalOfVehicles
	{
		return m_nTotalVehicles;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_total_vehicles ( int nTotalVehicles )
	{
		m_nTotalVehicles = nTotalVehicles;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public int get_vehicle_stopped ( )
	{
		return m_nVehicleStopped;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_vehicle_stopped ( int nVehicleStopped )
	{
		m_nVehicleStopped = nVehicleStopped;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public int get_id ( )
	{
		return m_nID;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_id ( int nID )
	{
		this.m_nID = nID;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public LaneState get_state ( )
	{
		return m_LaneState;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_state ( LaneState value )
	{
		m_LaneState = value;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_name ( )
	{
		return m_szLaneName;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_name ( String szLaneName )
	{
		m_szLaneName = szLaneName;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Operations get_operations ( )
	{
		return m_operations;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_operations ( Operations value )
	{
		m_operations = value;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public BarreraState get_barrera_state ( )
	{
		return m_BarreraState;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_barrera_state ( BarreraState value )
	{
		m_BarreraState = value;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public TrafficLightState get_passage_state ( )
	{
		return m_PassageState;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_passage_state ( TrafficLightState value )
	{
		m_PassageState = value;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public TrafficLightState get_marquise_state ( )
	{
		return m_MarquiseState;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_marquise_state ( TrafficLightState value )
	{
		m_MarquiseState = value;
	}
}