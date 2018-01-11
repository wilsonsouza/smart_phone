/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.button_impl;
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.frame_window_impl;
import br.com.tk.mcs.components.space_impl;
import br.com.tk.mcs.components.spinner_impl;
import br.com.tk.mcs.database.nosql_persistence_controller_lane;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.generic.field_text_set_max_length;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.tools.ipaddress_validate;

public class TrackRegister extends frame_window_impl implements View.OnClickListener,
                                                                TextWatcher
{
	public final static  String                            TAG          = TrackRegister.class.getName ( );
	public final static  String                            PARAM_IP     = "current_ip";
	private final static int                               SIZE         = 0xdc;
	private              boolean                           m_update     = false;
	private              nosql_persistence_controller_lane m_controller = null;
	private              spinner_impl                      m_arrow      = null;
	private              spinner_impl                      m_lane       = null;
	private              edittext_impl                     m_ip_address = null;
	private              String                            m_old_ip     = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		
		this.get_toolbar_handle ( )
		    .get_caption_handle ( )
		    .set_caption ( R.string.register_title );
		this.get_layout_handle ( )
		    .setOrientation ( LinearLayout.VERTICAL );
		
		this.get_layout_handle ( )
		    .set_padding ( config_display_metrics.Padding );
		
		this.m_controller = new nosql_persistence_controller_lane ( this );
		
		m_arrow = new spinner_impl ( this, R.string.track_register_arrow );
		{
			this.m_arrow.Params.gravity = Gravity.CENTER_HORIZONTAL;
			this.m_arrow.setEnabled ( true );
			this.m_arrow.set_border ( true );
			this.m_arrow.Params.gravity = Gravity.RIGHT;
		}
		m_lane = new spinner_impl ( this, R.string.track_register_lane );
		{
			this.m_lane.Params.gravity = Gravity.CENTER_HORIZONTAL;
			this.m_lane.set_border ( true );
			this.m_lane.setEnabled ( true );
			this.m_lane.Params.gravity = Gravity.RIGHT;
		}
		m_ip_address = new edittext_impl ( this, R.string.track_register_ip, View.NO_ID );
		{
			this.m_ip_address.get_handle ( )
			                 .setText ( R.string.track_register_ip );
			this.m_ip_address.Params.gravity = Gravity.RIGHT;
			field_text_set_max_length.set_length ( this.m_ip_address.get_handle ( ), 0x10, 0x10 );
			
			this.m_ip_address.get_handle_base ( )
			                 .set_data_width ( View.NO_ID );
			this.m_ip_address.get_handle ( )
			                 .setCursorVisible ( true );
			this.m_ip_address.get_handle ( )
			                 .setInputType ( android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_CLASS_TEXT );
			this.m_ip_address.get_handle ( )
			                 .addTextChangedListener ( this );
			this.m_ip_address.set_border ( true );
			this.m_ip_address.set_enabled ( true );
		}
		
		linear_vertical body = new linear_vertical ( this );
		{
			final int space_size = config_display_metrics.space_between_controls * 2;
			
			body.Params.gravity = ( Gravity.CENTER_HORIZONTAL );
			body.set_padding ( config_display_metrics.Padding );
			body.addView ( new space_impl ( this,
			                                space_size,
			                                space_size,
			                                false
			) );
			
			body.addView ( this.m_arrow,
			               this.m_arrow.Params
			             );
			body.addView ( new space_impl ( this,
			                                8,
			                                8,
			                                false
			) );
			body.addView ( this.m_lane,
			               this.m_lane.Params
			             );
			body.addView ( new space_impl ( this,
			                                8,
			                                8,
			                                false
			) );
			body.addView ( this.m_ip_address,
			               this.m_ip_address.Params
			             );
			body.addView ( new space_impl ( this,
			                                8,
			                                8,
			                                false
			) );
		}
		button_impl button_ok = new button_impl ( this,
		                                          R.string.button_ok,
		                                          false
		);
		{
			button_ok.get_handle ( )
			         .setOnClickListener ( this );
			button_ok.Params.gravity = Gravity.CENTER_HORIZONTAL;
			
			body.addView ( button_ok, button_ok.Params );
			this.get_layout_handle ( )
			    .addView ( body, body.Params );
		}
		
		//m_sentido = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<String> spinner_arrow_list = new ArrayAdapter<String> ( this,
		                                                                     android.R.layout.simple_spinner_item,
		                                                                     this.get_arrow_list ( )
		);
		ArrayAdapter<String> spinner_lane_list = new ArrayAdapter<String> ( this,
		                                                                    android.R.layout.simple_spinner_item,
		                                                                    this.get_lane_list ( 30,
		                                                                                         "Pista %02d"
		                                                                                       )
		);
		{
			spinner_arrow_list.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
			spinner_lane_list.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
			
			this.m_arrow.get_handle ( )
			            .setAdapter ( spinner_arrow_list );
			this.m_lane.get_handle ( )
			           .setAdapter ( spinner_lane_list );
		}
		//
		Bundle bundle = getIntent ( ).getExtras ( );
		
		if ( bundle != null )
		{
			String         ip_param = bundle.getString ( "IP" );
			final String[] record   = m_controller.get_detals_by_ip ( ip_param.trim ( ) );
			
			this.m_old_ip = ip_param;
			
			this.m_lane.get_handle ( )
			           .setSelection ( spinner_lane_list.getPosition ( record[ nosql_persistence_controller_lane.ID_NAME ] ) );
			this.m_arrow.get_handle ( )
			            .setSelection (
				            spinner_arrow_list.getPosition ( record[ nosql_persistence_controller_lane.ID_ARROW ] ) );
			this.m_ip_address.get_handle ( )
			                 .setText ( record[ nosql_persistence_controller_lane.ID_IP ] );
			this.m_update = ! this.m_update;
		}
		
		this.get_layout_handle ( )
		    .invalidate ( true );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( View view )
	{
		final String ip_address = this.m_ip_address.get_data ( );
		final String dir = this.m_arrow.get_handle ( )
		                               .getSelectedItem ( )
		                               .toString ( )
		                               .trim ( );
		final String lane = this.m_lane.get_handle ( )
		                               .getSelectedItem ( )
		                               .toString ( )
		                               .trim ( );
		
		if ( ! ip_address.isEmpty ( ) && ! dir.isEmpty ( ) && ! lane.isEmpty ( ) )
		{
			if ( ! ipaddress_validate.get_instance ( )
			                         .is_valid ( ip_address.trim ( ) ) )
			{
				String szFmt = String.format ( Locale.FRANCE,
				                               getString ( R.string.alert_ip_message ),
				                               ip_address.trim ( )
				                             );
				new message_box ( this,
				                  getString ( R.string.ids_warning ),
				                  szFmt,
				                  message_box.IDWARNING
				);
				return;
			}
			
			if ( ! m_update )
			{
				if ( ! m_controller.insert ( lane.trim ( ),
				                             ip_address.trim ( ),
				                             dir.trim ( )
				                           ) )
				{
					new message_box ( this,
					                  R.string.ids_warning,
					                  R.string.register_msg_error,
					                  message_box.IDWARNING
					);
				}
				else
				{
					Toast.makeText ( this,
					                 getString ( R.string.register_msg_add ),
					                 Toast.LENGTH_SHORT
					               )
					     .show ( );
					this.finish ( );
				}
			}
			else
			{
				if ( ! m_controller.update ( this.m_old_ip.trim ( ),
				                             ip_address.trim ( ),
				                             lane.trim ( )
				                           ) )
				{
					new message_box ( this,
					                  R.string.ids_warning,
					                  R.string.register_msg_error,
					                  message_box.IDWARNING
					);
				}
				else
				{
					new message_box ( this,
					                  R.string.ids_warning,
					                  R.string.register_msg_up,
					                  message_box.IDWARNING
					);
					this.finish ( );
				}
			}
		}
		else
		{
			new message_box ( this,
			                  R.string.ids_warning,
			                  R.string.register_msg_error,
			                  message_box.IDWARNING
			);
		}
	}
	
	/**
	 * This method is called to notify you that, within <code>s</code>,
	 * the <code>count</code> characters beginning at <code>start</code>
	 * are about to be replaced by new text with length <code>after</code>.
	 * It is an error to attempt to make changes to <code>s</code> from
	 * this callback.
	 *
	 * @param s
	 * @param start
	 * @param count
	 * @param after
	 */
	@Override
	public void beforeTextChanged ( CharSequence s,
	                                int start,
	                                int count,
	                                int after
	                              )
	{
	
	}
	
	/**
	 * This method is called to notify you that, within <code>s</code>,
	 * the <code>count</code> characters beginning at <code>start</code>
	 * have just replaced old text that had length <code>before</code>.
	 * It is an error to attempt to make changes to <code>s</code> from
	 * this callback.
	 *
	 * @param s
	 * @param start
	 * @param before
	 * @param count
	 */
	@Override
	public void onTextChanged ( CharSequence s,
	                            int start,
	                            int before,
	                            int count
	                          )
	{
		final boolean success = ipaddress_validate.get_instance ( )
		                                          .is_valid ( this.m_ip_address.get_data ( ) );
		final View view = this.get_layout_handle ( )
		                      .findViewById ( R.string.button_ok );
		{
			if ( view != null && success )
			{
				( ( Button ) view ).setEnabled ( success );
			}
		}
	}
	
	/**
	 * This method is called to notify you that, somewhere within
	 * <code>s</code>, the text has been changed.
	 * It is legitimate to make further changes to <code>s</code> from
	 * this callback, but be careful not to get yourself into an infinite
	 * loop, because any changes you make will cause this method to be
	 * called again recursively.
	 * (You are not told where the change took place because other
	 * afterTextChanged() methods may already have made other changes
	 * and invalidated the offsets.  But if you need to know here,
	 * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
	 * to mark your place and then look up from here where the span
	 * ended up.
	 *
	 * @param s
	 */
	@Override
	public void afterTextChanged ( Editable s )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private ArrayList<String> get_arrow_list ( )
	{
		final String[] items = new String[]{ "P",
		                                     "S"
		};
		final ArrayList<String> items_out = new ArrayList<> ( );
		
		for ( String data : items )
		{
			items_out.add ( ( String ) data );
		}
		return items_out;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private ArrayList<String> get_lane_list ( int capacity,
	                                          String fmt_name
	                                        )
	{
		final ArrayList<String> items_out = new ArrayList<> ( );
		
		for ( int i = 1; i <= capacity; i++ )
		{
			items_out.add ( String.format ( Locale.FRANCE,
			                                fmt_name,
			                                i
			                              ) );
		}
		return items_out;
	}
}
