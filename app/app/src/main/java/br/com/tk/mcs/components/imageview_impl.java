/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class imageview_impl extends linear_vertical
{
	public final static String    TAG          = imageview_impl.class.getName ( );
	private             ImageView m_image_view = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl ( final Context context,
	                        final int resource_id
	                      )
	{
		super ( context );
		this.m_image_view = new ImageView ( this.getContext ( ) );
		this.Params.set_dimension ( config_display_metrics.ButtonImageIcon );
		this.set_image ( resource_id );
		this.addView ( this.m_image_view,
		               this.Params
		             );
		this.set_border ( false );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl ( final Context context,
	                        final Point point,
	                        final int resource_id,
	                        final boolean bBorder
	                      )
	{
		super ( context );
		this.m_image_view = new ImageView ( context );
		this.Params.set_dimension ( point );
		this.set_image ( resource_id );
		this.addView ( this.m_image_view,
		               this.Params
		             );
		this.set_border ( bBorder );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl set_image ( int resource_id )
	{
		try
		{
			if ( resource_id != - 1 )
			{
				Bitmap bmp = BitmapFactory.decodeResource ( getContext ( ).getResources ( ),
				                                            resource_id
				                                          );
				this.setId ( resource_id );
				bmp.setDensity ( this.Params.get_display ( ).densityDpi );
				bmp = Bitmap.createScaledBitmap ( bmp,
				                                  this.Params.width,
				                                  this.Params.height,
				                                  true
				                                );
				this.m_image_view.setImageBitmap ( bmp );
			}
		}
		catch ( Exception e )
		{
			Log.e ( TAG,
			        e.getMessage ( )
			      );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl resize ( Bitmap bmp,
	                               Point p
	                             )
	{
		try
		{
			Point np = p; //this.to_point ( p );
			Bitmap bitbmp = Bitmap.createScaledBitmap ( bmp,
			                                            np.x,
			                                            np.y,
			                                            true
			                                          );
			bitbmp.setDensity ( this.Params.get_display ( ).densityDpi );
			this.m_image_view.setImageBitmap ( bitbmp );
		}
		catch ( Exception e )
		{
			Log.e ( TAG,
			        e.getMessage ( )
			      );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl to_icon ( @Nullable final Point p )
	{
		Point point = this.to_point ( p == null ?
		                              new Point ( config_display_metrics.notifican_icons_size,
		                                          config_display_metrics.notifican_icons_size
		                              ) :
		                              p );
		Bitmap bmp = ( ( BitmapDrawable ) this.m_image_view.getDrawable ( ) ).getBitmap ( );
		
		this.m_image_view.setImageBitmap ( Bitmap.createScaledBitmap ( bmp,
		                                                               point.x,
		                                                               point.y,
		                                                               true
		                                                             ) );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl resize ( final ImageView pView,
	                               Point p
	                             )
	{
		try
		{
			Point  np  = this.to_point ( p );
			Bitmap bmp = ( ( BitmapDrawable ) pView.getDrawable ( ) ).getBitmap ( );
			bmp = Bitmap.createScaledBitmap ( bmp,
			                                  np.x,
			                                  np.y,
			                                  true
			                                );
			pView.setImageBitmap ( bmp );
		}
		catch ( Exception e )
		{
			Log.e ( TAG,
			        e.getMessage ( )
			      );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl set_padding ( Rect padding )
	{
		if ( padding != null )
		{
			padding = this.to_rect ( padding );
			this.m_image_view.setPadding ( padding.left,
			                               padding.top,
			                               padding.right,
			                               padding.bottom
			                             );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl set_margins ( Rect margins )
	{
		this.Params.set_margins ( margins );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl set_color ( final int color )
	{
		this.setBackgroundColor ( color );
		this.m_image_view.setBackgroundColor ( color );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Drawable to_drawable ( )
	{
		//
		return this.m_image_view.getDrawable ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public boolean isEnabled ( )
	{
		//
		return this.m_image_view.isEnabled ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void setEnabled ( boolean enabled )
	{
		this.m_image_view.setEnabled ( enabled );
		super.setEnabled ( enabled );
	}
	//-----------------------------------------------------------------------------------------------------------------//
	public imageview_impl set_enabled(boolean enabled)
	{
		this.setEnabled ( enabled );
		return this;
	}
	//-----------------------------------------------------------------------------------------------------------------//
	public final ImageView get_handle ( )
	{
		//
		return this.m_image_view;
	}
}
