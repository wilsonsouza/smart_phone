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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import br.com.tk.mcs.R;
import br.com.tk.mcs.components.button_impl;
import br.com.tk.mcs.components.download_bitmap_by_asyncronized_task;
import br.com.tk.mcs.components.edittext_impl;
import br.com.tk.mcs.components.frame_window_impl;
import br.com.tk.mcs.components.imageview_impl;
import br.com.tk.mcs.components.radiobutton_impl;
import br.com.tk.mcs.components.spinner_impl;
import br.com.tk.mcs.components.vertical_scrollview_impl;
import br.com.tk.mcs.dialogs_ui.display_message;
import br.com.tk.mcs.dialogs_ui.message_box;
import br.com.tk.mcs.dispatch.vehicle_parameters;
import br.com.tk.mcs.drivers.bluetooth_printer_controller;
import br.com.tk.mcs.generic.config_display_metrics;
import br.com.tk.mcs.lane.Lane;
import br.com.tk.mcs.lane.Operations;
import br.com.tk.mcs.layouts.linear_horizontal;
import br.com.tk.mcs.layouts.linear_layout_impl;
import br.com.tk.mcs.layouts.linear_vertical;
import br.com.tk.mcs.login.Body;
import br.com.tk.mcs.manager.PrinterManagerController;
import br.com.tk.mcs.remote.response.RemotePaymentPermittedResponse;
import br.com.tk.mcs.remote.response.RemotePaymentResponse;
import br.com.tk.mcs.tools.Utils;

/**
 * Created by wilsonsouza on 24/11/2017.
 */

public class FrameReleaseVehicle extends frame_window_impl
   implements TextWatcher, AdapterView.OnItemSelectedListener, View.OnClickListener
{
   public final static String IDS_PARAMETERS = "vehicle_parameters";
   public final static String IDS_PRINTER = "printer_handler";
   public final static String TAG = FrameReleaseVehicle.class.getName( );
   private static int ID_DOCKET_LENGTH = 0xc;
   private static int ID_BORD_LENGTH = 0x7;
   private Point m_point = config_display_metrics.VehicleImage;
   private Lane m_lane_handle = null;
   private radiobutton_impl m_isento = null;
   private radiobutton_impl m_tag = null;
   private edittext_impl m_number_board_0 = null;
   private edittext_impl m_field_tag = null;
   private imageview_impl m_bitmap = null;
   private spinner_impl m_group = null;
   private String m_imagelink = null;
   private display_message m_dialog = null;
   private bluetooth_printer_controller m_printer = null;
   private ArrayAdapter<String> m_isento_of_list = null;
   private String[] m_list = new String[]{"Concessionária",
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
   private String m_isento_code = "";
   private button_impl m_ok;
   private button_impl m_cancel;
   private Context m_context = null;
   
   //---------------------------------------------------------------------------------------------//
   @Override
   protected void onCreate( Bundle handle )
   {
      super.onCreate( handle );
      vehicle_parameters vehicle = null;
      
      this.get_toolbar_handle( )
          .get_caption_handle( )
          .get_handle( )
          .setText( R.string.dialog_release_vehicle );
      this.get_icon_handle( )
          .set_image( R.drawable.ic_release_vehicle );
      this.m_context = this;
      vehicle = this.getIntent( )
                    .getExtras( )
                    .getParcelable( IDS_PARAMETERS );
      
      this.m_lane_handle = vehicle.m_current_lane;
      this.m_printer = (PrinterManagerController) this.getIntent( )
                                                      .getSerializableExtra( IDS_PRINTER );
      this.m_imagelink = vehicle.m_mcs_bitmap_link;
      this.prepare_child_controls( );
   }
   
   //---------------------------------------------------------------------------------------------//
   @Override
   protected FrameReleaseVehicle prepare_child_controls( )
   {
      linear_vertical body = new linear_vertical( this );
      {
         body.set_margins( config_display_metrics.Padding );
      }
      linear_vertical edit = new linear_vertical( this );
      {
         Rect padding = config_display_metrics.Padding;
         //
         edit.Params = edit.build( linear_layout_impl.MATCH,
                                   linear_layout_impl.WRAP );
         this.m_isento = new radiobutton_impl( this,
                                               R.string.ids_isento,
                                               true,
                                               false,
                                               -1
         );
         this.m_group = new spinner_impl( this,
                                          R.string.ids_group,
                                          -1,
                                          -1,
                                          false,
                                          true
         );
         this.m_number_board_0 = new edittext_impl( this,
                                                    R.string.ids_number_id,
                                                    0x7,
                                                    false,
                                                    -1,
                                                    -1,
                                                    true
         );
         this.m_tag = new radiobutton_impl( this,
                                            R.string.ids_tag,
                                            true,
                                            false,
                                            -1
         );
         this.m_field_tag = new edittext_impl( this,
                                               R.string.ids_tag_placa,
                                               0xc,
                                               false,
                                               -1,
                                               -1,
                                               true
         );
         this.m_bitmap = new imageview_impl( this,
                                             this.m_point,
                                             R.drawable.box_error,
                                             true
         ).set_padding( padding );
         
         this.m_bitmap.Params.gravity = Gravity.CENTER_HORIZONTAL;
         this.m_group.Params.gravity = Gravity.CENTER_HORIZONTAL;
         this.m_number_board_0.Params.gravity = Gravity.CENTER_HORIZONTAL;
         this.m_field_tag.Params.gravity = Gravity.CENTER_HORIZONTAL;
         
         edit.addView( this.m_isento,
                       this.m_isento.Params );
         edit.addView( this.m_group,
                       this.m_group.Params );
         edit.addView( this.m_number_board_0,
                       this.m_number_board_0.Params );
         edit.addView( this.m_tag,
                       this.m_tag.Params );
         edit.addView( this.m_field_tag,
                       this.m_field_tag.Params );
         
         body.addView( this.m_bitmap );
         body.addView( edit,
                       edit.Params );
         
         int height = body.Params.get_display( ).heightPixels + config_display_metrics.VehicleImage.y;
         body.Params = body.build( linear_vertical.LayoutParams.MATCH_PARENT,
                                   height );

			/* set main window components */
         vertical_scrollview_impl base = new vertical_scrollview_impl( this,
                                                                       false );
         base.get_contaneir_handle( )
             .addView( body,
                       body.Params );
         this.get_layout_handle( )
             .addView( base,
                       base.Params );
         
         linear_horizontal buttons = new linear_horizontal( this );
         
         this.m_ok = new button_impl( this,
                                      R.string.manager_button_confirm,
                                      false );
         this.m_cancel = new button_impl( this,
                                          R.string.button_cancel,
                                          true );
         this.m_ok.get_handle( )
                  .setOnClickListener( this );
         this.m_cancel.get_handle( )
                      .setOnClickListener( this );
         
         buttons.Params.gravity = Gravity.CENTER_HORIZONTAL;
         buttons.addView( this.m_ok );
         buttons.addView( this.m_cancel );
         
         body.addView( buttons.alloc_space( 0,
                                            config_display_metrics.small_context_size ) );
         body.addView( buttons,
                       buttons.Params );
         {
            this.m_isento.get_handle( )
                         .setOnClickListener( this );
            this.m_tag.get_handle( )
                      .setOnClickListener( this );
         }
         {
            this.m_field_tag.set_input_type( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
            this.m_number_board_0.set_input_type( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS );
         }
         
         this.m_tag.get_handle( )
                   .setOnClickListener( this );
         this.m_isento.get_handle( )
                      .setOnClickListener( this );
         this.m_number_board_0.set_enabled( false );
         this.m_field_tag.set_enabled( false );
         
         this.m_field_tag.get_handle( )
                         .addTextChangedListener( this );
         this.m_number_board_0.get_handle( )
                              .addTextChangedListener( this );
      }
      
      this.m_isento_of_list = new ArrayAdapter<>( this,
                                                  android.R.layout.simple_list_item_checked
      );
      this.m_isento_of_list.addAll( this.m_list );
      this.m_group.get_handle( )
                  .setAdapter( this.m_isento_of_list );
      this.m_group.get_handle( )
                  .setOnItemSelectedListener( this );
      
      try
      {
         this.m_imagelink = this.m_lane_handle.get_operations( )
                                              .is_remote_payment_permitted( )
                                              .get_transaction( )
                                              .get_transaction_id( );
         Log.e(getClass().getName(), "VehiclePhoto " + this.m_imagelink);
      }
      catch (Exception e)
      {
         //Log.e(getClass().getName(), e.getMessage());
         e.printStackTrace( );
      }
      
      new download_bitmap_by_asyncronized_task( this.m_bitmap,
                                                this.m_point
      ).execute( Utils.mount_image_link( this,
                                         this.m_imagelink
      ) );
      this.m_dialog = new display_message( this,
                                           -1,
                                           -1
      );
      
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
      if (this.m_isento.is_checked( ))
      {
         this.m_ok.set_enabled( this.m_isento_code.length( ) > 0 &&
                                   this.m_number_board_0.get_data( )
                                                        .length( ) == ID_BORD_LENGTH );
      }
      if (this.m_tag.is_checked( ))
      {
         this.m_ok.set_enabled( this.m_field_tag.get_data( )
                                                .length( ) == ID_BORD_LENGTH ||
                                   this.m_field_tag.get_data( )
                                                   .length( ) == ID_DOCKET_LENGTH );
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
   public void afterTextChanged( Editable s )
   {
   
   }
   
   /**
    * <p>Callback method to be invoked when an item in this view has been
    * selected. This callback is invoked only when the newly selected
    * position is different from the previously selected position or if
    * there was no selected item.</p>
    * <p>
    * Impelmenters can call getItemAtPosition(position) if they need to access the
    * data associated with the selected item.
    *
    * @param parent   The AdapterView where the selection happened
    * @param view     The view within the AdapterView that was clicked
    * @param position The position of the view in the adapter
    * @param id       The row id of the item that is selected
    */
   @Override
   public void onItemSelected( AdapterView<?> parent,
                               View view,
                               int position,
                               long id
   )
   {
      this.m_isento_code = (String) parent.getItemAtPosition( position );
   }
   
   /**
    * Callback method to be invoked when the selection disappears from this
    * view. The selection can disappear for instance when touch is activated
    * or when the adapter becomes empty.
    *
    * @param parent The AdapterView that now contains no selected item.
    */
   @Override
   public void onNothingSelected( AdapterView<?> parent )
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
      int id = v.getId( );
      
      switch (id)
      {
         case R.string.manager_button_confirm:
         {
            if (this.m_isento.is_checked( ))
            {
               new LauncherIsento( ).run( );
            }
            if (this.m_tag.is_checked( ))
            {
               new LauncherPayTag( ).run( );
            }
            break;
         }
         case R.string.button_cancel:
         {
            finish( );
            break;
         }
         default:
         {
            boolean inseto = this.m_isento.is_checked( ) && v == this.m_isento.get_handle( );
            boolean tag = this.m_tag.is_checked( ) && v == this.m_tag.get_handle( );
            
            this.m_isento.set_checked( inseto );
            this.m_tag.set_checked( tag );
            this.m_group.set_enabled( inseto );
            this.m_number_board_0.set_enabled( inseto );
            this.m_number_board_0.get_handle( )
                                 .setCursorVisible( inseto );
            
            this.m_field_tag.set_enabled( tag );
            this.m_field_tag.get_handle( )
                            .setCursorVisible( tag );
            
            if (inseto)
            {
               this.m_number_board_0.get_handle( )
                                    .requestFocus( );
            }
            if (tag)
            {
               this.m_field_tag.get_handle( )
                               .requestFocus( );
            }
            break;
         }
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   class LauncherPayTag extends Thread
   {
      public LauncherPayTag( )
      {
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
            RemotePaymentResponse pSuccess = RemotePaymentResponse.ResponseERROR;
            String szCaption = m_context.getString( R.string.manager_rdtag_title );
            String szTag = m_field_tag.get_data( )
                                      .toUpperCase( )
                                      .trim( );
            Operations operations = m_lane_handle.get_operations( );
            //
            RemotePaymentPermittedResponse rpp = operations.is_remote_payment_permitted( );
            pSuccess = operations.make_remote_payment( rpp,
                                                       szTag,
                                                       null );
            /* close dialog */
            close_dialog( m_dialog );
            Log.e( this.getClass( )
                       .getName( ),
                   "payment with tag " + pSuccess.toString( ) );
            /* check process result */
            if (pSuccess != RemotePaymentResponse.ResponseOK)
            {
               String fmt = RemotePaymentResponse.format( m_context,
                                                          pSuccess,
                                                          szTag );
               
               //Log.e(this.getName(), String.format("Result %s, %s", pSuccess, fmt));
               show_message_box( szCaption,
                                 fmt,
                                 RemotePaymentResponse.format( pSuccess ) );
            }
            else
            {
               finish( );
            }
         }
         catch (Exception e)
         {
            close_dialog( m_dialog );
            show_message_box( m_context.getString( R.string.ids_warning ),
                              e.getMessage( ),
                              message_box.IDERROR );
            //Log.e(getClass().getName(), e.getMessage());
            e.printStackTrace( );
         }
      }
   }
   
   //-----------------------------------------------------------------------------------------------------------------//
   class LauncherIsento extends Thread
   {
      public LauncherIsento( )
      {
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
            RemotePaymentResponse pSuccess = RemotePaymentResponse.ResponseERROR;
            String szCaption = m_context.getString( R.string.manager_rdmoney_title );
            Operations operations = m_lane_handle.get_operations( );
            String szBoard = m_number_board_0.get_data( )
                                             .toUpperCase( )
                                             .trim( );

            /* process money */
            RemotePaymentPermittedResponse rpp = operations.is_remote_payment_permitted( );
            pSuccess = operations.make_remote_payment( rpp,
                                                       szBoard,
                                                       m_isento_code );

            /* hide dialog */
            close_dialog( m_dialog );

            /* check response */
            if (pSuccess != RemotePaymentResponse.ResponseOK)
            {
               String fmt = RemotePaymentResponse.format( m_context,
                                                          pSuccess,
                                                          szBoard );
               
               //Log.e(this.getName(), String.format("Result %s, %s", pSuccess, fmt));
               show_message_box( szCaption,
                                 fmt,
                                 RemotePaymentResponse.format( pSuccess ) );
            }
            else
            {
               /* process ok then send ticket to printer */
               //StringBuffer pData = CreateTicket.Builder(m_pLane);
               //m_printer.SendCupomToPrinter(pData);
               finish( );
            }
         }
         catch (Exception e)
         {
            close_dialog( m_dialog );
            show_message_box( m_context.getString( R.string.ids_warning ),
                              e.getMessage( ),
                              message_box.IDERROR );
            //Log.e(getClass().getName(), e.getMessage());
            e.printStackTrace( );
         }
      }
   }
   
}
