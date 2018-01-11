/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

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
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.scannerbarcode_impl;
import br.com.tk.mcs.components.space_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.generic.CreateTicket;
import br.com.tk.mcs.generic.company_setup;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.manager.PrinterManagerController;
import br.com.tk.mcs.remote.response.RemotePaymentResponse;

/**
 * Created by wilsonsouza on 23/01/17.
 */

public class DialogPaymentMoney extends dialog_base_impl implements TextWatcher,
                                                                    scannerbarcode_impl.on_scannerbarcode_listener
{
	public final static  String TAG      = DialogPaymentMoney.class.getName ( );
	private static final int    RECIPT   = 0xC;
	private static final int    CATEGORY = 0x2;
	
	protected edittext_impl            m_pRecipt   = null;
	protected edittext_impl            m_pCategory = null;
	protected Lane                     m_pLane     = null;
	protected PrinterManagerController m_pPrinter  = null;
	protected display_message          m_dialog    = null;
	protected scannerbarcode_impl      m_barcode   = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogPaymentMoney ( Context pWnd,
	                            final Lane pLane,
	                            final PrinterManagerController pPrinter
	                          )
	{
		super ( pWnd,
		        R.string.manager_rdmoney_title,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_pPrinter = pPrinter;
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
	public dialog_base_impl create_and_prepare ( ) throws
	                                               Exception
	{
		linear_vertical body = new linear_vertical ( getContext ( ) );
		{
			this.m_barcode = new scannerbarcode_impl ( get_handle ( ),
			                                           config_display_metrics.ScannerPoint,
			                                           false
			);
			this.m_barcode.set_on_scannerbarcode_listener ( this );
			this.m_barcode.setPadding ( 8,
			                            8,
			                            8,
			                            8
			                          );
			body.setGravity ( Gravity.CENTER );
		}
		linear_horizontal edit = new linear_horizontal ( getContext ( ) );
		{
			this.m_pRecipt = new edittext_impl ( getContext ( ),
			                                     R.string.ids_do_recibo,
			                                     RECIPT,
			                                     true,
			                                     - 1,
			                                     - 1,
			                                     true
			);
			this.m_pCategory = new edittext_impl ( getContext ( ),
			                                       R.string.ids_category,
			                                       CATEGORY,
			                                       true,
			                                       - 1,
			                                       0x50,
			                                       true
			);
			this.m_pRecipt.get_handle ( )
			              .setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
			this.m_pCategory.get_handle ( )
			                .setInputType ( InputType.TYPE_CLASS_NUMBER );
			
			this.m_pCategory.get_handle ( )
			                .addTextChangedListener ( this );
			this.m_pCategory.get_handle ( )
			                .setCursorVisible ( true );
			this.m_pRecipt.get_handle ( )
			              .addTextChangedListener ( this );
			this.m_pRecipt.get_handle ( )
			              .setCursorVisible ( true );
			
			edit.addView ( this.m_pRecipt );
			edit.addView ( this.m_pCategory );
			edit.set_margins ( new Rect ( 8,
			                              8,
			                              8,
			                              8
			) );
			edit.setGravity ( Gravity.CENTER );
			
			edit.Params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
			
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
		
		this.m_dialog = new display_message ( getContext ( ),
		                                      R.string.manager_rdmoney_title,
		                                      R.string.manager_assign_process
		);
		this.set_buttons ( new int[]{ R.string.manager_button_confirm,
		                              R.string.manager_button_cancel
		} );
		this.set_icon ( R.drawable.ic_paymoney );
		this.get_view ( )
		    .addView ( body,
		               body.Params
		             );
		super.create_and_prepare ( )
		     .set_keyboard ( false,
		                     this.m_pRecipt.get_handle ( )
		                   );
		return this;
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
		                    m_pRecipt.get_data ( )
		                             .length ( ) == RECIPT && m_pCategory.get_data ( )
		                                                                 .length ( ) == CATEGORY
		                  );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void afterTextChanged ( Editable s )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onStart ( )
	{
		super.onStart ( );
		this.set_keyboard ( false,
		                    this.m_pRecipt.get_handle ( )
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
	public void on_has_bufferdata ( Result pResult )
	{
		this.m_pRecipt.get_handle ( )
		              .setText ( pResult.getText ( ) );
		this.m_pCategory.get_handle ( )
		                .requestFocus ( );
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
				show_dialog ( m_dialog );
				final Operations p = m_pLane.get_operations ( );
				final String szRecipt = m_pRecipt.get_data ( )
				                                 .toUpperCase ( )
				                                 .trim ( );
				final String szCat = m_pCategory.get_data ( )
				                                .toUpperCase ( )
				                                .trim ( );
				final RemotePaymentResponse pOK = p.payment_rd_cash ( szRecipt,
				                                                      szCat
				                                                    );
				//
				close_dialog ( m_dialog );
				//
				if ( pOK != RemotePaymentResponse.ResponseOK )
				{
					final String szMessage = RemotePaymentResponse.format ( getContext ( ),
					                                                        pOK,
					                                                        szRecipt
					                                                      );
					final String szCaption = getContext ( ).getString ( R.string.manager_rdmoney_title );
					
					//Log.e(getClass().getName(), String.format("Result %s, %s", pOK, szMessage));
					show_message_box ( szCaption,
					                   szMessage,
					                   RemotePaymentResponse.format ( pOK )
					                 );
				}
				else
				{
					final String szMsg = RemotePaymentResponse.format ( getContext ( ),
					                                                    pOK,
					                                                    szRecipt
					                                                  );
					final String szCaption = getContext ( ).getString ( R.string.manager_rdmoney_title );
					
					show_message_box ( szCaption,
					                   szMsg,
					                   RemotePaymentResponse.format ( pOK )
					                 );
					dismiss ( );
					boolean bOnline = m_pPrinter.is_online ( );
					/* check if printer is online */
					if ( bOnline )
					{
						get_handle ( ).runOnUiThread ( new Runnable ( )
						{
							protected String m_szBuffer = p.get_cupom_id ( );
							protected StringBuffer m_pBuffer = CreateTicket.builder ( m_szBuffer );
							
							@Override
							public void run ( )
							{
                        /* send ticket to printer */
								m_pPrinter.send_cupom_to_printer ( m_pBuffer );
							}
						} );
					}
				}
			}
			catch ( final Exception e )
			{
				dismiss ( );
				show_message_box ( getContext ( ).getString ( R.string.ids_caption_error ),
				                   e.getMessage ( ),
				                   message_box.IDERROR
				                 );
				//Log.e(getClass().getName(), e.getMessage());
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------//
}
