/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Point;
import android.widget.Space;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 31/01/17.
 */

public class space_impl extends linear_vertical
{
	public final static String TAG      = space_impl.class.getName ( );
	private             Space  m_handle = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public space_impl ( final Context context )
	{
		super ( context );
		
		int space_value = config_display_metrics.space_between_controls;
		this.builder ( new Point ( space_value, space_value ), false );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public space_impl ( Context context,
	                    int max_width,
	                    int max_height,
	                    boolean is_border
	                  )
	{
		super ( context );
		this.builder ( new Point ( max_width, max_height ), is_border );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public space_impl ( Context context,
	                    Point area,
	                    boolean is_border
	                  )
	{
		super ( context );
		this.builder ( area, is_border );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public space_impl builder ( Point point_values,
	                            boolean isborder
	                          )
	{
		this.m_handle = new Space ( this.getContext ( ) );
		
		this.Params = this.build ( point_values );
		this.set_border ( isborder );
		this.addView ( this.m_handle, this.Params );
		return this;
	}
}
