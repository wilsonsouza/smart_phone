/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.generic;

import android.text.InputFilter;
import android.widget.EditText;

/**
 * Created by wilsonsouza on 07/12/16.
 */

public class field_text_set_max_length
{
	public final static String TAG = field_text_set_max_length.class.getName ( );
	
	//-----------------------------------------------------------------------------------------------------------------//
	public static void set_length ( EditText owner,
	                                final int nWidth,
	                                final int nType
	                              )
	{
		owner.setFilters ( new InputFilter[]{ new InputFilter.LengthFilter ( nWidth ) } );
		owner.setInputType ( nType );
	}
}
