/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.EditText;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.generic.device_screen;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class edittext_impl extends linear_vertical
{
	public final static String        TAG            = edittext_impl.class.getName ( );
	public final static int           DEFAULT        = NO_ID;
	public final static int           ID_TEXT_LEFT   = 1;
	public final static int           ID_TEXT_RIGHT  = 2;
	public final static int           ID_TEXT_CENTER = 4;
	public final static int           ID_EDIT_LEFT   = 0x10;
	public final static int           ID_EDIT_RIGHT  = 0x20;
	private             edittext_base m_handle       = null;
	private             textview_impl m_view         = null;
	private             Rect          m_margins      = config_display_metrics.EditRect;
	private             Rect          m_padding      = config_display_metrics.Padding;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_impl ( final Context context,
	                       final int caption_id,
	                       final int max_field_length
	                     )
	{
		super ( context,
		        null,
		        0
		      );
		
		this.builder ( caption_id )
		    .builder ( max_field_length,
		               false
		             );
		this.set_font_size ( NO_ID )
		    .set_enabled ( false )
		    .set_data_width ( NO_ID );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_impl ( Context context,
	                       int caption_id,
	                       int max_field_length,
	                       boolean isenabled,
	                       final int font_size,
	                       int nWidth,
	                       boolean isborder
	                     )
	{
		super ( context,
		        null,
		        0
		      );
		
		this.builder ( caption_id )
		    .builder ( max_field_length,
		               false
		             );
		this.set_font_size ( font_size )
		    .set_enabled ( isenabled );
		this.set_data_width ( nWidth );
		this.set_border ( isborder );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_impl builder ( final int caption_id )
	{
		if ( caption_id != NO_ID )
		{
			this.m_view = new textview_impl ( this.getContext ( ) );
			
			this.m_view.set_caption ( caption_id );
			this.m_view.set_color ( textview_impl.TRACOLOR );
			this.m_view.set_font_size ( textview_impl.DEFAULT );
			this.m_view.set_padding ( this.m_margins );
			this.m_view.Params.gravity = ( Gravity.START );
			
			this.addView ( this.m_view, this.m_view.Params );
			this.addView ( this.alloc_space ( 0, config_display_metrics.space_between_controls ) );
			this.invalidate ( true );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final edittext_impl builder ( int max_field_length,
	                                     boolean is_border
	                                   )
	{
		this.m_handle = new edittext_base ( this.getContext ( ) ).builder ( max_field_length,
		                                                                    false,
		                                                                    NO_ID
		                                                                  );
		
		this.m_handle.set_border ( is_border );
		this.m_handle.Params.gravity = Gravity.START;
		this.set_data_width ( NO_ID );
		
		this.addView ( this.m_handle, this.m_handle.Params );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public edittext_impl set_border ( boolean bDraw )
	{
		if ( bDraw )
		{
			borderwidget_impl.builder ( this.m_handle );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public String get_data ( )
	{
		return this.m_handle.get_handle ( )
		                    .getText ( )
		                    .toString ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_impl set_data ( String buffer )
	{
		this.m_handle.get_handle ( )
		             .setText ( buffer );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public edittext_impl set_margins ( Rect margins )
	{
		this.m_view.set_margins ( margins );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public edittext_impl set_padding ( Rect padding )
	{
		padding = device_screen.rect_to_dp ( padding );
		this.m_handle.setPadding ( padding.left,
		                           padding.top,
		                           padding.right,
		                           padding.bottom
		                         );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private edittext_impl set_font_size ( int nFontSize )
	{
		switch ( nFontSize )
		{
			case DEFAULT:
				font_impl.set_size ( this.m_handle,
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				font_impl.set_size ( this.m_view.get_handle ( ),
				                     font_impl.DEFAULT_SIZE,
				                     font_impl.DEFAULT_SIZE
				                   );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_impl set_enabled ( boolean bEnabled )
	{
		this.setEnabled ( bEnabled );
		this.m_handle.setEnabled ( bEnabled );
		this.m_view.setEnabled ( bEnabled );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private edittext_impl set_data_width ( int data_width )
	{
		this.m_handle.set_data_width ( data_width );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final void set_alignment ( final int combinations )
	{
		if ( ( combinations & ID_TEXT_LEFT ) == ID_TEXT_LEFT )
		{
			this.m_view.Params.gravity = Gravity.LEFT;
		}
		else if ( ( combinations & ID_TEXT_RIGHT ) == ID_TEXT_RIGHT )
		{
			this.m_view.Params.gravity = Gravity.RIGHT;
		}
		else if ( ( combinations & ID_TEXT_CENTER ) == ID_TEXT_CENTER )
		{
			this.m_view.Params.gravity = Gravity.CENTER;
		}
		else if ( ( combinations & ID_EDIT_LEFT ) == ID_EDIT_LEFT )
		{
			this.m_handle.Params.gravity = ( Gravity.LEFT );
		}
		else if ( ( combinations & ID_EDIT_RIGHT ) == ID_EDIT_RIGHT )
		{
			this.m_handle.Params.gravity = ( Gravity.RIGHT );
		}
		
		this.m_view.setLayoutParams ( this.m_view.Params );
		this.m_handle.setLayoutParams ( this.m_handle.Params );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final edittext_base get_handle_base ( )
	{
		//
		return m_handle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public EditText get_handle ( )
	{
		//
		return this.m_handle.get_handle ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final textview_impl get_textview_handle ( )
	{
		//
		return m_view;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_impl set_input_type ( int input_type )
	{
		this.get_handle ( )
		    .setInputType ( input_type );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public edittext_impl set_cursor ( boolean enabled_or_disabled )
	{
		//
		this.m_handle.get_handle ( )
		             .setCursorVisible ( enabled_or_disabled );
		return this;
	}
}
