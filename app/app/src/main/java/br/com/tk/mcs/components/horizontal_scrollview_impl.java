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
import android.widget.HorizontalScrollView;

import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 17/02/17.
 */

public class horizontal_scrollview_impl extends linear_vertical
{
	private final static String               TAG            = horizontal_scrollview_impl.class.getName ( );
	private              HorizontalScrollView m_hscroll_view = null;
	private              linear_horizontal    m_contaneir    = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public horizontal_scrollview_impl ( final Context context,
	                                    final boolean is_border
	                                  )
	{
		super ( context );
		this.m_hscroll_view = new HorizontalScrollView ( context );
		this.m_contaneir = new linear_horizontal ( context );
		this.set_border ( is_border );
		
		this.m_hscroll_view.addView ( this.m_contaneir,
		                              this.m_contaneir.Params
		                            );
		this.addView ( this.m_hscroll_view,
		               this.Params
		             );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public horizontal_scrollview_impl ( final Context context,
	                                    final boolean bBorder,
	                                    final int nColor,
	                                    final Rect margins,
	                                    final Rect padding
	                                  )
	{
		super ( context );
		this.m_hscroll_view = new HorizontalScrollView ( this.getContext ( ) );
		this.m_contaneir = new linear_horizontal ( this.getContext ( ) );
		this.set_border ( bBorder );
		this.m_hscroll_view.addView ( this.m_contaneir,
		                              this.m_contaneir.Params
		                            );
		this.addView ( this.m_hscroll_view,
		               this.Params
		             );
		this.set_border ( bBorder,
		                  nColor,
		                  Color.TRANSPARENT
		                );
		this.set_padding ( padding );
		this.Params.set_margins ( margins );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public horizontal_scrollview_impl set_padding ( final Rect padding )
	{
		if ( padding != null )
		{
			Rect p = this.to_rect ( padding );
			this.m_hscroll_view.setPadding ( p.left,
			                                 p.top,
			                                 p.right,
			                                 p.bottom
			                               );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final linear_horizontal get_contaneir_handle ( )
	{
		//
		return m_contaneir;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final HorizontalScrollView get_handle ( )
	{
		//
		return m_hscroll_view;
	}
}
