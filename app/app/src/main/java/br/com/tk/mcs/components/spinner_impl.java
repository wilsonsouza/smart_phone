/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 31/01/17.
 */

public class spinner_impl extends linear_vertical
{
	public final static String        TAG              = spinner_impl.class.getName ( );
	public final static int           DEFAULT          = NO_ID;
	public final static int           ID_TEXT_LEFT     = 1;
	public final static int           ID_TEXT_RIGHT    = 2;
	public final static int           ID_TEXT_CENTER   = 4;
	public final static int           ID_SPINNER_LEFT  = 0x10;
	public final static int           ID_SPINNER_RIGHT = 0x20;
	private             Spinner       m_handle         = null;
	private             textview_impl m_text_handle    = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public spinner_impl ( final Context context,
	                      final int resource_caption_id
	                    )
	{
		super ( context );
		
		this.builder ( resource_caption_id )
		    .builder ( false, false )
		    .set_data_width ( NO_ID )
		    .set_font_size ( NO_ID );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public spinner_impl ( Context context,
	                      int caption_id,
	                      int font_size,
	                      int max_width,
	                      boolean is_enabled,
	                      boolean is_border
	                    )
	{
		super ( context );
		
		this.builder ( caption_id )
		    .builder ( is_enabled, is_border )
		    .set_font_size ( font_size )
		    .set_data_width ( max_width );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public spinner_impl builder ( boolean isenabled,
	                              boolean isborder
	                            )
	{
		this.m_handle = new Spinner ( getContext ( ) );
		this.Params.gravity = Gravity.START;
		this.setEnabled ( isenabled );
		
		this.set_font_size ( NO_ID )
		    .set_margins ( config_display_metrics.EditRect )
		    .set_border ( isborder );
		
		this.addView ( this.m_handle, this.Params );
		this.addView ( this.alloc_space ( 0, config_display_metrics.space_between_controls ) );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public spinner_impl builder ( int caption_id )
	{
		if ( caption_id != NO_ID )
		{
			this.m_text_handle = new textview_impl ( getContext ( ) );
			this.m_text_handle.Params.gravity = ( Gravity.START );
			this.m_text_handle.set_caption ( caption_id )
			                  .set_font_size ( textview_impl.DEFAULT );
			this.m_text_handle.set_margins ( config_display_metrics.Padding );
			
			this.addView ( this.m_text_handle, this.m_text_handle.Params );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private spinner_impl set_font_size ( int nFontSize )
	{
		switch ( nFontSize )
		{
			case DEFAULT:
				font_impl.set_size ( this.m_handle,
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				font_impl.set_size ( this.m_text_handle,
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public boolean isEnabled ( )
	{
		//
		return this.m_handle.isEnabled ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setEnabled ( boolean bEnabled )
	{
		this.m_handle.setEnabled ( bEnabled );
		this.m_text_handle.setEnabled ( bEnabled );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public spinner_impl set_enabled ( boolean enabled )
	{
		this.setEnabled ( enabled );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private spinner_impl set_data_width ( int nWidth )
	{
		switch ( nWidth )
		{
			case - 1:
				nWidth = config_display_metrics.to_sp ( config_display_metrics.minimum_control_width );
				break;
			default:
				nWidth = config_display_metrics.to_sp ( nWidth );
		}
		
		this.m_handle.setMinimumWidth ( nWidth );
		this.m_handle.setMinimumHeight ( this.to_sp ( config_display_metrics.minimum_control_height ) );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public spinner_impl set_default_items ( final ArrayList<String> array_list )
	{
		ArrayAdapter<String> adapter_items = new ArrayAdapter<String> ( getContext ( ),
		                                                                android.R.layout.simple_spinner_item,
		                                                                array_list
		);
		adapter_items.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
		this.m_handle.setAdapter ( adapter_items );
		
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public spinner_impl set_alignment ( final int combinations )
	{
		if ( ( combinations & ID_TEXT_LEFT ) == ID_TEXT_LEFT )
		{
			this.m_text_handle.setGravity ( Gravity.LEFT );
		}
		else if ( ( combinations & ID_TEXT_RIGHT ) == ID_TEXT_RIGHT )
		{
			this.m_text_handle.setGravity ( Gravity.RIGHT );
		}
		else if ( ( combinations & ID_TEXT_CENTER ) == ID_TEXT_CENTER )
		{
			this.m_text_handle.setGravity ( Gravity.CENTER );
		}
		else if ( ( combinations & ID_SPINNER_LEFT ) == ID_SPINNER_LEFT )
		{
			this.setGravity ( Gravity.LEFT );
		}
		else if ( ( combinations & ID_SPINNER_RIGHT ) == ID_SPINNER_RIGHT )
		{
			this.setGravity ( Gravity.RIGHT );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Spinner get_handle ( )
	{
		/**/
		return this.m_handle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final textview_impl get_textview_handle ( )
	{
		/**/
		return this.m_text_handle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public spinner_impl set_border ( final boolean is_border )
	{
		if ( is_border )
		{
			borderwidget_impl.builder ( m_handle );
		}
		return this;
	}
}
