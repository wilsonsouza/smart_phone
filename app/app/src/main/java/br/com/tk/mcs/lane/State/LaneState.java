package br.com.tk.mcs.lane.State;

import android.content.Context;

import br.com.tk.mcs.R;
import br.com.tk.mcs.tools.TColor;

/**
 * Created by revolution on 26/02/16.
 */

public enum LaneState
{
	Starting ( 0 ),
	Closed ( 1 ),
	Finishing ( 2 ),
	Opened ( 3 ),
	OpenMaintenance ( 4 ),
	Maintenance ( 5 ),
	NoSync ( 6 );
	
	private int value = 0;
	
	private LaneState ( int value )
	{
		this.value = value;
	}
	
	public static LaneState from_value ( int value )
	{
		for ( LaneState my : LaneState.values ( ) )
		{
			if ( my.value == value )
			{
				return my;
			}
		}
		return null;
	}
	
	public static int get_value ( LaneState state )
	{
		switch ( state )
		{
			case Starting:
				return 0;
			case Closed:
				return 1;
			case Finishing:
				return 2;
			case Opened:
				return 3;
			case OpenMaintenance:
				return 4;
			case Maintenance:
				return 5;
			default:
				return 6;
		}
	}
	
	public static String get_text ( Context cx,
	                                LaneState state
	                              )
	{
		switch ( state )
		{
			case Opened:
				return cx.getText ( R.string.state_lane_open )
				         .toString ( );
			case OpenMaintenance:
				return cx.getText ( R.string.state_lane_maintance )
				         .toString ( );
			case Closed:
				return cx.getText ( R.string.state_lane_closed )
				         .toString ( );
			case NoSync:
				return cx.getText ( R.string.state_lane_comunication )
				         .toString ( );
			default:
				return "";
		}
	}
	
	public static int get_color ( LaneState state )
	{
		switch ( state )
		{
			case Opened:
				return TColor.GREEN;
			case OpenMaintenance:
				return TColor.YELLOW;
			case Closed:
				return TColor.RED;
			case NoSync:
				return TColor.GRAY;
			default:
				return TColor.BLUE;
		}
	}
	
	int value ( )
	{
		return value;
	}
}