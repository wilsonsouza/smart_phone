/*

   Sistema de Gestão de Pistas

   (C) 2016 TecSidel

   Created by wilson.souza (WR DevInfo)

   Description:
 */
package br.com.tk.mcs.activitys_operations;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.zxing.Result;

import br.com.tk.mcs.R;
import br.com.tk.mcs.adapter.lane_manager_base;
import br.com.tk.mcs.components.button_impl;
import br.com.tk.mcs.components.checkbox_impl;
import br.com.tk.mcs.components.download_bitmap_by_asyncronized_task;
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.frame_window_impl;
import br.com.tk.mcs.components.imageview_impl;
import br.com.tk.mcs.components.scannerbarcode_impl;
import br.com.tk.mcs.components.space_impl;
import br.com.tk.mcs.components.tablewidget_impl;
import br.com.tk.mcs.components.vertical_scrollview_impl;
import br.com.tk.mcs.dialogs_ui.dialog_base_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.dispatch.tableview_item;
import br.com.tk.mcs.generic.CreateTicket;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.manager.PrinterManagerController;
import br.com.tk.mcs.remote.response.GetLongStatusResponse;
import br.com.tk.mcs.remote.response.RemotePaymentResponse;
import br.com.tk.mcs.tools.Utils;

/**
 * Created by wilsonsouza on 24/11/2017.
 */

public class FrameTableViewItem extends frame_window_impl implements TextWatcher

{
   public final static String TAG = FrameTableViewItem.class.getName( );
   public final static String IDS_PARAMETERS = "current_item";
   private static int ID_BOARD_LENGTH = 0x7;
   private Point m_point = config_display_metrics.VehicleImage;
   private imageview_impl m_bmp = null;
   private edittext_impl m_datetime = null;
   private edittext_impl m_via = null;
   private edittext_impl m_board = null;
   private checkbox_impl m_down = null;
   private Lane m_lane_handle = null;
   private PrinterManagerController m_printer = null;
   private lane_manager_base.recipe m_item = null;
   private GetLongStatusResponse.DetailArray m_details = null;
   private display_message m_dialog = null;
   private String m_imagelink = null;
   private checkbox_impl m_comment = null;
   private String m_transaction_id = "";
   private edittext_impl m_cupom_id = null;
   private edittext_impl m_category = null;
   private imageview_impl m_do_scanner = null;
   private button_impl m_money;
   private button_impl m_tag;
   private button_impl m_cancel;
   
   //---------------------------------------------------------------------------------------------//
   @Override
   protected void onCreate( Bundle handle )
   {
      tableview_item param = null;
      super.onCreate( handle );
      
      this.get_toolbar_handle( )
          .get_caption_handle( )
          .get_handle( )
          .setText( R.string.dialog_tag );
      this.get_icon_handle( )
          .set_image( R.drawable.ic_paytag );
      //
      Bundle b = this.getIntent( )
                     .getExtras( );
      try
      {
         param = (tableview_item) b.getParcelable( IDS_PARAMETERS );
         
         this.m_item = new lane_manager_base.recipe( param.m_date_time,
                                                     param.m_lane_name,
                                                     param.m_tag_name,
                                                     param.m_photo_link,
                                                     param.m_payment_type,
                                                     param.m_payment_means,
                                                     param.m_vechicle_class,
                                                     param.m_lane,
                                                     param.m_details_array );
         this.m_lane_handle = this.m_item.m_lane;
         //this.m_printer = param.m_manager.get_printer_manager_handle( );
         this.m_details = this.m_item.m_detail_array;
      }
      catch (Exception e)
      {
         e.printStackTrace( );
      }
      this.prepare_child_controls( );
   }
   
   //---------------------------------------------------------------------------------------------//
   protected FrameTableViewItem prepare_child_controls( )
   {
      linear_horizontal cupom = new linear_horizontal( this );
      {
         this.m_board = new edittext_impl( this,
                                           R.string.ids_placa,
                                           ID_BOARD_LENGTH );
         this.m_board.set_enabled( true )
                     .set_border( true );
         
         this.m_bmp = new imageview_impl( this,
                                          this.m_point,
                                          R.drawable.box_error,
                                          true );
         this.m_bmp.set_padding( config_display_metrics.EditRect );
         this.m_bmp.setGravity( Gravity.CENTER );
         
         this.m_down = new checkbox_impl( this,
                                          R.string.ids_bbaixa,
                                          false,
                                          false,
                                          -1
         );
         this.m_datetime = new edittext_impl( this,
                                              R.string.ids_datetime,
                                              26,
                                              false,
                                              -1,
                                              -1,
                                              true
         );
         this.m_via = new edittext_impl( this,
                                         R.string.ids_via,
                                         0x12,
                                         false,
                                         -1,
                                         -1,
                                         true
         );
         this.m_comment = new checkbox_impl( this,
                                             R.string.IDS_VIOLATION,
                                             false,
                                             false,
                                             checkbox_impl.DEFAULT
         );
         this.m_cupom_id = new edittext_impl( this,
                                              R.string.ids_do_recibo,
                                              0xc,
                                              true,
                                              -1,
                                              -1,
                                              true
         );
         {
            this.m_cupom_id.invalidate( true );
            Point point = config_display_metrics.ButtonImageIcon;
            this.m_do_scanner = new imageview_impl( this,
                                                    point,
                                                    R.drawable.ic_tbl_photo_image,
                                                    false
            ).set_padding( new Rect( 8,
                                     8,
                                     0,
                                     0
            ) );
            
            cupom.addView( this.m_cupom_id );
            cupom.addView( this.m_do_scanner );
            cupom.Params.gravity = Gravity.RIGHT;
         }
         this.m_category = new edittext_impl( this,
                                              R.string.ids_category,
                                              0x2,
                                              false,
                                              -1,
                                              0x50,
                                              true
         );
         this.m_comment.set_margins( new Rect( 0,
                                               0,
                                               8,
                                               0
         ) );
         this.m_down.set_margins( new Rect( 0,
                                            0,
                                            8,
                                            0
         ) );
         
         this.m_datetime.get_handle( )
                        .setInputType( InputType.TYPE_DATETIME_VARIATION_NORMAL );
         this.m_via.get_handle( )
                   .setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
         this.m_board.get_handle( )
                     .setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
         this.m_cupom_id.get_handle( )
                        .setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
         this.m_category.get_handle( )
                        .setInputType( InputType.TYPE_CLASS_NUMBER );
         
         this.m_cupom_id.get_handle( )
                        .addTextChangedListener( this );
         this.m_cupom_id.get_handle( )
                        .setCursorVisible( true );
         this.m_category.get_handle( )
                        .addTextChangedListener( this );
         this.m_category.get_handle( )
                        .setCursorVisible( true );
         this.m_board.get_handle( )
                     .addTextChangedListener( this );
         this.m_board.get_handle( )
                     .setCursorVisible( true );
      }
      
      linear_vertical left = new linear_vertical( this );
      {
         this.m_datetime.Params.gravity = Gravity.CENTER_HORIZONTAL;
         this.m_via.Params.gravity = Gravity.CENTER_HORIZONTAL;
         this.m_board.Params.gravity = Gravity.CENTER_HORIZONTAL;
         //
         left.addView( this.m_bmp,
                       this.m_bmp.Params );
         left.addView( this.m_datetime,
                       this.m_datetime.Params );
         left.addView( this.m_via,
                       this.m_via.Params );
         left.addView( cupom );
         left.addView( this.m_board,
                       this.m_board.Params );
         
         linear_vertical down = new linear_vertical( this );
         down.Params = down.build( down.to_sp( config_display_metrics.minimum_control_width ),
                                   down.Params.WRAP_CONTENT );
         down.Params.gravity = Gravity.CENTER_HORIZONTAL;
         down.addView( this.m_category,
                       this.m_category.Params );
         down.addView( this.m_down,
                       this.m_down.Params );
         down.addView( this.m_comment,
                       this.m_comment.Params );
         left.addView( down,
                       down.Params );
         left.set_margins( config_display_metrics.EditRect );
         
         linear_horizontal buttons = new linear_horizontal( this );
         this.m_money = new button_impl( this,
                                         R.string.ids_pgto_money,
                                         false );
         this.m_tag = new button_impl( this,
                                       R.string.ids_pgto_tag_placa,
                                       false );
         this.m_cancel = new button_impl( this,
                                          R.string.button_cancel,
                                          true );
         //
         buttons.addView( this.m_money,
                          this.m_money.Params );
         buttons.addView( this.m_tag,
                          this.m_tag.Params );
         buttons.addView( this.m_cancel,
                          this.m_cancel.Params );
         //
         this.m_money.get_handle( )
                     .setOnClickListener( this );
         this.m_tag.get_handle( )
                   .setOnClickListener( this );
         this.m_cancel.get_handle( )
                      .setOnClickListener( this );
         //
         buttons.Params.gravity = Gravity.CENTER_HORIZONTAL;
         left.addView( left.alloc_space( 0,
                                         config_display_metrics.space_between_controls ) );
         left.addView( buttons,
                       buttons.Params );
         
         if (this.m_item != null)
         {
            boolean is_payed = false;
            
            this.m_datetime.set_data( this.m_item.m_date_time.trim( ) );
            this.m_via.set_data( this.m_item.m_lane_name.trim( ) );
            this.m_imagelink = this.m_item.m_photo_link.trim( );
            this.m_transaction_id = this.m_item.m_photo_link.trim( );
            //
            is_payed = !this.m_item.m_payment_means.isEmpty( ) && this.m_item.m_payment_means.equals( "NO" );
            //
            this.m_down.set_checked( is_payed );
            this.m_comment.set_checked( this.m_item.m_payment_means.equals( "NO" ) );
         }
         
         if (this.m_details != null)
         {
            boolean bdown = (!this.m_details.PaymentMeans.isEmpty( ) && !this.m_details.PaymentMeans.equals( "NO" ));
            boolean bcomment = this.m_details.PaymentMeans.equals( "NO" );
            
            this.m_datetime.set_data( this.m_details.Moment );
            this.m_via.set_data( this.m_item.m_lane_name );
            this.m_cupom_id.set_data( this.m_details.TransactionId );
            this.m_imagelink = this.m_details.TransactionId;
            this.m_transaction_id = this.m_details.TransactionId;
            this.m_down.set_checked( bdown );
            this.m_comment.set_checked( bcomment );
            //this.m_board.Data.set_text ( this.m_details.PanNumber );
            this.m_category.set_data( this.m_details.VehicleClass );
         }
      }
      
      this.m_do_scanner.setOnClickListener( this );
      int height = left.Params.get_display( ).heightPixels + config_display_metrics.VehicleImage.y;
      left.Params = left.build( left.Params.MATCH_PARENT,
                                height );
      /* set main window components */
      vertical_scrollview_impl base = new vertical_scrollview_impl( this,
                                                                    false );
      base.get_contaneir_handle( )
          .addView( left,
                    left.Params );
      this.get_layout_handle( )
          .addView( base,
                    base.Params );
      
      new download_bitmap_by_asyncronized_task( this.m_bmp,
                                                this.m_point ).execute( Utils.mount_image_link( this,
                                                                                                this.m_imagelink
      ) );
      this.m_dialog = new display_message( this,
                                           -1,
                                           -1 );
      this.m_cupom_id.get_handle( )
                     .requestLayout( );
      
      return this;
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
   public void beforeTextChanged( CharSequence s,
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
   public void onTextChanged( CharSequence s,
                              int start,
                              int before,
                              int count
   )
   {
      this.m_tag.set_enabled( this.m_cupom_id.get_data( )
                                             .length( ) == 0xc &&
                                 this.m_board.get_data( )
                                             .length( ) == ID_BOARD_LENGTH );
      this.m_money.set_enabled( this.m_cupom_id.get_data( )
                                               .length( ) == 0xc &&
                                   this.m_category.get_data( )
                                                  .length( ) == 1 );
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
   public void afterTextChanged( Editable s )
   {
   
   }
   
   /**
    * Called when a view has been clicked.
    *
    * @param v The view that was clicked.
    */
   @Override
   public void onClick( View v )
   {
      final int nId = v.getId( );
      
      switch (nId)
      {
         case R.string.ids_pgto_money:
            new LauncherPayMoney( ).start( );
            break;
         case R.string.ids_pgto_tag_placa:
            new LauncherPayTag( ).start( );
            break;
         case R.string.button_cancel:
            this.finish( );
            break;
         case R.drawable.ic_tbl_photo_image:
            new LauncherScanner( this,
                                 R.string.dlg_do_scanner,
                                 dialog_base_impl.LM_HORIZONTAL
            );
            break;
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   class LauncherScanner extends dialog_base_impl implements scannerbarcode_impl.on_scannerbarcode_listener
   {
      private scannerbarcode_impl m_scanner = new scannerbarcode_impl( getContext( ),
                                                                       config_display_metrics.ScannerPoint,
                                                                       false
      );
      
      public LauncherScanner( Context context,
                              int resource_caption_id,
                              int layout_mode
      )
      {
         super( context,
                resource_caption_id,
                layout_mode
         );
         try
         {
            this.create_and_prepare( );
         }
         catch (Exception e)
         {
            e.printStackTrace( );
         }
      }
      
      //-----------------------------------------------------------------------------------------------------------------//
      @Override
      public dialog_base_impl create_and_prepare( ) throws
                                                    Exception
      {
         linear_horizontal body = new linear_horizontal( getContext( ) );
         {
            this.m_scanner.set_on_scannerbarcode_listener( this );
            this.m_scanner.setPadding( 8,
                                       8,
                                       0,
                                       0
            );
            body.setGravity( Gravity.CENTER );
            body.addView( this.m_scanner,
                          this.m_scanner.Params
            );
            body.addView( new space_impl( getContext( ),
                                          0x32,
                                          8,
                                          false
            ) );
         }
         this.set_icon( R.drawable.ic_paytag );
         this.get_view( )
             .addView( body );
         this.set_buttons( new int[]{R.string.button_cancel} );
         return super.create_and_prepare( );
      }
      
      //-----------------------------------------------------------------------------------------------------------------//
      @Override
      protected void onStart( )
      {
         super.onStart( );
         this.m_scanner.start( );
      }
      
      //-----------------------------------------------------------------------------------------------------------------//
      @Override
      protected void onStop( )
      {
         this.m_scanner.stop( );
         super.onStop( );
      }
      
      //-----------------------------------------------------------------------------------------------------------------//
      @Override
      public void onClick( android.view.View pView )
      {
         final int nId = pView.getId( );
         
         switch (nId)
         {
            case R.string.ids_take_picture:
               //m_scanner.onClick(m_scanner.Data);
               break;
            case R.string.button_cancel:
               this.dismiss( );
               break;
         }
      }
      
      //-----------------------------------------------------------------------------------------------------------------//
      @Override
      public void on_has_bufferdata( Result result )
      {
         m_cupom_id.get_handle( )
                   .setText( result.getText( ) );
         m_board.get_handle( )
                .requestFocus( );
         this.dismiss( );
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   class LauncherPayTag extends Thread
   {
      public LauncherPayTag( )
      {
         super( );
         
         setName( getClass( ).getName( ) );
         /* display message */
         m_dialog.set_caption( R.string.manager_rdtag_title );
         m_dialog.setMessage( R.string.manager_assign_process );
         show_dialog( m_dialog );
      }
      
      @Override
      public void run( )
      {
         try
         {
            String szBoard = m_board.get_data( )
                                    .toUpperCase( )
                                    .trim( );
            String szCupom = m_cupom_id.get_data( )
                                       .toUpperCase( )
                                       .trim( );
            /* make payment */
            RemotePaymentResponse success = m_lane_handle.get_operations( )
                                                         .payment_rd_tag( szCupom,
                                                                          szBoard
                                                         );
            /* close dialog */
            close_dialog( m_dialog );
            /* check process result */
            final String fmt = RemotePaymentResponse.format( get_toolbar_handle( ).getContext( ),
                                                             success,
                                                             szBoard
            );
            
            if (success != RemotePaymentResponse.ResponseOK)
            {
               show_message_box( R.string.manager_rdtag_title,
                                 fmt,
                                 RemotePaymentResponse.format( success )
               );
            }
            else
            {
               show_message_box( R.string.manager_rdtag_title,
                                 fmt,
                                 RemotePaymentResponse.format( success )
               );
               finish( );
            }
         }
         catch (Exception e)
         {
            close_dialog( m_dialog );
            show_message_box( R.string.ids_warning,
                              e.getMessage( ),
                              message_box.IDERROR
            );
         }
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   class LauncherPayMoney extends Thread
   {
      public LauncherPayMoney( )
      {
         super( );
         
         setName( getClass( ).getName( ) );
         /* display message */
         m_dialog.set_caption( R.string.manager_rdmoney_title );
         m_dialog.setMessage( R.string.manager_assign_process );
         show_dialog( m_dialog );
      }
      
      @Override
      public void run( )
      {
         try
         {
            final String szCupom = m_cupom_id.get_data( )
                                             .toUpperCase( )
                                             .trim( );
            final String szCategory = m_category.get_data( )
                                                .trim( );
            final Context context = get_toolbar_handle( ).getContext( );
            final RemotePaymentResponse success = m_lane_handle.get_operations( )
                                                               .payment_rd_cash( szCupom,
                                                                                 szCategory
                                                               );
            //
            Log.e( TAG,
                   String.format( "TransactionId %s",
                                  m_transaction_id
                   )
            );

            /* hide dialog */
            close_dialog( m_dialog );
            final String fmt = RemotePaymentResponse.format( context,
                                                             success,
                                                             m_transaction_id
            );

            /* check response */
            if (success != RemotePaymentResponse.ResponseOK)
            {
               show_message_box( R.string.manager_rdmoney_title,
                                 fmt,
                                 RemotePaymentResponse.format( success )
               );
            }
            else
            {
               final boolean is_online = m_printer.is_online( );
               
               show_message_box( R.string.manager_rdmoney_title,
                                 fmt,
                                 RemotePaymentResponse.format( success )
               );
               finish( );
               /* check if printer is online */
               if (is_online)
               {
                  runOnUiThread( new Runnable( )
                  {
                     final String sel_cupom_id = m_lane_handle.get_operations( )
                                                              .get_cupom_id( );
                     final StringBuffer ticket_pointer = CreateTicket.builder( sel_cupom_id );
                     
                     @Override
                     public void run( )
                     {
							   /* send ticket to printer */
                        m_printer.send_cupom_to_printer( ticket_pointer );
                     }
                  } );
               }
            }
         }
         catch (Exception e)
         {
            close_dialog( m_dialog );
            show_message_box( R.string.ids_warning,
                              e.getMessage( ),
                              message_box.IDERROR
            );
         }
      }
   }
}
