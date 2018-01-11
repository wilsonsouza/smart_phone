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
import br.com.tk.mcs.lane.State.TrafficLightState;

/**
 * Created by wilsonsouza on 23/01/17.
 */

public class DialogChangeMarquiseState extends dialog_base_impl
{
	public final static String TAG     = DialogChangeGateState.class.getName ( );
	protected           Lane   m_pLane = null;
	protected TrafficLightState m_state;
	protected display_message m_pDlg = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogChangeMarquiseState ( Context pWnd,
	                                   final Lane pLane
	                                 )
	{
		super ( pWnd,
		        R.string.manager_marq_title,
		        LM_HORIZONTAL
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
		String szMsg = String.format ( getContext ( ).getString ( R.string.manager_marq_response ),
		                               m_pLane.get_name ( )
		                             );
		
		this.set_icon ( R.drawable.box_question );
		this.m_pDlg = new display_message ( this.getContext ( ),
		                                    R.string.ids_wait,
		                                    R.string.change_marquise_state
		);
		this.set_message ( szMsg );
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
				final String szValue = m_pLane.get_operations ( )
				                              .get_long_status ( )
				                              .get_device ( )
				                              .getLightStateCanopy ( );
				final int nBit = Integer.parseInt ( szValue );
				
				switch ( nBit )
				{
					case 1:
						m_pLane.get_operations ( )
						       .set_traffic_light_off ( );
						break;
					case 2:
						m_pLane.get_operations ( )
						       .set_traffic_light_on ( );
						break;
				}
			}
			catch ( Exception e )
			{
				close_dialog ( m_pDlg );
				show_message_box ( R.string.manager_marq_title,
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
			}
		}
	}
}
