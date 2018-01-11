/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CheckBox;

import br.com.tk.mcs.layouts.linear_horizontal;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class checkbox_impl extends linear_horizontal
{
	public final static String   TAG     = checkbox_impl.class.getName ( );
	public final static int      DEFAULT = - 1;
	public final static int      CAPTION = - 2;
	private             CheckBox m_data  = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public checkbox_impl ( final Context context,
	                       final int caption_resource_id
	                     )
	{
		super ( context );
		
		this.builder ( caption_resource_id, NO_ID, false );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public checkbox_impl ( final Context context,
	                       final int caption_resource_id,
	                       final boolean enabled,
	                       final boolean checked,
	                       final int font_size
	                     )
	{
		super ( context );
		
		this.builder ( caption_resource_id, font_size, enabled )
		    .set_checked ( checked );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public checkbox_impl ( final Context context,
	                       final String caption,
	                       final boolean enabled,
	                       final boolean checked,
	                       final int font_size
	                     )
	{
		super ( context );
		
		this.builder ( NO_ID, font_size, enabled )
		    .set_caption ( caption )
		    .set_checked ( checked );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public checkbox_impl builder ( int caption_resource_id,
	                               int font_size,
	                               boolean enabled
	                             )
	{
		this.m_data = new AppCompatCheckBox ( this.getContext ( ) );
		this.set_caption ( caption_resource_id )
		    .set_font_size ( font_size )
		    .set_enabled ( enabled )
		    .set_checked ( false );
		this.addView ( this.m_data, this.Params );
		this.get_handle().setTextColor( Color.BLACK );
		this.get_handle().setBackgroundColor( Color.WHITE );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public checkbox_impl set_enabled ( boolean enabled )
	{
		this.setEnabled ( enabled );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public checkbox_impl set_caption ( int caption_id )
	{
		if ( caption_id != NO_ID )
		{
			this.m_data.setTag ( caption_id );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public checkbox_impl set_caption ( String caption )
	{
		if ( caption != null )
		{
			this.m_data.setTag ( caption );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private checkbox_impl set_font_size ( final int font_size )
	{
		//checkbox.set_font_size
		switch ( font_size )
		{
			case DEFAULT:
				font_impl.set_size ( this.m_data,
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				break;
			case CAPTION:
				font_impl.set_size ( this.m_data,
				                     this.m_data.getTextSize ( ),
				                     font_impl.CAPTION_SIZE
				                   );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Typeface get_font ( )
	{
		//
		return this.m_data.getTypeface ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final void set_font ( final Typeface typeface_value )
	{
		//
		this.m_data.setTypeface ( typeface_value );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setEnabled ( final boolean enabled )
	{
		this.m_data.setEnabled ( enabled );
		super.setEnabled ( enabled );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public checkbox_impl set_checked ( final boolean checked )
	{
		if ( checked )
		{
			this.m_data.setTextColor ( Color.BLUE );
		}
		this.m_data.setChecked ( checked );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final CheckBox get_handle ( )
	{
		//
		return this.m_data;
	}
}
