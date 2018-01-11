package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.view.View;

import br.com.tk.mcs.R;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.lane.Lane;

/**
 * Created by wilsonsouza on 4/18/17.
 */

public class DialogSimulatePassenger extends dialog_base_impl
{
	public final static String          TAG     = DialogSimulatePassenger.class.getName ( );
	private             Lane            m_pLane = null;
	private             display_message m_pDlg  = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogSimulatePassenger ( Context pWnd,
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
		String szMsg = String.format ( this.getContext ( )
		                                   .getString ( R.string.dlg_simulation_question ),
		                               this.m_pLane.get_name ( )
		                             );
		String szDlg = String.format ( this.getContext ( )
		                                   .getString ( R.string.dlg_simulation_running ),
		                               this.m_pLane.get_name ( )
		                             );
		
		this.set_message ( szMsg );
		this.set_icon ( R.drawable.ic_passenger_simulation );
		this.set_buttons ( new int[]{ R.string.manager_button_confirm,
		                              R.string.manager_button_cancel
		} );
		this.m_pDlg = new display_message ( this.getContext ( ),
		                                    R.string.ids_wait,
		                                    szDlg
		);
		this.set_control_by_id ( R.string.manager_button_confirm,
		                         true
		                       );
		return super.create_and_prepare ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View pView )
	{
		final int which = pView.getId ( );
		
		switch ( which )
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
		public Launcher ( )
		{
			this.setName ( getClass ( ).getName ( ) );
			set_control_by_id ( R.string.manager_button_confirm,
			                    false
			                  );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void run ( )
		{
			try
			{
				show_dialog ( m_pDlg );
				if ( ! m_pLane.get_operations ( )
				              .simulate_passage ( ) )
				{
					show_message_box ( R.string.ids_caption_error,
					                   R.string.dlg_simulation_error,
					                   message_box.IDERROR
					                 );
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace ( );
			}
			
			close_dialog ( m_pDlg );
			dismiss ( );
		}
	}
}
