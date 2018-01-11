/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;

import br.com.tk.mcs.R;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_layout_impl;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class imagebutton extends linear_layout_impl
{
	public final static String         TAG          = imagebutton.class.getName ( );
	public static final int            UP           = 0xff00;
	public static final int            DOWN         = 0xf65a;
	public static final int            LEFT         = 0xf55a;
	public static final int            RIGHT        = 0xfff1;
	public static       Point          m_offset     = config_display_metrics.ButtonImageIcon;
	private             imageview_impl m_image_view = null;
	private             textview_impl  m_text_view  = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imagebutton ( Context context,
	                     int nCaption,
	                     Point point,
	                     int nResourceID,
	                     boolean bBorder,
	                     int nMode
	                   )
	{
		super ( context );
		
		this.m_image_view = new imageview_impl ( this.getContext ( ),
		                                         m_offset,
		                                         nResourceID,
		                                         false
		);
		this.m_text_view = new textview_impl ( this.getContext ( ),
		                                       nCaption,
		                                       textview_impl.DEFAULT,
		                                       textview_impl.TRACOLOR,
		                                       false
		);
		this.setId ( nResourceID );
		this.invalidate ( this.m_text_view,
		                  true
		                );
		this.set_details ( point,
		                   nMode
		                 );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imagebutton ( Context context,
	                     String szCaption,
	                     Point point,
	                     int nResourceID,
	                     boolean bBorder,
	                     int nMode
	                   )
	{
		super ( context );
		
		this.m_image_view = new imageview_impl ( this.getContext ( ),
		                                         m_offset,
		                                         nResourceID,
		                                         false
		);
		this.setId ( nResourceID );
		this.m_text_view = new textview_impl ( this.getContext ( ) ).set_caption ( szCaption )
		                                                            .set_font_size ( textview_impl.DEFAULT )
		                                                            .set_color ( textview_impl.TRACOLOR );
		this.invalidate ( this.m_text_view,
		                  true
		                );
		this.set_details ( point,
		                   nMode
		                 );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imagebutton set_details ( Point point,
	                                 int nMode
	                               )
	{
		/*
			set internal id
		 */
		this.setId ( this.m_image_view.getId ( ) );
		
		if ( this.m_text_view.getWidth ( ) > 0 )
		{
			if ( point == null )
			{
				point = new Point ( 0,
				                    0
				);
			}
			
			if ( nMode == UP || nMode == DOWN )
			{
				point.x = this.m_text_view.getWidth ( ) + 0x10;
				point.y = m_offset.y + this.m_text_view.getHeight ( ) + 0x10;
			}
			
			if ( nMode == LEFT || nMode == RIGHT )
			{
				point.x = m_offset.x + this.m_text_view.getWidth ( ) + 0x10;
				point.y = m_offset.y + 0x10;
			}
			this.Params = this.build ( new Point ( point ) );
		}
		
		switch ( nMode )
		{
			case UP:
			case DOWN:
				this.setOrientation ( VERTICAL );
				break;
			case LEFT:
			case RIGHT:
				this.setOrientation ( HORIZONTAL );
				break;
		}
		
		switch ( nMode )
		{
			case UP:
				this.addView ( this.m_image_view,
				               this.m_image_view.Params
				             );
				this.addView ( this.m_text_view,
				               this.m_text_view.Params
				             );
				break;
			case DOWN:
				this.addView ( this.m_image_view,
				               this.m_image_view.Params
				             );
				this.addView ( this.m_text_view,
				               this.m_text_view.Params
				             );
				break;
			case LEFT:
				this.addView ( this.m_image_view,
				               this.m_image_view.Params
				             );
				this.addView ( this.m_text_view,
				               this.m_text_view.Params
				             );
				break;
			case RIGHT:
				this.addView ( this.m_text_view,
				               this.m_text_view.Params
				             );
				this.addView ( this.m_image_view,
				               this.m_image_view.Params
				             );
				break;
		}
		
		this.setGravity ( Gravity.CENTER );
		this.m_text_view.get_handle ( )
		                .setTextColor ( Color.BLACK );
		this.m_image_view.setBackgroundResource ( R.drawable.button_selector );
		this.setBackgroundResource ( R.drawable.button_selector );
		this.setEnabled ( false );
		this.invalidate ( true );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setEnabled ( boolean enabled )
	{
		this.m_image_view.setEnabled ( enabled );
		this.m_text_view.setEnabled ( enabled );
		super.setEnabled ( enabled );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setBackgroundResource ( int nResourceId )
	{
		this.m_image_view.setBackgroundResource ( nResourceId );
		super.setBackgroundResource ( nResourceId );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final imageview_impl get_image_view ( ) /*GetImageView*/
	{
		return this.m_image_view;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final textview_impl get_text_view ( )
	{
		/*****/
		return this.m_text_view;
	}
}
