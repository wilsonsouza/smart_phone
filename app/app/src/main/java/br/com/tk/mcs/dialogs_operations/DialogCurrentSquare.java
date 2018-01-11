/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
*/
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.view.View;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.manager.ProcessManager;

/**
 * Created by wilsonsouza on 5/8/17.
 * dialog_current_square
 */
public class DialogCurrentSquare extends dialog_base_impl
{
	public final static String         TAG               = DialogCurrentSquare.class.getName ( );
	protected           ProcessManager m_builder_manager = null;
	
	public DialogCurrentSquare ( Context pWnd,
	                             final ProcessManager pOwner
	                           )
	{
		super ( pWnd,
		        R.string.dlg_current_square,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_builder_manager = pOwner;
			this.create_and_prepare ( );
		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public dialog_base_impl create_and_prepare ( ) throws Exception
	{
		linear_vertical body = new linear_vertical ( this.getContext ( ) );
		{
		}
		//
		this.set_icon ( R.drawable.cctv_camera_icon );
		this.set_buttons ( new int[]{ R.string.button_ok } );
		return super.create_and_prepare ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View pView )
	{
	
	}
}
