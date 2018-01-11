/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.layouts;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class linear_vertical extends linear_layout_impl
{
	public static final String TAG = linear_vertical.class.getName ( );
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_vertical ( Context context )
	{
		super ( context );
		this.setOrientation ( VERTICAL );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_vertical ( Context context,
	                         AttributeSet attrs
	                       )
	{
		super ( context,
		        attrs
		      );
		this.setOrientation ( VERTICAL );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public linear_vertical ( Context context,
	                         AttributeSet attrs,
	                         int defStyleAttr
	                       )
	{
		super ( context,
		        attrs,
		        defStyleAttr
		      );
		this.setOrientation ( VERTICAL );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public linear_vertical set_fullscreen ( )
	{
		super.set_fullscreen ( );
		return this;
	}
}
