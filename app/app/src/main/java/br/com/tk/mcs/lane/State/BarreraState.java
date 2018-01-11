package br.com.tk.mcs.lane.State;

/**
 * Created by revolution on 26/02/16.
 */

public enum BarreraState
{
	SensorUnknown ( 0 ),
	SensorON ( 2 ),
	SensorOFF ( 1 );
	
	private int m_value = 0;
	
	BarreraState ( int value )
	{
		this.m_value = value;
	}
	
	public static BarreraState from_value ( int value )
	{
		for ( BarreraState b : BarreraState.values ( ) )
		{
			if ( b.m_value == value )
			{
				return b;
			}
		}
		return null;
	}
	
	int value ( )
	{
		return m_value;
	}
}
