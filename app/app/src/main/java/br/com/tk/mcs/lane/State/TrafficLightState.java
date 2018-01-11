package br.com.tk.mcs.lane.State;

/**
 * Created by revolution on 26/02/16.
 */

public enum TrafficLightState
{
	LightOff ( 0 ),
	LightRed ( 2 ),
	LightGreen ( 1 ),
	LightUnkown ( 3 );
	
	private int m_value = 0;
	
	TrafficLightState ( int value )
	{
		this.m_value = value;
	}
	
	public static TrafficLightState from_value ( int value )
	{
		for ( TrafficLightState p : TrafficLightState.values ( ) )
		{
			if ( p.m_value == value )
			{
				return p;
			}
		}
		return null;
	}
	
	int value ( )
	{
		return m_value;
	}
}
