/*

   Sistema de Gestão de Pistas

   (C) 2016, 2017 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.generic;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by wilsonsouza on 13/11/2017.
 */

public class device_screen /*device_screen*/
{
	//--------------------------------------------------------------------------------------------//
	public static Point point_to_sp ( int x,
	                                  int y
	                                )
	{
		return new Point ( config_display_metrics.to_sp ( x ),
		                   config_display_metrics.to_sp ( y )
		);
	}
	//--------------------------------------------------------------------------------------------//
	public static Point point_to_sp ( Point p )
	{
		return new Point ( config_display_metrics.to_sp ( p.x ),
		                   config_display_metrics.to_sp ( p.y )
		);
	}
	//--------------------------------------------------------------------------------------------//
	public static Point point_to_dp ( int x,
	                                  int y
	                                )
	{
		return new Point ( config_display_metrics.to_dp ( x ),
		                   config_display_metrics.to_dp ( y )
		);
	}
	//--------------------------------------------------------------------------------------------//
	public static Point point_to_dp ( Point p )
	{
		return new Point ( config_display_metrics.to_dp ( p.x ),
		                   config_display_metrics.to_dp ( p.y )
		);
	}
	//--------------------------------------------------------------------------------------------//
	public static Rect rect_to_sp ( int x,
	                                int y,
	                                int w,
	                                int h
	                              )
	{
		return new Rect ( config_display_metrics.to_sp ( x ),
		                  config_display_metrics.to_sp ( y ),
		                  config_display_metrics.to_sp ( w ),
		                  config_display_metrics.to_sp ( h )
		);
	}
	//--------------------------------------------------------------------------------------------//
	public static Rect rect_to_dp ( int x,
	                                int y,
	                                int w,
	                                int h
	                              )
	{
		return new Rect ( config_display_metrics.to_dp ( x ),
		                  config_display_metrics.to_dp ( y ),
		                  config_display_metrics.to_dp ( w ),
		                  config_display_metrics.to_dp ( h )
		);
	}
	//--------------------------------------------------------------------------------------------//
	public static Rect rect_to_sp ( Rect r )
	{
		return device_screen.rect_to_sp ( r.left,
		                                  r.top,
		                                  r.right,
		                                  r.bottom
		                                );
	}
	//--------------------------------------------------------------------------------------------//
	public static Rect rect_to_dp ( Rect r )
	{
		return device_screen.rect_to_dp ( r.left,
		                                  r.top,
		                                  r.right,
		                                  r.bottom
		                                );
	}
}
