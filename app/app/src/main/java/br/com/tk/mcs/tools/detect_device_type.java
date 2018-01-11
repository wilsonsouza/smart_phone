package br.com.tk.mcs.tools;

import br.com.tk.mcs.generic.config_display_metrics;

/**
 * Created by wilsonsouza on 27/11/2017.
 */

public class detect_device_type extends Object
{
	public final static boolean is_smart_phone()
	{
		if(config_display_metrics.get_device_inch () < 6.1)
		{
			return true;
		}
		return false;
	}
}
