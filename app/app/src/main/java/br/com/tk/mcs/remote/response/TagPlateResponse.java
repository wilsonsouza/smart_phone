package br.com.tk.mcs.remote.response;

import android.content.Context;

import java.util.Locale;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.message_box;

/**
 * Created by revolution on 04/02/16.
 */

public enum TagPlateResponse
{
	Paid ( 1 ),
	Exempt ( 2 ),
	BlackList ( 3 ),
	NoRegister ( 4 ),
	Error ( 5 ),
	SizeError ( 6 );
	
	private int value;
	
	//-----------------------------------------------------------------------------------------------------------------//
	TagPlateResponse ( int value )
	{
		this.value = value;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static TagPlateResponse from_value ( int value )
	{
		for ( TagPlateResponse my : TagPlateResponse.values ( ) )
		{
			if ( my.value == value )
			{
				return my;
			}
		}
		return Error;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static String get_text ( Context ctx,
	                                TagPlateResponse value
	                              )
	{
		switch ( value )
		{
			case Paid:
				return ctx.getString ( R.string.manager_tag_response_paid );
			case Exempt:
				return ctx.getString ( R.string.manager_tag_response_exempt );
			case BlackList:
				return ctx.getString ( R.string.manager_tag_response_blacklist );
			case NoRegister:
				return ctx.getString ( R.string.manager_tag_response_noregister );
			case SizeError:
				return ctx.getString ( R.string.manager_tag_response_size_error );
		}
		return ctx.getString ( R.string.manager_tag_response_error );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static String format ( Context context,
	                              TagPlateResponse value,
	                              String szData
	                            )
	{
		String fmt = "";
		
		switch ( szData.length ( ) )
		{
			case 7:
				fmt = String.format ( Locale.FRANCE,
				                      "com Placa %s",
				                      szData
				                    );
				break;
			case 0xA:
				fmt = String.format ( Locale.FRANCE,
				                      "com Tag %s",
				                      szData
				                    );
				break;
		}
		
		switch ( value )
		{
			case Paid:
			case Exempt:
			case BlackList:
			case NoRegister:
			case SizeError:
				return String.format ( TagPlateResponse.get_text ( context,
				                                                   value
				                                                 ),
				                       fmt
				                     );
		}
		return TagPlateResponse.get_text ( context,
		                                   value
		                                 );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static int format ( TagPlateResponse value )
	{
		switch ( value )
		{
			case Paid:
			case Exempt:
				return message_box.IDOK;
			case BlackList:
				return message_box.IDWARNING;
			case NoRegister:
				return message_box.IDWARNING;
			case SizeError:
				return message_box.IDWARNING;
		}
		return message_box.IDERROR;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public int value ( )
	{
		return value;
	}
}
