package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;

import br.com.tk.mcs.R;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_horizontal;

/**
 * Created by wilsonsouza on 09/02/17.
 */

public class button_impl extends linear_horizontal implements View.OnClickListener
{
	public final static int    DEFAULT_RESOURCE = - 1;
	public final static int    IDOK             = 0xf901;
	public final static int    IDCANCEL         = 0x765f;
	public final static int    LEFT             = 0xff01;
	public final static int    RIGHT            = 0xa001;
	public final static int    UP               = 0x9871;
	public final static int    DOWN             = 0x87f0;
	private             Button m_data           = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public button_impl ( final Context context,
	                     final int caption_resource_id,
	                     final boolean enabled
	                   )
	{
		super ( context );
		this.builder ( caption_resource_id, NO_ID, enabled );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public button_impl ( final Context pWnd,
	                     final int nID,
	                     final boolean bEnabled,
	                     final int background_resource_id,
	                     final Rect padding,
	                     final Rect margins
	                   )
	{
		super ( pWnd );
		this.builder ( nID, background_resource_id, bEnabled );
		
		if(padding != null)
		{
			Rect p = this.to_rect ( padding );
			this.m_data.setPadding ( p.left, p.top, p.right, p.bottom );
		}
		
		if(margins != null)
		{
			this.set_margins ( margins );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public button_impl builder ( int resource_id,
	                             int background_resource_id,
	                             boolean isenabled
	                           )
	{
		this.m_data = new Button ( this.getContext ( ) );
		
		this.m_data.setText ( resource_id );
		this.m_data.setId ( resource_id );
		this.m_data.setEnabled ( isenabled );
		this.setBackgroundResource ( background_resource_id );
		font_impl.set_size ( this.m_data, 0, 0 );
		
		this.addView ( this.m_data, this.Params );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setEnabled ( final boolean bEnabled )
	{
		this.m_data.setEnabled ( bEnabled );
		super.setEnabled ( bEnabled );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setOnClickListener ( final View.OnClickListener pfnClick )
	{
		this.m_data.setOnClickListener ( pfnClick );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setBackgroundResource ( final int resource_id )
	{
		if ( resource_id != NO_ID )
		{
			this.m_data.setBackgroundResource ( resource_id );
			super.setBackgroundResource ( resource_id );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_icon ( final icon_details icon )
	{
		final Point icon_size = new Point ( config_display_metrics.notifican_icons_size,
		                                    config_display_metrics.notifican_icons_size
		);
		final imageview_impl icon_impl = new imageview_impl ( this.getContext ( ),
		                                                      icon_size,
		                                                      NO_ID,
		                                                      false
		);
		
		switch ( icon.get_icon_id ( ) )
		{
			case IDOK:
				icon_impl.set_image ( R.drawable.button_ok_3d );
				break;
			case IDCANCEL:
				icon_impl.set_image ( R.drawable.button_cancel_3d );
				break;
			default:
				icon_impl.set_image ( icon.get_icon_id ( ) );
				break;
		}
		
		switch ( icon.get_id ( ) )
		{
			case LEFT:
				this.m_data.setCompoundDrawables ( icon_impl.to_drawable ( ),
				                                   null,
				                                   null,
				                                   null
				                                 );
				break;
			case DOWN:
				this.m_data.setCompoundDrawables ( null,
				                                   null,
				                                   null,
				                                   icon_impl.to_drawable ( )
				                                 );
				break;
			case RIGHT:
				this.m_data.setCompoundDrawables ( null,
				                                   null,
				                                   icon_impl.to_drawable ( ),
				                                   null
				                                 );
				break;
			case UP:
				this.m_data.setCompoundDrawables ( null,
				                                   icon_impl.to_drawable ( ),
				                                   null,
				                                   null
				                                 );
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Button get_handle ( )
	{
		return this.m_data;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final void set_enabled ( final boolean enabled )
	{
		this.setEnabled ( enabled );
		this.m_data.setEnabled ( enabled );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static class icon_details
	{
		private int m_id      = LEFT;
		private int m_icon_id = IDOK;
		
		public icon_details ( final int nIconId,
		                      final int nId
		                    )
		{
			this.m_id = nId;
			this.m_icon_id = nIconId;
		}
		
		public final int get_id ( )
		{
			return m_id;
		}
		
		public final int get_icon_id ( )
		{
			return m_icon_id;
		}
	}
}
