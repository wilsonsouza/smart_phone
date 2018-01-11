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

import java.util.concurrent.Executors;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.lane.State.LaneState;

/**
 * Created by wilsonsouza on 23/01/17.
 * <p>
 * Quando pista mista alterada para avi nao abre somente reinicia.
 */

public class DialogOpenLane extends dialog_base_impl
{
	public final static String          TAG          = DialogOpenLane.class.getName ( );
	protected           Lane            m_pLane      = null;
	protected           String          m_szOperator = null;
	protected           LaneState       m_state      = LaneState.Closed;
	protected           View            m_pView      = null;
	protected           display_message m_pDlg       = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogOpenLane ( Context pWnd,
	                        View pView,
	                        Lane pLane,
	                        String szOperator,
	                        LaneState state
	                      )
	{
		super ( pWnd,
		        R.string.manager_open_title,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_szOperator = szOperator;
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
		String szMessage = String.format ( getContext ( ).getString ( R.string.open_lane ),
		                                   m_pLane.get_name ( )
		                                 );
		
		this.set_message ( String.format ( getContext ( ).getString ( R.string.openlane_message ),
		                                   this.m_pLane.get_name ( )
		                                 ) );
		this.set_icon ( R.drawable.ic_openlane );
		this.set_buttons ( new int[]{ R.string.manager_button_confirm,
		                              R.string.manager_button_cancel
		} );
		this.m_pDlg = new display_message ( getContext ( ),
		                                    R.string.ids_wait,
		                                    szMessage
		);
		super.create_and_prepare ( );
		this.set_control_by_id ( R.string.manager_button_confirm,
		                         true
		                       );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
		int which = v.getId ( );
		
		switch ( which )
		{
			case R.string.manager_button_confirm:
			{
				Thread p = Executors.defaultThreadFactory ( )
				                    .newThread ( new Launcher ( ) );
				{
					p.setName ( getClass ( ).getName ( ) );
					p.start ( );
				}
				break;
			}
			case R.string.manager_button_cancel:
				dismiss ( );
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class Launcher implements Runnable
	{
		public Launcher ( )
		{
			//this.setName(getClass().getName());
		}
		
		@Override
		public void run ( )
		{
			try
			{
				get_handle ( ).runOnUiThread ( new Runnable ( )
				{
					@Override
					public void run ( )
					{
						m_pDlg.show ( );
						m_pView.setEnabled ( true );
					}
				} );
				
				final Operations p = m_pLane.get_operations ( );
				{
					p.set_operator_id ( m_szOperator );
					p.set_lane_on ( );
				}
			}
			catch ( Exception e )
			{
				get_handle ( ).runOnUiThread ( new Runnable ( )
				{
					@Override
					public void run ( )
					{
						close_dialog ( m_pDlg );
						new message_box ( getContext ( ),
						                  R.string.manager_open_title,
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
				
				Log.e ( getClass ( ).getName ( ),
				        e.getMessage ( )
				      );
			}
			
			close_dialog ( m_pDlg );
			dismiss ( );
		}
	}
}
