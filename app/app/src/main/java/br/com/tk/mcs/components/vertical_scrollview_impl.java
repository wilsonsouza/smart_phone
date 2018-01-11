/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.widget.ScrollView;

import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 20/12/17.
 */

public class vertical_scrollview_impl extends linear_vertical
{
	private final static String          TAG           = horizontal_scrollview_impl.class.getName ( );
	private              ScrollView      m_scroll_view = null;
	private              linear_vertical m_contaneir   = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public vertical_scrollview_impl ( final Context context,
	                                  final boolean is_border
	                                )
	{
		super ( context );
		this.builder ( is_border, Color.TRANSPARENT );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public vertical_scrollview_impl ( final Context context,
	                                  final boolean bBorder,
	                                  final int nColor,
	                                  final Rect margins,
	                                  final Rect padding
	                                )
	{
		super ( context );
		
		this.builder ( bBorder, nColor )
		    .set_padding ( padding )
		    .set_margins ( margins );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public vertical_scrollview_impl builder ( boolean border,
	                                          int border_color
	                                        )
	{
		this.m_scroll_view = new ScrollView ( this.getContext ( ) );
		this.m_contaneir = new linear_vertical ( this.getContext ( ) );
		
		this.m_scroll_view.addView ( this.m_contaneir, this.m_contaneir.Params );
		this.addView ( this.m_scroll_view, this.Params );
		
		this.set_border ( border, Color.TRANSPARENT, border_color );
		this.set_border ( border );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public vertical_scrollview_impl set_padding ( final Rect padding )
	{
		if ( padding != null )
		{
			Rect p = this.to_rect ( padding );
			this.m_scroll_view.setPadding ( p.left,
			                                p.top,
			                                p.right,
			                                p.bottom
			                              );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final linear_vertical get_contaneir_handle ( )
	{
		//
		return m_contaneir;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final ScrollView get_handle ( )
	{
		//
		return m_scroll_view;
	}
}
