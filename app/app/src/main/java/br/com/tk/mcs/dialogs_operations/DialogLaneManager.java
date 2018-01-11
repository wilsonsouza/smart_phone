/*

   Sistema de Gestão de Pistas

   (C) 2016, 2017 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.util.Log;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.manager.ProcessManager;

/**
 * Created by wilsonsouza on 19/09/2017.
 */

public class DialogLaneManager extends dialog_base_impl
{
	public final static String         TAG       = DialogLaneManager.class.getName ( );
	protected           ProcessManager m_Builder = null;
	protected           int            m_IconId  = - 1;
	
	//-------------------------------------------------------------------------------------------//
	public DialogLaneManager ( Context pWnd,
	                           ProcessManager pBuilder,
	                           int nIconId
	                         )
	{
		super ( pWnd,
		        R.string.dlg_lane_manager,
		        dialog_base_impl.LM_HORIZONTAL
		      );
		try
		{
			this.m_Builder = pBuilder;
			this.m_IconId = nIconId;
		}
		catch ( Exception e )
		{
			Log.e ( e.getClass ( )
			         .getName ( ),
			        e.getMessage ( )
			      );
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	@Override
	public dialog_base_impl create_and_prepare ( ) throws Exception
	{
		return this;
	}
	
	//-------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View view )
	{
	
	}
	//-------------------------------------------------------------------------------------------//
}
