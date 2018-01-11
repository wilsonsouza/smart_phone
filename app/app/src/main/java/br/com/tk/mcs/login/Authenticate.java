/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import br.com.tk.mcs.R;
import br.com.tk.mcs.database.nosql_persistence_controller_lane;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.dialogs_ui.question_box;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.main.Main;
import br.com.tk.mcs.main.SetIpFromTheSquare;
import br.com.tk.mcs.main.TrackManager;
import br.com.tk.mcs.remote.response.UserRequestResponse;
import br.com.tk.mcs.tools.Utils;

/**
 * Created by wilsonsouza on 21/11/2017.
 */

public class Authenticate extends Thread implements Runnable
{
	public final static String                            TAG               = Authenticate.class.getName ( );
	protected           display_message                   m_pDlg            = null;
	protected           Main                              m_handle          = null;
	private             nosql_persistence_controller_lane m_controller_lane = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public Authenticate ( final Main handle )
	{
		super ( );
		this.setName ( this.getClass ( )
		                   .getName ( ) );
		this.m_handle = handle;
		this.m_pDlg = new display_message ( this.m_handle, R.string.main_login, R.string.main_alert_login_sync );
		this.m_controller_lane = this.m_handle.get_manager_handle ( )
		                                      .get_controller_lane_handle ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void run ( )
	{
		try
		{
			int                 i            = 0;
			UserRequestResponse access_grant = UserRequestResponse.NoAuthorized;
			//
			this.m_handle.show_dialog ( m_pDlg );
			//
			for ( final String szName : this.m_handle.get_manager_handle ( )
			                                         .get_controller_lane_handle ( )
			                                         .get_array_of_names ( ) )
			{
			   /* put on message on Main thread */
				this.m_handle.set_message_box ( m_pDlg,
				                                String.format ( m_handle.getString ( R.string.ids_syncronize ), szName )
				                              );
				/**/
				try
				{
					
					final String szAddress = m_controller_lane.get_by_name ( szName );
					final String szUrl     = String.format ( this.m_handle.getString ( R.string.ids_url_lane ), szAddress );
					final String szUser = m_handle.get_manager_handle ( )
					                              .get_login ( )
					                              .get_user ( )
					                              .get_data ( );
					final String szPwd = m_handle.get_manager_handle ( )
					                             .get_login ( )
					                             .get_password ( )
					                             .get_data ( );
					final Lane pLane = new Lane ( i++, szName, szUser, new Operations ( szUrl, szUser ) );
					/**/
					Log.i ( TAG, "Lane Url " + szUrl );
					access_grant = pLane.get_operations ( )
					                    .user_request ( szUser, szPwd );
					
					if ( access_grant == UserRequestResponse.Authorized )
					{
						break;
					}
				}
				catch ( Exception e )
				{
					e.printStackTrace ( );
				}
			}
			//
			this.m_handle.close_dialog ( m_pDlg );
			//
			if ( access_grant != UserRequestResponse.Authorized )
			{
				if ( access_grant == UserRequestResponse.NoAuthorized )
				{
					this.m_handle.show_message_box ( R.string.ids_caption_error,
					                                 R.string.main_alert_login_error,
					                                 message_box.IDERROR
					                               );
				}
				else
				{
					this.m_handle.show_message_box ( R.string.ids_warning,
					                                 R.string.main_alert_login_no_communication,
					                                 message_box.IDWARNING
					                               );
				}
			}
			else
			{
				Intent track_manager = new Intent ( this.m_handle,
				                                    TrackManager.class
				);
				Bundle bundle = new Bundle ( );
				
				bundle.putString ( Utils.OPERATOR,
				                   this.m_handle.get_manager_handle ( )
				                                .get_login ( )
				                                .get_user ( )
				                                .get_data ( )
				                 );
				track_manager.putExtras ( bundle );
				
				if ( this.verify_lane_ips ( ) )
				{
					this.m_handle.startActivity ( track_manager );
				}
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public void start ( )
	{
		Log.i ( this.getClass ( )
		            .getName ( ),
		        getState ( ).toString ( ) + " isAlive " + this.isAlive ( ) + ", " + this.isDaemon ( )
		      );
		if ( ! this.isAlive ( ) )
		{
			Log.i ( TAG,
			        "Starting..."
			      );
			super.start ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	protected final boolean verify_lane_ips ( )
	{
		if ( this.m_handle.get_manager_handle ( )
		                  .get_controller_lane_handle ( )
		                  .count ( ) == 0 )
		{
			final Intent set_square_ip = new Intent ( this.m_handle, SetIpFromTheSquare.class );
			new question_box ( this.m_handle,
			                   R.string.ids_warning,
			                   R.string.ids_lane_ip_empty,
			                   question_box.DEFAULT
			).set_onclick_listener ( new question_box.OnClickListener ( )
			{
				@Override
				public void on_click ( final question_box dialog,
				                       final View view
				                     )
				{
					switch ( view.getId ( ) )
					{
						case R.string.manager_button_confirm:
							dialog.dismiss ( );
							m_handle.startActivity ( set_square_ip );
							break;
						case R.string.manager_button_cancel:
							dialog.dismiss ( );
							break;
					}
				}
			} );
		}
		return m_handle.get_manager_handle ( )
		               .get_controller_lane_handle ( )
		               .count ( ) > 0;
	}
}
