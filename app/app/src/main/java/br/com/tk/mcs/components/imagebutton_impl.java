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
import android.util.Log;
import android.widget.ImageButton;

import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class imagebutton_impl extends linear_vertical
{
	public final static String      TAG  = imagebutton_impl.class.getName ( );
	private             ImageButton Data = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imagebutton_impl ( Context context,
	                          Point point,
	                          int nResourceID,
	                          boolean bBorder,
	                          Rect padding,
	                          Rect margins
	                        )
	{
		super ( context );
		this.Data = new ImageButton ( this.getContext ( ) );
		this.set_image ( nResourceID );
		this.set_margins ( margins );
		this.set_padding ( padding );
		this.Params.set_dimension ( new Point ( point.x,
		                                        point.y
		) );
		this.addView ( this.Data,
		               this.Params
		             );
		this.set_border ( bBorder );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void set_image ( int nResourceID )
	{
		if ( nResourceID != - 1 )
		{
			Bitmap bmp = BitmapFactory.decodeResource ( getContext ( ).getResources ( ),
			                                            nResourceID
			                                          );
			this.Data.setId ( nResourceID );
			this.resize ( bmp,
			              new Point ( this.Params.width,
			                          this.Params.height
			              )
			            );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void resize ( Bitmap bmp,
	                     Point p
	                   )
	{
		Bitmap bitbmp = Bitmap.createScaledBitmap ( bmp,
		                                            to_dp ( p.x ),
		                                            to_dp ( p.y ),
		                                            true
		                                          );
		this.Data.setImageBitmap ( bitbmp );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void resize ( ImageButton pView,
	                     Point p
	                   )
	{
		try
		{
			Bitmap bmp = ( ( BitmapDrawable ) pView.getDrawable ( ) ).getBitmap ( );
			bmp = Bitmap.createScaledBitmap ( bmp,
			                                  p.x,
			                                  p.y,
			                                  true
			                                );
			pView.setImageBitmap ( bmp );
		}
		catch ( Exception e )
		{
			Log.e ( imagebutton_impl.class.getClass ( )
			                              .getName ( ),
			        e.getMessage ( )
			      );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imagebutton_impl set_padding ( Rect padding )
	{
		if ( padding != null )
		{
			padding = this.to_rect ( padding );
			this.setPadding ( padding.left,
			                  padding.top,
			                  padding.right,
			                  padding.bottom
			                );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imagebutton_impl set_margins ( Rect margins )
	{
		if ( margins != null )
		{
			margins = this.to_rect ( margins );
			this.Params.setMargins ( margins.left,
			                         margins.top,
			                         margins.right,
			                         margins.bottom
			                       );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public imagebutton_impl set_color ( int nColor )
	{
		this.setBackgroundColor ( nColor );
		this.Data.setBackgroundColor ( nColor );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final ImageButton get_handle ( )
	{
		//
		return this.Data;
	}
}
