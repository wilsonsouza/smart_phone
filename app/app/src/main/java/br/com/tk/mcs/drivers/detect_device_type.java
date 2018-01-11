package br.com.tk.mcs.drivers;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by wilsonsouza on 02/02/17.
 */

public class detect_device_type
{
	public final static String TAG = detect_device_type.class.getName ( );
	
	public static boolean is_tablet ( Context context )
	{
		try
		{
			TelephonyManager manager    = ( TelephonyManager ) context.getSystemService ( context.TELEPHONY_SERVICE );
			String           deviceInfo = manager.getDeviceId ( ); // the IMEI
			
			Log.d ( detect_device_type.TAG,
			        "IMEI or unique ID is " + deviceInfo
			      );
			Log.d ( detect_device_type.class.getClass ( )
			                                .getName ( ),
			        "Device detected " + deviceInfo == null ?
			        "Tablet" :
			        "Phone"
			      );
			
			if ( deviceInfo == null )
			{
				return true;
			}
		}
		catch ( Exception e )
		{
			Log.e ( detect_device_type.TAG,
			        e.getMessage ( )
			      );
		}
		return false;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static boolean is_smart_phone ( Context context )
	{
		try
		{
			TelephonyManager manager    = ( TelephonyManager ) context.getSystemService ( context.TELEPHONY_SERVICE );
			String           deviceInfo = manager.getDeviceId ( ); // the IMEI
			
			Log.d ( detect_device_type.TAG,
			        "IMEI or unique ID is " + deviceInfo
			      );
			Log.d ( detect_device_type.TAG,
			        "Device detected " + deviceInfo == null ?
			        "Tablet" :
			        "Phone"
			      );
			
			if ( deviceInfo != null )
			{
				return true;
			}
		}
		catch ( Exception e )
		{
			Log.e ( detect_device_type.TAG,
			        e.getMessage ( )
			      );
		}
		return false;
	}
}
