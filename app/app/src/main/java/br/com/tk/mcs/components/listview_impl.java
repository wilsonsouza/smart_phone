/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_layout_impl;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 3/2/17.
 */

public class listview_impl extends linear_vertical implements View.OnClickListener
{
	public final static  String   TAG       = listview_impl.class.getName ( );
	private final static int      ID_FOOTER = 0x88990A;
	private              ListView m_handle  = null;
	
	//---------------------------------------------------------------------------------------------//
	public listview_impl ( Context context )
	{
		super ( context );
		
		this.m_handle = new ListView ( context );
		this.addView ( this.m_handle, this.Params );
	}
	
	//---------------------------------------------------------------------------------------------//
	public final ListView get_handle ( )
	{
		//
		return this.m_handle;
	}
	
	//---------------------------------------------------------------------------------------------//
	public final listview_impl set_adapter ( final ArrayAdapter<String> array_of_items )
	{
		this.m_handle.setAdapter ( array_of_items );
		return this;
	}
	
	//---------------------------------------------------------------------------------------------//
	public final listview_impl set_minimum_area ( final Point area )
	{
		this.m_handle.setMinimumWidth ( area.x );
		this.m_handle.setMinimumHeight ( area.y );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final linear_horizontal set_header ( final int[] resource_header_id,
	                                        final boolean isselectable
	                                      )
	{
		linear_horizontal layout = new linear_horizontal ( getContext ( ) );
		int                     max    = 0;
		layout.setMinimumWidth ( this.getWidth ( ) );
		
		for ( int caption_id : resource_header_id )
		{
			textview_impl top = new textview_impl ( this.getContext ( ) );
			
			top.set_caption ( caption_id );
			top.set_font_size ( textview_impl.CAPTION );
			top.setId ( caption_id );
			
			max = ( max > top.get_pixels_length ( ) ?
			        max :
			        top.get_pixels_length ( ) );
			
			top.Params.width = max + config_display_metrics.notifican_icons_size;
			top.Params.gravity = Gravity.CENTER_VERTICAL;
			
			layout.addView ( top, top.Params );
		}
		return layout;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_horizontal create_footer ( final int[] resource_captions_id,
	                                         final int[] resource_icons_id,
	                                         final boolean isselectable,
	                                         final int gravity_values
	                                       )
	{
		linear_horizontal layout = new linear_horizontal ( this.getContext ( ) );
		
		for ( int i = 0; i < resource_icons_id.length; i++ )
		{
			imagebutton b = new imagebutton ( this.getContext ( ),
			                                  resource_captions_id[ i ],
			                                  null,
			                                  resource_icons_id[ i ],
			                                  false,
			                                  imagebutton.UP
			);
			
			b.set_margins ( config_display_metrics.Padding );
			layout.addView ( b, b.Params );
			b.setEnabled ( isselectable );
		}
		
		layout.Params.gravity = gravity_values;
		layout.setId ( ID_FOOTER );
		layout.invalidate ( true );
		return layout;
	}
	
	//---------------------------------------------------------------------------------------------//
	public listview_impl set_layout_parameters ( linear_layout_impl.parameters params )
	{
		this.m_handle.setLayoutParams ( params );
		return this;
	}
	
	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick ( View v )
	{
	
	}
}
