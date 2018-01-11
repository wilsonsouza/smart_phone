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
import br.com.tk.mcs.lane.State.LaneState;

/**
 * Created by wilsonsouza on 23/01/17.
 */

public class DialogCloseLane extends dialog_base_impl
{
	public final static String          TAG     = DialogCloseLane.class.getName ( );
	protected           View            m_pView = null;
	protected           Lane            m_pLane = null;
	protected           LaneState       m_state = LaneState.Closed;
	protected           display_message m_pDlg  = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogCloseLane ( Context pWnd,
	                         View pView,
	                         Lane pLane,
	                         LaneState state
	                       )
	{
		super ( pWnd,
		        R.string.manager_close_title,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_pView = pView;
			this.m_pLane = pLane;
			this.m_state = state;
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
		String szMsg = String.format ( get_handle ( ).getString ( R.string.close_lane ),
		                               this.m_pLane.get_name ( )
		                             );
		
		this.set_icon ( R.drawable.ic_closelane );
		this.set_message ( String.format ( get_handle ( ).getString ( R.string.closelane_message ),
		                                   this.m_pLane.get_name ( )
		                                 ) );
		this.set_buttons ( new int[]{ R.string.manager_button_confirm,
		                              R.string.manager_button_cancel
		} );
		this.m_pDlg = new display_message ( getContext ( ),
		                                    R.string.ids_wait,
		                                    szMsg
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
						m_pView.setEnabled ( false );
					}
				} );
				
				m_pLane.get_operations ( )
				       .set_lane_off ( );
				Log.i ( getClass ( ).getName ( ),
				        m_pLane.get_operations ( )
				               .get_long_status ( )
				               .get_lane_state ( )
				               .toString ( )
				      );
			}
			catch ( Exception e )
			{
				get_handle ( ).runOnUiThread ( new Runnable ( )
				{
					@Override
					public void run ( )
					{
						m_pDlg.dismiss ( );
						new message_box ( get_handle ( ),
						                  R.string.manager_close_title,
						                  R.string.manager_response_error,
						                  R.drawable.box_error
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
			finally
			{
				close_dialog ( m_pDlg );
				dismiss ( );
			}
		}
	}
}
