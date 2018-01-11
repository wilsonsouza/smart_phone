/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioButton;

import br.com.tk.mcs.layouts.linear_horizontal;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class radiobutton_impl extends linear_horizontal
{
	public final static String      TAG      = radiobutton_impl.class.getName ( );
	public final static int         DEFAULT  = - 1;
	public final static int         CAPTION  = - 2;
	private             RadioButton m_handle = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public radiobutton_impl ( final Context context,
	                          final int caption_resource_id,
	                          final boolean enabled
	                        )
	{
		super ( context );
		
		this.builder ( caption_resource_id, enabled, false );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public radiobutton_impl ( Context context,
	                          int caption_id,
	                          boolean enabled,
	                          boolean checked,
	                          int font_size
	                        )
	{
		super ( context );
		
		this.builder ( caption_id, enabled, checked )
		    .set_font_size ( font_size );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public radiobutton_impl ( Context context,
	                          String caption,
	                          boolean enabled,
	                          boolean checked,
	                          int font_size
	                        )
	{
		super ( context );
		
		this.builder ( NO_ID, enabled, checked )
		    .set_font_size ( font_size )
		    .set_text ( caption );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public radiobutton_impl builder ( int caption_id,
	                                  boolean enabled,
	                                  boolean checked
	                                )
	{
		this.m_handle = new AppCompatRadioButton ( this.getContext ( ) );
		
		this.set_font_size ( NO_ID )
		    .set_text ( caption_id )
		    .set_enabled ( enabled )
		    .set_checked ( checked );
		
		this.addView ( this.m_handle, this.Params );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final radiobutton_impl set_text_color ( final int color )
	{
		this.m_handle.setTextColor ( color );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private radiobutton_impl set_font_size ( int nFontSize )
	{
		switch ( nFontSize )
		{
			case DEFAULT:
				font_impl.set_size ( this.m_handle,
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				break;
			case CAPTION:
				font_impl.set_size ( this.m_handle,
				                     this.m_handle.getTextSize ( ),
				                     font_impl.CAPTION_SIZE
				                   );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setEnabled ( boolean enabled )
	{
		this.m_handle.setEnabled ( enabled );
		super.setEnabled ( enabled );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final radiobutton_impl set_enabled ( final boolean enabled )
	{
		this.m_handle.setEnabled ( enabled );
		this.setEnabled ( enabled );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public boolean is_checked ( )
	{
		//
		return this.m_handle.isChecked ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final radiobutton_impl set_checked ( boolean checked )
	{
		this.m_handle.setChecked ( checked );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setOnClickListener ( View.OnClickListener pClick )
	{
		//
		this.m_handle.setOnClickListener ( pClick );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public int getId ( )
	{
		//
		return this.m_handle.getId ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setId ( final int id )
	{
		//
		this.m_handle.setId ( id );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final radiobutton_impl set_text ( final String data )
	{
		this.m_handle.setText ( data );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final String get_text ( )
	{
		return this.m_handle.getText ( )
		                    .toString ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final radiobutton_impl set_text ( final int id )
	{
		if ( id != NO_ID )
		{
			this.m_handle.setText ( id );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setBackgroundColor ( final int color )
	{
		this.m_handle.setBackgroundColor ( color );
		super.setBackgroundColor ( color );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final RadioButton get_handle ( )
	{
		return this.m_handle;
	}
}
