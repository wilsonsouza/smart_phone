/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

   Plates to testes
   | 0618      | 00100    | 1000000004 | DBT0000 | 01           |        | BL       | PRE     | 05     | 01     |           |            |        |
   | 0618      | 00100    | 1000000005 | EJX5458 | 04           |        | BL       | POS     | 00     | 02     |           |            |        |
   | 0618      | 00100    | 1000000006 | CYB6750 | 04           |        | BL       | POS     | 00     | 02     |           |            |        |
   | 0618      | 00100    | 1000000007 | EJX5459 | 04           |        | BL       | POS     | 00     | 02     |           |            |        |
   | 0618      | 00100    | 1000000008 | EJX5456 | 04           |        | BL       | POS     | 00     | 02     |           |            |        |

   View live log file
   tail -f /via/trazas/TRAZA.LIS
   Description:
 */
package br.com.tk.mcs.dialogs_operations;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.Locale;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.download_bitmap_by_asyncronized_task;
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.imageview_impl;
import br.com.tk.mcs.components.radiobutton_impl;
import br.com.tk.mcs.components.spinner_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.drivers.bluetooth_printer_controller;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.remote.response.RemotePaymentPermittedResponse;
import br.com.tk.mcs.remote.response.RemotePaymentResponse;
import br.com.tk.mcs.tools.Utils;

/**
 * Created by wilsonsouza on 31/01/17.
 */

public class DialogReleaseVehicle extends dialog_base_impl implements dialog_base_impl.IRunnable,
                                                                      TextWatcher,
                                                                      AdapterView.OnItemSelectedListener
{
	public final static String                       TAG              = DialogReleaseVehicle.class.getName ( );
	protected static    int                          DOCKET           = 0xc;
	protected static    int                          BORD             = 0x7;
	protected           Point                        m_point          = config_display_metrics.VehicleImage;
	protected           Lane                         m_pLane          = null;
	protected           radiobutton_impl             m_isento         = null;
	protected           radiobutton_impl             m_tag            = null;
	protected           edittext_impl                m_number_board_0 = null;
	protected           edittext_impl                m_field_tag      = null;
	protected           imageview_impl               m_bitmap         = null;
	protected           spinner_impl                 m_group          = null;
	protected           String                       m_imagelink      = null;
	protected           display_message              m_dialog         = null;
	protected           bluetooth_printer_controller m_printer        = null;
	protected           ArrayAdapter<String>         m_pIsentoList    = null;
	protected           String[]                     m_pList          = new String[]{ "Concessionária",
	                                                                                  "PRF",
	                                                                                  "Bombeiro",
	                                                                                  "Forças Armadas",
	                                                                                  "Órgão Público",
	                                                                                  "Polícia Militar",
	                                                                                  "Polícia Cívil",
	                                                                                  "Ibama",
	                                                                                  "Isentos Autarquias",
	                                                                                  "CNO",
	                                                                                  "Tecsidel"
	};
	protected           String                       m_szIsentoCode   = "";
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogReleaseVehicle ( Context pWnd,
	                              final Lane pLane,
	                              final String szImageLink,
	                              bluetooth_printer_controller pCon
	                            )
	{
		super ( pWnd,
		        R.string.dialog_release_vehicle,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_pLane = pLane;
			this.m_imagelink = szImageLink;
			this.m_printer = pCon;
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
		linear_horizontal body = new linear_horizontal ( getContext ( ) );
		{
			body.set_margins ( new Rect ( 8,
			                              8,
			                              8,
			                              8
			) );
		}
		linear_vertical edit = new linear_vertical ( getContext ( ) );
		{
			Rect padding = new Rect ( 8,
			                          8,
			                          8,
			                          8
			);
			this.m_isento = new radiobutton_impl ( getContext ( ),
			                                       R.string.ids_isento,
			                                       true,
			                                       false,
			                                       - 1
			);
			this.m_isento.Params.gravity = Gravity.LEFT;
			this.m_group = new spinner_impl ( getContext ( ),
			                                  R.string.ids_group,
			                                  - 1,
			                                  - 1,
			                                  false,
			                                  true
			);
			this.m_group.Params.gravity = Gravity.RIGHT;
			this.m_number_board_0 = new edittext_impl ( getContext ( ),
			                                            R.string.ids_number_id,
			                                            0x7,
			                                            false,
			                                            - 1,
			                                            - 1,
			                                            true
			);
			this.m_number_board_0.Params.gravity = Gravity.RIGHT;
			this.m_tag = new radiobutton_impl ( getContext ( ),
			                                    R.string.ids_tag,
			                                    true,
			                                    false,
			                                    - 1
			);
			this.m_tag.Params.gravity = Gravity.LEFT;
			this.m_field_tag = new edittext_impl ( getContext ( ),
			                                       R.string.ids_tag_placa,
			                                       0xc,
			                                       false,
			                                       - 1,
			                                       - 1,
			                                       true
			);
			this.m_field_tag.Params.gravity = Gravity.RIGHT;
			this.m_bitmap = new imageview_impl ( getContext ( ),
			                                     this.m_point,
			                                     R.drawable.box_error,
			                                     true
			).set_padding ( padding );
			this.m_bitmap.setGravity ( Gravity.CENTER );
			
			edit.addView ( this.m_isento );
			edit.addView ( this.m_group );
			edit.addView ( this.m_number_board_0 );
			edit.addView ( this.m_tag );
			edit.addView ( this.m_field_tag );
			body.addView ( edit );
			
			{
				this.m_isento.get_handle ( )
				             .setOnClickListener ( this );
				this.m_tag.get_handle ( )
				          .setOnClickListener ( this );
			}
			{
				this.m_field_tag.get_handle ( )
				                .setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
				this.m_number_board_0.get_handle ( )
				                     .setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
			}
			
			this.m_field_tag.get_handle ( )
			                .addTextChangedListener ( this );
			this.m_number_board_0.get_handle ( )
			                     .addTextChangedListener ( this );
		}
		linear_vertical img = new linear_vertical ( getContext ( ) );
		{
			img.set_margins ( new Rect ( 8,
			                             8,
			                             8,
			                             8
			) );
			img.addView ( this.m_bitmap );
			body.addView ( img );
		}
		
		this.m_pIsentoList = new ArrayAdapter<> ( getContext ( ),
		                                          android.R.layout.simple_list_item_checked
		);
		this.m_pIsentoList.addAll ( this.m_pList );
		this.m_group.get_handle ( )
		            .setAdapter ( this.m_pIsentoList );
		this.m_group.get_handle ( )
		            .setOnItemSelectedListener ( this );
		
		try
		{
			this.m_imagelink = this.m_pLane.get_operations ( )
			                               .is_remote_payment_permitted ( )
			                               .get_transaction ( )
			                               .get_transaction_id ( );
			//Log.e(getClass().getName(), "VehiclePhoto " + this.m_imagelink);
		}
		catch ( Exception e )
		{
			//Log.e(getClass().getName(), e.getMessage());
			e.printStackTrace ( );
		}
		
		this.set_icon ( R.drawable.ic_release_vehicle );
		this.get_view ( )
		    .addView ( body );
		this.set_buttons ( new int[]{ R.string.manager_button_confirm,
		                              R.string.manager_button_cancel
		} );
		new download_bitmap_by_asyncronized_task ( this.m_bitmap,
		                                           this.m_point
		).execute ( Utils.mount_image_link ( getContext ( ),
		                                     this.m_imagelink
		                                   ) );
		this.m_dialog = new display_message ( getContext ( ),
		                                      - 1,
		                                      - 1
		);
		this.set_keyboard ( false );
		return super.create_and_prepare ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View view )
	{
		int which = view.getId ( );
		
		switch ( which )
		{
			case R.string.manager_button_confirm:
				this.run ( which );
				break;
			case R.string.manager_button_cancel:
				this.dismiss ( );
				break;
			default:
			{
				boolean bInseto = ( view == m_isento.get_handle ( ) && m_isento.is_checked ( ) );
				boolean bTag    = ( view == m_tag.get_handle ( ) && m_tag.is_checked ( ) );
				{
					m_isento.set_checked ( bInseto );
					m_tag.set_checked ( bTag );
					m_group.setEnabled ( bInseto );
					m_number_board_0.set_enabled ( bInseto );
					m_number_board_0.get_handle ( )
					                .setCursorVisible ( bInseto );
					
					m_field_tag.set_enabled ( bTag );
					m_field_tag.get_handle ( )
					           .setCursorVisible ( bTag );
					
					if ( bInseto )
					{
						this.m_number_board_0.get_handle ( )
						                     .requestFocus ( );
					}
					if ( bTag )
					{
						this.m_field_tag.get_handle ( )
						                .requestFocus ( );
					}
				}
				break;
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void run ( int which )
	{
		String caption_error = getContext ( ).getString ( R.string.ids_caption_error );
		
		try
		{
			if ( m_tag.is_checked ( ) )
			{
				new LauncherPayTag ( ).start ( );
			}
			else if ( m_isento.is_checked ( ) )
			{
				new LauncherIsento ( ).start ( );
			}
		}
		catch ( Exception e )
		{
			this.close_dialog ( this.m_dialog );
			show_message_box ( caption_error,
			                   e.getMessage ( ),
			                   message_box.IDERROR
			                 );
			Log.e ( getClass ( ).getName ( ),
			        e.getMessage ( )
			      );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onStart ( )
	{
		this.m_tag.get_handle ( )
		          .performClick ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	protected void onStop ( )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	private void DoItFree ( )
	{
		show_message_box ( R.string.ids_warning,
		                   R.string.ids_op_isento,
		                   message_box.IDWARNING
		                 );
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
		if ( m_isento.is_checked ( ) )
		{
			final int nOk = this.m_number_board_0.get_data ( )
			                                     .trim ( )
			                                     .length ( );
			set_control_by_id ( R.string.manager_button_confirm,
			                    nOk == BORD && ! m_szIsentoCode.isEmpty ( )
			                  );
		}
		else if ( m_tag.is_checked ( ) )
		{
			final int nOk = this.m_field_tag.get_data ( )
			                                .trim ( )
			                                .length ( );
			set_control_by_id ( R.string.manager_button_confirm,
			                    nOk == DOCKET || nOk == BORD
			                  );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void afterTextChanged ( Editable s )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onItemSelected ( AdapterView<?> parent,
	                             View view,
	                             int position,
	                             long id
	                           )
	{
		m_szIsentoCode = String.format ( Locale.FRANCE,
		                                 "%02d",
		                                 position + 1
		                               );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onNothingSelected ( AdapterView<?> parent )
	{
		m_szIsentoCode = "";
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class LauncherPayTag extends Thread
	{
		public LauncherPayTag ( )
		{
			setName ( getClass ( ).getName ( ) );
			/* display message */
			m_dialog.set_caption ( R.string.manager_rdtag_title );
			m_dialog.setMessage ( R.string.manager_assign_process );
			show_dialog ( m_dialog );
		}
		
		@Override
		public void run ( )
		{
			try
			{
				RemotePaymentResponse pSuccess  = RemotePaymentResponse.ResponseERROR;
				String                szCaption = getContext ( ).getString ( R.string.manager_rdtag_title );
				String szTag = m_field_tag.get_data ( )
				                          .toUpperCase ( )
				                          .trim ( );
				Operations operations = m_pLane.get_operations ( );
				//
				RemotePaymentPermittedResponse rpp = operations.is_remote_payment_permitted ( );
				pSuccess = operations.make_remote_payment ( rpp,
				                                            szTag,
				                                            null
				                                          );
		      /* close dialog */
				close_dialog ( m_dialog );
				Log.e ( this.getClass ( )
				            .getName ( ),
				        "payment with tag " + pSuccess.toString ( )
				      );
            /* check process result */
				if ( pSuccess != RemotePaymentResponse.ResponseOK )
				{
					String fmt = RemotePaymentResponse.format ( getContext ( ),
					                                            pSuccess,
					                                            szTag
					                                          );
					
					//Log.e(this.getName(), String.format("Result %s, %s", pSuccess, fmt));
					show_message_box ( szCaption,
					                   fmt,
					                   RemotePaymentResponse.format ( pSuccess )
					                 );
				}
				else
				{
					dismiss ( );
				}
			}
			catch ( Exception e )
			{
				close_dialog ( m_dialog );
				show_message_box ( getContext ( ).getString ( R.string.ids_warning ),
				                   e.getMessage ( ),
				                   message_box.IDERROR
				                 );
				//Log.e(getClass().getName(), e.getMessage());
				e.printStackTrace ( );
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class LauncherIsento extends Thread
	{
		public LauncherIsento ( )
		{
			setName ( getClass ( ).getName ( ) );
         /* display message */
			m_dialog.set_caption ( R.string.manager_rdmoney_title );
			m_dialog.setMessage ( R.string.manager_assign_process );
			show_dialog ( m_dialog );
		}
		
		@Override
		public void run ( )
		{
			try
			{
				RemotePaymentResponse pSuccess   = RemotePaymentResponse.ResponseERROR;
				String                szCaption  = getContext ( ).getString ( R.string.manager_rdmoney_title );
				Operations            operations = m_pLane.get_operations ( );
				String szBoard = m_number_board_0.get_data ( )
				                                 .toUpperCase ( )
				                                 .trim ( );

            /* process money */
				RemotePaymentPermittedResponse rpp = operations.is_remote_payment_permitted ( );
				pSuccess = operations.make_remote_payment ( rpp,
				                                            szBoard,
				                                            m_szIsentoCode
				                                          );

            /* hide dialog */
				close_dialog ( m_dialog );

            /* check response */
				if ( pSuccess != RemotePaymentResponse.ResponseOK )
				{
					String fmt = RemotePaymentResponse.format ( getContext ( ),
					                                            pSuccess,
					                                            szBoard
					                                          );
					
					//Log.e(this.getName(), String.format("Result %s, %s", pSuccess, fmt));
					show_message_box ( szCaption,
					                   fmt,
					                   RemotePaymentResponse.format ( pSuccess )
					                 );
				}
				else
				{
               /* process ok then send ticket to printer */
					//StringBuffer pData = CreateTicket.builder(m_pLane);
					//m_printer.send_cupom_to_printer(pData);
					dismiss ( );
				}
			}
			catch ( Exception e )
			{
				close_dialog ( m_dialog );
				show_message_box ( getContext ( ).getString ( R.string.ids_warning ),
				                   e.getMessage ( ),
				                   message_box.IDERROR
				                 );
				//Log.e(getClass().getName(), e.getMessage());
				e.printStackTrace ( );
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------//
}
