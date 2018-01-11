package br.com.tk.mcs.remote.response;

/**
 * Created by revolution on 04/02/16.
 */

public enum UserRequestResponse
{
	Authorized ( 1 ),
	NoAuthorized ( 2 ),
	Error ( 3 );
	
	private int value = 0;
	
	UserRequestResponse ( int value )
	{
		this.value = value;
	}
	
	public static UserRequestResponse from_value ( int value )
	{
		if ( value > 0 )
		{
			return Authorized;
		}
		else if ( value == 0 )
		{
			return NoAuthorized;
		}
		return Error;
	}
	
	public int value ( )
	{
		return value;
	}
}
