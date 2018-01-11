/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.layouts;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.com.tk.mcs.components.borderwidget_impl;
import br.com.tk.mcs.components.space_impl;
import br.com.tk.mcs.components.update_content_impl;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.generic.device_screen;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class linear_layout_impl extends LinearLayout
{
	public static final String     TAG    = linear_layout_impl.class.getName ( );
	public static final int        WRAP   = LayoutParams.WRAP_CONTENT;
	public static final int        MATCH  = LayoutParams.MATCH_PARENT;
	public              parameters Params = new parameters ( WRAP,
	                                                         WRAP
	);
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl ( final Context context )
	{
		super ( context );
		this.setLayoutParams ( this.Params );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl ( final Context context,
	                            final AttributeSet attrs
	                          )
	{
		super ( context,
		        attrs
		      );
		this.setLayoutParams ( this.Params );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl ( final Context context,
	                            final AttributeSet attrs,
	                            final int defStyleAttr
	                          )
	{
		super ( context,
		        attrs,
		        defStyleAttr
		      );
		this.setLayoutParams ( Params );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl set_margins ( @NonNull final Rect r )
	{
		this.Params.set_margins ( r );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl set_padding ( @NonNull final Rect padding )
	{
		Rect r = this.to_rect ( padding );
		this.setPadding ( r.left,
		                  r.top,
		                  r.right,
		                  r.bottom
		                );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public int to_dp ( final int value )
	{
		//
		return config_display_metrics.to_dp ( value );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public parameters build ( final int width,
	                          final int heigth
	                        )
	{
		return new parameters ( width,
		                        heigth
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public parameters build ( final Point point )
	{
		//
		return new parameters ( point );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl invalidate ( final boolean is_force_update )
	{
		update_content_impl.invalidate ( this,
		                                 is_force_update
		                               );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl invalidate ( final ViewGroup handle,
	                                       final boolean is_update
	                                     )
	{
		update_content_impl.invalidate ( handle,
		                                 is_update
		                               );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl set_border ( final int background_color )
	{
		new borderwidget_impl ( this,
		                        background_color,
		                        1,
		                        Color.BLACK
		);
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl set_border ( final boolean isborder,
	                                       final int background_border_color,
	                                       final int border_color
	                                     )
	{
		if ( isborder )
		{
			new borderwidget_impl ( this,
			                        ( background_border_color == NO_ID ?
			                          Color.TRANSPARENT :
			                          background_border_color ),
			                        1,
			                        border_color
			);
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl set_border ( final boolean isborder )
	{
		if ( isborder )
		{
			new borderwidget_impl ( this,
			                        Color.WHITE,
			                        1,
			                        Color.BLACK
			);
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Point to_point ( @NonNull final Point p )
	{
		return new Point ( this.to_dp ( p.x ),
		                   this.to_dp ( p.y )
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Rect to_rect ( @NonNull final Rect r )
	{
		return new Rect ( this.to_dp ( r.left ),
		                  this.to_dp ( r.top ),
		                  this.to_dp ( r.right ),
		                  this.to_dp ( r.bottom )
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Point to_point ( final int x,
	                        final int y
	                      )
	{
		return new Point ( this.to_dp ( x ),
		                   this.to_dp ( y )
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Rect to_rect ( final int x,
	                      final int y,
	                      final int r,
	                      final int b
	                    )
	{
		return new Rect ( this.to_dp ( x ),
		                  this.to_dp ( y ),
		                  this.to_dp ( r ),
		                  this.to_dp ( b )
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public int to_sp ( final int value )
	{
		/**/
		return device_screen.point_to_sp ( value, 0 ).x;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Point point_to_sp ( final int width,
	                           final int height
	                         )
	{
		//
		return device_screen.point_to_sp ( width, height );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Point point_to_sp ( Point point_values )
	{
		//
		return device_screen.point_to_sp ( point_values );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Rect rect_to_sp ( int x,
	                         int y,
	                         int w,
	                         int h
	                       )
	{
		//
		return device_screen.rect_to_sp ( x, y, w, h );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Rect rect_to_sp ( Rect rect_values )
	{
		//
		return device_screen.rect_to_sp ( rect_values );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public space_impl alloc_space ( int width,
	                                int height
	                              )
	{
		/* put space between controls */
		return new space_impl ( this.getContext ( ), width, height, false );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_layout_impl set_fullscreen ( )
	{
		this.Params = this.build ( MATCH, MATCH );
		return this;
	}
	
	//---------------------------------------------------------------------------------------------//
	public linear_layout_impl update_params ( )
	{
		this.setLayoutParams ( this.Params );
		this.invalidate( true );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public class parameters extends LayoutParams
	{
		public final String TAG = parameters.class.getName ( );
		
		//-----------------------------------------------------------------------------------------------------------------//
		public parameters ( final int width,
		                    final int height
		                  )
		{
			super ( width,
			        height
			      );
			/* set real dimension */
			this.update ( new Point ( width,
			                          height
			) );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public parameters ( @NonNull final Point point )
		{
			super ( point.x,
			        point.y
			      );
			this.update ( point );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		private parameters update ( @NonNull final Point point )
		{
			if ( point.x == MATCH_PARENT )
			{
				this.width = this.get_display ( ).widthPixels;
			}
			if ( point.y == MATCH_PARENT )
			{
				this.height = this.get_display ( ).heightPixels - this.get_actionbar_height ( );
			}
			return this;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public parameters set_margins ( @NonNull final Rect r )
		{
			if ( r != null )
			{
				Rect p = to_rect ( r );
				this.setMargins ( p.left,
				                  p.top,
				                  p.right,
				                  p.bottom
				                );
			}
			return this;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public parameters set_dimension ( @NonNull final Point point )
		{
			this.width = to_dp ( point.x );
			this.height = to_dp ( point.y );
			return this;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final int get_actionbar_height ( )
		{
			TypedValue tv = new TypedValue ( );
			//
			getContext ( ).getTheme ( )
			              .resolveAttribute ( android.R.attr.actionBarSize,
			                                  tv,
			                                  true
			                                );
			int nHeight = getResources ( ).getDimensionPixelSize ( tv.resourceId );
			//
			Log.i ( this.getClass ( )
			            .getName ( ),
			        String.format ( "ActionBar height %d",
			                        nHeight
			                      )
			      );
			return nHeight;
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final DisplayMetrics get_display ( )
		{
			//
			return Resources.getSystem ( )
			                .getDisplayMetrics ( );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		public final Configuration get_config ( )
		{
			//
			return Resources.getSystem ( )
			                .getConfiguration ( );
		}
	}
}
