/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import java.util.Vector;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.tablewidget_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.lane.Lane;

/**
 * Created by wilsonsouza on 24/02/17.
 */

public class DialogViolation extends dialog_base_impl
{
	public final static String TAG     = DialogViolation.class.getName ( );
	private             Lane   m_pLane = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogViolation ( Context pWnd,
	                         int nID,
	                         final Lane pLane
	                       )
	{
		super ( pWnd,
		        nID,
		        dialog_base_impl.LM_HORIZONTAL
		      );
		try
		{
			this.m_pLane = pLane;
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
		TableView pTable = new TableView ( this.getContext ( ) );
		this.set_icon ( R.drawable.ic_violation );
		this.get_view ( )
		    .addView ( pTable,
		               pTable.Params
		             );
		this.set_buttons ( new int[]{ R.string.button_ok,
		                              R.string.button_cancel
		} );
		return super.create_and_prepare ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
		int nWhich = v.getId ( );
		
		switch ( nWhich )
		{
			case R.string.button_ok:
				break;
			case R.string.button_cancel:
				dismiss ( );
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class TableView extends tablewidget_impl implements android.view.View.OnClickListener
	{
		public TableView ( Context pWnd ) throws
		                                  Exception
		{
			super ( pWnd );
			Vector<fielddata> Items = new Vector<> ( );
			{
				Items.add ( new fielddata ( R.string.tbl_datetime,
				                            DEFAULT
				) );
				Items.add ( new fielddata ( R.string.tbl_via,
				                            DEFAULT
				) );
				Items.add ( new fielddata ( R.string.tbl_photo,
				                            DEFAULT
				) );
			}
			this.create_header ( Items );
			this.set_border ( true );
			this.set_margins ( new Rect ( 8,
			                              8,
			                              8,
			                              8
			) );
		}
		
		@Override
		public void onClick ( View v )
		{
		}
	}
}
