/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.google.zxing.Result;

import br.com.tk.mcs.R;
import br.com.tk.mcs.R.string;
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.scannerbarcode_impl;
import br.com.tk.mcs.components.space_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.remote.response.RemotePaymentResponse;

/**
 * Created by wilsonsouza on 05/12/16.
 */

public class DialogPaymentTag extends dialog_base_impl implements TextWatcher,
                                                                  scannerbarcode_impl.on_scannerbarcode_listener
{
	static final int DOCKET  = 0xc;
	static final int BORD    = 0x7;
	static final int SCANNER = 0xfff0;
	
	private edittext_impl       m_Docket  = null;
	private edittext_impl       m_Board   = null;
	private Lane                m_pLane   = null;
	private scannerbarcode_impl m_barcode = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogPaymentTag ( Context pWnd,
	                          final Lane pLane
	                        )
	{
		super ( pWnd,
		        string.ids_pay_caption,
		        LM_HORIZONTAL
		      );
		
		try
		{
			this.m_pLane = pLane;
			this.create_and_prepare ( );
		}
		catch ( Exception e )
		{
			new message_box ( pWnd,
			                  string.ids_warning,
			                  e,
			                  message_box.IDERROR
			);
			//Log.e(getClass().getName(), e.getMessage());
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public dialog_base_impl create_and_prepare ( ) throws
	                       Exception
	{
		linear_vertical body = new linear_vertical ( getContext ( ) );
		{
			this.m_barcode = new scannerbarcode_impl ( getContext ( ),
			                                           config_display_metrics.ScannerPoint,
			                                           false
			);
			this.m_barcode.set_on_scannerbarcode_listener ( this );
			this.m_barcode.setPadding ( 8,
			                            8,
			                            0,
			                            0
			                          );
			body.setGravity ( Gravity.CENTER );
		}
		linear_horizontal edit = new linear_horizontal ( getContext ( ) );
		{
			m_Docket = new edittext_impl ( getContext ( ),
			                               string.ids_do_recibo,
			                               DOCKET,
			                               true,
			                               - 1,
			                               - 1,
			                               true
			);
			m_Board = new edittext_impl ( getContext ( ),
			                              string.ids_tag_placa,
			                              BORD,
			                              true,
			                              - 1,
			                              150,
			                              true
			);
			m_Docket.get_handle ( )
			        .setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
			m_Board.get_handle ( )
			       .setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
			
			m_Docket.get_handle ( )
			        .addTextChangedListener ( this );
			m_Docket.get_handle ( )
			        .setCursorVisible ( true );
			m_Board.get_handle ( )
			       .addTextChangedListener ( this );
			m_Board.get_handle ( )
			       .setCursorVisible ( true );
			
			edit.addView ( m_Docket );
			edit.addView ( m_Board );
			edit.set_margins ( new Rect ( 8,
			                              8,
			                              8,
			                              8
			) );
			edit.setGravity ( Gravity.CENTER );
			
			if ( ! company_setup.is_scanner )
			{
				edit.setOrientation ( LinearLayout.VERTICAL );
			}
		}
		body.addView ( new space_impl ( this.getContext ( ),
		                                0,
		                                8,
		                                false
		) );
		
		if ( company_setup.is_scanner )
		{
			body.addView ( m_barcode,
			               m_barcode.Params
			             );
		}
		
		body.addView ( edit );
		this.set_icon ( R.drawable.ic_paytag );
		this.get_view ( )
		    .addView ( body );
		this.set_buttons ( new int[]{ string.manager_button_confirm,
		                              string.button_cancel
		} );
		super.create_and_prepare ( );
		super.set_keyboard ( false,
		                     this.m_Docket.get_handle ( )
		                   );
		return this;
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onStart ( )
	{
		super.onStart ( );
		this.set_keyboard ( false,
		                    this.m_Docket.get_handle ( )
		                  );
		this.m_barcode.start ( );
		this.m_barcode.get_handle ( )
		              .requestFocus ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onStop ( )
	{
		this.m_barcode.stop ( );
		super.onStop ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void beforeTextChanged ( CharSequence s,
	                                int start,
	                                int count,
	                                int after
	                              )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onTextChanged ( CharSequence s,
	                            int start,
	                            int before,
	                            int count
	                          )
	{
		set_control_by_id ( R.string.manager_button_confirm,
		                    this.m_Docket.get_data ( )
		                                 .length ( ) == DOCKET && this.m_Board.get_data ( )
		                                                                      .length ( ) == BORD
		                  );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void afterTextChanged ( Editable s )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void on_has_bufferdata ( final Result pResult )
	{
		m_Docket.get_handle ( )
		        .setText ( pResult.getText ( ) );
		m_Board.get_handle ( )
		       .requestFocus ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View v )
	{
		int which = v.getId ( );
		
		switch ( which )
		{
			case string.manager_button_confirm:
				new Launcher ( ).start ( );
				break;
			case string.button_cancel:
				this.dismiss ( );
				break;
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class Launcher extends Thread
	{
		protected Context               m_context      = getContext ( );
		protected RemotePaymentResponse m_success      = RemotePaymentResponse.ResponseERROR;
		protected String                m_captionerror = m_context.getString ( string.ids_caption_error );
		protected String                m_caption      = m_context.getString ( string.manager_rdtag_title );
		protected display_message       m_dialog       = new display_message ( m_context,
		                                                                       string.manager_rdtag_title,
		                                                                       string.manager_assign_process
		);
		protected String                m_board        = m_Board.get_data ( )
		                                                        .toUpperCase ( )
		                                                        .trim ( );
		protected String                m_docket       = m_Docket.get_data ( )
		                                                         .toUpperCase ( )
		                                                         .trim ( );
		
		public Launcher ( )
		{
			super ( );
			setName ( getClass ( ).getName ( ) );
		}
		
		@Override
		public void run ( )
		{
			try
			{
				Operations op = m_pLane.get_operations ( );
				show_dialog ( m_dialog );
				this.m_success = op.payment_rd_tag ( this.m_docket,
				                                     this.m_board
				                                   );
			}
			catch ( final Exception e )
			{
				show_message_box ( m_captionerror,
				                   e.getMessage ( ),
				                   message_box.IDERROR
				                 );
				//Log.e(getClass().getName(), e.getMessage());
				e.printStackTrace ( );
			}
			//
			close_dialog ( m_dialog );
			//
			if ( this.m_success != RemotePaymentResponse.ResponseOK )
			{
				String fmt = RemotePaymentResponse.format ( getContext ( ),
				                                            m_success,
				                                            m_board
				                                          );
				
				//Log.e(getClass().getName(), String.format("Result %s, %s", m_success, fmt));
				show_message_box ( m_caption,
				                   fmt,
				                   RemotePaymentResponse.format ( m_success )
				                 );
			}
			else
			{
				String fmt = RemotePaymentResponse.format ( getContext ( ),
				                                            m_success,
				                                            m_board
				                                          );
				show_message_box ( m_caption,
				                   fmt,
				                   RemotePaymentResponse.format ( m_success )
				                 );
				dismiss ( );
			}
		}
	}
}
