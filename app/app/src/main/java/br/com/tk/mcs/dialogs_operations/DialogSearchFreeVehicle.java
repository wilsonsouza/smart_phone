/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import java.util.Vector;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.button_impl;
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.tablewidget_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;

/**
 * Created by wilsonsouza on 24/02/17.
 */

public class DialogSearchFreeVehicle extends dialog_base_impl
{
	public final static    String          TAG       = DialogSearchFreeVehicle.class.getName ( );
	protected final static int             PLATE     = 0x7;
	protected              Lane            m_pLane   = null;
	protected              linear_vertical m_pBody   = null;
	protected              TableView       m_pTable  = null;
	protected              edittext_impl   m_pSearch = null;
	protected              button_impl     m_pOK     = null;
	protected              button_impl     m_pClear  = null;
	protected              display_message m_pDlg    = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogSearchFreeVehicle ( Context pWnd,
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
			Rect padding = new Rect ( 8,
			                          8,
			                          8,
			                          8
			);
			
			this.m_pLane = pLane;
			this.m_pTable = new TableView ( getContext ( ) );
			this.m_pBody = new linear_vertical ( getContext ( ) );
			this.m_pSearch = new edittext_impl ( getContext ( ),
			                                     R.string.tbl_board,
			                                     PLATE,
			                                     true,
			                                     - 1,
			                                     - 1,
			                                     true
			);
			this.m_pOK = new button_impl ( getContext ( ),
			                               R.string.button_ok,
			                               false,
			                               R.drawable.button_selector,
			                               padding,
			                               null
			);
			this.m_pClear = new button_impl ( getContext ( ),
			                                  R.string.button_clear,
			                                  true,
			                                  R.drawable.button_selector,
			                                  padding,
			                                  null
			);
			this.m_pDlg = new display_message ( this.getContext ( ),
			                                    - 1,
			                                    - 1
			);
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
		linear_horizontal pActions = new linear_horizontal ( getContext ( ) );
		{
			pActions.addView ( this.m_pSearch,
			                   this.m_pSearch.Params
			                 );
			pActions.addView ( this.m_pOK,
			                   this.m_pOK.Params
			                 );
			pActions.addView ( this.m_pClear,
			                   this.m_pClear.Params
			                 );
			pActions.setGravity ( Gravity.CENTER );
			pActions.set_margins ( new Rect ( 8,
			                                  8,
			                                  8,
			                                  8
			) );
			
			this.m_pOK.set_margins ( new Rect ( 8,
			                                    8,
			                                    8,
			                                    8
			) );
			this.m_pOK.get_handle ( )
			          .setOnClickListener ( this );
			this.m_pClear.get_handle ( )
			             .setOnClickListener ( this );
			
			this.m_pSearch.get_handle ( )
			              .setInputType ( InputType.TYPE_CLASS_TEXT );
			this.m_pSearch.get_handle ( )
			              .addTextChangedListener ( new TWatcher ( ) );
		}
		
		this.set_icon ( R.drawable.ic_free );
		this.m_pBody.addView ( pActions,
		                       pActions.Params
		                     );
		this.m_pBody.addView ( this.m_pTable,
		                       this.m_pTable.Params
		                     );
		this.m_pTable.Params.height = 0x100;
		this.get_view ( )
		    .addView ( this.m_pBody,
		               this.m_pBody.Params
		             );
		//
		this.set_buttons ( new int[]{ R.string.button_ok,
		                              R.string.button_cancel
		} );
		return super.create_and_prepare ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
		int which = v.getId ( );
		
		switch ( which )
		{
			case R.string.button_ok:
				break;
			case R.string.button_cancel:
				dismiss ( );
				break;
		}
		
		if ( v == this.m_pClear.get_handle ( ) )
		{
			this.m_pSearch.get_handle ( )
			              .getText ( )
			              .clear ( );
			this.m_pSearch.get_handle ( )
			              .requestFocus ( );
		}
		else if ( v == this.m_pOK.get_handle ( ) )
		{
			new message_box ( getContext ( ),
			                  R.string.ids_warning,
			                  R.string.ids_unavailable,
			                  message_box.IDWARNING
			);
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class TWatcher implements TextWatcher
	{
		@Override
		public void beforeTextChanged ( CharSequence s,
		                                int start,
		                                int count,
		                                int after
		                              )
		{
		}
		
		@Override
		public void onTextChanged ( CharSequence s,
		                            int start,
		                            int before,
		                            int count
		                          )
		{
			m_pOK.setEnabled ( s.length ( ) > 0 );
			m_pClear.setEnabled ( s.length ( ) > 0 );
		}
		
		@Override
		public void afterTextChanged ( Editable s )
		{
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class TableView extends tablewidget_impl implements View.OnClickListener
	{
		protected Vector<fielddata> m_queue = new Vector<> ( );
		
		public TableView ( Context pWnd )
		{
			super ( pWnd );
			this.m_queue.add ( new fielddata ( R.string.tbl_datetime,
			                                   0x96
			) );
			this.m_queue.add ( new fielddata ( R.string.tbl_via,
			                                   0x96
			) );
			this.m_queue.add ( new fielddata ( R.string.tbl_board,
			                                   0x96
			) );
			
			super.create_header ( this.m_queue );
			super.create ( this );
		}
		
		@Override
		public void onClick ( View v )
		{
		}
	}
}
