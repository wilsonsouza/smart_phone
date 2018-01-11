/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_layout_impl;

/**
 * Created by wilsonsouza on 17/02/17.
 */

public class toolbar_impl extends linear_horizontal
{
	private final static int               ID_ICONS   = 0x009fff09;
	private              AppCompatActivity m_instance = null;
	private              ActionBar         m_handle   = null;
	private              textview_impl     m_caption  = null;
	private              linear_horizontal m_icons    = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public toolbar_impl ( AppCompatActivity pWnd,
	                      int nCaption
	                    )
	{
		super ( pWnd );
		this.m_instance = pWnd;
		this.m_handle = pWnd.getSupportActionBar ( );
		this.m_handle.setDisplayShowCustomEnabled ( true );
		this.m_handle.setDisplayShowTitleEnabled ( false );
		this.Params = this.build ( MATCH,
		                           this.Params.get_actionbar_height ( )
		                         );
		//this.Params.width -= 0x10;
		
		int[] anColors = new int[]{ textview_impl.CUSTOM,
		                            Color.WHITE
		};
		this.m_caption = new textview_impl ( pWnd,
		                                     nCaption,
		                                     textview_impl.CAPTION,
		                                     anColors,
		                                     false
		);
		this.m_caption.Params.gravity = ( Gravity.LEFT | Gravity.CENTER_VERTICAL );
		this.addView ( this.m_caption,
		               this.m_caption.Params
		             );
		this.m_caption.invalidate ( true );
		this.m_handle.setCustomView ( this );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_icon_list ( int[] icons,
	                            View.OnClickListener pOnClick
	                          )
	{
		int               width  = this.Params.get_actionbar_height ( ) / 2;
		Point             area   = new Point ( width, width );
		linear_horizontal pIcons = new linear_horizontal ( this.getContext ( ) );
		{
			int start_pos = ( icons.length * area.x );
			
			pIcons.Params = this.build ( start_pos, this.Params.get_actionbar_height ( ) );
			pIcons.Params.gravity = ( Gravity.RIGHT | Gravity.CENTER_VERTICAL );
			
			for ( int icon : icons )
			{
				imageview_impl bmp = new imageview_impl ( this.getContext ( ), area, icon, false );
				{
					bmp.Params.gravity = ( Gravity.CENTER | Gravity.CENTER_VERTICAL );
					bmp.set_padding ( config_display_metrics.Padding );
					pIcons.addView ( bmp, bmp.Params );
					bmp.invalidate ( true );
					bmp.setOnClickListener ( pOnClick );
				}
			}
			/**/
			pIcons.setId ( ID_ICONS );
			pIcons.setGravity ( Gravity.RIGHT | Gravity.CENTER_VERTICAL );
			this.addView ( pIcons );
			this.invalidate ( true );
			pIcons.invalidate ( true );
			//pIcons.Params.width = this.calcule_width ( pIcons );
			//pIcons.setLayoutParams ( pIcons.Params );
			this.m_icons = pIcons;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final imageview_impl get_icon_by_id ( final int icon_ic )
	{
		View view = this.findViewById ( ID_ICONS );
		
		if ( view != null )
		{
			return ( imageview_impl ) view.findViewById ( icon_ic );
		}
		return null;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private int calcule_width ( linear_layout_impl pIcons )
	{
		int data = this.m_handle.getCustomView ( )
		                        .getWidth ( );
		int value  = 0;
		int nFlags = ActionBar.DISPLAY_USE_LOGO & this.m_handle.getDisplayOptions ( );
		int n      = pIcons.getWidth ( );
		
		switch ( nFlags )
		{
			case ActionBar.DISPLAY_USE_LOGO:
				value = this.Params.width - data - 8;
				break;
			default:
				n -= ( pIcons.getChildCount ( ) * 8 );
				n += config_display_metrics.HomeIndicator.x;
				value = ( this.Params.width - data ) + n;
				break;
		}
		return value;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_icon ( final int nIndex,
	                       final int nIconID
	                     )
	{
	   /* run on Main thread address */
		( ( Activity ) this.getContext ( ) ).runOnUiThread ( new change_state_icon ( nIndex, nIconID ) );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_visible ( final int index,
	                          final boolean enabled
	                        )
	{
		final Activity view = ( Activity ) this.getContext ( );
		
		view.runOnUiThread ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				imageview_impl p = ( imageview_impl ) m_icons.getChildAt ( index );
				
				if ( enabled )
				{
					p.setVisibility ( VISIBLE );
				}
				else
				{
					p.setVisibility ( INVISIBLE );
				}
			}
		} );
	}
	
	//---------------------------------------------------------------------------------------------//
	public final textview_impl get_caption_handle ( )
	{
		//
		return this.m_caption;
	}
	
	//---------------------------------------------------------------------------------------------//
	public final ActionBar get_actionbar_handle ( )
	{
		//
		return this.m_handle;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class change_state_icon implements Runnable
	{
		private int m_index = 0;
		private int m_id    = 0;
		
		public change_state_icon ( final int nIndex,
		                           final int nIconId
		                         )
		{
			this.m_index = nIndex;
			this.m_id = nIconId;
		}
		
		@Override
		public void run ( )
		{
			imageview_impl bmp = ( imageview_impl ) m_icons.getChildAt ( this.m_index );
			
			if ( bmp.getId ( ) != this.m_id )
			{
				bmp.set_image ( this.m_id );
			}
		}
	}
}
