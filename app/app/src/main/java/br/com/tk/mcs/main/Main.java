/*

   Sistema de Gestão de Pistas

   (C) 2016, 2017 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.frame_window_impl;
import br.com.tk.mcs.dialogs_operations.DialogTableViewItem;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.login.Authenticate;
import br.com.tk.mcs.login.Manager;
import br.com.tk.mcs.tools.Utils;
import br.com.tk.mcs.tools.cpf_validate_code;

public class Main extends frame_window_impl implements View.OnClickListener
{
	private static int     LENGTH                         = 0x1e;
	private static int     MIN_DISPLAY_RESOLUTION_ALLOWED = 0x220;
	private        Manager m_manager                      = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
	   /* put on my customer toolbar */
		//this.setTheme(R.style.AppTheme_NoActionBar);
		super.onCreate ( savedInstanceState );

      /* set density and display metrics */
		config_display_metrics.builder ( this );
		/* create_and_prepare all controls on screen */
		this.m_manager = new Manager ( this );
		Utils.set_keyboard_off ( this );

      /* detect that is the device type */
		if ( this.m_manager.get_layout ( ).Params.height < MIN_DISPLAY_RESOLUTION_ALLOWED )
		{
			View.OnClickListener fn_proc_clicked = new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View view )
				{
					Log.e ( TAG, getString ( R.string.ids_device_not_supported ) );
					finish ( );
				}
			};
			
			new message_box ( this,
			                  R.string.ids_device_error,
			                  R.string.ids_device_not_supported,
			                  message_box.IDERROR,
			                  fn_proc_clicked
			);
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View v )
	{
		String user = m_manager.get_login ( )
		                       .get_user ( )
		                       .get_data ( );
		String password = m_manager.get_login ( )
		                           .get_password ( )
		                           .get_data ( );
		         /* launcher_lanes */
		Authenticate authenticate = new Authenticate ( this );
		
		Utils.set_keyboard_service_hidden ( Main.this );
		
		if ( ! check_user_if_root_then_start_lane_square_config_window ( user, password ) )
		{
		   /* verify if exists any lane registered */
			if ( ! this.verify_if_exists_lanes ( ) )
			{
				return;
			}

         /* test new manager lanes window */
			this.debug_window_lanes ( user, password );
         /* test dialog view item */
			this.test_dialog_view_item ( user, password );

         /* verify if physical personal code VerifyPersonalPhysicalCode*/
			if ( ! user_authenticate_within_cpf ( user ) )
			{
				return;
			}

         /* pass to connection with land and validation of user */
			authenticate.start ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private Main debug_window_lanes ( String user,
	                                  String password
	                                )
	{
		if ( user.equals ( "1" ) && password.equals ( "2016" ) && this.m_manager.get_controller_lane_handle ( )
		                                                                        .count ( ) > 0 )
		{
			Intent pWnd = new Intent ( this, TrackManager.class );
			
			m_manager.get_login ( )
			         .get_user ( )
			         .set_data ( "00001" );
			
			company_setup.is_debug = true;
			pWnd.putExtra ( Utils.OPERATOR, this.m_manager.get_login ( )
			                                              .get_user ( )
			                                              .get_data ( ) );
			startActivity ( pWnd );
			this.m_manager.get_login ( )
			              .clear_fields ( );
		}
		return this;
	}
	//-----------------------------------------------------------------------------------------------------------------//
	
	private Main test_dialog_view_item ( String user,
	                                     String password
	                                   )
	{
		if ( user.equals ( "3" ) && password.equals ( "2016" ) )
		{
			new DialogTableViewItem ( Main.this,
			                          null,
			                          null,
			                          null,
			                          null
			);
			this.m_manager.get_login ( )
			              .clear_fields ( );
		}
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private boolean user_authenticate_within_cpf ( String user )
	{
		if ( user.length ( ) >= 0xb )
		{
			if ( ! cpf_validate_code.is_valid ( user ) )
			{
				new message_box ( this, R.string.ids_warning, R.string.invalid_cpf, message_box.IDWARNING );
				return false;
			}
		}
		return true;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private boolean check_user_if_root_then_start_lane_square_config_window ( String user,
	                                                                          String password
	                                                                        )
	{
		if ( user.equals ( Utils.ROOT ) && password.equals ( Utils.ROOT ) )
		{
			this.startActivity ( new Intent ( this, LaneSquareConfig.class ) );
			return true;
		}
		return false;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private boolean verify_if_exists_lanes ( )
	{
		if ( this.m_manager.get_controller_lane_handle ( )
		                   .count ( ) == 0 )
		{
			this.check_user_if_root_then_start_lane_square_config_window ( Utils.ROOT, Utils.ROOT );
			
			if ( this.m_manager.get_controller_lane_handle ( )
			                   .count ( ) == 0 )
			{
				return false;
			}
		}
		return true;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	public final Manager get_manager_handle ( )
	{
		/* manager handle */
		return this.m_manager;
	}
}