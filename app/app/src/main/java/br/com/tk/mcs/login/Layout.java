/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import br.com.tk.mcs.R;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 21/11/2017.
 * linear_vertical
 */

public class Layout extends linear_vertical
{
	public final static String TAG = Layout.class.getName ( );
	
	public Layout ( Context context )
	{
		super ( context );
		this.Params = this.build ( MATCH,
		                           MATCH
		                         );
		this.setGravity ( Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL );
		this.setBackgroundResource ( R.drawable.login_central_master );
		( ( AppCompatActivity ) context ).setContentView ( this,
		                                                   this.Params
		                                                 );
	}
}
