/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.widget.ScrollView;

import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 17/02/17.
 */

public class scrollview_impl extends linear_vertical
{
	private ScrollView      Data      = null;
	private linear_vertical Contaneir = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public scrollview_impl ( Context context,
	                         boolean bBorder,
	                         int nColor,
	                         Rect padding,
	                         Rect margins
	                       )
	{
		super ( context );
		this.Data = new ScrollView ( this.getContext ( ) );
		this.Contaneir = new linear_vertical ( this.getContext ( ) );
		this.set_border ( bBorder );
		this.Data.addView ( this.Contaneir,
		                    this.Contaneir.Params
		                  );
		this.addView ( this.Data,
		               this.Params
		             );
		this.set_border ( bBorder,
		                  nColor,
		                  Color.BLACK
		                );
		this.set_padding ( padding );
		this.Params.set_margins ( margins );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public scrollview_impl set_padding ( Rect padding )
	{
		super.set_padding ( padding );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final ScrollView get_handle ( )
	{
		return this.Data;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final linear_vertical get_contaneir ( )
	{
		return this.Contaneir;
	}
}
