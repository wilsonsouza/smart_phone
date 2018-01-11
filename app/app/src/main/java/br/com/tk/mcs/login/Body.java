/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.login;

import android.content.Context;
import android.view.Gravity;

import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 21/11/2017.
 */

public class Body extends linear_vertical
{
	public final static String TAG = Body.class.getName ( );
	
	public Body ( Context context,
	              Layout layout
	            )
	{
		super ( context );
		this.Params = this.build ( WRAP,
		                           WRAP
		                         );
		this.Params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		layout.addView ( this,
		                 this.Params
		               );
	}
}
