/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.widget.TextView;

import br.com.tk.mcs.R;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;

/**
 * Created by wilsonsouza on 27/01/17.
 */

public class textview_impl extends linear_horizontal
{
	public final static String   TAG      = textview_impl.class.getName ( );
	public final static int      DEFAULT  = - 2;
	public final static int      CUSTOM   = - 4;
	public final static int      CAPTION  = - 1;
	public final static int      NL_COLOR = - 1;
	public final static int      RL_COLOR = - 3;
	public final static int      PS_TYPE  = 0;
	public final static int      PS_COLOR = 1;
	public final static int[]    DEFCOLOR = new int[]{ RL_COLOR,
	                                                   R.drawable.window_caption_bar
	};
	public final static int[]    TRACOLOR = new int[]{ NL_COLOR,
	                                                   Color.TRANSPARENT
	};
	private             TextView m_handle = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public textview_impl ( final Context context )
	{
		super ( context );
		
		this.builder ( NO_ID, false, NO_ID );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public textview_impl ( final Context context,
	                       final int caption_id,
	                       final int font_size,
	                       final int[] array_list_color,
	                       final boolean isborder
	                     )
	{
		super ( context );
		
		this.builder ( caption_id, isborder, font_size )
		    .set_color ( array_list_color );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public textview_impl builder ( int caption_id,
	                               boolean isborder,
	                               int font_size
	                             )
	{
		this.m_handle = new TextView ( this.getContext ( ) );
		
		this.Params = this.build ( WRAP, WRAP );
		this.Params.gravity = ( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL );
		this.set_border ( isborder )
		    .set_font_size ( font_size )
		    .set_caption ( caption_id );
		this.addView ( this.m_handle, this.Params );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public textview_impl set_border ( final boolean bDraw )
	{
		if ( bDraw )
		{
			borderwidget_impl.builder ( this );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public <T> textview_impl set_caption ( final T caption )
	{
		if ( caption.getClass ( )
		            .getName ( )
		            .equals ( "java.lang.String" ) )
		{
			if ( caption.toString ( ) != null )
			{
				this.m_handle.setText ( Html.fromHtml ( caption.toString ( ) ) );
			}
			else
			{
				this.m_handle.setText ( "" );
			}
		}
		else if ( caption.getClass ( )
		                 .getName ( )
		                 .equals ( "java.lang.Integer" ) )
		{
			int id = Integer.parseInt ( caption.toString ( ) );
			
			if ( id != - 1 )
			{
				this.m_handle.setText ( Html.fromHtml ( getContext ( ).getString ( id ) ) );
			}
			else
			{
				this.m_handle.setText ( "" );
			}
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public textview_impl set_color ( final int[] nColor )
	{
		if ( nColor.length == 0 )
		{
			return this;
		}
		
		switch ( nColor[ PS_TYPE ] )
		{
			case NL_COLOR:
				this.setBackgroundColor ( nColor[ PS_COLOR ] );
				this.m_handle.setBackgroundColor ( nColor[ PS_COLOR ] );
				break;
			case DEFAULT:
				this.setBackgroundResource ( DEFCOLOR[ PS_COLOR ] );
				this.setBackgroundResource ( DEFCOLOR[ PS_COLOR ] );
				break;
			case RL_COLOR:
				this.setBackgroundResource ( nColor[ PS_COLOR ] );
				this.setBackgroundResource ( nColor[ PS_COLOR ] );
				break;
			case CUSTOM:
				this.m_handle.setTextColor ( nColor[ 1 ] );
				break;
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( Build.VERSION_CODES.M )
	public textview_impl set_font_size ( final int nFontSize )
	{
		this.m_handle.setTextAppearance ( config_display_metrics.TextStyle );
		
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
				                     font_impl.CAPTION_SIZE,
				                     font_impl.CAPTION_SIZE
				                   );
				break;
			default:
				this.m_handle.setTextSize ( nFontSize,
				                            config_display_metrics.get_configuration ( ).fontScale
				                          );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final TextView get_handle ( )
	{
		/* get textview handle */
		return this.m_handle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final int get_pixels_length ( )
	{
		//
		return ( int ) this.m_handle.getPaint ( )
		                            .measureText ( this.m_handle.getText ( )
		                                                        .toString ( ) );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Rect get_pixels_area ( )
	{
		final String buffer = this.m_handle.getText ( )
		                                   .toString ( );
		Rect r = new Rect ( );
		this.m_handle.getPaint ( )
		             .getTextBounds ( buffer,
		                              0,
		                              buffer.length ( ),
		                              r
		                            );
		return r;
	}
	//-----------------------------------------------------------------------------------------------------------------//
	public final Rect get_pixels_one_byte()
	{
		Rect rect = new Rect();
		
		this.m_handle.getPaint ().getTextBounds ( "H", 0, 1, rect );
		return rect;
	}
	//-----------------------------------------------------------------------------------------------------------------//
	public textview_impl set_padding ( final Rect padding )
	{
		Rect r = this.to_rect ( padding );
		
		this.m_handle.setPadding ( r.left,
		                           r.top,
		                           r.right,
		                           r.bottom
		                         );
		return this;
	}
}
