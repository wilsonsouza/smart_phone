/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.components;

import android.annotation.TargetApi;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

/**
 * Created by wilsonsouza on 12/01/17.
 */

public class borderwidget_impl extends GradientDrawable
{
	//-----------------------------------------------------------------------------------------------------------------//
	public borderwidget_impl ( )
	{}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( Build.VERSION_CODES.JELLY_BEAN )
	public borderwidget_impl ( View pView,
	                           int nColor,
	                           int nWidth,
	                           int nStrokeColor
	                         )
	{
		super ( );
		this.setColor ( nColor );
		this.setStroke ( nWidth,
		                 nStrokeColor
		               );
		pView.setBackground ( this );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( Build.VERSION_CODES.JELLY_BEAN )
	public static borderwidget_impl builder ( View pView,
	                                          int nBkgClr,
	                                          int nStrokeClr
	                                        )
	{
		borderwidget_impl p = new borderwidget_impl ( );
		{
			p.setColor ( nBkgClr );
			p.setStroke ( 1,
			              nStrokeClr
			            );
			pView.setBackground ( p );
		}
		return p;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static borderwidget_impl builder ( View pView,
	                                          int nBkgClr,
	                                          int nStrokeClr,
	                                          int nWidth
	                                        )
	{
		return new borderwidget_impl ( pView,
		                               nBkgClr,
		                               nWidth,
		                               nStrokeClr
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@TargetApi( Build.VERSION_CODES.JELLY_BEAN )
	public static borderwidget_impl builder ( View pView )
	{
		borderwidget_impl p = new borderwidget_impl ( );
		{
			p.setColor ( android.graphics.Color.WHITE );
			p.setStroke ( 1,
			              android.graphics.Color.BLACK
			            );
			pView.setBackground ( p );
		}
		return p;
	}
}
