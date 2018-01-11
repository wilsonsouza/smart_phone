/*

   Sistema de Gestão de Pistas

   (C) 2016 Tecsidel

   Updated by wilson.souza (WR DevInfo)

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

import com.google.zxing.Result;

import java.util.Vector;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.checkbox_impl;
import br.com.tk.mcs.components.download_bitmap_by_asyncronized_task;
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.imageview_impl;
import br.com.tk.mcs.components.scannerbarcode_impl;
import br.com.tk.mcs.components.space_impl;
import br.com.tk.mcs.components.tablewidget_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.generic.CreateTicket;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.manager.PrinterManagerController;
import br.com.tk.mcs.remote.response.RemotePaymentResponse;
import br.com.tk.mcs.tools.Utils;

/**
 * Created by wilsonsouza on 30/01/17.
 */

public class DialogTableViewItem extends dialog_base_impl implements TextWatcher
{
	public final static String                        TAG               = DialogTableViewItem.class.getName ( );
	protected static    int                           ID_BOARD          = 0x7;
	protected static    int                           ID_TAG            = 0xC;
	protected           Point                         m_point           = config_display_metrics.VehicleImage;
	protected           imageview_impl                m_bmp             = null;
	protected           edittext_impl                 m_datetime        = null;
	protected           edittext_impl                 m_via             = null;
	protected           edittext_impl                 m_tag             = null;
	protected           edittext_impl                 m_board           = null;
	protected           checkbox_impl                 m_unknow          = null;
	protected           checkbox_impl                 m_bdown           = null;
	protected           Lane                          m_pLane           = null;
	protected           PrinterManagerController      m_printer         = null;
	protected           tablewidget_impl.tableitem    m_item            = null;
	protected           tablewidget_impl.detailsarray m_details         = null;
	protected           display_message               m_dialog          = null;
	protected           String                        m_imagelink       = null;
	protected           checkbox_impl                 m_comment         = null;
	protected           String                        m_szTransactionId = "";
	protected           edittext_impl                 m_cupom_id        = null;
	protected           edittext_impl                 m_category        = null;
	protected           imageview_impl                m_do_scanner      = null;
	protected           linear_horizontal             m_body            = null;
	
	//-----------------------------------------------------------------------------------------------------------------//
	public DialogTableViewItem ( Context pWnd,
	                             final tablewidget_impl.tableitem pItem,
	                             final Lane pLane,
	                             final PrinterManagerController pCon,
	                             final tablewidget_impl.detailsarray pDetails
	                           )
	{
		super ( pWnd,
		        R.string.dialog_tag,
		        LM_HORIZONTAL
		      );
		try
		{
			this.m_pLane = pLane;
			this.m_printer = pCon;
			this.m_item = pItem;
			this.m_details = pDetails;
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
		linear_horizontal cupom = new linear_horizontal ( getContext ( ) );
		linear_horizontal body  = new linear_horizontal ( getContext ( ) );
		{
			Rect padding = config_display_metrics.EditRect;
			
			this.m_board = new edittext_impl ( getContext ( ),
			                                   R.string.ids_placa,
			                                   ID_BOARD,
			                                   true,
			                                   - 1,
			                                   - 1,
			                                   true
			);
			this.m_board.Params.gravity = Gravity.RIGHT;
			this.m_bmp = new imageview_impl ( getContext ( ),
			                                  this.m_point,
			                                  R.drawable.box_error,
			                                  true
			).set_padding ( padding );
			
			this.m_bmp.setGravity ( Gravity.CENTER );
			this.m_bdown = new checkbox_impl ( getContext ( ),
			                                   R.string.ids_bbaixa,
			                                   false,
			                                   false,
			                                   - 1
			);
			this.m_bdown.Params.gravity = Gravity.RIGHT;
			this.m_datetime = new edittext_impl ( getContext ( ),
			                                      R.string.ids_datetime,
			                                      26,
			                                      false,
			                                      - 1,
			                                      - 1,
			                                      true
			);
			this.m_datetime.Params.gravity = Gravity.RIGHT;
			this.m_via = new edittext_impl ( getContext ( ),
			                                 R.string.ids_via,
			                                 0x12,
			                                 false,
			                                 - 1,
			                                 - 1,
			                                 true
			);
			this.m_via.Params.gravity = Gravity.RIGHT;
			this.m_unknow = new checkbox_impl ( getContext ( ),
			                                    "None",
			                                    false,
			                                    false,
			                                    - 1
			);
			this.m_unknow.Params.gravity = Gravity.RIGHT;
			this.m_comment = new checkbox_impl ( getContext ( ),
			                                     R.string.IDS_VIOLATION,
			                                     false,
			                                     false,
			                                     checkbox_impl.DEFAULT
			);
			this.m_comment.Params.gravity = Gravity.RIGHT;
			//
			this.m_cupom_id = new edittext_impl ( getContext ( ),
			                                      R.string.ids_do_recibo,
			                                      0xc,
			                                      true,
			                                      - 1,
			                                      - 1,
			                                      true
			);
			{
				this.m_cupom_id.invalidate ( true );
				Point point = new Point ( this.m_cupom_id.getHeight ( ),
				                          this.m_cupom_id.getHeight ( )
				);
				this.m_do_scanner = new imageview_impl ( getContext ( ),
				                                         point,
				                                         R.drawable.ic_tbl_photo_image,
				                                         false);
				
				this.m_do_scanner.setPadding ( 8,
				                               8,
				                               0,
				                               0
				                             );
				cupom.addView ( this.m_cupom_id );
				cupom.addView ( this.m_do_scanner );
				cupom.Params.gravity = Gravity.RIGHT;
			}
			this.m_category = new edittext_impl ( getContext ( ),
			                                      R.string.ids_category,
			                                      0x2,
			                                      false,
			                                      - 1,
			                                      0x50,
			                                      true
			);
			this.m_category.Params.gravity = Gravity.RIGHT;
			
			this.m_comment.set_margins ( new Rect ( 0,
			                                        0,
			                                        8,
			                                        0
			) );
			this.m_bdown.set_margins ( new Rect ( 0,
			                                      0,
			                                      8,
			                                      0
			) );
			
			body.set_margins ( config_display_metrics.EditRect );
			
			this.m_datetime.get_handle ( )
			               .setInputType ( InputType.TYPE_DATETIME_VARIATION_NORMAL );
			this.m_via.get_handle ( )
			          .setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
			this.m_board.get_handle ( )
			            .setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
			this.m_cupom_id.get_handle ( )
			               .setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
			this.m_category.get_handle ( )
			               .setInputType ( InputType.TYPE_CLASS_NUMBER );
			
			this.m_cupom_id.get_handle ( )
			               .addTextChangedListener ( this );
			this.m_cupom_id.get_handle ( )
			               .setCursorVisible ( true );
			this.m_category.get_handle ( )
			               .addTextChangedListener ( this );
			this.m_category.get_handle ( )
			               .setCursorVisible ( true );
			this.m_board.get_handle ( )
			            .addTextChangedListener ( this );
			this.m_board.get_handle ( )
			            .setCursorVisible ( true );
		}
		linear_vertical left = new linear_vertical ( getContext ( ) );
		{
			left.addView ( this.m_datetime );
			left.addView ( this.m_via );
			left.addView ( cupom );
			left.addView ( this.m_board );
			left.addView ( this.m_category );
			left.addView ( this.m_bdown );
			left.addView ( this.m_comment );
			left.set_margins ( config_display_metrics.EditRect );
			
			if ( this.m_item != null )
			{
				Vector<tablewidget_impl.fielddata> data     = this.m_item.get_items ( );
				boolean                            is_payed = false;
				
				if ( data.size ( ) >= 4 )
				{
					this.m_datetime.get_handle ( )
					               .setText ( data.get ( 0 )
					                              .get_name ( )
					                              .trim ( ) );
					this.m_via.get_handle ( )
					          .setText ( data.get ( 1 )
					                         .get_name ( )
					                         .trim ( ) );
					this.m_imagelink = data.get ( 3 )
					                       .get_name ( )
					                       .trim ( );
					this.m_szTransactionId = data.get ( 3 )
					                             .get_name ( )
					                             .trim ( );
					//
					is_payed = ! data.get ( 4 )
					                 .get_name ( )
					                 .trim ( )
					                 .isEmpty ( ) && ! data.get ( 4 )
					                                       .get_name ( )
					                                       .trim ( )
					                                       .equals ( "NO" );
					//
					this.m_bdown.set_checked ( is_payed );
					this.m_comment.set_checked ( data.get ( 4 )
					                                 .get_name ( )
					                                 .trim ( )
					                                 .equals ( "NO" ) );
				}
			}
			
			if ( this.m_details != null )
			{
				boolean bdown = ( ! this.m_details.get_payment_means ( )
				                                  .isEmpty ( ) && ! this.m_details.get_payment_means ( )
				                                                                  .equals ( "NO" ) );
				boolean bcomment = this.m_details.get_payment_means ( )
				                                 .equals ( "NO" );
				
				this.m_datetime.get_handle ( )
				               .setText ( this.m_details.get_moment ( ) );
				this.m_via.get_handle ( )
				          .setText ( this.m_details.get_alias_lane_name ( ) );
				this.m_imagelink = this.m_details.get_transaction_id ( );
				this.m_szTransactionId = this.m_details.get_transaction_id ( );
				this.m_bdown.set_checked ( bdown );
				this.m_comment.set_checked ( bcomment );
				//this.m_board.Data.set_text ( this.m_details.PanNumber );
				this.m_category.get_handle ( )
				               .setText ( this.m_details.get_vehicle_class ( ) );
			}
		}
		
		this.m_do_scanner.setOnClickListener ( this );
		this.set_buttons ( new int[]{ R.string.ids_pgto_money,
		                              R.string.ids_pgto_tag_placa,
		                              R.string.button_cancel
		} );
		this.set_icon ( R.drawable.ic_paytag );
		/* change local to global class variable */
		this.m_body = body;
		
		this.m_body.addView ( left,
		                      left.Params
		                    );
		this.m_body.addView ( this.m_bmp,
		                      this.m_bmp.Params
		                    );
		this.get_view ( )
		    .addView ( this.m_body,
		               this.m_body.Params
		             );
		
		new download_bitmap_by_asyncronized_task ( this.m_bmp,
		                                           this.m_point
		).execute ( Utils.mount_image_link ( getContext ( ),
		                                     this.m_imagelink
		                                   ) );
		this.m_dialog = new display_message ( getContext ( ),
		                                      - 1,
		                                      - 1
		);
		this.m_cupom_id.get_handle ( )
		               .requestLayout ( );
		this.set_keyboard ( false );
		return super.create_and_prepare ( );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void onClick ( android.view.View v ) // DialogInterface dialog, int which)
	{
		final int nId = v.getId ( );
		
		switch ( nId )
		{
			case R.string.ids_pgto_money:
				new LauncherPayMoney ( ).start ( );
				break;
			case R.string.ids_pgto_tag_placa:
				new LauncherPayTag ( ).start ( );
				break;
			case R.string.button_cancel:
				this.dismiss ( );
				break;
			case R.drawable.ic_tbl_photo_image:
				this.DoScanner ( );
				break;
		}
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
		boolean bId = this.m_cupom_id.get_data ( )
		                             .trim ( )
		                             .isEmpty ( );
		boolean bPlate = this.m_board.get_data ( )
		                             .trim ( )
		                             .isEmpty ( );
		boolean bCat = this.m_category.get_data ( )
		                              .trim ( )
		                              .isEmpty ( );
		
		set_control_by_id ( R.string.ids_pgto_money,
		                    ! bId && ! bCat
		                  );
		set_control_by_id ( R.string.ids_pgto_tag_placa,
		                    ! bId && ! bPlate
		                  );
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	@Override
	public void afterTextChanged ( Editable s )
	{
	
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	protected void DoScanner ( )
	{
		new LauncherScanner ( getContext ( ),
		                      R.string.dlg_do_scanner,
		                      dialog_base_impl.LM_HORIZONTAL
		);
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class LauncherScanner extends dialog_base_impl implements scannerbarcode_impl.on_scannerbarcode_listener
	{
		protected scannerbarcode_impl m_scanner = new scannerbarcode_impl ( getContext ( ),
		                                                                    config_display_metrics.ScannerPoint,
		                                                                    false
		);
		
		public LauncherScanner ( Context pWnd,
		                         int nID,
		                         int nLayoutMode
		                       )
		{
			super ( pWnd,
			        nID,
			        nLayoutMode
			      );
			try
			{
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
				this.m_scanner.set_on_scannerbarcode_listener ( this );
				this.m_scanner.setPadding ( 8,
				                            8,
				                            0,
				                            0
				                          );
				body.setGravity ( Gravity.CENTER );
				body.addView ( this.m_scanner,
				               this.m_scanner.Params
				             );
				body.addView ( new space_impl ( getContext ( ),
				                                0x32,
				                                8,
				                                false
				) );
			}
			this.set_icon ( R.drawable.ic_paytag );
			this.get_view ( )
			    .addView ( body );
			this.set_buttons ( new int[]{ R.string.button_cancel } );
			return super.create_and_prepare ( );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		protected void onStart ( )
		{
			super.onStart ( );
			this.m_scanner.start ( );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		protected void onStop ( )
		{
			this.m_scanner.stop ( );
			super.onStop ( );
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void onClick ( android.view.View pView )
		{
			final int nId = pView.getId ( );
			
			switch ( nId )
			{
				case R.string.ids_take_picture:
					//m_scanner.onClick(m_scanner.Data);
					break;
				case R.string.button_cancel:
					this.dismiss ( );
					break;
			}
		}
		
		//-----------------------------------------------------------------------------------------------------------------//
		@Override
		public void on_has_bufferdata ( Result result )
		{
			m_cupom_id.get_handle ( )
			          .setText ( result.getText ( ) );
			m_board.get_handle ( )
			       .requestFocus ( );
			this.dismiss ( );
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class LauncherPayTag extends Thread
	{
		public LauncherPayTag ( )
		{
			super ( );
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
				String szBoard = m_board.get_data ( )
				                        .toUpperCase ( )
				                        .trim ( );
				String szCupom = m_cupom_id.get_data ( )
				                           .toUpperCase ( )
				                           .trim ( );
				Operations operations = m_pLane.get_operations ( );
				/* make payment */
				pSuccess = operations.payment_rd_tag ( szCupom,
				                                       szBoard
				                                     );
			   /* close dialog */
				close_dialog ( m_dialog );
            /* check process result */
				final String fmt = RemotePaymentResponse.format ( getContext ( ),
				                                                  pSuccess,
				                                                  szBoard
				                                                );
				
				if ( pSuccess != RemotePaymentResponse.ResponseOK )
				{
					show_message_box ( szCaption,
					                   fmt,
					                   RemotePaymentResponse.format ( pSuccess )
					                 );
				}
				else
				{
					show_message_box ( szCaption,
					                   fmt,
					                   RemotePaymentResponse.format ( pSuccess )
					                 );
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
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------//
	class LauncherPayMoney extends Thread
	{
		public LauncherPayMoney ( )
		{
			super ( );
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
				RemotePaymentResponse pSuccess  = RemotePaymentResponse.ResponseERROR;
				final String          szCaption = getContext ( ).getString ( R.string.manager_rdmoney_title );
				final String szCupom = m_cupom_id.get_data ( )
				                                 .toUpperCase ( )
				                                 .trim ( );
				final String szCategory = m_category.get_data ( )
				                                    .trim ( );
				final Operations operations = m_pLane.get_operations ( );
				//
				Log.e ( this.getClass ( )
				            .getName ( ),
				        String.format ( "TransactionId %s",
				                        m_szTransactionId
				                      )
				      );
				pSuccess = operations.payment_rd_cash ( szCupom,
				                                        szCategory
				                                      );

            /* hide dialog */
				close_dialog ( m_dialog );
				final String fmt = RemotePaymentResponse.format ( getContext ( ),
				                                                  pSuccess,
				                                                  m_szTransactionId
				                                                );

            /* check response */
				if ( pSuccess != RemotePaymentResponse.ResponseOK )
				{
					show_message_box ( szCaption,
					                   fmt,
					                   RemotePaymentResponse.format ( pSuccess )
					                 );
				}
				else
				{
					boolean bOnline = m_printer.is_online ( );
					
					show_message_box ( szCaption,
					                   fmt,
					                   RemotePaymentResponse.format ( pSuccess )
					                 );
					dismiss ( );
               /* check if printer is online */
					if ( bOnline )
					{
						get_handle ( ).runOnUiThread ( new Runnable ( )
						{
							final String m_szData = operations.get_cupom_id ( );
							final StringBuffer m_pData = CreateTicket.builder ( this.m_szData );
							
							@Override
							public void run ( )
							{
                        /* send ticket to printer */
								m_printer.send_cupom_to_printer ( this.m_pData );
							}
						} );
					}
				}
			}
			catch ( Exception e )
			{
				close_dialog ( m_dialog );
				show_message_box ( getContext ( ).getString ( R.string.ids_warning ),
				                   e.getMessage ( ),
				                   message_box.IDERROR
				                 );
			}
		}
	}
}
