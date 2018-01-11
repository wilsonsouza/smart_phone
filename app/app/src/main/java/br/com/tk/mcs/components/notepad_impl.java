/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Rect;
import android.text.InputFilter;
import android.view.Gravity;
import android.widget.EditText;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class notepad_impl extends linear_horizontal
{
	public static final int           DEFAULT = - 1;
	public static final String        TAG     = notepad_impl.class.getName ( );
	private             EditText      Data    = null;
	private             textview_impl Text    = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public notepad_impl ( Context context,
	                      int caption_id,
	                      boolean isborder
	                    )
	{
		super ( context, null, config_display_metrics.EditStyle );
		
		this.builder ( caption_id, isborder, NO_ID )
		    .set_data_height ( NO_ID )
		    .set_data_width ( NO_ID )
		    .set_enabled ( false );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public notepad_impl ( Context context,
	                      int caption_id,
	                      int edit_size,
	                      boolean isenabled,
	                      int height,
	                      int width,
	                      boolean isborder
	                    )
	{
		super ( context );
		
		this.builder ( caption_id, isborder, edit_size )
		    .set_enabled ( isenabled )
		    .set_data_width ( width )
		    .set_data_height ( height );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public notepad_impl ( Context context,
	                      String szLabel,
	                      int nSize,
	                      boolean bEnabled,
	                      int nHeight,
	                      int nWidth,
	                      boolean bBorder
	                    )
	{
		super ( context );
		
		this.builder ( NO_ID, bBorder, nSize );
		this.Text.set_caption ( szLabel );
		this.set_enabled ( bEnabled )
		    .set_data_height ( nHeight )
		    .set_data_width ( nWidth );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public notepad_impl builder ( int caption_id,
	                              boolean isborder,
	                              int edit_size
	                            )
	{
		this.Data = new EditText ( this.getContext ( ) );
		this.Text = new textview_impl ( this.getContext ( ),
		                                caption_id,
		                                textview_impl.DEFAULT,
		                                textview_impl.TRACOLOR,
		                                false
		);
		
		this.Text.setGravity ( Gravity.LEFT );
		this.Data.setFilters ( new InputFilter[]{ new InputFilter.LengthFilter ( edit_size ) } );
		
		this.set_padding ( config_display_metrics.Padding )
		    .set_font_size ( DEFAULT )
		    .set_enabled ( false )
		    .set_data_width ( NO_ID )
		    .set_margins ( config_display_metrics.Padding )
		    .set_data_height ( NO_ID );
		
		if ( caption_id != DEFAULT )
		{
			this.addView ( this.alloc_space ( 0, config_display_metrics.space_between_controls ) );
			this.addView ( this.Text, this.Text.Params );
		}
		
		this.addView ( this.alloc_space ( 0, config_display_metrics.space_between_controls ) );
		this.addView ( this.Data, this.Params );
		this.set_border ( isborder );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public notepad_impl set_border ( boolean bDraw )
	{
		if ( bDraw )
		{
			borderwidget_impl.builder ( this.Data );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_data ( )
	{
		return this.Data.getText ( )
		                .toString ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public notepad_impl set_margins ( Rect margins )
	{
		if(margins != null)
		{
			super.set_margins ( margins );
			this.Text.set_margins ( margins );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public notepad_impl set_padding ( Rect padding )
	{
		if(padding != null)
		{
			this.Data.setPadding ( padding.left,
			                       padding.top,
			                       padding.right,
			                       padding.bottom
			                     );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private notepad_impl set_font_size ( int nFontSize )
	{
		switch ( nFontSize )
		{
			case DEFAULT:
				font_impl.set_size ( this.Data,
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				font_impl.set_size ( this.Text.get_handle ( ),
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public notepad_impl set_enabled ( boolean bEnabled )
	{
		this.setEnabled ( bEnabled );
		this.Data.setEnabled ( bEnabled );
		this.Text.setEnabled ( bEnabled );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public notepad_impl set_data_width ( int nWidth )
	{
		switch ( nWidth )
		{
			case - 1:
				nWidth = to_sp ( config_display_metrics.minimum_control_width );
				break;
			default:
				nWidth = to_sp ( nWidth );
				break;
		}
		this.Data.setWidth ( nWidth );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public notepad_impl set_data_height ( int nHeight )
	{
		switch ( nHeight )
		{
			case DEFAULT:
				nHeight = to_sp ( config_display_metrics.minimum_control_height );
				break;
			default:
				nHeight = to_sp ( nHeight );
				break;
		}
		this.Data.setHeight ( nHeight );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final EditText get_handle ( )
	{
		//
		return this.Data;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final textview_impl get_textview_handle ( )
	{
		//
		return this.Text;
	}
}
