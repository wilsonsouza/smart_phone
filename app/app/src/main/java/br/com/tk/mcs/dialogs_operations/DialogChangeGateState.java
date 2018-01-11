/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.util.Log;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.lane.State.BarreraState;

/**
 * Created by wilsonsouza on 23/01/17.
 */

public class DialogChangeGateState extends dialog_base_impl
{
	public final static String          TAG     = DialogChangeGateState.class.getName ( );
	protected           Lane            m_pLane = null;
	protected           BarreraState    m_state = BarreraState.SensorUnknown;
	protected           display_message m_pDlg  = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogChangeGateState ( Context pWnd,
	                               Lane pLane
	                             )
	{
		super ( pWnd,
		        R.string.manager_barrera_title,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_pLane = pLane;
			this.m_state = pLane.get_barrera_state ( );
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
		this.m_pDlg = new display_message ( getContext ( ),
		                                    R.string.ids_wait,
		                                    R.string.change_gate_state
		);
		this.m_state = this.m_pLane.get_barrera_state ( );
		
		if ( this.m_state == BarreraState.SensorON )
		{
			this.set_icon ( R.drawable.ln_barrer_up_hd );
			this.set_message ( String.format ( getContext ( ).getString ( R.string.manager_barrera_close_response ),
			                                   m_pLane.get_name ( )
			                                 ) );
		}
		else
		{
			this.set_icon ( R.drawable.ln_barrer_down_hd );
			this.set_message ( String.format ( getContext ( ).getString ( R.string.manager_barrera_open_response ),
			                                   m_pLane.get_name ( )
			                                 ) );
		}
		
		this.set_buttons ( new int[]{ R.string.manager_button_confirm,
		                              R.string.manager_button_cancel
		} );
		this.set_control_by_id ( R.string.manager_button_confirm,
		                         true
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
			case R.string.manager_button_confirm:
				new Launcher ( ).start ( );
				break;
			case R.string.manager_button_cancel:
				dismiss ( );
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
				final Operations p = m_pLane.get_operations ( );
				
				if ( m_state == BarreraState.SensorON )
				{
					p.set_barrier_off ( );
				}
				else if ( m_state == BarreraState.SensorOFF )
				{
					p.set_barrier_on ( );
				}
			}
			catch ( Exception e )
			{
				close_dialog ( m_pDlg );
				show_message_box ( R.string.manager_barrera_title,
				                   R.string.manager_response_error,
				                   message_box.IDERROR
				                 );
				Log.e ( TAG,
				        e.getMessage ( )
				      );
			}
			finally
			{
				dismiss ( );
				close_dialog ( m_pDlg );
				Log.i ( TAG,
				        m_state.toString ( )
				      );
			}
		}
	}
}
