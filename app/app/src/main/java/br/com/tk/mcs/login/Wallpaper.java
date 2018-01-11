/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.login;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;

import br.com.tk.mcs.components.imageview_impl;
import br.com.tk.mcs.generic.config_display_metrics;

/**
 * Created by wilsonsouza on 21/11/2017.
 */

public class Wallpaper extends imageview_impl
{
	public final static String TAG = Wallpaper.class.getName ( );
	
	public Wallpaper ( Context context,
	                   Body body_layout
	                 )
	{
		super ( context,
		        config_display_metrics.WallpaperOffset,
		        config_display_metrics.Wallpaper,
		        false
		      );
		this.Params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		   /*
		   logo of sp 99 tamoios
         this.setImageResource(R.drawable.logotamoios01);
          */
         /*
         logo of cro
          */
		this.set_padding ( new Rect ( 0, 0, 0, 0x20 ) );
		body_layout.addView ( this, this.Params );
	}
}
