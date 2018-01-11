/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;

import java.util.Vector;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.edittext_completeview_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.remote.response.TagPlateResponse;

/**
 * Created by wilsonsouza on 23/01/17.
 */

public class DialogSearchTag extends dialog_base_impl implements android.view.View.OnClickListener
{
	protected static int                        TAG      = 0xA;
	protected static int                        BOARD    = 0x7;
	protected        Vector<Lane>               m_pLanes = new Vector<> ( );
	protected        display_message            m_pDlg   = null;
	protected        edittext_completeview_impl m_board  = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogSearchTag ( Context pWnd,
	                         final Vector<Lane> pLanes
	                       )
	{
		super ( pWnd,
		        R.string.manager_tag_title,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_pLanes.addAll ( pLanes );
			this.create_and_prepare ( );
		}
		catch ( final Exception e )
		{
			Log.e ( this.getClass ( )
			            .getName ( ),
			        e.getMessage ( )
			      );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public dialog_base_impl create_and_prepare ( ) throws Exception
	{
		this.m_board = new edittext_completeview_impl ( getContext ( ),
		                                                R.string.manager_tag_field,
		                                                TAG,
		                                                true,
		                                                - 1,
		                                                - 1,
		                                                true
		);
		this.m_board.get_handle ().setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
		this.m_board.get_handle ().addTextChangedListener ( new FieldTextWatcher ( ) );
		this.m_board.get_handle ().setCursorVisible ( true );
		
		this.m_board.Params.gravity = Gravity.CENTER;
		this.set_icon ( R.drawable.ic_search_plate );
		this.get_view ().addView ( this.m_board,
		                    this.m_board.Params
		                  );
		this.set_buttons ( new int[]{ R.string.button_ok,
		                              R.string.button_cancel
		} );
		
		this.m_board.set_adapter ( );
		this.m_pDlg = new display_message ( getContext ( ),
		                                    R.string.manager_tag_title,
		                                    R.string.manager_tag_find
		);
		return super.create_and_prepare ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View v )
	{
		int which = v.getId ( );
		
		switch ( which )
		{
			case R.string.button_ok:
				new Launcher ( ).start ( );
				break;
			case R.string.button_cancel:
				this.dismiss ( );
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class FieldTextWatcher implements TextWatcher
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
			set_control_by_id ( R.string.button_ok,
			                    s.length ( ) == TAG || s.length ( ) == BOARD
			                  );
		}
		
		@Override
		public void afterTextChanged ( Editable s )
		{
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class Launcher extends Thread
	{
		protected String  m_szData  = m_board.get_data ( )
		                                     .toUpperCase ( )
		                                     .trim ( );
		protected Context m_context = getContext ( );
		protected String  m_szType  = ( m_szData.length ( ) == BOARD ?
		                                "Placa" :
		                                "TAG" );
		protected String  m_szMsg   = String.format ( m_context.getString ( R.string.ids_searching ),
		                                              m_szType + " " + m_szData
		                                            );
		protected String m_szMessage;
		
		//-----------------------------------------------------------------------------------------------------------------//
		public Launcher ( )
		{
			super ( );
			setName ( getClass ( ).getName ( ) );
			m_board.add_queue_of_complete_view ( this.m_szData );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void run ( )
		{
			try
			{
				set_message_task ( m_pDlg,
				                   this.m_szMsg
				                 );
				show_dialog ( m_pDlg );
				
				TagPlateResponse Result = TagPlateResponse.Error;
				//
				for ( final Lane pLane : m_pLanes )
				{
					try
					{
						set_message_task ( m_pDlg,
						                   this.m_szMsg + " na " + pLane.get_name ( )
						                 );
						
						final Operations pCurrentOperations = pLane.get_operations ( );
						Result = pCurrentOperations.tag_plate_request ( this.m_szData.trim ( ) );
						
						if ( Result != TagPlateResponse.Error )
						{
							break;
						}
					}
					catch ( final Exception e )
					{
						e.printStackTrace ( );
					}
				}
				//
				Log.i ( getClass ( ).getName ( ),
				        this.m_szMessage = TagPlateResponse.get_text ( this.m_context,
				                                                       Result
				                                                     )
				      );
				close_dialog ( m_pDlg );
				
				if ( ! this.m_szMessage.isEmpty ( ) )
				{
					String fmt = TagPlateResponse.format ( this.m_context,
					                                       Result,
					                                       m_board.get_data ( )
					                                              .trim ( )
					                                     );
					int    ID  = TagPlateResponse.format ( Result );
					
					show_message_box ( R.string.ids_placa,
					                   fmt,
					                   ID
					                 );
				}
			}
			catch ( final Exception e )
			{
				close_dialog ( m_pDlg );
				show_message_box ( R.string.ids_caption_error,
				                   e.getMessage ( ),
				                   message_box.IDERROR
				                 );
				//Log.e(this.getClass().getName(), e.getMessage());
				e.printStackTrace ( );
			}
		}
	}
}
