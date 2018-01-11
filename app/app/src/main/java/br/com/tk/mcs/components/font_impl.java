/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import br.com.tk.mcs.generic.config_display_metrics;

/**
 * Created by wilsonsouza on 12/02/17.
 */

public class font_impl extends Object
{
	public final static float DEFAULT_SIZE = config_display_metrics.FontSize;
	public final static float CAPTION_SIZE = config_display_metrics.CaptionSize;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public font_impl ( )
	{
		super ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static <T> void set_size ( T view,
	                                  float fSize,
	                                  float fScale
	                                )
	{
		DisplayMetrics dm = Resources.getSystem ( )
		                             .getDisplayMetrics ( );
		Typeface face     = Typeface.DEFAULT;
		float    fCurSize = 0;
		//text view
		if ( view instanceof TextView )
		{
			if ( fScale == DEFAULT_SIZE )
			{
				( ( TextView ) view ).setTypeface ( face,
				                                    Typeface.NORMAL
				                                  );
			}
			else if ( fScale == CAPTION_SIZE )
			{
				( ( TextView ) view ).setTypeface ( face,
				                                    Typeface.BOLD
				                                  );
			}
		}
		//edit text
		if ( view instanceof EditText )
		{
			( ( EditText ) view ).setTypeface ( face,
			                                    Typeface.BOLD
			                                  );
		}
		//check box
		if ( view instanceof CheckBox )
		{
			( ( CheckBox ) view ).setTypeface ( face,
			                                    Typeface.NORMAL
			                                  );
		}
		//button
		if ( view instanceof Button )
		{
			( ( Button ) view ).setTypeface ( face,
			                                  Typeface.NORMAL
			                                );
		}
		//radio button
		if ( view instanceof RadioButton )
		{
			( ( RadioButton ) view ).setTypeface ( face,
			                                       Typeface.NORMAL
			                                     );
		}
	}
}
