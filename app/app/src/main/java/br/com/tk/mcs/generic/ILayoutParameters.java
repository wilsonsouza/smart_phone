/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.generic;

/**
 * Created by wilsonsouza on 16/12/16.
 */

public class ILayoutParameters
{
	static public int WRAP  = android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
	static public int MATCH = android.widget.LinearLayout.LayoutParams.MATCH_PARENT;
	
	//-----------------------------------------------------------------------------------------------------------------//
	static public android.widget.LinearLayout.LayoutParams Linear ( int w,
	                                                                int h
	                                                              )
	{
		return new android.widget.LinearLayout.LayoutParams ( w,
		                                                      h
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	static public android.widget.GridLayout.LayoutParams Grid ( )
	{
		return new android.widget.GridLayout.LayoutParams ( );
	}
}
