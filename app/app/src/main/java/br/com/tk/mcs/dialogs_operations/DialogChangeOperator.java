/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.util.Log;
import android.view.View;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.lane.State.LaneState;

/**
 * Created by wilsonsouza on 23/01/17.
 */

public class DialogChangeOperator extends dialog_base_impl
{
	public final static String          TAG               = DialogChangeOperator.class.getName ( );
	protected           View            m_pView           = null;
	protected           Lane            m_pLane           = null;
	protected           boolean         m_bChangeOperator = false;
	protected           LaneState       m_state           = LaneState.NoSync;
	protected           display_message m_pDlg            = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogChangeOperator ( Context pWnd,
	                              View pView,
	                              Lane pLane,
	                              LaneState state
	                            )
	{
		super ( pWnd,
		        R.string.manager_resp_title,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_pLane = pLane;
			this.m_state = state;
			this.m_pView = pView;
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
		this.m_pDlg = new display_message ( this.getContext ( ),
		                                    R.string.ids_wait,
		                                    R.string.verify_operator
		);
		this.set_icon ( R.drawable.ic_operator );
		this.set_message ( R.string.manager_resp_response );
		this.set_buttons ( new int[]{ R.string.manager_button_confirm,
		                              R.string.manager_button_cancel
		} );
		this.set_control_by_id ( R.string.manager_button_confirm,
		                         true
		                       );
		return super.create_and_prepare ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final boolean is_change_operator ( )
	{
		return this.m_bChangeOperator;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
		int nID = v.getId ( );
		switch ( nID )
		{
			case R.string.manager_button_confirm:
				new Launcher ( ).start ( );
				break;
			case R.string.manager_button_cancel:
				this.dismiss ( );
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class Launcher extends Thread
	{
		public final String TAG = Launcher.class.getName ( );
		
		public Launcher ( )
		{
			this.setName ( TAG );
		}
		
		@Override
		public void run ( )
		{
			try
			{
				show_dialog ( m_pDlg );
				get_handle ( ).runOnUiThread ( new Runnable ( )
				{
					@Override
					public void run ( )
					{
						m_pView.setEnabled ( false );
					}
				} );
				final Operations op = m_pLane.get_operations ( );
				{
					op.set_change_operator_responsible ( );
				}
			}
			catch ( Exception e )
			{
				close_dialog ( m_pDlg );
				get_handle ( ).runOnUiThread ( new Runnable ( )
				{
					@Override
					public void run ( )
					{
						new message_box ( get_handle ( ),
						                  R.string.manager_resp_title,
						                  R.string.manager_response_error,
						                  message_box.IDERROR
						);
						//
						if ( m_state != LaneState.NoSync && m_state != LaneState.Starting )
						{
							m_pView.setEnabled ( true );
						}
					}
				} );
				
				Log.e ( TAG,
				        e.getMessage ( )
				      );
			}
			finally
			{
				dismiss ( );
				close_dialog ( m_pDlg );
				m_bChangeOperator = true;
			}
		}
	}
}
